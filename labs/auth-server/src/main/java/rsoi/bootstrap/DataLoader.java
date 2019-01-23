package rsoi.bootstrap;

import bean.AuthorizedUser;
import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rsoi.AuthorizedUserRepository;
import rsoi.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    private final AuthorizedUserRepository authorizedUserRepository;
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public DataLoader(AuthorizedUserRepository authorizedUserRepository, UserRepository userRepository) {
        this.authorizedUserRepository = authorizedUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
       System.out.println("LOADING DATA...");
        User user1=new User();
        user1.setFirstName("John");
        user1.setLastName("Johnson");
        user1.setUsername("john");

        AuthorizedUser authUser1 = new AuthorizedUser();
        authUser1.setPassword(encoder.encode("123"));
        authUser1.setUser(user1);

        userRepository.save(user1);
        authorizedUserRepository.save(authUser1);
        System.out.println("DATA LOADED...");
    }
}
