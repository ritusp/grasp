package com.grasp.controller;

import com.grasp.models.User;
import com.grasp.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("user")
@SessionAttributes("user")
public class UserController {

    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "register")
    public String displayAddNewUser(Model model) {

        model.addAttribute("title", "Sign up");
        model.addAttribute(new User());
        return "user/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processAddNewUser(Model model, @ModelAttribute @Valid User user, Errors errors, String verify){
        if(verify.equals(user.getPassword())){


            userDao.save(user);
            model.addAttribute("title","Welcome ");
            model.addAttribute("User", user);
            return "user/index";
        }
        else{
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("title", "Sign up");
            model.addAttribute("message", "Passwords do not match");
            return "user/register";
        }
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title","Sign in");
        model.addAttribute(new User());
        return "user/login";

    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String displayLogin(Model model, @ModelAttribute User user, Errors errors) {

        User userFromDB;

        Optional<User> optionalUser = userDao.findByUsername(user.getUsername());

        if(optionalUser.isPresent()){
            userFromDB = optionalUser.get();

        }else{
            model.addAttribute("title","Try again : wrong username ");
            model.addAttribute("user", user );
            return "user/login";
        }

        if(user.getUsername().equalsIgnoreCase(userFromDB.getUsername()) && user.getPassword().equals(userFromDB.getPassword())){
            model.addAttribute("title","Welcome ");
            model.addAttribute("user", userFromDB );
            return "user/index";
        }else{
            model.addAttribute("title","Try again : wrong username / password");
            model.addAttribute("user", user );
            return "user/login";
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logOut(@SessionAttribute("user") User currentUserInSession, Model model){
        currentUserInSession = null;
        model.addAttribute("user",currentUserInSession);
        return "index";
    }

}