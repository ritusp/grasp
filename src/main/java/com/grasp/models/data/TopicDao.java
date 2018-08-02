package com.grasp.models.data;


import com.grasp.models.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TopicDao extends CrudRepository<Topic, Integer> {

}
