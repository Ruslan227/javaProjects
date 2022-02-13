package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

@Controller
public class UserIdPage extends Page {
    private final UserService userService;

    public UserIdPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String userId(@PathVariable String id, Model model) {
        User user = null;
        try {
            long parsedID = Long.parseLong(id);
            user = userService.findById(parsedID);
        } catch (NumberFormatException e) {
            //ignore
        }

        if (user != null) {
            model.addAttribute("user", user);
        }
        return "UserIdPage";
    }
}









