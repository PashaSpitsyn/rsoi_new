package rsoi.controller;

import bean.Car;
import bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rsoi.UserRepository;
import rsoi.persistence.CarRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarRepository repository;
    @Autowired
    private UserRepository userRepository;


   /* @RequestMapping("/list")
    public List<Car> list(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOGGER.info("get all cars for page {} of size {}", page, size);
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }*/

    @RequestMapping(value = "/id/{id}", method = RequestMethod.POST)
    public void update(@PathVariable Long id, @RequestBody Car car) {
        LOGGER.info("Update car {}", id);
        //car.setId(id);
        repository.save(car);
    }

   /* @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Optional<Car> get(@PathVariable Long id) {
        LOGGER.info("Request car {}", id);
        return repository.findById(id);
    }*/

    @GetMapping("car/new")
    public String newCar(Model model) {
        model.addAttribute("car", new Car());
        return "car/carform";
    }

    @GetMapping("car/list")
    public String list(Model model) {
        model.addAttribute("cars", repository.findAll());
        return "main";
    }

    @PostMapping("car")
    public String saveOrUpdate(@Valid Car car, BindingResult bindingResult, Principal principal) {
        if (principal != null) {
            if (bindingResult.hasErrors()) {
                return "car/carform";
            } else {
                User user = car.getUser();
                userRepository.save(user);
                if (car.getId().isEmpty()) {
                    car.setId(null);

                }
                repository.save(car);
                return "redirect:/car/list";
            }
        } else
            return "error/401";

    }

    @GetMapping("car/{id}/update")
    public String updateCar(@PathVariable String id, Model model) {

        Optional<Car> car = repository.findById(id);
        if (car.isPresent()) {
            model.addAttribute("car", car.get());
        }else{
            throw new RuntimeException("Could not find car with id="+id);
        }
        return "car/carform";

    }

    @GetMapping("car/{id}/delete")
    public String deleteById(@PathVariable String id) {
        repository.deleteById(id);
        return "redirect:/car/list";
    }

    @GetMapping("car/{id}/show")
    public String showCar(@PathVariable String id, Model model) {

        Optional<Car> car = repository.findById(id);
        if (car.isPresent()) {
            model.addAttribute("car", car.get());
        }else{
            throw new RuntimeException("Could not find car with id="+id);
        }

        return "car/show";
    }

}
