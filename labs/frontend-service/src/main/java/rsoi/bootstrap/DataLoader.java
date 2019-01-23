package rsoi.bootstrap;

import bean.AuthorizedUser;
import bean.Car;
import bean.User;
import bean.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rsoi.AuthorizedUserRepository;
import rsoi.UserRepository;
import rsoi.persistence.CarRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final AuthorizedUserRepository authorizedUserRepository;

    @Autowired
    public DataLoader(CarRepository carRepository, UserRepository userRepository, AuthorizedUserRepository authorizedUserRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.authorizedUserRepository = authorizedUserRepository;
    }

    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        System.out.println("Loading data...");

        User user1=new User();
        user1.setFirstName("Marco");
        user1.setLastName("Mindoni");
        user1.setUsername("mc_marco");

        User user2=new User();
        user2.setFirstName("Roberto");
        user2.setLastName("Bartolini");
        user2.setUsername("rob");

        User user3=new User();
        user3.setFirstName("Lara");
        user3.setLastName("Craft");

        AuthorizedUser authUser1 = new AuthorizedUser();
        authUser1.setPassword("123");
        authUser1.setUser(user1);
        authUser1.setSalt("Qoi6b");
        authUser1.setHash(("myPassw123"+authUser1.getSalt()).hashCode());

        AuthorizedUser authUser2 = new AuthorizedUser();
        authUser2.setUser(user2);
        authUser2.setSalt("frtz");
        authUser2.setHash(("12345q"+authUser2.getSalt()).hashCode());

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        authorizedUserRepository.save(authUser1);
        authorizedUserRepository.save(authUser2);


        Car car1 = new Car();
        car1.setMark(Mark.BMW);
        car1.setType(FrameType.JEEP);
        car1.setLocation(Location.MECHANIC);
        car1.setColor(Color.BLUE);
        car1.setRepairState(RepairState.BROKEN);
        car1.setUser(user1);
        car1.setDescription("It was a nice car...");

        Car car2 = new Car();
        car2.setMark(Mark.LEXUS);
        car2.setType(FrameType.UNIVERSAL);
        car2.setColor(Color.RED);
        car2.setRepairState(RepairState.BROKEN);
        car2.setLocation(Location.MECHANIC);
        car2.setUser(user2);
        car2.setDescription("Change color");

        Car car3 = new Car();
        car3.setMark(Mark.MERCEDES);
        car3.setType(FrameType.SEDAN);
        car3.setColor(Color.PINK);
        car3.setRepairState(RepairState.BROKEN);
        car3.setLocation(Location.PARKING);
        car3.setUser(user3);
        car3.setDescription("Bubble gum on the driver seat");

        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);

        System.out.println("Data was loaded...");
    }

}
