package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final CommentService commentService;

    public PostPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Guest
    @GetMapping("post/{id}")
    public String postId(@PathVariable String id, Model model, HttpSession session) {
        Post post = null;
        try {
            long parsedID = Long.parseLong(id);
            post = postService.findById(parsedID);
        } catch (NumberFormatException e) {
            //ignore
        }

        if (post != null) {
            model.addAttribute("post", post);
            model.addAttribute("comments", post.getComments());
            model.addAttribute("commentForm", new CommentCredentials());
            return "PostPage";
        } else {
            putMessage(session, "No such post");
            return "IndexPage";
        }

    }

    @PostMapping("/post/{id}")
    public String comment(@PathVariable String id, @Valid @ModelAttribute("commentForm") CommentCredentials commentForm,
                        BindingResult bindingResult,
                        HttpSession httpSession,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "EnterPage";
        }
        // id - post id
        // user - model
        Post post = postService.findById(Long.parseLong(id));
        User user = (User) model.getAttribute("user");


        commentForm.setPost(post);
        // add comment to comments list in post

        commentForm.setUser(user);
        commentService.fillComment(post, model, commentForm);
        putMessage(httpSession, "comment has been added");

        return "redirect:/post/{id}";
    }
}
