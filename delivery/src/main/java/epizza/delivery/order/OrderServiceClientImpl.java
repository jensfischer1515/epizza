package epizza.delivery.order;

import lombok.RequiredArgsConstructor;


import java.net.URI;


import org.springframework.hateoas.PagedResources;

import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.UriComponentsBuilderMethodArgumentResolver;
import org.springframework.web.util.UriComponentsBuilder;



@RequiredArgsConstructor
public class OrderServiceClientImpl implements OrderServiceClient {

    private final RestTemplate restTemplate;

    private final URI baseUri;

    @Override
    public void selectOrder(Integer orderId, DeliveryJob job) {
        URI uri = UriComponentsBuilder.fromUri(baseUri).path("/orders/" + orderId + "/delivery").build().toUri();
        restTemplate.postForObject(uri, job, Void.class);
    }

    @Override

    public PagedResources<Order> getOrders() {
        URI uri = UriComponentsBuilder.fromUri(baseUri).path("/orders").build().toUri();
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParametrizedReturnType()).getBody();
    }



    // That's for avoiding generics type erasure.
    private static final class ParametrizedReturnType extends TypeReferences.PagedResourcesType<Order> {}

}
