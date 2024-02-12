package com.example.ems.repository;

import com.example.ems.entity.TaskAssignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskAssignRepo extends JpaRepository<TaskAssignEntity, Integer> {

    List<TaskAssignEntity> findByEmployeeEntityEmpId(int empId);

    List<TaskAssignEntity> findByTaskPriorityEntityPriorityId(int priorityId);

}
