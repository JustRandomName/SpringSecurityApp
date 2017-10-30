package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.InstructionsDao;
import net.proselyte.springsecurityapp.dao.StepDao;
import net.proselyte.springsecurityapp.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepServiceImpl implements StepService {
    @Autowired
    private StepDao stepDao;
    @Override
    public void save(Step step) {
        stepDao.save(step);
    }

    @Override
    public Step findById(Integer id) {
        return stepDao.findById(id);
    }
}