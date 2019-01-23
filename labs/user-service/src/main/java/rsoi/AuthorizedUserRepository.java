package rsoi;

import bean.AuthorizedUser;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
//@Order(1)
public interface AuthorizedUserRepository extends MongoRepository<AuthorizedUser, String> {

    @Query("{'user.username': ?0}")
    Optional<AuthorizedUser> findByUser_Username(String username);

    Optional<AuthorizedUser> findFirstByUser_Id(String id);
}
