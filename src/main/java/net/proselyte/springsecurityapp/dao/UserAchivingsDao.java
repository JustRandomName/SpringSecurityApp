package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.UserAchivings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAchivingsDao extends JpaRepository<UserAchivings, Long> {
}
