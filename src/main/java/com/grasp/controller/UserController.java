package com.grasp.controller;

import com.grasp.models.User;
import com.grasp.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")

public class UserController {

    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "register")
    public String displayAddNewUser(Model model) {

        model.addAttribute("title", "Register");
        model.addAttribute(new User());
        return "user/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processAddNewUser(Model model, @ModelAttribute @Valid User user, Errors errors, String verify){
        if(verify.equals(user.getPassword())){
            userDao.save(user);
            model.addAttribute("User", user);
            return "user/index";
        }
        else{
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("title", "Register");
            model.addAttribute("message", "Passwords do not match");
            return "user/register";
        }
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {

        model.addAttribute("title","Login Page");


        return "user/login";

    }
}