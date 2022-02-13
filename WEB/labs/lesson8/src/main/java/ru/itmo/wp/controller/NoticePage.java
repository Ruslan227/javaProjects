package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.validator.NoticeCredentialsAddValidator;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {

    private final NoticeService noticeService;
    private final NoticeCredentialsAddValidator noticeCredentialsAddValidator;

    public NoticePage(NoticeService noticeService, NoticeCredentialsAddValidator noticeCredentialsAddValidator) {
        this.noticeService = noticeService;
        this.noticeCredentialsAddValidator = noticeCredentialsAddValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(noticeCredentialsAddValidator);
    }

    @GetMapping("/notice")
    public String getNotice(Model model) {
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String setNotice(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                           BindingResult bindingResult,
                           HttpSession httpSession,
                            Model model) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        if (model.getAttribute("user") != null) {


            noticeService.addNotice(noticeForm);
            putMessage(httpSession, "Congrats, you have added notice!");
            return "redirect:";
        } else {
            putMessage(httpSession, "You should be logged in to write notice");
        }
        return "NoticePage";
    }
}

