package com.ssafy.travelquest.domain.user.repository;

import com.ssafy.travelquest.domain.user.entity.JobClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobClassRepository {

    List<JobClass> findAll();

    JobClass findByCode(@Param("code") String code);
}

