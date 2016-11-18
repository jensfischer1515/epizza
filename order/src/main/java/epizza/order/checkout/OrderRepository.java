package epizza.order.checkout;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, QueryDslPredicateExecutor<Order> {
    Page<Order> findByDeliveryBoyIsNull(Pageable pageable);
}
