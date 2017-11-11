package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagsDao  extends JpaRepository<Tags, Long> {
    List<Tags> findAll();

}
