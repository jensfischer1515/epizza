package epizza.order.delivery;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Value;

@Value
public class DeliveryJob {

	@NotEmpty
	String deliveryBoy;

	@NotNull
	LocalDateTime estimatedTimeOfDelivery;

}
