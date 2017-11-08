package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsDao extends JpaRepository<Comments, Long> {
    List<Comments> findAllByInstructionId(Long id);
}