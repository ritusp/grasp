package com.grasp.controller;


import com.grasp.models.Result;
import com.grasp.models.User;
import com.grasp.models.data.ResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping(value = "result")
public class ResultController {

    @Autowired
    private ResultDao resultDao;

    @RequestMapping(value = "")
    public String displayResult(Model model, @SessionAttribute("user") User currentUserInSession)
    {
        List<Result> results = resultDao.findByUser_Id(currentUserInSession.getId());
        model.addAttribute("title", "Exam Result");
        model.addAttribute("results", results);


        return "/exam/resultList";
    }


}
