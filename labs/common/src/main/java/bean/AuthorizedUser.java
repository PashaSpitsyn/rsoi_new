package bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
public class AuthorizedUser {

    @Id
    private String id;
    private Integer hash;
    private String salt;
    private String password;
    @DBRef
    private User user;
    private String token;
}
