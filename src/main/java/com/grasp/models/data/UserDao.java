package com.grasp.models.data;

import com.grasp.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer>{

   // Optional<User> findByUsername();
}
