package epizza.delivery.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Collections;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RequiredArgsConstructor
@Slf4j
public class OrderServiceClientImpl implements OrderServiceClient {

	private final RestTemplate restTemplate;

	private final URI baseUri;

    @Override
    public void selectOrder(Integer orderId, DeliveryJob job) {
    		URI uri = orderUri(orderId);
    		log.info("selecting order via uri {}", uri);
    		restTemplate.postForObject(uri, job, Void.class);
    }

    @Override

    public PagedResources<Order> getOrders() {
    		URI uri = ordersUri();
    		log.info("getting orders via uri {}", uri);
    		return restTemplate.exchange(uri, HttpMethod.GET, null, new FunnyType()).getBody();
    }

    
    
	private URI orderUri(Integer orderId) {
		return UriComponentsBuilder.fromUri(baseUri).path("/orders/" + orderId + "/delivery").build().toUri();
	}

	private URI ordersUri() {
		return UriComponentsBuilder.fromUri(baseUri).path("/orders").build().toUri();
	}


	static class FunnyType extends ParameterizedTypeReference<PagedResources<Order>> {
		
	}

}
