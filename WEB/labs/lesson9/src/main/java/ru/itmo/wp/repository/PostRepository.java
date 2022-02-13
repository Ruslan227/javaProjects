package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreationTimeDesc();

    @Transactional
    @Modifying
    @Query(value = "UPDATE post_comments SET comments_id=?2 WHERE post_id=?1", nativeQuery = true)
    void updateComments(long id, List<Comment> comments);
}
