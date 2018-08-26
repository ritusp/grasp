package com.grasp.controller;

import com.grasp.util.Calculation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "main")

public class MainController {

    @RequestMapping(value = "index")
    public String index(Model model){

        model.addAttribute("title","Welcome to Math Basics !");

        return "index";
    }

    @RequestMapping(value = "practicetest", method = RequestMethod.GET)
    public String displayTestForm(Model model) {
        model.addAttribute("title", "Choose Topic");
        return "practicetest";
    }




    @RequestMapping(value = "result", method = RequestMethod.GET)
    public String displayResult(Model model){
        model.addAttribute("title", "Result");
        return "result";
    }
}
