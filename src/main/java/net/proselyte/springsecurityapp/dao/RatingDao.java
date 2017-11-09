package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Rating;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RatingDao extends JpaRepository<Rating, Long> {
    List<Rating> findAllByInstrId (Long instr_id);
    Rating findByUserId(Long userId);
}
