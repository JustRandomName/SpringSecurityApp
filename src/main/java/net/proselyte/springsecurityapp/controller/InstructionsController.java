package net.proselyte.springsecurityapp.controller;

import com.sun.org.apache.bcel.internal.generic.Instruction;
import net.proselyte.springsecurityapp.dao.*;
import net.proselyte.springsecurityapp.model.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


@Controller
public class InstructionsController {
    @Autowired
    private UserDao userDao;
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
    @Autowired
    private TagsDao tagsDao;
    @Autowired
    private InstrTagDao instrTagDao;
    @Autowired
    private AchivingsDao achivingsDao;
    @Autowired
    private UserAchivingsDao userAchivingsDao;

    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public String tagsCloud(Model model) {
        List<Tags> tags = tagsDao.findAll();
        Vector<String> tag = new Vector<>();
        Vector<Integer> counter = new Vector<>();
        for (int i = 0; i < tags.size(); i++) {
            tag.add(tags.get(i).getTag());
            counter.add(tags.get(i).getCounter());
        }
        model.addAttribute("counter", counter);
        model.addAttribute("tagsCloud", tag);
        return "/tag";
    }

    @RequestMapping(value = "/seeInstructions/{username}", method = RequestMethod.GET)
    public String see(@PathVariable("username") String username, Model model) {
        User user = userService.findByUsername(username).get(0);
        List<Instructions> instructions = instructionsDao.findAllByOwnerId(user.getId());
        model.addAttribute("instructions", instructions);
        return "/seeInstructions";
    }

    @RequestMapping(value = "/editInstruction/{instructionId}", method = RequestMethod.GET)
    public String edit(@PathVariable("instructionId") Long instructionId, Model model) {
        try {
            List<InstrTag> tagsList = instrTagDao.findAllByInstrId(Math.toIntExact(instructionId));
            String tags[] = new String[tagsList.size()];
            for (int i = 0; i < tagsList.size(); i++) {
                tags[i] = tagsList.get(i).getTagName();
            }
            List<Tags> tagsList1 = tagsDao.findAll();
            String tags1[] = new String[tagsList1.size()];
            for (int i = 0; i < tagsList1.size(); i++) {
                tags1[i] = tagsList1.get(i).getTag();
            }
            model.addAttribute("tags1", tags);
            model.addAttribute("tags2", tags1);
        } catch (Exception e) {
            e.getMessage();
        }
        Instructions instructions = instructionsDao.findById(instructionId).get(0);
        List<Step> steps = stepDao.findAllByInstructionsId(instructionId);
        model.addAttribute("instruction", instructions);
        model.addAttribute("steps", steps);
        return "/editInstruction";
    }

    @RequestMapping(value = "/editInstructions", method = RequestMethod.GET)
    @ResponseBody
    public void editInstructions(@RequestParam Long instructionId, @RequestParam String heading, @RequestParam String content, @RequestParam("tags") String tags) {
        String[] arr =  setTags(tags);
        List<InstrTag> instrTags = instrTagDao.findAllByInstrId(Math.toIntExact(instructionId));
        instrTagDao.delete(instrTags);
        for(int i = 0; i < arr.length; i++){
            InstrTag instrTag = new InstrTag();
            instrTag.setTagName(arr[i]);
            instrTag.setInstrId(Math.toIntExact(instructionId));
            instrTagDao.save(instrTag);
        }
        Instructions instructions = instructionsDao.findById(instructionId).get(0);
        instructions.setHeading(heading);
        instructions.setContent(content);
        instructionsDao.save(instructions);
    }

    @RequestMapping(value = "/editStep", method = RequestMethod.GET)
    public void editStep(@RequestParam Long instructionId, @RequestParam int number, @RequestParam String content) {
        number--;
        try {
            List<Step> steps = stepDao.findAllByInstructionsId(instructionId);

            steps.get(number).setContent(content);
            stepDao.save(steps.get(number));
        } catch (Exception e) {
            Step step = new Step();
            step.setContent(content);
            step.setInstructionsId(instructionId);
            step.setNumber(number + 1);
            step.setHeading("LOL");
            stepDao.save(step);
        }
    }

    @Autowired
    private LikeDao likeDao;

    @RequestMapping(value = "/addLike", method = RequestMethod.GET)
    public @ResponseBody
    int addLike(@RequestParam Long commentId) {
        Comments comments = commentsDao.findById(commentId);
        User user;
        try {
            user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
        } catch (Exception e) {
            return 0;
        }
        user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
        Like buf = likeDao.findByUserIdAndCommentId(user.getId(), commentId);
        User user1 = userDao.findById(user.getId());
        int i = achivingsDao.findAllByAchivName("likeGod").getThreshold();
        int j = user1.getNumderLikes() + 1;
        user1.setNumderLikes(j);
        if (j == achivingsDao.findAllByAchivName("likeGod").getThreshold()) {
            UserAchivings userAchivings = new UserAchivings();
            userAchivings.setAchiv("likeGod");
            userAchivings.setUserId(user1.getId());
            userAchivingsDao.save(userAchivings);
            userDao.save(user1);
        }
        if (buf == null) {

            comments.addLike();
            commentsDao.save(comments);
            Like like = new Like();
            like.setCommentId(commentId);
            like.setUserId(user.getId());
            likeDao.save(like);
        }
        return comments.getLikes();
    }

    @RequestMapping(value = "/deleteSteps", method = RequestMethod.GET)
    public void deleteSteps(@RequestParam Long instructionId, @RequestParam int number) {
        List<Step> steps = stepDao.findAllByInstructionsId(instructionId);
        for (int i = number; i < steps.size(); i++) {
            stepDao.delete(steps.get(i));
        }
    }

    @RequestMapping(value = "/createInstruction", method = RequestMethod.GET)
    public String add(Model model) {
        try {
            List<Tags> tagsList = tagsDao.findAll();
            String[] arr = new String[tagsList.size()];
            for (int i = 0; i < tagsList.size(); i++) {
                arr[i] = tagsList.get(i).getTag();
            }
            model.addAttribute("tags", arr);
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
    void block(@RequestParam String heading, @RequestParam String content, @RequestParam Integer owner, @RequestParam("tags") String tags) {
        String[] tg = setTags(tags);
        checkTags(tg);
        try {
            User user = userDao.findById(owner);
            user.setNumderInstr(user.getNumderInstr() + 1);

            if (achivingsDao.findAllByAchivName("instrGod").getThreshold() == user.getNumderInstr()) {
                UserAchivings userAchivings = new UserAchivings();
                userAchivings.setAchiv("instrGod");
                userAchivings.setUserId(user.getId());
                userDao.save(user);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        instructions = new Instructions();
        instructions.setHeading(heading);
        instructions.setContent(content);
        instructions.setOwnerId(owner);
        instructionsService.save(instructions);
        saveTag(Math.toIntExact(instructions.getId()), tg);

    }

    private void saveTag(int instrId, String[] tags) {
        for (int i = 0; i < tags.length; i++) {
            InstrTag instrTag = new InstrTag();
            instrTag.setTagName(tags[i]);
            instrTag.setInstrId(instrId);
            instrTagDao.save(instrTag);
        }
    }

    private String[] setTags(String tags) {
        return tags.split(",");
    }

    private void checkTags(String[] tags) {
        List<Tags> tagsList = tagsDao.findAll();
        for (int i = 0; i < tags.length; i++) {
            for (int j = 0; j < tagsList.size(); j++) {
                boolean k = tags[i].equals(tagsList.get(j).getTag());
                if (k == true) break;
                if ((j == tagsList.size() - 1) & (k == false)) {
                    Tags tags1 = new Tags();
                    tags1.setTag(tags[i]);
                    tagsDao.save(tags1);
                }
            }
        }
    }

    @Autowired
    private CommentsDao commentsDao;

    @RequestMapping(value = "/addComment", method = RequestMethod.GET)
    public @ResponseBody
    String addComment(@RequestParam Long instructionsId, @RequestParam String content) {
        User user;
        try {
            user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);

        } catch (Exception e) {
            return "0";
        }
        user.setNumberComments(user.getNumberComments() + 1);
        if (user.getNumberComments() == achivingsDao.findAllByAchivName("commentGod").getThreshold()) {
            UserAchivings userAchivings = new UserAchivings();
            userAchivings.setAchiv("commentGod");
            userAchivings.setUserId(user.getId());
            userDao.save(user);
        }
        Comments comments = new Comments();
        comments.setContent(content);
        comments.setOwnerId(user.getId());
        comments.setInstructionId(instructionsId);
        commentsDao.save(comments);
        return comments.getId().toString();
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
        List<Comments> comments = commentsDao.findAllByInstructionId(current);
        model.addAttribute("steps", steps);
        if (step == 0) {
            model.addAttribute("currentStep", null);
        } else {
            model.addAttribute("currentStep", steps.get(step - 1));
        }
        model.addAttribute("lastStep", steps.size());
        model.addAttribute("comments", comments);
        return "/viewInstruction";
    }

    @Autowired
    private InstructionsDao instructionsDao;

    @RequestMapping(value = "/searchInstructions", method = RequestMethod.GET)
    public String search(@RequestParam String search, Model model) {
        List<Instructions> instructions = instructionsDao.findAllByHeadingContainsOrContentContainsOrderByRatingDesc(search, search);
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
        try {
            User user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
            model.addAttribute("user", user.getId());
        } catch (Exception e) {
            int i = 0;
            model.addAttribute("user", i);
        }
        List<Step> steps = stepDao.findAllByInstructionsId(inst_id);
        model.addAttribute("id", inst_id);
        model.addAttribute("Steps", steps);
        int buffer = addMark(inst_id);
        model.addAttribute("naxyu", buffer);
        return "/viewAllSteps";
    }

    private int addMark(Long inst_id) {
        List<Rating> marks = ratingDao.findAllByInstrId(inst_id);

        int buffer = 0;
        for (int i = 0; i < marks.size(); i++) {
            buffer += marks.get(i).getMark();
        }
        if (marks.size() != 0) {
            buffer = buffer / marks.size();
        }
        return Math.toIntExact(buffer);
    }

    @RequestMapping(value = "/changeMark", method = RequestMethod.GET)
    public @ResponseBody
    int changeMark(@RequestParam("userId") Long userId, @RequestParam("instrId") Long instrId, @RequestParam("mark") int mark, Model model) {
        int k;
        try {
            Rating rating = ratingDao.findByUserIdAndInstrId(userId, instrId);
            k = addMark(instrId);
            model.addAttribute("val", k);
            if (rating == null) {
                throw new Exception();
            }
        } catch (Exception e) {

            Rating rating = new Rating();
            rating.setInstr_id(instrId);
            rating.setUser_id(userId);
            rating.setMark(mark);
            ratingDao.save(rating);
            Instructions instructions = instructionsDao.findAllById(instrId);
            instructions.setRating(rating.getMark());
            instructionsDao.save(instructions);
        }
        k = addMark(instrId);
        return k;
    }

    @RequestMapping(value = "/findInstructions", method = RequestMethod.GET)
    @ResponseBody
    public Vector<Instructions> findInstructions(@RequestParam("tags") String tags, Model model) {
        List<InstrTag> tagsList = instrTagDao.findAllByTagName(tags);
        Vector<Long> instructionsVector = new Vector<>();
        for (InstrTag aTagsList : tagsList) {
            instructionsVector.add(Long.valueOf(aTagsList.getInstrId()));
        }
        Vector<Instructions> instructions = new Vector<>();
        for (int i = 0; i < instructionsVector.size(); i++) {
            instructions.add(instructionsDao.findAllById(instructionsVector.get(i)));
        }
        return instructions;
    }


}