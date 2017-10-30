package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.dao.InstructionsDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Instructions;
import net.proselyte.springsecurityapp.model.Step;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.InstructionsService;
import net.proselyte.springsecurityapp.service.StepService;
import net.proselyte.springsecurityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class InstructionsController {

    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    private StepService stepService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createInstruction", method = RequestMethod.GET)
    public String add(Model model) {
        User user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
        model.addAttribute("currentId", user.getId());
        return "createInstruction";
    }

    //----------------
    @RequestMapping(value = "/block", method = RequestMethod.GET)
    public @ResponseBody void block(@RequestParam String heading, @RequestParam String content, @RequestParam Integer owner) {
        Instructions instructions = new Instructions();
        instructions.setHeading(heading);
        instructions.setContent(content);
        instructions.setOwnerId(owner);
        instructionsService.save(instructions);
    }

    @RequestMapping(value = "/createNewStep", method = RequestMethod.GET)
    public @ResponseBody void createNewStep(@RequestParam int instructionsId,@RequestParam int max){
        Step step=new Step();
        step.setInstructionsId(instructionsId);
        step.setNumber(max);
        stepService.save(step);
    }
//----------------
}
