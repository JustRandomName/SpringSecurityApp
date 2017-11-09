package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.dao.*;
import net.proselyte.springsecurityapp.model.*;
import net.proselyte.springsecurityapp.service.InstructionsService;
import net.proselyte.springsecurityapp.service.StepService;
import net.proselyte.springsecurityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
        List<Comments> comments=commentsDao.findAllByInstructionId(current);
        model.addAttribute("steps", steps);
        if(step==0)
        {
            model.addAttribute("currentStep", null);
        }else {
            model.addAttribute("currentStep", steps.get(step-1));
        }
        model.addAttribute("lastStep", steps.size());
        model.addAttribute("comments",comments);
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
    public @ResponseBody int addComment(@RequestParam Long instructionsId,@RequestParam String content)
    {
        User user;
        try {
            user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
        }catch (Exception e){
            return 0;
        }
        Comments comments=new Comments();
        comments.setContent(content);
        comments.setOwnerId(user.getId());
        comments.setInstructionId(instructionsId);
        commentsDao.save(comments);
        return user.getId();
    }
    @RequestMapping(value = "/viewAllSteps/{inst_id}", method = RequestMethod.GET)
    public String viewAll(@PathVariable("inst_id") Long inst_id, Model model) {
        List<Step> steps = stepDao.findAllByInstructionsId(inst_id);
        model.addAttribute("id", inst_id);
        model.addAttribute("Steps", steps);
        return "/viewAllSteps";
    }
@Autowired
private RatingDao ratingDao;

    @RequestMapping(value = "/changeMark/{userId}/{instrId}", method = RequestMethod.GET)
    public boolean changeMark(@PathVariable("userId") Long userId, @PathVariable("instrId") Long instrId, @RequestParam Long mark){
        try{
            Rating rating = ratingDao.findByUserId(userId);
        }catch (Exception e) {
            Rating rating = new Rating();
            rating.setInstr_id(instrId);
            rating.setUser_id(userId);
            rating.setMark(mark);
            ratingDao.save(rating);
            return true;
        }
        return false;
    }
    @RequestMapping(value = "/addMark/{inst_id}", method = RequestMethod.GET)
    public String addMark(@PathVariable("inst_id") Long inst_id, @RequestParam Long userId ,  Model model) {
        try{
            Rating rating = ratingDao.findByUserId(userId);
            model.addAttribute("check", true);
        }catch (Exception e){
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
    @Autowired
    private TagsDao tagsDao;
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
    @RequestMapping(value = "/seeInstructions/{username}",method = RequestMethod.GET)
    public String see(@PathVariable("username") String username,Model model)
    {
        User user=userService.findByUsername(username).get(0);
        List<Instructions> instructions=instructionsDao.findAllByOwnerId(user.getId());
        model.addAttribute("instructions",instructions);
        return "/seeInstructions";
    }

    @RequestMapping(value="/editInstruction/{instructionId}",method = RequestMethod.GET)
    public String edit(@PathVariable("instructionId") Long instructionId,Model model)
    {
        Instructions instructions=instructionsDao.findById(instructionId).get(0);
        List<Step> steps=stepDao.findAllByInstructionsId(instructionId);
        model.addAttribute("instruction",instructions);
        model.addAttribute("steps",steps);
        return "/editInstruction";
    }
    @RequestMapping(value="/editInstructions",method = RequestMethod.GET)
    @ResponseBody
    public  void editInstructions(@RequestParam Long instructionId,@RequestParam String heading,@RequestParam String content)
    {
        Instructions instructions=instructionsDao.findById(instructionId).get(0);
        instructions.setHeading(heading);
        instructions.setContent(content);
        instructionsDao.save(instructions);
    }
    @RequestMapping(value="/editStep",method = RequestMethod.GET)
    public void editStep(@RequestParam Long instructionId,@RequestParam int number,@RequestParam String content)
    {
        number--;
        try{
        List<Step> steps=stepDao.findAllByInstructionsId(instructionId);

        steps.get(number).setContent(content);
        stepDao.save(steps.get(number));}
        catch (Exception e){
            Step step=new Step();
            step.setContent(content);
            step.setInstructionsId(instructionId);
            step.setNumber(number);
            step.setHeading("LOL");
            stepDao.save(step);
        }
    }
//    @RequestMapping(value = "/addTag", method = RequestMethod.GET)
//    public List<String> addtags(@RequestParam String tag){
//
//    }
}
