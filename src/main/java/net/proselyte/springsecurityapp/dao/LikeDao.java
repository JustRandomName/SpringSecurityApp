package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeDao extends JpaRepository<Like, Long> {
    List<Like> findAllByCommentId(Long Id);
    Like findByUserIdAndCommentId(int userId,Long commentId);
}