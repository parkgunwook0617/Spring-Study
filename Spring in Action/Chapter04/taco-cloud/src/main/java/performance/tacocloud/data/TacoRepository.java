package performance.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import performance.tacocloud.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
