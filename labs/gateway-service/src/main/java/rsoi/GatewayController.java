package rsoi;

import bean.Bill;
import bean.Car;
import bean.Part;
import bean.enumeration.Location;
import bean.enumeration.RepairState;
import bean.enumeration.State;
import bean.enumeration.WashState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rsoi.bean.CarOrder;

import java.util.Random;
import java.util.stream.Collectors;

@RequestMapping("/gateway")
@RestController
public class GatewayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.bill}")
    private String billService;

    @Value("${service.car}")
    private String carService;

    @Value("${service.part}")
    private String partService;

    @RequestMapping(value = "/calculateRepairCost", method = RequestMethod.POST)
    public Bill carArrived(@RequestBody CarOrder carOrder) {
        Car car = getCar(carOrder);
        LOGGER.info("Creating car {}", car);
        restTemplate.postForObject(carService + "/id/" + car.getId(), car, String.class);
        Double partsTotalCost = 0.0;
        LOGGER.info("Calculating total costs");
        for (Long partId : car.getNecessaryParts()) {
            Part part = getOrCreatePart(partId);
            partsTotalCost += part.getCost();
        }
        LOGGER.info("Creating new bill");
        Bill bill = new Bill();
        bill.setId(carOrder.getId());
        bill.setState(State.UNPAID);
        double repairPrice = RANDOM.nextDouble() * 10_000;
        bill.setAmmount(partsTotalCost + repairPrice);
        restTemplate.postForObject(billService + "/id/" + bill.getId(), bill, String.class);
        return bill;
    }

    @RequestMapping(value = "/car/{carId}/addPart/{partId}", method = RequestMethod.POST)
    public Bill addPart(@PathVariable Long carId, @PathVariable Long partId) {
        Part part = getOrCreatePart(partId);
        LOGGER.info("Updating car with new part");
        Car car = restTemplate.getForObject(carService + "/id/" + carId, Car.class);
        car.getNecessaryParts().add(partId);
        restTemplate.postForObject(carService + "/id/" + car.getId(), car, String.class);

        LOGGER.info("Updating bill for the car");
        Bill bill = restTemplate.getForObject(billService + "/id/" + carId, Bill.class);
        if (bill.getState() == State.UNPAID) {
            bill.setAmmount(bill.getAmmount() + part.getCost());
        } else {
            bill.setState(State.UNPAID);
            bill.setAmmount(part.getCost());
        }
        restTemplate.postForObject(billService + "/id/" + bill.getId(), bill, String.class);
        return bill;
    }

    @RequestMapping(value = "/bill/{billId}", method = RequestMethod.POST)
    public Car payBill(@PathVariable Long billId) {
        LOGGER.info("Updating bill with paid state");
        Bill bill = restTemplate.getForObject(billService + "/id/" + billId, Bill.class);
        if (bill.getState() == State.UNPAID) {
            bill.setState(State.PAID);
        }
        restTemplate.postForObject(billService + "/id/" + bill.getId(), bill, String.class);

        LOGGER.info("Updating car with completed state");
        Car car = restTemplate.getForObject(carService + "/id/" + billId, Car.class);
        car.setLocation(Location.PARKING);
        car.setRepairState(RepairState.READY);
        if(car.getWashState() != null){
            car.setWashState(WashState.CLEAN);
        }
        restTemplate.postForObject(carService + "/id/" + car.getId(), car, String.class);
        return car;
    }

    private Part getOrCreatePart(Long partId) {
        LOGGER.info("Looking for part {}", partId);
        Part part = restTemplate.getForObject(partService + "/id/" + partId, Part.class);
        if (part == null) {
            LOGGER.info("Creating new part {}", partId);
            part = new Part();
            part.setId(partId);
            part.setName("Part #" + partId);
            part.setCost(RANDOM.nextDouble() * 100);
            restTemplate.postForObject(partService + "/id/" + part.getId(), part, String.class);
        }
        return part;
    }

    private Car getCar(CarOrder carOrder) {
        Car car = new Car();
        //car.setId(carOrder.getId());
        car.setColor(carOrder.getColor());
        car.setType(carOrder.getType());
        car.setRepairState(RepairState.BROKEN);
        car.setLocation(Location.PARKING);
        car.setWashState(RANDOM.nextBoolean() ? WashState.REQUESTED : null);
        car.setNecessaryParts(RANDOM.longs(3, 0, 1000).boxed().collect(Collectors.toList()));
        return car;
    }




}
