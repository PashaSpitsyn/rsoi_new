package rsoi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bean.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import rsoi.persistence.BillRepository;

import java.util.List;
import java.util.Optional;

@RequestMapping("/bills")
@RestController
public class BillController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillRepository repository;

    @RequestMapping("/list")
    public List<Bill> list(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOGGER.info("get all bills for page {} of size {}", page, size);
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.POST)
    public void update(@PathVariable Long id, @RequestBody Bill bill) {
        LOGGER.info("Update bill {}", id);
        bill.setId(id);
        repository.save(bill);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Optional<Bill> get(@PathVariable Long id) {
        LOGGER.info("get bill {}", id);
        return repository.findById(id);
    }

    public void setRepository(BillRepository repository) {
        this.repository = repository;
    }
}
