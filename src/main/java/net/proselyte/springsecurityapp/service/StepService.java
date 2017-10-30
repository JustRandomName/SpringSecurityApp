package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.Step;


public interface StepService {

    void save(Step step);

    Step findById(Integer id);
}