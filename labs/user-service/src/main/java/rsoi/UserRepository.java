package rsoi;

import bean.User;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

   @Query("{'username': {$regex: ?0 }})")
   Optional<User> findFirstByUsername(String username);

}
