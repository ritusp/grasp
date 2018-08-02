package com.grasp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
@RequestMapping(value = "exam")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class ExamController {

    //Reading the property  numberOfQuestionInExam from application.properties
    @Value("${exam.numberOfQuestionInExam}")
    int numberOfQuestionInExam;

    @RequestMapping(value = "additionexam", method = RequestMethod.GET)
    public String add(Model model) {

        Random rand = new Random();
        int firstNumber = rand.nextInt(10);
        int secondNumber = rand.nextInt(10);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("correctAnswers", 0);
        model.addAttribute("totalQuestion", 0);
        model.addAttribute("title", "Addition Test");

        return "exam/additionexam";
    }

    @RequestMapping(value = "additionexam", method = RequestMethod.POST)
    public String checkAddResult(Model model, @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion") int totalQuestion, @RequestParam("answerByUser") int answerByUser, @RequestParam("firstNumber") int firstNumber, @RequestParam("secondNumber") int secondNumber) {

        int answer = firstNumber + secondNumber;

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);
            return "exam/result";
        }

        //Generate next set of number

        Random rand = new Random();
        firstNumber = rand.nextInt(10);
        secondNumber = rand.nextInt(10);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestion", totalQuestion);
        model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

        return "exam/additionexam";
    }

    @RequestMapping(value = "multiplicationexam", method = RequestMethod.GET)
    public String multiply(Model model) {

        Random rand = new Random();
        int firstNumber = rand.nextInt(10);
        int secondNumber = rand.nextInt(10);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("correctAnswers", 0);
        model.addAttribute("totalQuestion", 0);
        model.addAttribute("title", "Multiplication Test");

        return "exam/multiplicationexam";
    }

    @RequestMapping(value = "multiplicationexam", method = RequestMethod.POST)
    public String checkMultiplyResult(Model model, @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion") int totalQuestion, @RequestParam("answerByUser") int answerByUser, @RequestParam("firstNumber") int firstNumber, @RequestParam("secondNumber") int secondNumber) {

        int answer = firstNumber * secondNumber;

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);
            return "exam/result";
        }

        //Generate next set of number

        Random rand = new Random();
        firstNumber = rand.nextInt(10);
        secondNumber = rand.nextInt(10);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestion", totalQuestion);
        model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

        return "exam/multiplicationexam";
    }

}
