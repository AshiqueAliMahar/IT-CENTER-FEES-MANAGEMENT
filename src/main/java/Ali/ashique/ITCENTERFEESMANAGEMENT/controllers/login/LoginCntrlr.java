package Ali.ashique.ITCENTERFEESMANAGEMENT.controllers.login;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@AllArgsConstructor
public class LoginCntrlr {

    private SecurityContext authentication;//=SecurityContextHolder.getContext().getAuthentication();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (authentication.getAuthentication() != null && authentication.getAuthentication().isAuthenticated())
            return "redirect:/";
        return "login";
    }
}
