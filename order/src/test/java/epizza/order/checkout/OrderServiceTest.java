package epizza.order.checkout;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Objects;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import epizza.order.OrderApplicationTest;
import epizza.order.catalog.Pizza;
import epizza.order.catalog.PizzaRepository;

@Transactional
@OrderApplicationTest(properties = {
        "spring.jpa.properties.hibernate.show_sql=false",
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.properties.hibernate.use_sql_comments=false"
})
public class OrderServiceTest {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @MockBean
    private OrderEventPublisher orderEventPublisher;

    @Autowired
    private PizzaRepository pizzaRepository;

    private Order order1;

    private Order order2;

    @Before
    public void createOrder() {
        order1 = new Order();
        order1.setDeliveryAddress(address());
        order1.setOrderItems(ImmutableList.of(orderItem()));
        order1 = orderService.create(order1);

        order2 = new Order();
        order2.setOrderItems(ImmutableList.of(orderItem()));
        order2.setDeliveryAddress(address());
        order2.setDeliveryBoy("Guy XY");
        order2 = orderService.create(order2);
    }

    @After
    public void deleteOrder() {
        orderRepository.deleteAll();
    }

    @Test
    public void should_find_unassigned_orders() {
        // GIVEN

        // WHEN
        Page<Order> unassignedOrders = orderService.getAll(new PageRequest(0, 20));

        // THEN

        then(unassignedOrders.getContent()).extracting(Order::getDeliveryBoy).filteredOn(Objects::nonNull).isEmpty();

        then(unassignedOrders.getContent()).extracting(Order::getId).containsOnly(order1.getId());
    }

    private OrderItem orderItem() {
        return OrderItem.builder()
                .pizza(pizza())
                .quantity(1)
                .build();
    }

    private Pizza pizza() {
        return pizzaRepository.findOne(1L);
    }

    private Address address() {
        return Address.builder()
                .firstname("Joe")
                .lastname("Developer")
                .street("Street")
                .city("City")
                .postalCode("12345")
                .telephone("555-shoe")
                .build();
    }
}
