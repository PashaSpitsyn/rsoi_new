package bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Part {

    @Id
    private Long id;
    private String name;
    private Double cost;
}
