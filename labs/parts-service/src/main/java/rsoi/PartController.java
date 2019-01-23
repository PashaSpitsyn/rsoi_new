package rsoi;

import bean.Car;
import bean.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import rsoi.persistence.PartRepository;

import java.util.List;
import java.util.Optional;

@RequestMapping("/parts")
@RestController
public class PartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartController.class);

    @Autowired
    private PartRepository repository;

    @RequestMapping("/list")
    public List<Part> list(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOGGER.info("get all cars for page {} of size {}", page, size);
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.POST)
    public void update(@PathVariable Long id, @RequestBody Part car) {
        LOGGER.info("Update part {}", id);
        car.setId(id);
        repository.save(car);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Optional<Part> get(@PathVariable Long id) {
        LOGGER.info("Request part {}", id);
        return repository.findById(id);
    }

}
