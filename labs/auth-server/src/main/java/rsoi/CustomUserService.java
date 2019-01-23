package rsoi;

import bean.AuthorizedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rsoi.AuthorizedUserRepository;
import rsoi.UserRepository;

import java.util.Optional;

@Service
@Order(1)
public class CustomUserService implements UserDetailsService {

    @Autowired
    @Qualifier("authorizedUserRepository")
    private AuthorizedUserRepository authorizedUserRepository;
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<bean.User> user = userRepository.findFirstByUsername(username);
        Optional<AuthorizedUser> authorizedUser = authorizedUserRepository.findFirstByUser_Id(user.get().getId());

        User securedUser = null;
        if (authorizedUser.isPresent()){
            securedUser = new User(username,authorizedUser.get().getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
        }
        return securedUser;
    }
}
