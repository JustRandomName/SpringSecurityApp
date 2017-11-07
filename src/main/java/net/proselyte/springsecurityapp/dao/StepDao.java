package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepDao extends JpaRepository<Step,Long> {
    Step findById(int id);
    List<Step> findAllByInstructionsId(Long id);
}