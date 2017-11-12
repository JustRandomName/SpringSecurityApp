package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.InstrTag;
import net.proselyte.springsecurityapp.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrTagDao extends JpaRepository<InstrTag, Long> {
    List<InstrTag> findAllByInstrId(int instrId);
    List<InstrTag> findAllByTagName(String tag);
}
