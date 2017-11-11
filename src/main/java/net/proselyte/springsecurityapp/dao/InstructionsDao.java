package net.proselyte.springsecurityapp.dao;

import com.sun.org.apache.bcel.internal.generic.Instruction;
import net.proselyte.springsecurityapp.model.Instructions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructionsDao extends JpaRepository<Instructions, Long> {
    Instructions findAllById(Long id);
    List<Instructions> findById(Long id);
    List<Instructions> findAllByOwnerId(int id);
    List<Instructions> findAllByHeadingContainsOrContentContains(String content, String heading);
}