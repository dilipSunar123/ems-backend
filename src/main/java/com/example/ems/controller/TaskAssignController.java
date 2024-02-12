package com.example.ems.controller;

import com.example.ems.entity.EmployeeEntity;
import com.example.ems.entity.TaskAssignEntity;
import com.example.ems.entity.TaskPriorityEntity;
import com.example.ems.repository.EmployeeRepo;
import com.example.ems.repository.TaskAssignRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TaskAssignController {

    @Autowired
    private TaskAssignRepo taskAssignRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    private final JavaMailSender mailSender;

    @Autowired
    public TaskAssignController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/findByEmployeeEntityEmpId/{empId}")
    private List<TaskAssignEntity> findByEmployeeEntityEmpId (@PathVariable int empId) {
        return taskAssignRepo.findByEmployeeEntityEmpId(empId);
    }

    @PostMapping("/addTask")
    private ResponseEntity<?> addTask (@RequestBody TaskAssignEntity entity) {

        TaskAssignEntity myEntity = new TaskAssignEntity();

        myEntity.setTaskTitle(entity.getTaskTitle());
        myEntity.setTaskDesc(entity.getTaskDesc());
        myEntity.setStatus("Pending");
        myEntity.setEmployeeEntity(entity.getEmployeeEntity());
        myEntity.setTaskPriorityEntity(entity.getTaskPriorityEntity());
        myEntity.setAssignedBy(entity.getAssignedBy());
        myEntity.setStartDate(entity.getStartDate());
        myEntity.setEndDate(entity.getEndDate());

//        LocalDateTime localDateTime = LocalDateTime.now();
//        myEntity.setStartDate(String.valueOf(localDateTime));   //
//        myEntity.setEndDate(String.valueOf(localDateTime)); //

        taskAssignRepo.save(myEntity);

        return ResponseEntity.ok("New Task Assigned");
    }

    @GetMapping("/sendProgressMail/{progress}/{taskId}")
    private TaskAssignEntity sendProgressMail (@PathVariable String progress, @PathVariable int taskId) {

        TaskAssignEntity entity = taskAssignRepo.getReferenceById(taskId);

        int managerId = entity.getAssignedBy();
        EmployeeEntity managerEntity = employeeRepo.getReferenceById(managerId);

        String managerEmailId = managerEntity.getEmail();

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Progress Update - Task");
        message.setText("Hello " + managerEntity.getEmp_name() + "\n\n" + entity.getEmployeeEntity().getEmp_name() + "has done a progress - " + progress);
        message.setTo(managerEmailId);

        mailSender.send(message);


        return entity;
    }

    @PutMapping("/changeStatus/{taskId}/{status}")
    private ResponseEntity<?> changeStatus (@PathVariable int taskId, @PathVariable String status) {

        TaskAssignEntity entity = taskAssignRepo.getReferenceById(taskId);

        entity.setStatus(status);   // updated one
        entity.setEmployeeEntity(entity.getEmployeeEntity());
        entity.setTaskTitle(entity.getTaskTitle());
        entity.setTaskDesc(entity.getTaskDesc());
        entity.setStartDate(entity.getStartDate());
        entity.setEndDate(entity.getEndDate());
        entity.setTaskId(entity.getTaskId());
        entity.setAssignedBy(entity.getAssignedBy());

        taskAssignRepo.save(entity);

        // send status change mail
        EmployeeEntity managerEntity = employeeRepo.getReferenceById(entity.getAssignedBy());
        EmployeeEntity employeeEntity = entity.getEmployeeEntity();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(managerEntity.getEmail());    // manager

        if (status.equals("Completed")) {
            message.setSubject("Task Completed Report");
            message.setText("Hello " + managerEntity.getEmp_name() + "\n\nMr/Mrs " + employeeEntity.getEmp_name() +
                    " has successfully completed his/her work. The progress of the work is : " + status +
                    "\nComplete details of the task are - \n\n" +
                    "Task ID - " + entity.getTaskId() +
                    "\nAssigned by - " + managerEntity.getEmp_name() +
                    "\nTask start date - " + entity.getStartDate() +
                    "\nTask end date - " + entity.getEndDate() +
                    "\nStatus - " + entity.getStatus() +
                    "\nTask title - " + entity.getTaskTitle() +
                    "\nTask Description - " + entity.getTaskDesc());

            mailSender.send(message);

            return ResponseEntity.ok("Status Changed");
        }

        message.setSubject("Task Progress");
        message.setText("Hello " + managerEntity.getEmp_name() + "\n\nMr/Mrs " + employeeEntity.getEmp_name() +
                " is doing good with his/her work. The status of the work is : " + status +
                "\nComplete details of the task are - \n\n" +
                "Task ID - " + entity.getTaskId() +
                "\nAssigned by - " + managerEntity.getEmp_name() +
                "\nTask start date - " + entity.getStartDate() +
                "\nTask end date - " + entity.getEndDate() +
                "\nStatus - " + entity.getStatus() +
                "\nTask title - " + entity.getTaskTitle() +
                "\nTask Description - " + entity.getTaskDesc());

        mailSender.send(message);

        return ResponseEntity.ok("Status Changed");
    }

    // Task completed update
//    @GetMapping("/sendEmailToManager/{taskId}/{progress}")
//    public ResponseEntity<?> sendEmailToManager(@PathVariable int taskId, @PathVariable String progress) {
//
//        TaskAssignEntity entity = taskAssignRepo.getReferenceById(taskId);
//        EmployeeEntity managerEntity = employeeRepo.getReferenceById(entity.getAssignedBy());
//        EmployeeEntity employeeEntity = entity.getEmployeeEntity();
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(managerEntity.getEmail());    // manager
//
//
//
//        return ResponseEntity.ok("Email sent successfully");
//    }

    @GetMapping("/findByPriorityId/{priorityId}")
    private List<TaskAssignEntity> findByPriorityId (@PathVariable int priorityId) {
        return taskAssignRepo.findByTaskPriorityEntityPriorityId(priorityId);
    }

    @PutMapping("/updatePriorityLevel/{taskId}/{priorityId}")
    private String updatePriorityLevel (@PathVariable int taskId, @PathVariable int priorityId) {

        TaskAssignEntity entity = taskAssignRepo.getReferenceById(taskId);

        TaskAssignEntity updatedEntity = new TaskAssignEntity();

        updatedEntity.setTaskId(entity.getTaskId());
        updatedEntity.setTaskTitle(entity.getTaskTitle());
        updatedEntity.setTaskDesc(entity.getTaskDesc());
        updatedEntity.setStartDate(entity.getStartDate());
        updatedEntity.setEndDate(entity.getEndDate());
        updatedEntity.setStatus(entity.getStatus());
        updatedEntity.setEmployeeEntity(entity.getEmployeeEntity());
        updatedEntity.setAssignedBy(entity.getAssignedBy());

        TaskPriorityEntity priorityEntity = new TaskPriorityEntity();
        priorityEntity.setPriorityId(priorityId);

        updatedEntity.setTaskPriorityEntity(priorityEntity);    // updated one

        taskAssignRepo.save(updatedEntity);

        return "Priority updated successfully";

    }

}
