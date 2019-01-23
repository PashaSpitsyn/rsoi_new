package rsoi;

import java.security.Principal;
import java.util.Optional;

import bean.AuthorizedUser;
import bean.User;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    AuthorizedUserRepository authorizedUserRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/user/me")
    public Principal user(Principal principal) {
        System.out.println(principal);
        System.out.println(principal.getName());
        Optional<User> user = userRepository.findFirstByUsername(principal.getName());
        Optional<AuthorizedUser> authorizedUser = authorizedUserRepository.findFirstByUser_Id(user.get().getId());

        if(AbstractAuthenticationToken.class.isInstance(principal)){
            AbstractAuthenticationToken authToken = (AbstractAuthenticationToken) principal;
            String tokenValue = ((OAuth2AuthenticationDetails) authToken.getDetails()).getTokenValue();
            authorizedUser.get().setToken(tokenValue);
            authorizedUserRepository.save(authorizedUser.get());
        }
        return principal;
    }

}