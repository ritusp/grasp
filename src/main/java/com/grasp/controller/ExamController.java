package com.grasp.controller;

import com.grasp.models.Result;
import com.grasp.models.Topic;

import com.grasp.models.User;
import com.grasp.models.data.ResultDao;
import com.grasp.models.data.TopicDao;
import com.grasp.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping(value = "exam")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
//@SessionAttributes("examtopicid")
public class ExamController {

    //Reading the property  numberOfQuestionInExam from application.properties
    @Value("${exam.numberOfQuestionInExam}")
    int numberOfQuestionInExam;

    @Autowired
    private ResultDao resultDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="sampleTest", method = RequestMethod.GET)
            public String addSample(Model model) {
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
    @RequestMapping(value = "sampleTest", method = RequestMethod.POST)
    public String checkSampleResult(Model model, @Valid Result newResult,   @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion")int totalQuestion, @RequestParam("answerByUser") int answerByUser, @RequestParam("firstNumber")int firstNumber, @RequestParam("secondNumber") int secondNumber) {

        int answer = firstNumber + secondNumber;

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

            return "exam/sampleResult";
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

    @RequestMapping(value = "additionexam", method = RequestMethod.GET)
    public String add(Model model) {
     //   model.addAttribute("examtopicid","14");
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
    public String checkAddResult(@SessionAttribute("user") User currentUserInSession, Model model, @Valid Result newResult,   @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion")int totalQuestion, @RequestParam("answerByUser") int answerByUser, @RequestParam("firstNumber")int firstNumber, @RequestParam("secondNumber") int secondNumber) {

        int answer = firstNumber + secondNumber;

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

           Result result = new Result();
           result.setTotalQuestion(totalQuestion);
           result.setCorrectAnswer(correctAnswers);
           result.setUser(currentUserInSession);
           result.setTopic("Add");
         //   result.setTopic((Topic) topicDao.findById(examtopicid).get());
            resultDao.save(result);


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

        model.addAttribute("examtopicid","16");

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
    public String checkMultiplyResult(Model model, @SessionAttribute("user") User currentUserInSession, @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion") int totalQuestion, @RequestParam("answerByUser") int answerByUser, @RequestParam("firstNumber") int firstNumber, @RequestParam("secondNumber") int secondNumber) {

        int answer = firstNumber * secondNumber;

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

            Result result = new Result();
            result.setTotalQuestion(totalQuestion);
            result.setCorrectAnswer(correctAnswers);
            result.setUser(currentUserInSession);
            result.setTopic("Multiply");
            resultDao.save(result);

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
