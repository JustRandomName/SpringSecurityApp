package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Instructions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructionsDao extends JpaRepository<Instructions, Long> {
    List<Instructions> findById(Long id);
    List<Instructions> findAllByOwnerId(int id);
    List<Instructions> findAllByHeadingContainsOrContentContains(String content, String heading);
}