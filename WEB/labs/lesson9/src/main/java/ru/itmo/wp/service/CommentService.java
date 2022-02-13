package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment addComment(CommentCredentials commentForm) {
        Comment comment = new Comment();
        comment.setText(commentForm.getText());
        return comment;
    }

    public  void fillComment(Post post, Model model, CommentCredentials commentForm) {

        Comment comment = new Comment();
        comment.setPost(commentForm.getPost());
        comment.setUser(commentForm.getUser());
        comment.setText(commentForm.getText());

        List<Comment> comments = post.getComments();
//        if (comments == null) {
//            comments = new ArrayList<>();
//        }
        comments.add(comment);
//        post.setComments(comments);
        model.addAttribute("comments", comments);
        commentRepository.save(comment);
        postRepository.updateComments(post.getId(), comments);
    }
}
