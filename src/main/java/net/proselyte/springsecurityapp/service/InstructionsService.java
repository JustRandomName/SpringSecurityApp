package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.Instructions;

import java.util.List;

public interface InstructionsService {

    void save(Instructions instruction);

    List<Instructions> findById(Long id);
}
