package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.model.UserRole;
import pl.sda.javapoz.service.NavbarLinkService;
import pl.sda.javapoz.service.UserService;

import javax.validation.Valid;

/**
 * Created by RENT on 2017-03-22.
 */
@Controller
public class UserRegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private NavbarLinkService navbarLinkService;

    @GetMapping("register")
    public ModelAndView modelAndView() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("navbarLinks",navbarLinkService.fetchLinks());
        modelAndView.addObject("user", new User());
        return modelAndView;
    }


    @PostMapping("register")
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult bindResult) {
        UserRole role = new UserRole();
        if(bindResult.hasErrors())
            return "register";
        else {
            if(user.getFirstName().equals("Andrzej")){
                role.setRole("ROLE_ADMIN");
                user.setAdmin(true);
            }
            else{
                role.setRole("ROLE_USER");
                user.setAdmin(false);
            }
            userService.addUserWitRole(user,role);
            return "redirect:/login";
        }
    }


}
