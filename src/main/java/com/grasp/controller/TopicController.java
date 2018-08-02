package com.grasp.controller;


import com.grasp.models.Topic;
import com.grasp.models.data.TopicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "topic")
public class TopicController {

    @Autowired
    private TopicDao topicDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayAddNewTopic(Model model) {

        model.addAttribute("title", "Add Topic");
        model.addAttribute(new Topic());
        return "topic";
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processAddNewTopic(Model model, @ModelAttribute @Valid Topic topic, Errors errors){

        Topic topic1 = new Topic();
        topic1.setName(topic.getName());
            topicDao.save(topic1);
            model.addAttribute("topic", topic);
            model.addAttribute(new Topic());
            return "topic";
        }
}