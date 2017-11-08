package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.dao.CommentsDao;
import net.proselyte.springsecurityapp.dao.InstructionsDao;
import net.proselyte.springsecurityapp.dao.StepDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Comments;
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
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        try{
        User user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
        model.addAttribute("currentId", user.getId());
        return "createInstruction";}
        catch(Exception e) {
            return "redirect:/login";
        }
    }

    private Instructions instructions;

    //----------------
    @RequestMapping(value = "/block", method = RequestMethod.GET)
    public @ResponseBody
    void block(@RequestParam String heading, @RequestParam String content, @RequestParam Integer owner) {
        instructions = new Instructions();
        instructions.setHeading(heading);
        instructions.setContent(content);
        instructions.setOwnerId(owner);
        instructionsService.save(instructions);
    }

    //----------------
    @RequestMapping(value = "/saveStep", method = RequestMethod.GET)
    public @ResponseBody
    void saveStep(@RequestParam String content, @RequestParam int number) {
        Step step = new Step();
        step.setContent(content);
        step.setHeading("LOL");
        step.setNumber(number);
        step.setInstructionsId(instructions.getId());
        stepService.save(step);
    }

    @Autowired
    private StepDao stepDao;

    @RequestMapping(value = "/viewInstruction/{current}/{step}", method = RequestMethod.GET)
    public String viewInstr(@PathVariable("current") Long current, @PathVariable int step, Model model) {
        model.addAttribute("current", current);
        Instructions instructions = instructionsService.findById(current).get(0);
        model.addAttribute("instruction", instructions);
        List<Step> steps = stepDao.findAllByInstructionsId(current);
        model.addAttribute("steps", steps);
        if(step==0)
        {
            model.addAttribute("currentStep", null);
        }else {
            model.addAttribute("currentStep", steps.get(step-1));
        }
        model.addAttribute("lastStep", steps.size());
        return "/viewInstruction";
    }

    @Autowired
    private InstructionsDao instructionsDao;

    @RequestMapping(value = "/searchInstructions", method = RequestMethod.GET)
    public String search(@RequestParam String search, Model model) {
        List<Instructions> instructions = instructionsDao.findAllByHeadingContainsOrContentContains(search, search);
        List<Step> steps=stepDao.findAllByHeadingContainsOrContentContainsOrderByInstructionsId(search,search);
        for(int i=0;i<steps.size();i++)
        {
            boolean bool=false;
            for(int j=0;j<instructions.size();j++)
            {
                if(instructions.get(j).getId()==steps.get(i).getInstructionsId())
                {
                    bool=true;
                }
            }
            if(!bool){
                instructions.add(instructionsDao.findById(steps.get(i).getInstructionsId()).get(0));
            }
        }
        model.addAttribute("instructions", instructions);
        return "/searchInstructions";
    }
    @Autowired
    private CommentsDao commentsDao;
    @RequestMapping(value = "/addComment",method = RequestMethod.GET)
    public @ResponseBody String addComment(@RequestParam Long instructionsId,@RequestParam String content)
    {
        User user;
        try {
            user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
        }catch (Exception e){
            return "User not found";
        }
        Comments comments=new Comments();
        comments.setContent(content);
        comments.setOwnerId(user.getId());
        comments.setInstructionId(instructionsId);
        commentsDao.save(comments);
        return "Success";
    }
}