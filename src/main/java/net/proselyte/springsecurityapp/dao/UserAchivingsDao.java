package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.UserAchivings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchivingsDao extends JpaRepository<UserAchivings, Long> {
    List<UserAchivings> findAllByUserId(int id);
}
