package com.grasp.controller;

import com.grasp.util.Calculation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller


public class MainController {

    @RequestMapping(value = "")
    public String index(Model model){

        model.addAttribute("title","Welcome to Math Basics !");

        return "index";
    }

    @RequestMapping(value = "practicetest", method = RequestMethod.GET)
    public String displayTestForm(Model model) {
        model.addAttribute("title", "Test");
        return "practicetest";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

  /*      Calculation calculation = new Calculation();

        String operation;

        List<String>  results = calculation.results(operation);

        if(results == null ){
            //TODO:
        }


        model.addAttribute("title", "Addition");

        return "calculation"; */
        return "add";
    }


    @RequestMapping(value = "result", method = RequestMethod.GET)
    public String displayResult(Model model){
        model.addAttribute("title", "Result");
        return "result";
    }
}
