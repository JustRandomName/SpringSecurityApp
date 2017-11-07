package net.proselyte.springsecurityapp.controller;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import net.proselyte.springsecurityapp.dao.InstructionsDao;
import net.proselyte.springsecurityapp.dao.StepDao;
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
import sun.plugin.dom.core.Document;
import com.itextpdf.*;




import java.util.List;

@Controller
public class InstructionsController {

    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    private InstructionsDao instructionsDao;
    @Autowired
    private StepService stepService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private StepDao stepDao;

    @RequestMapping(value = "/createInstruction", method = RequestMethod.GET)
    public String add(Model model) {
        try {
            User user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
            model.addAttribute("currentId", user.getId());
        }catch (Exception e){
            return "login";
        }
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

    @RequestMapping(value = "/toPDF/{id}", method = RequestMethod.GET)
    public void createPDF(@PathVariable("id") int id){
        List<Step> steps = stepDao.findAllByInstructionsId(id);
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        Paragraph paragraph = new Paragraph();
        document.open();
        paragraph.add(steps.toString());
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();


    }
    @RequestMapping(value="/viewInstruction/{current}/{step}",method = RequestMethod.GET)
    public String viewInstr(@PathVariable("current") int current,@PathVariable int step, Model model){
        model.addAttribute("current",current);
        Instructions instructions=instructionsService.findById(current).get(0);
        model.addAttribute("instruction",instructions);
        List<Step> steps=stepDao.findAllByInstructionsId(current);
        model.addAttribute("steps",steps);
        model.addAttribute("currentStep",steps.get(step));
        model.addAttribute("lastStep",steps.size());
        return "/viewInstruction";
    }

    @RequestMapping(value = "/seeInstructions/{currentUsername}", method = RequestMethod.GET)
    public String seeInstr(@PathVariable("currentUsername") String username, Model model){
        try {
            User user = userDao.findByUsername(username).get(0);
            int i  = Math.toIntExact(user.getId());
            List<Instructions> instructions = instructionsDao.findAllByOwnerId(i);
            model.addAttribute("instructions", instructions);
        }catch (Exception e){
            e.getMessage();
        }
        return "userPage";
    }

    @RequestMapping(value = "/editingInstructions/{instructionsId}", method = RequestMethod.GET)
    public String editingInstructions(@PathVariable("instructionsId") int instId, Model model){
        try {
            List<Step> steps = stepDao.findAllByInstructionsId(instId);
            model.addAttribute("instrForEditing", steps);
        }catch (Exception e){
            e.getMessage();
        }
        return ""; //TODO: add view
    }

    @RequestMapping(value = "/saveEditing/{}")
    public  String saveEditing(@RequestParam List<Step> steps){
        for(int i = 0; i < steps.size(); i++){
            stepService.save(steps.get(i));
        }
        return  "userPage";
    }

//----------------
}
