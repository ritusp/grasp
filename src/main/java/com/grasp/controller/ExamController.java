package com.grasp.controller;

import com.grasp.mail.EmailServiceImpl;
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
import java.util.Timer;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "exam")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@SessionAttributes("startTimeOfExam")
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

    @Autowired
    public EmailServiceImpl emailService;

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

        long startTimeOfExam =System.currentTimeMillis();

        model.addAttribute("startTimeOfExam", startTimeOfExam);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("correctAnswers", 0);
        model.addAttribute("totalQuestion", 0);
        model.addAttribute("title", "Addition Test");

        return "exam/additionexam";
    }

    @RequestMapping(value = "additionexam", method = RequestMethod.POST)
    public String checkAddResult(@SessionAttribute("user") User currentUserInSession, @SessionAttribute("startTimeOfExam") long startTimeOfExam, Model model, @Valid Result newResult,   @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion")int totalQuestion, @RequestParam("answerByUser") int answerByUser, @RequestParam("firstNumber")int firstNumber, @RequestParam("secondNumber") int secondNumber) {

        int answer = firstNumber + secondNumber;

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

            long endTimeOfExam = System.currentTimeMillis();

            //Calculate the total time taken in Seconds.
            long totalTimeTaken = (endTimeOfExam - startTimeOfExam)/1000 ;

            Result result = new Result();
           result.setTotalQuestion(totalQuestion);
           result.setCorrectAnswer(correctAnswers);
           result.setUser(currentUserInSession);
           result.setTopic("Add");
           result.setTotalTimeTaken(totalTimeTaken);

            model.addAttribute("result", result);

         //   result.setTopic((Topic) topicDao.findById(examtopicid).get());
            resultDao.save(result);
            emailService.sendSimpleMessage(currentUserInSession.getEmail(),"Exam Result ", result.toString());

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

        long startTimeOfExam =System.currentTimeMillis();

        model.addAttribute("startTimeOfExam", startTimeOfExam);

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
    public String checkMultiplyResult(Model model, @SessionAttribute("user") User currentUserInSession, @SessionAttribute("startTimeOfExam") long startTimeOfExam, @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion") int totalQuestion, @RequestParam("answerByUser") int answerByUser, @RequestParam("firstNumber") int firstNumber, @RequestParam("secondNumber") int secondNumber) {

        int answer = firstNumber * secondNumber;

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

            long endTimeOfExam = System.currentTimeMillis();
            //Calculate the total time taken in Seconds.
            long totalTimeTaken = (endTimeOfExam - startTimeOfExam)/1000 ;

            Result result = new Result();
            result.setTotalQuestion(totalQuestion);
            result.setCorrectAnswer(correctAnswers);
            result.setUser(currentUserInSession);
            result.setTopic("Multiply");
            result.setTotalTimeTaken(totalTimeTaken);

            model.addAttribute("result", result);
            resultDao.save(result);

            emailService.sendSimpleMessage(currentUserInSession.getEmail(),"Exam Result ", result.toString());

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



    @RequestMapping(value = "subtractionexam", method = RequestMethod.GET)
    public String subtract(Model model) {

        model.addAttribute("examtopicid","15");

        Random rand = new Random();
        int firstNumber = rand.nextInt(10);
        int secondNumber = rand.nextInt(10);
        int temp = 0;
        if(secondNumber > firstNumber){
            temp = firstNumber;
            firstNumber = secondNumber;
            secondNumber = temp;
        }

        long startTimeOfExam =System.currentTimeMillis();

        model.addAttribute("startTimeOfExam", startTimeOfExam);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("correctAnswers", 0);
        model.addAttribute("totalQuestion", 0);
        model.addAttribute("title", "Subtraction Test");

        return "exam/subtractionexam";
    }

    @RequestMapping(value = "subtractionexam", method = RequestMethod.POST)
    public String checkSubtractResult(Model model, @SessionAttribute("user") User currentUserInSession, @SessionAttribute("startTimeOfExam") long startTimeOfExam, @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion") int totalQuestion, @RequestParam("answerByUser") int answerByUser, @RequestParam("firstNumber") int firstNumber, @RequestParam("secondNumber") int secondNumber) {

        int answer = firstNumber - secondNumber;

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

            long endTimeOfExam = System.currentTimeMillis();

            //Calculate the total time taken in Seconds.
            long totalTimeTaken = (endTimeOfExam - startTimeOfExam)/1000 ;

            Result result = new Result();
            result.setTotalQuestion(totalQuestion);
            result.setCorrectAnswer(correctAnswers);
            result.setUser(currentUserInSession);
            result.setTopic("Subtract");
            result.setTotalTimeTaken(totalTimeTaken);
            model.addAttribute("result", result);
            resultDao.save(result);

            emailService.sendSimpleMessage(currentUserInSession.getEmail(),"Exam Result ", result.toString());

            return "exam/result";
        }

        //Generate next set of number

        Random rand = new Random();
        firstNumber = rand.nextInt(10);
        secondNumber = rand.nextInt(10);

        int temp = 0;
        if(secondNumber > firstNumber){
            temp = firstNumber;
            firstNumber = secondNumber;
            secondNumber = temp;
        }

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestion", totalQuestion);
        model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

        return "exam/subtractionexam";
    }

    @RequestMapping(value = "divisionexam", method = RequestMethod.GET)
    public String divide(Model model) {

        model.addAttribute("examtopicid","17");

        Random rand = new Random();
        int firstNumber = rand.nextInt(10);
        while ( firstNumber == 0){
            firstNumber = rand.nextInt(10);
        }
        int secondNumber = rand.nextInt(10);
        while (secondNumber == 0){
            secondNumber = rand.nextInt(10);
        }
        int temp = 0;
        secondNumber = firstNumber * secondNumber;
        if(secondNumber > firstNumber){
            temp = firstNumber;
            firstNumber = secondNumber;
            secondNumber = temp;
        }
        long startTimeOfExam =System.currentTimeMillis();

        model.addAttribute("startTimeOfExam", startTimeOfExam);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("correctAnswers", 0);
        model.addAttribute("totalQuestion", 0);
        model.addAttribute("title", "Division Test");

        return "exam/divisionexam";
    }

    @RequestMapping(value = "divisionexam", method = RequestMethod.POST)
    public String checkDivisionResult(Model model, @SessionAttribute("user") User currentUserInSession, @SessionAttribute("startTimeOfExam") long startTimeOfExam, @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion") int totalQuestion, @RequestParam("answerByUser") int answerByUser,  @RequestParam("firstNumber") int firstNumber, @RequestParam("secondNumber") int secondNumber) {

        int answer = firstNumber / secondNumber;
        int remainder = firstNumber % secondNumber;

        if((answerByUser == answer)){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

            long endTimeOfExam = System.currentTimeMillis();

            //Calculate the total time taken in Seconds.
            long totalTimeTaken = (endTimeOfExam - startTimeOfExam)/1000 ;

            Result result = new Result();
            result.setTotalQuestion(totalQuestion);
            result.setCorrectAnswer(correctAnswers);
            result.setUser(currentUserInSession);
            result.setTopic("Division");
            result.setTotalTimeTaken(totalTimeTaken);
            model.addAttribute("result", result);
            resultDao.save(result);

            emailService.sendSimpleMessage(currentUserInSession.getEmail(),"Exam Result ", result.toString());

            return "exam/result";
        }

        //Generate next set of number

        Random rand = new Random();
        firstNumber = rand.nextInt(10);
        while (firstNumber == 0){
            firstNumber = rand.nextInt(10);
        }
        secondNumber = rand.nextInt(10);
        while(secondNumber == 0){
            secondNumber = rand.nextInt(10);
        }
        int temp = 0;
        secondNumber = firstNumber * secondNumber;
        if(secondNumber > firstNumber){
            temp = firstNumber;
            firstNumber = secondNumber;
            secondNumber = temp;
        }
        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestion", totalQuestion);
        model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

        return "exam/divisionexam";
    }

    @RequestMapping(value = "squarerootexam", method = RequestMethod.GET)
    public String squareroot(Model model) {

        model.addAttribute("examtopicid","19");

        Random rand = new Random();
        int firstNumber = rand.nextInt(10);
        firstNumber = firstNumber * firstNumber;

        long startTimeOfExam =System.currentTimeMillis();

        model.addAttribute("startTimeOfExam", startTimeOfExam);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("correctAnswers", 0);
        model.addAttribute("totalQuestion", 0);
        model.addAttribute("title", "Square Root Test");

        return "exam/squarerootexam";
    }

    @RequestMapping(value = "squarerootexam", method = RequestMethod.POST)
    public String checkSquarerootResult(Model model, @SessionAttribute("user") User currentUserInSession, @SessionAttribute("startTimeOfExam") long startTimeOfExam, @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion") int totalQuestion, @RequestParam("answerByUser") double answerByUser, @RequestParam("firstNumber") int firstNumber) {

        double answer = Math.sqrt(firstNumber);

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

            long endTimeOfExam = System.currentTimeMillis();

            //Calculate the total time taken in Seconds.
            long totalTimeTaken = (endTimeOfExam - startTimeOfExam)/1000 ;

            Result result = new Result();
            result.setTotalQuestion(totalQuestion);
            result.setCorrectAnswer(correctAnswers);
            result.setUser(currentUserInSession);
            result.setTopic("Square Root");
            result.setTotalTimeTaken(totalTimeTaken);
            model.addAttribute("result", result);
            resultDao.save(result);

            emailService.sendSimpleMessage(currentUserInSession.getEmail(),"Exam Result ", result.toString());

            return "exam/result";
        }

        //Generate next set of number

        Random rand = new Random();
        firstNumber = rand.nextInt(10);
        firstNumber = firstNumber * firstNumber;

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestion", totalQuestion);
        model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

        return "exam/squarerootexam";
    }

    @RequestMapping(value = "squareexam", method = RequestMethod.GET)
    public String square(Model model) {

        model.addAttribute("examtopicid","18");

        Random rand = new Random();
        int firstNumber = rand.nextInt(10);

        long startTimeOfExam =System.currentTimeMillis();

        model.addAttribute("startTimeOfExam", startTimeOfExam);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("correctAnswers", 0);
        model.addAttribute("totalQuestion", 0);
        model.addAttribute("title", "Square Test");

        return "exam/squareexam";
    }

    @RequestMapping(value = "squareexam", method = RequestMethod.POST)
    public String checkSquareResult(Model model, @SessionAttribute("user") User currentUserInSession, @SessionAttribute("startTimeOfExam") long startTimeOfExam, @RequestParam("correctAnswers") int correctAnswers, @RequestParam("totalQuestion") int totalQuestion, @RequestParam("answerByUser") double answerByUser, @RequestParam("firstNumber") int firstNumber) {

        double answer = firstNumber * firstNumber;

        if(answerByUser == answer){
            correctAnswers++;
        }
        totalQuestion++;

        if(totalQuestion == numberOfQuestionInExam ) {
            model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

            long endTimeOfExam = System.currentTimeMillis();

            //Calculate the total time taken in Seconds.
            long totalTimeTaken = (endTimeOfExam - startTimeOfExam)/1000 ;

            Result result = new Result();
            result.setTotalQuestion(totalQuestion);
            result.setCorrectAnswer(correctAnswers);
            result.setUser(currentUserInSession);
            result.setTopic("Square");
            result.setTotalTimeTaken(totalTimeTaken);
            model.addAttribute("result", result);
            resultDao.save(result);

            emailService.sendSimpleMessage(currentUserInSession.getEmail(),"Exam Result ", result.toString());

            return "exam/result";
        }

        //Generate next set of number

        Random rand = new Random();
        firstNumber = rand.nextInt(10);

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestion", totalQuestion);
        model.addAttribute("title", "Result : "+correctAnswers + " / " + totalQuestion);

        return "exam/squareexam";
    }

}
