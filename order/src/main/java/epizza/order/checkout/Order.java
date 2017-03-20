package epizza.order.checkout;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.javamoney.moneta.Money;
import org.springframework.hateoas.Identifiable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Entity
@Access(AccessType.FIELD)
@Table(name = "PIZZA_ORDER")
public class Order implements Identifiable<Long> {

    private static final Money DEFAULT_PRICE = Money.of(0.0, "EUR");

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    private LocalDateTime orderedAt;

    @ElementCollection
    @CollectionTable(name = "PIZZA_ORDER_ITEM")
    private List<OrderItem> orderItems = Lists.newArrayList();

    private String comment;

    @Basic(optional = false)
    private MonetaryAmount totalPrice = DEFAULT_PRICE;

    @Embedded
    private Address deliveryAddress;

    @Column(name = "ETBC")
    private LocalDateTime estimatedTimeOfBakingCompletion;


    @Column(name = "ETD")
    private LocalDateTime estimatedTimeOfDelivery;

    private String deliveryBoy;


    public List<OrderItem> getOrderItems() {
        return ImmutableList.copyOf(orderItems);
    }

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems.clear();
        this.totalPrice = DEFAULT_PRICE;
        orderItems.forEach(this::addOrderItem);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        this.totalPrice = this.totalPrice.add(orderItem.getPrice());
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MonetaryAmount getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(MonetaryAmount totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getEstimatedTimeOfBakingCompletion() {
        return estimatedTimeOfBakingCompletion;
    }

    public void setEstimatedTimeOfBakingCompletion(LocalDateTime estimatedTimeOfBakingCompletion) {
        this.estimatedTimeOfBakingCompletion = estimatedTimeOfBakingCompletion;
    }


    public String getDeliveryBoy() {
        return deliveryBoy;
    }

    public void setDeliveryBoy(String deliveryBoy) {
        this.deliveryBoy = deliveryBoy;
    }

    public LocalDateTime getEstimatedTimeOfDelivery() {
        return estimatedTimeOfDelivery;
    }

    public void setEstimatedTimeOfDelivery(LocalDateTime estimatedTimeOfDelivery) {
        this.estimatedTimeOfDelivery = estimatedTimeOfDelivery;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equal(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this) //
                .add("id", id) //
                .add("orderItems", orderItems) //
                .toString();
    }
}
