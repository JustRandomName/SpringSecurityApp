package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.dao.*;
import net.proselyte.springsecurityapp.model.Instructions;
import net.proselyte.springsecurityapp.model.Step;
import net.proselyte.springsecurityapp.model.Tags;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Controller
public class InstructionsController {

    @Autowired
    private StepDao stepDao;
    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    private StepService stepService;
    @Autowired
    private UserService userService;
    @Autowired
    private RatingDao ratingDao;
//    @Autowired
//    private LikeDao likeDao;
    @Autowired
    private TagsDao tagsDao;

    @RequestMapping(value = "/createInstruction", method = RequestMethod.GET)
    public String add(Model model) {
        try {
            User user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
            model.addAttribute("currentId", user.getId());
            return "createInstruction";
        } catch (Exception e) {
            return "redirect:/startpage";
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


    @RequestMapping(value = "/viewInstruction/{current}/{step}", method = RequestMethod.GET)
    public String viewInstr(@PathVariable("current") Long current, @PathVariable int step, Model model) {
        model.addAttribute("current", current);
        Instructions instructions = instructionsService.findById(current).get(0);
        model.addAttribute("instruction", instructions);
        List<Step> steps = stepDao.findAllByInstructionsId(current);
        model.addAttribute("steps", steps);
        if (step == 0) {
            model.addAttribute("currentStep", null);
        } else {
            model.addAttribute("currentStep", steps.get(step - 1));
        }
        model.addAttribute("lastStep", steps.size());
        return "/viewInstruction";
    }

    @Autowired
    private InstructionsDao instructionsDao;

    @RequestMapping(value = "/searchInstructions", method = RequestMethod.GET)
    public String search(@RequestParam String search, Model model) {
        List<Instructions> instructions = instructionsDao.findAllByHeadingContainsOrContentContains(search, search);
        List<Step> steps = stepDao.findAllByHeadingContainsOrContentContainsOrderByInstructionsId(search, search);
        for (int i = 0; i < steps.size(); i++) {
            boolean bool = false;
            for (int j = 0; j < instructions.size(); j++) {
                if (instructions.get(j).getId() == steps.get(i).getInstructionsId()) {
                    bool = true;
                }
            }
            if (!bool) {
                instructions.add(instructionsDao.findById(steps.get(i).getInstructionsId()).get(0));
            }
        }
        model.addAttribute("instructions", instructions);
        return "/searchInstructions";
    }

    @RequestMapping(value = "/viewAllSteps/{inst_id}", method = RequestMethod.GET)
    public String viewAll(@PathVariable("inst_id") Long inst_id, Model model) {
        List<Step> steps = stepDao.findAllByInstructionsId(inst_id);
        model.addAttribute("id", inst_id);
        model.addAttribute("Steps", steps);
        return "/viewAllSteps";
    }

    @RequestMapping(value = "/addMark/{inst_id}", method = RequestMethod.GET)
    public String addMark(@PathVariable("inst_id") Long inst_id, Model model) {
        Vector<Long> marks = ratingDao.findAllByInstrId(inst_id);
        Long buffer = 0L;
        for (int i = 0; i < marks.size(); i++) {
            buffer += marks.get(i);
        }
        buffer = buffer / marks.size();
        Instructions instructions = instructionsDao.findById(inst_id).get(0);
        instructions.setRating(buffer);
        instructionsDao.save(instructions);
        model.addAttribute("Mark", buffer);
        return "/viewAllSteps";
    }

    //    @RequestMapping(value = "setLike/{user_id}", method = RequestMethod.GET)
//    public String setLikes(@PathVariable("user_id") Long user_id){
//        try{
//
//            User user = likeDao.findByUser_id(user_id);
//            //TODO: check user
//        }catch (Exception e){
//
//        }
//    }
    @RequestMapping(value = "/getTags", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getTags(@RequestParam String tagName) {
        return createList(tagName);
    }

    public List<String> createList(String tagNames) {
        try {

            List<String> buffer = new ArrayList<>();
            List<Tags> tagsList = tagsDao.findAll();
            for (int i = 0; i < tagsList.size(); i++) {
                if (tagsList.get(i).getTag().contains(tagNames)) {
                    buffer.add(tagsList.get(i).getTag());
                    System.out.println(tagsList.get(i).getTag());
                }
            }
            return buffer;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

//    @RequestMapping(value = "/addTag", method = RequestMethod.GET)
//    public List<String> addtags(@RequestParam String tag){
//
//    }
}