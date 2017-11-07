package net.proselyte.springsecurityapp.controller;


import net.proselyte.springsecurityapp.dao.InstructionsDao;
import net.proselyte.springsecurityapp.dao.StepDao;
import net.proselyte.springsecurityapp.dao.UserDao;

import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.*;
import net.proselyte.springsecurityapp.validator.UserValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import twitter4j.auth.AccessToken;
import java.lang.*;
import java.sql.*;
import java.util.Random;



/*
Create Nikita Zhuchkevich
*/
@Controller
public class UserController {

    @Autowired
    public JsonService jsonService ;


    @Autowired
    private TwitterService twitterService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InstructionsDao instructionsDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    public String token = "";

    public String userNameForRePass = "";

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/login3", method = RequestMethod.POST)
    public String loginSubmit(String username, String password ,Model model) {
        try {
            securityService.autoLogin(username, password);
            User user = userService.findByUsername(username).get(0);
            System.out.println(user.getUsername());
        }
        catch (Exception e){
            model.addAttribute("error", "Username or password is incorrect.");
        }
        return "redirect:/userPage";
    }

    @RequestMapping(value = {"/confirmation{user}"}, method = RequestMethod.GET)
    public String Confirmation (@PathVariable("user")String user){
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        try {
            User user = userService.findByUsername(userForm.getUsername()).get(0);
        }catch (Exception e){
            token = "";
            createToken();
            Sender sender=new Sender("tester19990908@gmail.com","warhammer43");
            sender.send(userForm.getUsername(), "Вы зарегистрировались http://localhost:8087/varification/" + token + "/" + userForm.getUsername(),"tester19990908@gmail.com",userForm.getUsername());
            userForm.setEnabled(Boolean.FALSE);
            userService.save(userForm);
            return "/sendMessage";
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        return "/error";

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/project";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "root");
            String query = "select * from users, instructions where instructions.owner_id = users.id";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.last();
            String[][] information = new String[rs.getRow()][4];
            System.out.format("%s\n", rs.getRow());
            rs.first();
            int i = 0;
            do {
                information[i][0] = rs.getString("username");
                information[i][1] = rs.getString("name");
                information[i][2] = rs.getString("heading");
                information[i][3] = rs.getString("content");
                i++;
            }while (rs.next());
            model.addAttribute("information", information);
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

        model.addAttribute("instructions", instructionsDao.findAll());
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

        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/project";

            Class.forName(myDriver);
            try {
                Connection conn = DriverManager.getConnection(myUrl, "root", "root");
                Statement statement = conn.createStatement();


                ResultSet resultSet1 = statement.executeQuery("SELECT * FROM project.user_roles;");
                resultSet1.last();
                String roles[] = new String[resultSet1.getRow()];
                int i = 0;
                resultSet1.first();
                while(resultSet1.next()){
                    if(i == 0) resultSet1.first();
                    roles[i]=resultSet1.getString("role_id");
                    i++;
                }


                ResultSet resultSet = statement.executeQuery("SELECT * FROM project.users;");
                resultSet.last();
                String userName[] = new String[resultSet.getRow()];
                int crunch = resultSet.getRow()-1;
                int j = 0;
                resultSet.first();
                while (resultSet.next()){
                    if(j == 0) resultSet.first();
                    userName[j] = resultSet.getString("name");
                    if(resultSet.getString("name") == null)System.out.println(13);
                    if(j == crunch)break;
                    j++;
                }


                String inform[][] = new String[userName.length-1][2];
                for(int k = 0; k < userName.length-1 ; k++){
                    inform[k][0] = roles[k];
                    inform[k][1] = userName[k];
                }
                model.addAttribute("inform", inform);
            } catch (Exception e) {
                e.getMessage();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "admin";
    }


    @RequestMapping(value = "/startpage", method = RequestMethod.GET)
    public String startpage(Model model) { return "/startpage"; }

    @RequestMapping(value = "/twitter", method = RequestMethod.GET)
    public String twitter() throws  Exception{

        return "redirect:"+twitterService.getAuthURL();
    }
///////////////////////////////////////////////////////////////
    /////////////////////////////////////////

    @RequestMapping(value = "/userPage", method = RequestMethod.GET)
    public String add(Model model) {
        try {
            User user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
            model.addAttribute("currentUsername", user.getUsername());
            model.addAttribute("userName", user.getName());
            try {
                String myDriver = "com.mysql.jdbc.Driver";
                String myUrl = "jdbc:mysql://localhost/project";
                String sqlRequest = "select role_id from project.user_roles where user_id = " + user.getId() + ";";
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "root", "root");
                String query = sqlRequest;
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.last();
                resultSet.getRow();
                int i = resultSet.getInt("role_id");
                model.addAttribute("ROLE", i);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            return "/startpage";
        }

        return "/userPage";
    }



    @RequestMapping(value ="/tw", method = RequestMethod.GET)
    public String tw(@RequestParam("oauth_token") String oauth_token, @RequestParam("oauth_verifier") String oauth_verifier){
        AccessToken accessToken = twitterService.getAccessToken(oauth_verifier);
        System.out.println(accessToken.getScreenName());
        String pas=accessToken.getToken();
        User socialUser;
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


    @RequestMapping(value ="/vk", method = RequestMethod.GET)
    public String vk(){
        return "redirect:https://oauth.vk.com/authorize?client_id=5934856&display=page&redirect_uri=http://localhost:8087/example&response_type=code&v=5.62";
    }

    @RequestMapping(value ="/facebook", method = RequestMethod.GET)
    public String facebook(){
        return "redirect:https://www.facebook.com/v2.8/dialog/oauth?client_id=628809023972734&response_type=token&redirect_uri=http://localhost:8087/fk";
    }

    @RequestMapping(value ="/fk", method = RequestMethod.GET)
    public String fk(){
        return "oops";
    }

    @RequestMapping(value ="/fk1", method = RequestMethod.GET)
    public String fk1(String access_token){
        JSONObject jSONObject =  jsonService.readJsonFromUrl("https://graph.facebook.com/me?access_token="+access_token);
        User socialUser;
        if(userService.findByUsername(jSONObject.getString("id")).size()==0) {
            socialUser=new User();
            socialUser.setUsername(jSONObject.getString("id"));
            socialUser.setPassword(access_token);
            socialUser.setName(jSONObject.getString("name"));
            socialUser.setEnabled(Boolean.TRUE);
            userService.save(socialUser);
        }
        else{
            socialUser=userService.findByUsername(jSONObject.getString("id")).get(0);
            socialUser.setPassword(bCryptPasswordEncoder.encode(access_token));
            userDao.save(socialUser);
        }

        securityService.autoLogin(jSONObject.getString("id"), access_token);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public  String example(@RequestParam("code") String code){
        JSONObject jSONObject =  jsonService.readJsonFromUrl("https://oauth.vk.com/access_token?client_id=5934856&client_secret=EcwoQYo6Ypt47QxCv6SY&redirect_uri=http://localhost:8087/example&code="+code);
        String pas = jSONObject.getString("access_token");
        JSONObject jsonS=jsonService.readJsonFromUrl("https://api.vk.com/method/users.get?user_ids="+jSONObject.getLong("user_id"));
        User socialUser;
        if(userService.findByUsername(String.valueOf(jSONObject.getLong("user_id"))).size()==0) {
            socialUser=new User();
            socialUser.setUsername(String.valueOf(jSONObject.getLong("user_id")));
            socialUser.setPassword(jSONObject.getString("access_token"));
            socialUser.setName(((JSONObject)jsonS.getJSONArray("response").get(0)).getString("first_name")+ " " + ((JSONObject)jsonS.getJSONArray("response").get(0)).getString("last_name"));
            socialUser.setEnabled(Boolean.TRUE);
            userService.save(socialUser);
        }
        else{
            socialUser=userService.findByUsername(String.valueOf(jSONObject.getLong("user_id"))).get(0);
            socialUser.setPassword(bCryptPasswordEncoder.encode(jSONObject.getString("access_token")));
            userDao.save(socialUser);
        }

        securityService.autoLogin(String.valueOf(jSONObject.getLong("user_id")), pas);
        return "redirect:/welcome";
    }

    public void createToken(){
        String characters = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOASDFGHJKLZXCVBNM";
        Random random = new Random();
        for(int i = 0; i < 15; i++ )
            token += characters.charAt(random.nextInt(characters.length()));
    }

    @RequestMapping(value = "/varification/{token}/{userName}", method = RequestMethod.GET)
    public String varification(@PathVariable("token") String token_1, @PathVariable("userName") String userName_1){
        if (token_1.equals(token)){
            token = "";
            userName_1 +=".com";
            User user = userService.findByUsername(userName_1).get(0);
            user.setEnabled(Boolean.TRUE);
            userDao.save(user);
            return "/applyVarification";
        }else return "error";
    }

    @RequestMapping(value = "/createPass", method = RequestMethod.POST)
    public String newPass(String username){
        token="";
        createToken();
        userNameForRePass ="";
        Sender sender=new Sender("tester19990908@gmail.com","warhammer43");
        sender.send(username, "Ссылка для сброса пароля http://localhost:8087/newPass/" + token + "/" + username,"tester19990908@gmail.com",username);
        return "/sendMessage";
    }

    @RequestMapping(value = "/newPass/{token}/{userName}", method = RequestMethod.GET)
    public String createNewPass(@PathVariable("token") String token_1, @PathVariable("userName") String userName_1){
        if (token_1.equals(token)){
            token = "";
            userNameForRePass +=userName_1 + ".com";
            return "/newPassword";
        }else return "error";
    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.POST)
    public String changePassword(String password){
        User user = userService.findByUsername(userNameForRePass).get(0);
        user.setPassword(password);
        userService.save(user);
        return "/startpage";
    }

    @RequestMapping(value = "/inputEmail", method = RequestMethod.GET)
    public String inputEmail(){return "inputEmail";}

    @RequestMapping(value = "/makeAdmin", method = RequestMethod.GET)
    public void makeAdmen(String username) throws SQLException {
        try {
            User user = userService.findByUsername(username).get(0);
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/project";
            String sqlRequest = "update project.user_roles set role_id=2 where user_id= " + user.getId() + ";";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "root");
            String query = sqlRequest;
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

 }
