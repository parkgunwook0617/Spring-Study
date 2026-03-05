package performance.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import performance.tacocloud.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
