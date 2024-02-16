package com.example.ems.controller;

import com.example.ems.entity.*;
import com.example.ems.repository.EmployeeRepo;
import com.example.ems.repository.LeaveListRepo;
import com.example.ems.repository.TotalLeavesRepo;
import jakarta.mail.Multipart;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
public class EmployeeController {

    @Autowired
    public EmployeeRepo registerRepo;

    @Autowired
    public TotalLeavesRepo totalLeavesRepo;

    @Autowired
    private LeaveListRepo leaveListRepo;
    private final JavaMailSender javaMailSender;

    String pw;

    @Autowired
    public EmployeeController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // return all the employees in the org.
    @GetMapping("/getAllEmployees")
    private List<EmployeeEntity> getAllEmployees() {
        return registerRepo.findAll();
    }

    @GetMapping("/findEmployeeById/{empId}")
    private EmployeeEntity findEmployeeById(@PathVariable int empId) {
//        System.out.println(registerRepo.getReferenceById(empId));

        List<EmployeeEntity> employeeEntityList = registerRepo.findAll();

        for (EmployeeEntity entity: employeeEntityList) {
            if (entity.getEmpId() == empId)
                return entity;
        }

        return null;
    }


//    @GetMapping("/getAllEmployees")
//    private List<EmployeeDto> getAllEmployees() {
//        List<EmployeeEntity> employeeEntityList = registerRepo.findAll();
//
//        return employeeEntityList.stream().map(EmployeeDto::new).collect(Collectors.toList());
//    }


    // register a new employee
//    @PostMapping("/addEmployee")
//    private ResponseEntity<?> addEmployee(@RequestBody EmployeeEntity entity) {
//        // when a user is registered, he/she will be a normal employee
//        EmployeeTypeEntity employeeType = new EmployeeTypeEntity();
//
//        // employee is a manager
//        if (entity.getEmployeeType().getemployeeTypeId() == 2) {
//            employeeType.setemployeeTypeId(2);
//        } else {
//            // normal employee
//            employeeType.setemployeeTypeId(1);
//        }
//
//        JobRoleEntity jobRole = new JobRoleEntity();
//        jobRole.setJob_id(3);
//
//        entity.setEmployeeType(employeeType);
//        entity.setJobRoleEntity(jobRole);
//        entity.setDoj(LocalDateTime.now());
//        entity.setPassword(generateRandomPassword());
//        entity.setApprovalStatus(true);
//
//
//        registerRepo.save(entity);
//        sendEmail(entity.getEmail(), entity);
//
//        return ResponseEntity.ok("Employee added");
//    }

    @PostMapping("/addEmployee")
    private ResponseEntity<?> addEmployee(@RequestBody EmployeeEntity entity) {
        JobRoleEntity jobRole = new JobRoleEntity();
        jobRole.setJob_id(3);

        entity.setJobRoleEntity(jobRole);
        entity.setDoj(LocalDateTime.now());
        entity.setPassword(generateRandomPassword());
        entity.setApprovalStatus(true);
        entity.setProfilePicture(null);

        EmployeeEntity savedEmployee = registerRepo.save(entity);

        sendEmail(entity.getEmail(), entity);

        LeaveList leaveList =  new LeaveList();
        leaveList.setEmployeeEntity(savedEmployee);

        Map<Integer, Integer> leaveMap = new HashMap<>();
        leaveMap.put(1, 12); // January
        leaveMap.put(2, 11); // February
        leaveMap.put(3, 10);
        leaveMap.put(4, 9);
        leaveMap.put(5, 8);
        leaveMap.put(6, 7);
        leaveMap.put(7, 6);
        leaveMap.put(8, 5);
        leaveMap.put(9, 4);
        leaveMap.put(10, 3);
        leaveMap.put(11, 2);
        leaveMap.put(12, 1);

        int month = LocalDateTime.now().getMonthValue();
        // 0 is the default value if month is not found in the map
        int leaveCount = leaveMap.getOrDefault(month, 0);

        // Set leave counts
        leaveList.setSickLeave(leaveCount);
        leaveList.setFloaterLeave(leaveCount);
        leaveList.setPaidLeave(leaveCount);
        leaveList.setMaternityLeave(leaveCount);
        leaveList.setPaternityLeave(leaveCount);
        leaveList.setCasualLeave(leaveCount);


        TotalLeaves totalLeaves = new TotalLeaves();

        totalLeaves.setSickLeave(leaveCount);
        totalLeaves.setFloaterLeave(leaveCount);
        totalLeaves.setPaidLeave(leaveCount);
        totalLeaves.setMaternityLeave(leaveCount);
        totalLeaves.setPaternityLeave(leaveCount);
        totalLeaves.setCasualLeave(leaveCount);
        totalLeaves.setEmployeeEntity(savedEmployee);


        leaveListRepo.save(leaveList);
        totalLeavesRepo.save(totalLeaves);

        return ResponseEntity.ok("Employee added");
    }

    @PutMapping("/addProfilePicture/{empId}")
    private ResponseEntity<?> addProfilePicture (@PathVariable int empId, @RequestParam("file") MultipartFile file) {

        // check if emp exists
        Optional<EmployeeEntity> optionalEmployee = registerRepo.findById(empId);

        if (optionalEmployee.isPresent()) {
            try {

                // get the byte array from the uploaded file
                byte[] profilePicture = file.getBytes();

                // upload the employee's profile picture
                EmployeeEntity employee = optionalEmployee.get();
                employee.setProfilePicture(profilePicture);

                registerRepo.save(employee);

                return ResponseEntity.ok("Profile picture uploaded successfully");

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile picture");
            }
        }
        return ResponseEntity.ok("Something went wrong!");
    }

//    @GetMapping("/viewProfilePicture/{empId}")
//    @Transactional
//    private ResponseEntity<?> getProfilePicture(@PathVariable int empId) {
//
//        EmployeeEntity employee = registerRepo.getReferenceById(empId);
//
//
//        System.out.println(employee);
//
////        System.out.println(employee);
////
////        if (employee.getProfilePicture() != null) {
////            HttpHeaders headers = new HttpHeaders();
////
////            headers.setContentType(MediaType.IMAGE_JPEG);
////
////            // return the image data as a ByteArrayResource
////            return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(employee.getProfilePicture()));
////        } else {
////            // Handle case where no profile picture is available
////            return ResponseEntity.notFound().build();
////        }
//
//        return ResponseEntity.ok("hi");
//    }

    @GetMapping("/viewProfilePicture/{empId}")
    private ResponseEntity<byte[]> getProfilePicture(@PathVariable int empId) {
        Optional<EmployeeEntity> optionalEmployee = registerRepo.findById(empId);

        if (optionalEmployee.isPresent()) {
            EmployeeEntity employee = optionalEmployee.get();
            byte[] profilePicture = employee.getProfilePicture();

            if (profilePicture != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                // Return the image data as a byte array
                return ResponseEntity.ok().headers(headers).body(profilePicture);
            } else {
                // Handle case where no profile picture is available
                return ResponseEntity.notFound().build();
            }
        }

        // Handle case where employee with given empId is not found
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/removeProfilePicture/{empId}")
    private ResponseEntity<?> removeProfilePicture(@PathVariable int empId) {
        Optional<EmployeeEntity> optionalEmployee = registerRepo.findById(empId);

        if (optionalEmployee.isPresent()) {
            EmployeeEntity entity = optionalEmployee.get();

            // Set the profile picture to null
            entity.setProfilePicture(null);

            // Save the entity within a transaction
            registerRepo.save(entity);

            return ResponseEntity.ok("Profile picture removed successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/editShift/{shift}/{empId}")
    private ResponseEntity<?> editShift (@PathVariable String shift, @PathVariable int empId) {

        EmployeeEntity entity = registerRepo.getReferenceById(empId);

        if (!entity.getShift().equals(shift)) {
            sendShiftChangeEmail(entity.getEmail(), entity.getEmp_name(), entity.getShift(), shift);

            entity.setShift(shift);
            registerRepo.save(entity);

            return ResponseEntity.ok("Shift Changed");
        }

        return ResponseEntity.ok("Same shift selected");
    }

    private void sendShiftChangeEmail(String toEmail, String employeeName, String oldShift, String newShift) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        System.out.println(toEmail);
        mailMessage.setSubject("Shift Change Notification");
        mailMessage.setText("Dear " + employeeName + ",\n\n"
                + "Your shift has been changed from " + oldShift + " to " + newShift + ".\n\n"
                + "Thank you for your attention.");

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/editEmployee/{empId}")
    private ResponseEntity<?> editEmployee(@RequestBody EmployeeEntity updatedDetails, @PathVariable int empId) {

        Optional<EmployeeEntity> optionalEntity = registerRepo.findById(empId);

        if (optionalEntity.isPresent()) {
            EmployeeEntity existingEntity = optionalEntity.get();

            if (updatedDetails.getEmpId() != 0) {
                existingEntity.setEmpId(updatedDetails.getEmpId());
            }

            if (updatedDetails.getEmp_name() != null) {
                existingEntity.setEmp_name(updatedDetails.getEmp_name());
            }
            if (updatedDetails.getEmail() != null) {
                existingEntity.setEmail(updatedDetails.getEmail());
            }
            if (updatedDetails.getSkills() != null) {
                existingEntity.setSkills(updatedDetails.getSkills());
            }
            if (updatedDetails.getContact_no() != null) {
                existingEntity.setContact_no(updatedDetails.getContact_no());
            }
            if (updatedDetails.getAlternate_contact_no() != null) {
                existingEntity.setAlternate_contact_no(updatedDetails.getAlternate_contact_no());
            }
            if (updatedDetails.getPermanent_address() != null) {
                existingEntity.setPermanent_address(updatedDetails.getPermanent_address());
            }
            if (updatedDetails.getDob() != null) {
                existingEntity.setDob(updatedDetails.getDob());
            }
            if (updatedDetails.getDoj() != null) {
                existingEntity.setDoj(updatedDetails.getDoj());
            }
            if (updatedDetails.getShift() != null) {
                existingEntity.setShift(updatedDetails.getShift());
            }
            if (updatedDetails.getCorrespondence_address() != null) {
                existingEntity.setCorrespondence_address(updatedDetails.getCorrespondence_address());
            }
            if (updatedDetails.getMaritalStatus() != null) {
                existingEntity.setMaritalStatus(updatedDetails.getMaritalStatus());
            }
            if (updatedDetails.getGender() != null) {
                existingEntity.setGender(updatedDetails.getGender());
            }
            if (updatedDetails.getBloodGroup() != null) {
                existingEntity.setBloodGroup(updatedDetails.getBloodGroup());
            }
            if (updatedDetails.getPersonalEmail() != null) {
                existingEntity.setPersonalEmail(updatedDetails.getPersonalEmail());
            }
            if (updatedDetails.getJobRoleEntity() != null) {
                existingEntity.setJobRoleEntity(updatedDetails.getJobRoleEntity());
            }
            if (!updatedDetails.isFlag()) {
                existingEntity.setFlag(updatedDetails.isFlag());
            }
            if (updatedDetails.getDepartmentEntity() != null) {
                existingEntity.setDepartmentEntity(updatedDetails.getDepartmentEntity());
            }
            if (updatedDetails.getReportingManagerId() != 0) {
                existingEntity.setReportingManagerId(updatedDetails.getReportingManagerId());
            }
            if (updatedDetails.getEmployeeType() != null) {
                existingEntity.setEmployeeType(updatedDetails.getEmployeeType());
            }

            existingEntity.setApprovalStatus(false);

            registerRepo.save(existingEntity);

            sendApprovalEmail(existingEntity);

            return ResponseEntity.ok("Details updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private String sendApprovalEmail(EmployeeEntity entity) {
        SimpleMailMessage message = new SimpleMailMessage();

        EmployeeEntity managerEntity = registerRepo.getReferenceById(entity.getReportingManagerId());


        message.setTo(managerEntity.getEmail());
        message.setSubject("Update Approval Request");
        message.setText("An employee update has been made and requires your approval.\n" +
                "Employee ID: "+ entity.getEmpId() + "\n"+
                "Employee Name: " + entity.getEmp_name() + "\n\n"
                + "Please review and approve the update.\n\n"
                + "Thank you."
        );
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }

        return "Added";
    }

    @PutMapping("/approveEmployeeDataChanges/{empId}")
    private ResponseEntity<?> approveEmployeeDataChanges(@PathVariable int empId) {

        EmployeeEntity employee = registerRepo.getReferenceById(empId);

        if (!employee.isApprovalStatus()) {
            employee.setApprovalStatus(true);

            registerRepo.save(employee);

            return ResponseEntity.ok("Changes Approved!");
        }

        return ResponseEntity.internalServerError().body("Nothing has been modified!");

    }

    @PutMapping("/deleteEmployee/{empId}")
    private ResponseEntity<?> deleteEmployee (@PathVariable int empId) {
        EmployeeEntity entity = registerRepo.getReferenceById(empId);

        entity.setFlag(false);
        registerRepo.save(entity);

        return ResponseEntity.ok("Flag updated!");

    }

//    @PutMapping("/editEmployee")
//    private ResponseEntity<?> editEmployee(@RequestBody EmployeeEntity entity) {
//
//        EmployeeEntity updatedDetails = new EmployeeEntity();
//
//        updatedDetails.setEmpId(entity.getEmpId());
//        updatedDetails.setEmp_name(entity.getEmp_name());
//        updatedDetails.setEmail(entity.getEmail());
//        updatedDetails.setSkills(entity.getSkills());
//        updatedDetails.setContact_no(entity.getContact_no());
//        updatedDetails.setAlternate_contact_no(entity.getAlternate_contact_no());
//        updatedDetails.setPermanent_address(entity.getPermanent_address());
//        updatedDetails.setDob(entity.getDob());
//        updatedDetails.setDoj(entity.getDoj());
//        updatedDetails.setShift(entity.getShift());
//        updatedDetails.setCorrespondence_address(entity.getCorrespondence_address());
//        updatedDetails.setMaterialStatus(entity.getMaterialStatus());
//        updatedDetails.setGender(entity.getGender());
//        updatedDetails.setBloodGroup(entity.getBloodGroup());
//        updatedDetails.setPersonalEmail(entity.getPersonalEmail());
//        updatedDetails.setJobRoleEntity(entity.getJobRoleEntity());
//        updatedDetails.setFlag(entity.getFlag());
//        updatedDetails.setDepartmentEntity(entity.getDepartmentEntity());
//        updatedDetails.setReportingManagerId(entity.getReportingManagerId());
//        updatedDetails.setEmployeeType(entity.getEmployeeType());
//        updatedDetails.setApprovalStatus(false);
//
//        registerRepo.save(updatedDetails);
//
//        return ResponseEntity.ok("Details updated successfully");
//    }

    @PostMapping("/addAdmin")
    private ResponseEntity<?> addAdmin(@RequestBody EmployeeEntity admin) {

        // id of admin is 2 in the master table "jobs"
        JobRoleEntity jobRole = new JobRoleEntity();
        jobRole.setJob_id(2);
        admin.setJobRoleEntity(jobRole);

        admin.setDoj(LocalDateTime.now());
        admin.setPassword(generateRandomPassword());

        registerRepo.save(admin);

        sendEmail(admin.getEmail(), admin);

        return ResponseEntity.ok("Admin registered successfully");

    }

    public String sendEmail(String emailAdd, EmployeeEntity entity) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailAdd);
        message.setSubject("Login Credentials");
        message.setText("Congratulations!\nYou have been registered successfully.\n\nYour login credentials are:\n\nusername: " + emailAdd + "\npassword: " + pw +"\n\nClick on the link below to login  \n https://www.pexels.com/");

        javaMailSender.send(message);

        return "Email sent successfully!";
    }


    // login
    @GetMapping("/findEmployee/{email}/{password}")
    @CrossOrigin
    private int employeeExists(@PathVariable String email, @PathVariable String password) {

        EmployeeEntity myLocalEmployeeEntity = new EmployeeEntity();

        List<EmployeeEntity> employeeEntityList = registerRepo.findAll();

        for (EmployeeEntity singleEntity : employeeEntityList) {
            if (singleEntity.getEmail().equals(email)) {
                myLocalEmployeeEntity = singleEntity;

                break;
            }
        }
        if (BCrypt.checkpw(password, myLocalEmployeeEntity.getPassword())) {

            if (!myLocalEmployeeEntity.isFlag()) {
                return -1;
            }

            return myLocalEmployeeEntity.getJobRoleEntity().getJob_id();
        }

        return -1;
    }

    @GetMapping("/findEmployeeByEmail/{emailAdd}")
    private EmployeeEntity findEmployeeByEmail (@PathVariable String emailAdd) {

        List<EmployeeEntity> employeeEntityList = registerRepo.findAll();

        for (EmployeeEntity entity: employeeEntityList) {

            if (entity.getEmail().equals(emailAdd)) {
                return entity;
            }

        }
        return null;
    }


    @GetMapping("/checkEmailExists/{email}")
    boolean checkEmailExists (@PathVariable String email) {
        List<EmployeeEntity> employeeEntityList = registerRepo.findAll();

        for (EmployeeEntity employeeEntity: employeeEntityList) {

            if (employeeEntity.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public String generateRandomPassword() {
        Random random = new Random();

        int randomNumber = 100000 + random.nextInt(900000);

        pw = "ems" + randomNumber;
        System.out.println(pw);

        return BCrypt.hashpw("ems" + String.valueOf(randomNumber), BCrypt.gensalt());
    }

    @PutMapping("/resetPassword/{email}/{password}")
    void addPassword(@PathVariable String email, @PathVariable String password) {

        int idOfCorrespondingEmail = 0;

        List<EmployeeEntity> employeeEntityList = registerRepo.findAll();

        for (EmployeeEntity employeeEntity: employeeEntityList) {
            if (employeeEntity.getEmail().equals(email)) {
                idOfCorrespondingEmail = employeeEntity.getEmpId();
            }
        }

        EmployeeEntity employeeEntity = registerRepo.getReferenceById(idOfCorrespondingEmail);

        employeeEntity.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));   // updated one
        employeeEntity.setEmpId(employeeEntity.getEmpId());
        employeeEntity.setEmp_name(employeeEntity.getEmp_name());
        employeeEntity.setEmail(employeeEntity.getEmail());
        employeeEntity.setSkills(employeeEntity.getSkills());
        employeeEntity.setContact_no(employeeEntity.getContact_no());
        employeeEntity.setContact_no(employeeEntity.getContact_no());
        employeeEntity.setPermanent_address(employeeEntity.getPermanent_address());
        employeeEntity.setCorrespondence_address(employeeEntity.getCorrespondence_address());
        employeeEntity.setJobRoleEntity(employeeEntity.getJobRoleEntity());
        employeeEntity.setFlag(employeeEntity.isFlag());
        employeeEntity.setDepartmentEntity(employeeEntity.getDepartmentEntity());
        employeeEntity.setReportingManagerId(employeeEntity.getReportingManagerId());
        employeeEntity.setDoj(employeeEntity.getDoj());
        employeeEntity.setDob(employeeEntity.getDob());
        employeeEntity.setMaritalStatus(employeeEntity.getMaritalStatus());
        employeeEntity.setGender(employeeEntity.getGender());
        employeeEntity.setBloodGroup(employeeEntity.getBloodGroup());
        employeeEntity.setPersonalEmail(employeeEntity.getPersonalEmail());
        employeeEntity.setApprovalStatus(true);
        employeeEntity.setAlternate_contact_no(employeeEntity.getAlternate_contact_no());
        employeeEntity.setAlternate_contact_name(employeeEntity.getAlternate_contact_name());


        registerRepo.save(employeeEntity);
    }

    @GetMapping("/findAllEmployeeIdAndName")
    private HashMap<String, Integer> findAllEmployeeIdAndName() {

        HashMap<String, Integer> employeeList = new HashMap<>();

        List<EmployeeEntity> list = registerRepo.findAll();

        for (EmployeeEntity entity: list) {
            String empName = entity.getEmp_name();
            int empId = entity.getEmpId();

            employeeList.put(empName, empId);
        }

        return employeeList;

    }

    @PutMapping("/addReportingManager/{email}/{managerId}")
    private ResponseEntity addReportingManager(@PathVariable String email, @PathVariable int managerId) {

        int idOfCorrespondingEmail = 0;

        List<EmployeeEntity> employeeEntityList = registerRepo.findAll();

        for (EmployeeEntity entity: employeeEntityList) {
            if (entity.getEmail().equals(email)) {
                idOfCorrespondingEmail = entity.getEmpId();
            }
        }

        // check if the managerId provided is genuinely a manager
        EmployeeEntity employee = registerRepo.getReferenceById(managerId);

        if (employee.getEmployeeType().getemployeeTypeId() == 2) {

            EmployeeEntity employeeEntity = registerRepo.getReferenceById(idOfCorrespondingEmail);

            employeeEntity.setPassword(employeeEntity.getPassword());
            employeeEntity.setEmpId(employeeEntity.getEmpId());
            employeeEntity.setEmp_name(employeeEntity.getEmp_name());
            employeeEntity.setEmail(employeeEntity.getEmail());
            employeeEntity.setSkills(employeeEntity.getSkills());
            employeeEntity.setContact_no(employeeEntity.getContact_no());
            employeeEntity.setContact_no(employeeEntity.getContact_no());
            employeeEntity.setPermanent_address(employeeEntity.getPermanent_address());
            employeeEntity.setCorrespondence_address(employeeEntity.getCorrespondence_address());
            employeeEntity.setJobRoleEntity(employeeEntity.getJobRoleEntity());
            employeeEntity.setFlag(employeeEntity.isFlag());
            employeeEntity.setDepartmentEntity(employeeEntity.getDepartmentEntity());
            employeeEntity.setDob(employeeEntity.getDob());
            employeeEntity.setDoj(employeeEntity.getDoj());
            employeeEntity.setReportingManagerId(managerId);  // updated one
            employeeEntity.setMaritalStatus(employeeEntity.getMaritalStatus());
            employeeEntity.setGender(employeeEntity.getGender());
            employeeEntity.setBloodGroup(employeeEntity.getBloodGroup());
            employeeEntity.setPersonalEmail(employeeEntity.getPersonalEmail());
            employeeEntity.setApprovalStatus(true);
            employeeEntity.setAlternate_contact_name(employeeEntity.getAlternate_contact_name());
            employeeEntity.setAlternate_contact_no(employee.getAlternate_contact_no());

            registerRepo.save(employeeEntity);

            return ResponseEntity.ok("Manager ID added");

        }
        return null;

    }

    @GetMapping("/getEmployeesByManagerId/{reportingManagerId}")
    private List<EmployeeEntity> getEmployeesByManagerId(@PathVariable int reportingManagerId) {
        return registerRepo.findByReportingManagerId(reportingManagerId);
    }


    // find all managers or employee by id
    @GetMapping("/findAllManagers")
    private List<EmployeeEntity> findAllManagers() {
        return registerRepo.findByEmployeeTypeEmployeeTypeId(2);
    }


    @GetMapping("/findReportingManager/{empId}")
    private Optional<EmployeeEntity> findReportingManager (@PathVariable int empId) {
        EmployeeEntity entity = registerRepo.getReferenceById(empId);

        Optional<EmployeeEntity> manager = registerRepo.findById(entity.getReportingManagerId());

        return manager;
    }

    @GetMapping("/getEmployeesByDepartmentId/{deptId}")
    private List<?> getEmployeesByDepartmentId(@PathVariable int deptId) {
        return registerRepo.findByDepartmentEntityDeptId(deptId);
    }

    @PutMapping("/setEmployeeAsManager/{empId}")
    private void setEmployeeAsManager(@PathVariable int empId) {

        EmployeeEntity entity = registerRepo.getReferenceById(empId);

        // checking if the person is already not a manager
        if (entity.getEmployeeType().getemployeeTypeId() != 2) {

            entity.setPassword(entity.getPassword());
            entity.setEmpId(entity.getEmpId());
            entity.setEmp_name(entity.getEmp_name());
            entity.setEmail(entity.getEmail());
            entity.setSkills(entity.getSkills());
            entity.setContact_no(entity.getContact_no());
            entity.setContact_no(entity.getContact_no());
            entity.setPermanent_address(entity.getPermanent_address());
            entity.setCorrespondence_address(entity.getCorrespondence_address());
            entity.setJobRoleEntity(entity.getJobRoleEntity());
            entity.setFlag(entity.isFlag());
            entity.setDepartmentEntity(entity.getDepartmentEntity());
            entity.setReportingManagerId(entity.getReportingManagerId());
            entity.setDoj(entity.getDoj());
            entity.setDob(entity.getDob());
            entity.setPersonalEmail(entity.getPersonalEmail());
            entity.setGender(entity.getGender());
            entity.setMaritalStatus(entity.getMaritalStatus());
            entity.setBloodGroup(entity.getBloodGroup());
            entity.setApprovalStatus(true);
            entity.setAlternate_contact_name(entity.getAlternate_contact_name());
            entity.setAlternate_contact_no(entity.getAlternate_contact_no());

            // updation
            EmployeeTypeEntity employeeType = new EmployeeTypeEntity();
            employeeType.setemployeeTypeId(2);

            entity.setEmployeeType(employeeType);


            registerRepo.save(entity);
        }

    }
}
