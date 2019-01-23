package rsoi.persistence;

import bean.Bill;
import bean.enumeration.State;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends MongoRepository<Bill, Long> {

    public Optional<Bill> findById(Long id);

    public List<Bill> findByState(State state);

}
