package bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@Document
public class User {

    @Id
    private String id;
    @TextIndexed
    private String username;

    @NotEmpty(message = "Provide first name")
    @Length.List({
            @Length(min = 2, message = "The field must be at least 5 characters"),
            @Length(max = 50, message = "The field must be less than 50 characters")
    })
    private String firstName;
    @NotEmpty(message = "Provide last name")
    @Length.List({
            @Length(min = 2, message = "The field must be at least 5 characters"),
            @Length(max = 50, message = "The field must be less than 50 characters")
    })
    private String lastName;
    @DBRef
    private Set<Car> cars;


}
