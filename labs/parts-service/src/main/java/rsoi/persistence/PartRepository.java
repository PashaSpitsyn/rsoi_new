package rsoi.persistence;

import bean.Car;
import bean.Part;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PartRepository extends MongoRepository<Part, Long> {

    Optional<Part> findById(Long id);

}
