package rsoi.persistence;

import bean.Car;
import bean.enumeration.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends MongoRepository<Car, String> {

    //public Optional<Car> findById(String id);
    List<Car> findByLocation(Location location);

}
