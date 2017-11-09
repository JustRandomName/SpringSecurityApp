package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Vector;

public interface RatingDao extends JpaRepository<Rating, Long> {
    Vector<Long> findAllByInstrId (Long instr_id);

}
