package epizza.order.checkout;

public enum OrderStatus {

    NEW,

    BAKING,
    BAKED,
    BAKING_FAILED,

    DELIVERING,
    DELIVERED,
    DELIVERY_FAILED,

    CLOSED

}
