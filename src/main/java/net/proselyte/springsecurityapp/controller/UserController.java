package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.SecurityService;
import net.proselyte.springsecurityapp.service.TwitterService;
import net.proselyte.springsecurityapp.service.UserService;
import net.proselyte.springsecurityapp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import twitter4j.auth.AccessToken;
import java.lang.String;




/*
Create Nikita Zhuchkevich
*/
@Controller
public class UserController {

    @Autowired
    private TwitterService twitterService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }


    @RequestMapping(value = "/startpage", method = RequestMethod.GET)
    public String startpage(Model model) {
        return "/startpage";
    }

    @RequestMapping(value = "/twitter", method = RequestMethod.GET)
    public String twitter() throws  Exception{
        return "redirect:"+twitterService.getAuthURL();
    }

    @RequestMapping(value ="/tw", method = RequestMethod.GET)
    public String tw(@RequestParam("oauth_token") String oauth_token, @RequestParam("oauth_verifier") String oauth_verifier){
        AccessToken accessToken = twitterService.getAccessToken(oauth_verifier);
        System.out.println(accessToken.getScreenName());
        String pas=accessToken.getToken();
        User socialUser;
        String str = "sefsfe";
        if (userService.findByUsername(String.valueOf(String.valueOf(accessToken.getUserId()))).size() == 0) {
            socialUser = new User();
            socialUser.setUsername(String.valueOf(accessToken.getUserId()));
            socialUser.setPassword(accessToken.getToken());
            socialUser.setName(accessToken.getScreenName());
            socialUser.setEnabled(Boolean.TRUE);
            userService.save(socialUser);
        } else {
            socialUser = userService.findByUsername(String.valueOf(accessToken.getUserId())).get(0);
            socialUser.setPassword(bCryptPasswordEncoder.encode(accessToken.getToken()));
            userDao.save(socialUser);
        }

        securityService.autoLogin(String.valueOf(accessToken.getUserId()), pas);

        return "redirect:/welcome";
    }
}
