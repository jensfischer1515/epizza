package epizza.order.checkout;

import org.javamoney.moneta.Money;
import org.junit.Assert;
import org.junit.Test;

import epizza.order.catalog.Pizza;
import epizza.order.status.DeliveryStatus;
import epizza.order.status.OrderStatus;
import epizza.order.status.PaymentStatus;

import static org.assertj.core.api.BDDAssertions.then;

public class OrderTest {

    private Pizza salami = Pizza.builder()
            .name("Salami")
            .price(Money.of(9.9, "EUR"))
            .build();

    @Test
    public void should_calculate_total_price() {
        // GIVEN
        Order order = new Order();
        OrderItem orderItem = OrderItem.builder()
                .pizza(salami)
                .quantity(2)
                .build();

        // WHEN
        order.addOrderItem(orderItem);

        // THEN

        then(order.getTotalPrice()).isEqualByComparingTo(Money.of(19.8, "EUR"));
    }

    @Test
    public void order_current_state_12(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.FAILED);
        order.setDeliveryStatus(DeliveryStatus.FAILED);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.FAILED , orderStatus);
    }


    @Test
    public void order_current_state_11(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.FAILED);
        order.setDeliveryStatus(DeliveryStatus.DELIVERED);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.THIRD_PARTY_ERROR , orderStatus);
    }

    @Test
    public void order_current_state_10(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.FAILED);
        order.setDeliveryStatus(DeliveryStatus.SHIPPED);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.THIRD_PARTY_ERROR , orderStatus);
    }

    @Test
    public void order_current_state_9(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.FAILED);
        order.setDeliveryStatus(DeliveryStatus.PENDING);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.THIRD_PARTY_ERROR , orderStatus);
    }

    @Test
    public void order_current_state_8(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setDeliveryStatus(DeliveryStatus.FAILED);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.FAILED , orderStatus);
    }

    @Test
    public void order_current_state_7(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setDeliveryStatus(DeliveryStatus.DELIVERED);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.DONE , orderStatus);
    }

    @Test
    public void order_current_state_6(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setDeliveryStatus(DeliveryStatus.SHIPPED);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.IN_PROCESS , orderStatus);
    }

    @Test
    public void order_current_state_5(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setDeliveryStatus(DeliveryStatus.PENDING);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.IN_PROCESS , orderStatus);
    }

    @Test
    public void order_current_state_4(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setDeliveryStatus(DeliveryStatus.FAILED);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.FAILED , orderStatus);
    }

    @Test
    public void order_current_state_3(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setDeliveryStatus(DeliveryStatus.DELIVERED);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.THIRD_PARTY_ERROR , orderStatus);
    }

    @Test
    public void order_current_state_2(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setDeliveryStatus(DeliveryStatus.SHIPPED);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.IN_PROCESS , orderStatus);
    }

    @Test
    public void order_current_state_1(){
        //Given
        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setDeliveryStatus(DeliveryStatus.PENDING);
        //When
        OrderStatus orderStatus = order.getCurrentStatus();
        //then
        Assert.assertEquals("The status should be failed", OrderStatus.IN_PROCESS , orderStatus);
    }


}
