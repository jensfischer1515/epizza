package epizza.order.delivery;

import com.google.common.base.MoreObjects;

import lombok.Value;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Value
public class DeliveryJob {

    @NotEmpty
    String deliveryBoy;
    
    @NotNull
    LocalDateTime estimatedTimeOfDelivery;
    
}
