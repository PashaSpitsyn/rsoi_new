package bean;

import bean.enumeration.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@ToString
@Document
public class Car {

    @Id
    private String id;
    private FrameType type;
    private Color color;

    @Valid
    @DBRef
    private User user;
    //todo: long -> part
    private List<Long> necessaryParts;
    private RepairState repairState;
    private WashState washState;

    private Mark mark;
    private Location location;
    @NotEmpty(message = "Provide a description")
    private String description;

}
