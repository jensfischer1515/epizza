package epizza.order.checkout;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import epizza.order.delivery.DeliveryJob;
import epizza.order.status.DeliveryStatus;
import epizza.order.status.OrderStatus;
import epizza.order.status.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderEventPublisher orderEventPublisher;

    public Order create(Order order) throws OrderItemsEmptyException {
        if (order.getOrderItems().isEmpty()) {
            throw new OrderItemsEmptyException("order does not have any items");
        }

        order.setOrderedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.saveAndFlush(order);

        orderEventPublisher.sendOrderCreatedEvent(savedOrder);
        log.info("order created {}", savedOrder);
        return savedOrder;
    }

    public Order update(Order order) {

        return orderRepository.save(order);

    }

    public Optional<Order> getOrder(Long id) {

        return Optional.ofNullable(orderRepository.findOne(id));

    }

    public Page<Order> getAll(Pageable pageable) {

        return orderRepository.findByDeliveryBoyIsNull(pageable);

    }

    public Order assignDelivery(Order order, DeliveryJob deliveryJob) throws OrderAssignedException {

        if (order.getDeliveryBoy() != null) {
            throw new OrderAssignedException(String.format("Order '%d' is already assigned to '%s'", order.getId(), order.getDeliveryBoy()));
        }
        log.info("Assigning delivery job '{}' to order number {}", deliveryJob, order.getId());
        order.setDeliveryBoy(deliveryJob.getDeliveryBoy());
        order.setEstimatedTimeOfDelivery(deliveryJob.getEstimatedTimeOfDelivery());
        update(order);

        return order;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Order Items is empty.")
    public static class OrderItemsEmptyException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public OrderItemsEmptyException(String message) {
            super(message);
        }
    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Order already assigned.")
    public static class OrderAssignedException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public OrderAssignedException(String message) {
            super(message);
        }
    }

    public static class OrderStatusEmptyException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public OrderStatusEmptyException(String message) {
            super(message);
        }
    }

    public OrderStatus getOverallStatus(PaymentStatus paymentStatus, DeliveryStatus deliveryStatus) {

        OrderStatus orderStatus = null;

        if (paymentStatus == null || deliveryStatus == null){
            throw new OrderStatusEmptyException("status not null");
        }

        if (paymentStatus == PaymentStatus.FAILED && deliveryStatus == DeliveryStatus.FAILED){
            orderStatus = OrderStatus.FAILED;
        }else if (paymentStatus == PaymentStatus.FAILED && deliveryStatus == DeliveryStatus.DELIVERED){
            orderStatus = OrderStatus.THIRD_PARTY_ERROR;
        }

        return orderStatus;
    }

}
