package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Achivings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AchivingsDao extends JpaRepository<Achivings, Long> {
    Achivings findAllByAchivName(String achivName);
}
