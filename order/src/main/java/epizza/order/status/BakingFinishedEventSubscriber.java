package epizza.order.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import epizza.order.checkout.Order;
import epizza.order.checkout.OrderService;

@Component
public class BakingFinishedEventSubscriber extends AbstractOrderEventSubscriber {

    @Autowired
    public BakingFinishedEventSubscriber(ObjectMapper objectMapper, OrderService orderService) {
        super(orderService, objectMapper, "BakingFinished");
    }

    @Override
    protected void handleOrder(Order order, Map<String, Object> payload) {
    }
}
