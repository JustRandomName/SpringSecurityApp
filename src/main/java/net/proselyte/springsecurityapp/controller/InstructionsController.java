package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.dao.InstructionsDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Instructions;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.InstructionsService;
import net.proselyte.springsecurityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

        @Controller
    public class InstructionsController {

        @Autowired
        private InstructionsService instructionsService;
        @Autowired
        private UserDao userDao;
        @Autowired
        private UserService userService;
        @RequestMapping(value = "/createInstruction", method = RequestMethod.GET)
        public String add(Model model) {
            model.addAttribute("instructionsForm", new Instructions());
            return "createInstruction";
        }

        @RequestMapping(value = "/createInstruction", method = RequestMethod.POST)
        public String add(@ModelAttribute("instructionsForm") Instructions instructionsForm, BindingResult bindingResult, Model model) {

            instructionsService.save(instructionsForm);
            User user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
            model.addAttribute("users",  userDao.findAll());
            model.addAttribute("currentUsername", user.getUsername());
            return "redirect:/login";
        }
    }
