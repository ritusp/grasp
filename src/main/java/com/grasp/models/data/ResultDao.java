package com.grasp.models.data;


import com.grasp.models.Result;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ResultDao extends CrudRepository<Result, Integer> {

    @Query(value = "SELECT * FROM result  WHERE user_id =:userId",nativeQuery = true)
    public List<Result> findByUser_Id(@Param("userId") int userId);
}
