package rsoi.controller;

import bean.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rsoi.persistence.CarRepository;

import java.security.Principal;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    CarRepository carRepository;

    @RequestMapping({"/main","/main.html"})
    public String init(Principal principal, Model model) {
        if (principal!=null){
            List<Car> list = carRepository.findAll();
            model.addAttribute("cars",list);
            return "main";
        }
        else return "400";

    }
}
