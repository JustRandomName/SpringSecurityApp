package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.InstructionsDao;
import net.proselyte.springsecurityapp.model.Instructions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructionsServiceImpl implements InstructionsService {
    @Autowired
    private InstructionsDao instructionsDao;
    @Override
    public void save(Instructions instruction) {

        instructionsDao.save(instruction);
    }
    @Override
    public List<Instructions> findById(Integer id) {return instructionsDao.findById(id);}
}