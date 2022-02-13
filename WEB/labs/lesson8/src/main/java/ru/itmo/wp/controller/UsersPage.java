package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("usersForm", new UserCredentials());
        return "UsersPage";
    }


    @PostMapping("/users/all")
    public String usersPost(@ModelAttribute("usersForm") UserCredentials usersForm,
                            BindingResult bindingResult,
                            HttpSession httpSession,
                            Model model) {
        if (bindingResult.hasErrors()) {
            return "UsersPage";
        }
        User user = (User) model.getAttribute("user");
        if (user != null) {
            userService.disableOrEnable(usersForm);
            putMessage(httpSession, "Congrats, you have changed field!");
        } else {
            putMessage(httpSession, "You should be logged in to switch mode");
        }
        return "redirect:/users/all";
    }
}
