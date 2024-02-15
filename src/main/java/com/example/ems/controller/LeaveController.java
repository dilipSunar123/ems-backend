package com.example.ems.controller;

import com.example.ems.entity.*;
import com.example.ems.repository.AttendanceRepo;
import com.example.ems.repository.EmployeeRepo;
import com.example.ems.repository.LeaveListRepo;
import com.example.ems.repository.LeaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
public class LeaveController {

    @Autowired
    private LeaveRepo repo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private LeaveListRepo leaveListRepo;

    @Autowired
    private AttendanceRepo attendanceRepo;

    private JavaMailSender javaMailSender;

    private LocalDateTime dateTimeOfLeave;
    private List<EmployeeEntity> listOfTeammates;

    @Autowired
    public LeaveController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/requestLeave/{email}")
    private ResponseEntity<?> addLeave(@RequestBody LeaveEntity entity, @PathVariable String email) {

        LeaveEntity leaveEntity = new LeaveEntity();

        leaveEntity.setStartDate(entity.getStartDate());
        leaveEntity.setEndDate(entity.getEndDate());
        leaveEntity.setEmployeeEntity(entity.getEmployeeEntity());
        leaveEntity.setReason(entity.getReason());
        leaveEntity.setStatus("Pending");   // not yet approved
        leaveEntity.setLeaveTypeEntity(entity.getLeaveTypeEntity());
        leaveEntity.setAppliedOn(LocalDateTime.now());  // applied now (when data is stored in db)
        leaveEntity.setSameDay(entity.getSameDay());
        leaveEntity.setDiffDayStartIn(entity.getDiffDayStartIn());
        leaveEntity.setDiffDayEndIn(entity.getDiffDayEndIn());


        // runs for 3 days. Then, approve the leave if not approved.
        autoApproveLeave();

        int empId = entity.getEmployeeEntity().getEmpId();

        EmployeeEntity empEntity = employeeRepo.getReferenceById(empId);

        int managerId = empEntity.getReportingManagerId();

        String msg = sendEmailToManager(managerId, entity.getEmployeeEntity().getEmpId(), leaveEntity);

        // another person if employee wants to inform about the leave
        if (!email.equals("")) {

            System.out.println("---------------------------------------------hello");

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject("Leave Approval");
            message.setText("Hello, " + empEntity.getEmp_name() + " is requesting for a leave.\n\nDetails of leave are mentioned below - \n\n\n" +
                    "Employee name - " + empEntity.getEmp_name() +
                    "\nReason for leave - " + entity.getReason() +
                    "\nStart Date - " + entity.getStartDate() +
                    "\nEnd Date - " + entity.getEndDate() +
                    "\n\nPlease click on the link below to approve/reject his/her leave.");


            javaMailSender.send(message);

        }

        if (msg.equals("No reporting manager found for employee")) {
            return ResponseEntity.ok("No reporting manager found for employee");
        }

        repo.save(leaveEntity);

        return ResponseEntity.ok("Leave request sent for approval");

    }

    private String sendEmailToManager(@PathVariable int managerId, @PathVariable int empId, @PathVariable LeaveEntity leaveEntity) {

        SimpleMailMessage message = new SimpleMailMessage();

        // manager Email
        EmployeeEntity manager = employeeRepo.getReferenceById(managerId);
        EmployeeEntity employee = employeeRepo.getReferenceById(empId);

        if (employee.getReportingManagerId() == 0)
            return "No reporting manager found for employee";

        message.setTo(manager.getEmail());
        message.setSubject("Leave Approval");
//        message.setText("Hello, " +
//                manager.getEmp_name() + "!\n\nMr/Mrs " + employee.getEmp_name() +
//                " is requesting for a leave. \nClick on the link below to review the leave request and take appropriate " +
//                "action on it.");

        message.setText("Hello, " + manager.getEmp_name() + "\n" + employee.getEmp_name() + " is requesting for a leave.\n\nDetails of leave are mentioned below - \n\n\n" +
                "Employee name - " + employee.getEmp_name() +
                "\nReason for leave - " + leaveEntity.getReason() +
                "\nStart Date - " + leaveEntity.getStartDate() +
                "\nEnd Date - " + leaveEntity.getEndDate() +
                "\n\nPlease click on the link below to approve/reject his/her leave.");

        javaMailSender.send(message);

        return "Email sent to the manager";
    }

    @GetMapping("/findLeaveListOfEmp/{empId}")
    private LeaveList findLeaveListOfEmp (@PathVariable int empId) {
        return leaveListRepo.findByEmployeeEntityEmpId(empId);
    }

    @GetMapping("/findLeave/{leaveId}")
    private Optional<LeaveEntity> findLeaveRequest(@PathVariable int leaveId) {
        return repo.findById(leaveId);
    }

    @GetMapping("/findLeaveByEmpId/{empId}")
    private List<LeaveEntity> findLeaveByEmpId (@PathVariable int empId) {
        return repo.findByEmployeeEntityEmpId(empId);
    }


    @PutMapping("/takeActionOnLeaveRequest/{leaveId}/{action}")
    private ResponseEntity<?> takeActionOnLeaveRequest(@PathVariable int leaveId, @PathVariable String action) {

        LeaveEntity entity = repo.getReferenceById(leaveId);

        LeaveEntity myLocalLeaveEntity = new LeaveEntity();

        myLocalLeaveEntity.setSlno(entity.getSlno());
        myLocalLeaveEntity.setReason(entity.getReason());
        myLocalLeaveEntity.setEmployeeEntity(entity.getEmployeeEntity());
        myLocalLeaveEntity.setStartDate(entity.getStartDate());
        myLocalLeaveEntity.setEndDate(entity.getEndDate());
        myLocalLeaveEntity.setLeaveTypeEntity(entity.getLeaveTypeEntity());
        myLocalLeaveEntity.setStatus(action);   // updated one
        myLocalLeaveEntity.setAppliedOn(entity.getAppliedOn());
        myLocalLeaveEntity.setSameDay(entity.getSameDay());
        myLocalLeaveEntity.setDiffDayStartIn(entity.getDiffDayStartIn());
        myLocalLeaveEntity.setDiffDayEndIn(entity.getDiffDayEndIn());


        repo.save(myLocalLeaveEntity);

        EmployeeEntity employee = entity.getEmployeeEntity();
        if (action.equals("Approve")) {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setSubject("Leave Approved");
            message.setTo(employee.getEmail());
            message.setText("Congratulations!\n\nYour leave has been approved.\n\nYour leave starts from " + myLocalLeaveEntity.getStartDate() + " till " + myLocalLeaveEntity.getEndDate());

            javaMailSender.send(message);

            // add same details in the attendance table
            AttendanceEntity attendanceEntity = new AttendanceEntity();

            attendanceEntity.setLoginDateAndTime(null);
            attendanceEntity.setLogout_date_and_time(null);
            attendanceEntity.setLocation(null);
            attendanceEntity.setWorkFromEntity(null);
            attendanceEntity.setEmployeeEntity(employee);
            attendanceEntity.setLog("On Leave");
            attendanceEntity.setGrossHour(String.valueOf(0));
            attendanceEntity.setLeaveDate(String.valueOf(LocalDateTime.now()));

            attendanceRepo.save(attendanceEntity);


            // Calculate duration of leave in days
//            LocalDate startDate = LocalDate.parse(entity.getStartDate().substring(0, 10)); // Extracting date part only
//            LocalDate endDate = LocalDate.parse(entity.getEndDate().substring(0, 10)); // Extracting date part only
//            long durationInDays = ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 to include both start and end dates
//
//            // decrement count of leave
            String leaveAppliedFor = entity.getLeaveTypeEntity().getLeaveType();
//            System.out.println(entity.getLeaveTypeEntity().getLeaveType());

            List<LeaveList> leaveLists = leaveListRepo.findAll();

            LeaveList leaveList = leaveListRepo.findByEmployeeEntityEmpId(entity.getEmployeeEntity().getEmpId());
            int empId = entity.getEmployeeEntity().getEmpId();
            LeaveList employeeKaRow = new LeaveList();

            for (LeaveList list: leaveLists) {
                if (list.getEmployeeEntity().getEmpId() == empId) {
                    employeeKaRow = list;
                    break;
                }
            }

            switch (leaveAppliedFor) {
                case "Floater Leave":
                    if (employeeKaRow.getFloaterLeave() > 0) {
                        decrementLeaveBalance(employeeKaRow, "Floater Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
                    } else {
                        return ResponseEntity.ok("No floater leave available");
                    }
                    break;
                case "Maternity Leave":
                    if (employeeKaRow.getMaternityLeave() > 0) {
                        decrementLeaveBalance(employeeKaRow, "Maternity Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
                    } else {
                        return ResponseEntity.ok("No maternity leave available");
                    }
                    break;
                case "Paid Leave":
                    if (employeeKaRow.getPaidLeave() > 0) {
                        decrementLeaveBalance(employeeKaRow, "Paid Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
                    } else {
                        return ResponseEntity.ok("No paid leave available");
                    }
                    break;
                case "Paternity Leave":
                    if (employeeKaRow.getPaternityLeave() > 0) {
                        decrementLeaveBalance(employeeKaRow, "Paternity Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
                    } else {
                        return ResponseEntity.ok("No paternity leave available");
                    }
                    break;
                case "Sick Leave":
                    if (employeeKaRow.getSickLeave() > 0) {
                        decrementLeaveBalance(employeeKaRow, "Sick Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
                    } else {
                        return ResponseEntity.ok("No sick leave available");
                    }
                    break;
                case "Casual Leave":
                    if (employeeKaRow.getCasualLeave() > 0) {
                        decrementLeaveBalance(employeeKaRow, "Casual Leave", entity.getSameDay().equals("Yes")? 1 : 0.5);
                    } else {
                        return ResponseEntity.ok("No Casual leave available");
                    }
                    break;
//                    case "Unpaid Leave":
//                        decrementLeaveBalance(list, "Unpaid Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
//                        break;
                default:
                    break;
            }




//            for (LeaveList list : leaveLists) {
//                switch (leaveAppliedFor) {
//                    case "Floater Leave":
//                        if (list.getFloaterLeave() > 0) {
//                            decrementLeaveBalance(list, "Floater Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
//                        } else {
//                            return ResponseEntity.ok("No floater leave available");
//                        }
//                        break;
//                    case "Maternity Leave":
//                        if (list.getMaternityLeave() > 0) {
//                            decrementLeaveBalance(list, "Maternity Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
//                        } else {
//                            return ResponseEntity.ok("No maternity leave available");
//                        }
//                        break;
//                    case "Paid Leave":
//                        if (list.getPaidLeave() > 0) {
//                            decrementLeaveBalance(list, "Paid Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
//                        } else {
//                            return ResponseEntity.ok("No paid leave available");
//                        }
//                        break;
//                    case "Paternity Leave":
//                        if (list.getPaternityLeave() > 0) {
//                            decrementLeaveBalance(list, "Paternity Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
//                        } else {
//                            return ResponseEntity.ok("No paternity leave available");
//                        }
//                        break;
//                    case "Sick Leave":
//                        if (list.getSickLeave() > 0) {
//                            decrementLeaveBalance(list, "Sick Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
//                        } else {
//                            return ResponseEntity.ok("No sick leave available");
//                        }
//                        break;
//                    case "Casual Leave":
//                        if (list.getCasualLeave() > 0) {
//                            decrementLeaveBalance(list, "Casual Leave", entity.getSameDay().equals("Yes")? 1 : 0.5);
//                        } else {
//                            return ResponseEntity.ok("No Casual leave available");
//                        }
//                        break;
////                    case "Unpaid Leave":
////                        decrementLeaveBalance(list, "Unpaid Leave", entity.getSameDay().equals("Yes") ? 1 : 0.5);
////                        break;
//                    default:
//                        break;
//                }
//            }



        } else {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setSubject("Leave Rejected");
            message.setTo(employee.getEmail());
            message.setText("Your leave has been rejected due to some reason.");

            javaMailSender.send(message);
        }

        // inform all the teammates on leave approval
        if (action.equals("Approve")) {

            LeaveEntity leaveEntity = repo.getReferenceById(leaveId);
            EmployeeEntity manager = employeeRepo.getReferenceById(leaveEntity.getEmployeeEntity().getReportingManagerId());
            List<EmployeeEntity> list = employeeRepo.findByReportingManagerId(manager.getEmpId());

            listOfTeammates = list;

            dateTimeOfLeave = LocalDateTime.parse(myLocalLeaveEntity.getStartDate());
            leaveTimer();
            // send mail

            for (EmployeeEntity employeeEntity: list) {
                SimpleMailMessage message = new SimpleMailMessage();

                message.setSubject("On Leave");

                message.setTo(employeeEntity.getEmail());
                message.setText("Hello mates!\n\nI will be on leave from " + myLocalLeaveEntity.getStartDate() + " to " + myLocalLeaveEntity.getEndDate() + "\n\n- " + employeeEntity.getEmp_name());

                javaMailSender.send(message);
            }
        }

        if (action.equals("Approve"))
            return ResponseEntity.ok("Leave Approved");

        return ResponseEntity.ok("Leave request rejected");
    }


    private void decrementLeaveBalance(LeaveList list, String leaveType, double duration) {

        int empId = list.getEmployeeEntity().getEmpId();

        LeaveList updatedLeaveList = leaveListRepo.findByEmployeeEntityEmpId(empId);

//        System.out.println(updatedLeaveList);

        if (updatedLeaveList != null) {
            float balance = 0;

            switch (leaveType) {
                case "Floater Leave":
                    balance = updatedLeaveList.getFloaterLeave();
                    break;
                case "Maternity Leave":
                    balance = updatedLeaveList.getMaternityLeave();
                    break;
                case "Paid Leave":
                    balance = updatedLeaveList.getPaidLeave();
                    break;
                case "Paternity Leave":
                    balance = updatedLeaveList.getPaternityLeave();
                    break;
                case "Sick Leave":
                    balance = updatedLeaveList.getSickLeave();
                    break;
//                case "Unpaid Leave":
//                    balance = updatedLeaveList.getUnpaidLeave();
//                    break;
                case "Casual Leave":
                    balance = updatedLeaveList.getCasualLeave();
                    break;
            }
            balance -= duration;

            switch (leaveType) {
                case "Floater Leave":
                    updatedLeaveList.setFloaterLeave(balance);
                    break;
                case "Maternity Leave":
                    updatedLeaveList.setMaternityLeave(balance);
                    break;
                case "Paid Leave":
                    updatedLeaveList.setPaidLeave(balance);
                    break;
                case "Paternity Leave":
                    updatedLeaveList.setPaternityLeave(balance);
                    break;
                case "Sick Leave":
                    updatedLeaveList.setSickLeave(balance);
                    break;
//                case "Unpaid Leave":
//                    updatedLeaveList.setUnpaidLeave(balance);
//                    break;
                case "Casual Leave":
                    updatedLeaveList.setCasualLeave(balance);
                    break;
            }
            leaveListRepo.save(updatedLeaveList);
        }
    }



    private void sendMailOnTheDayOfLeave(List<EmployeeEntity> employeeEntityList) {

        for (EmployeeEntity employeeEntity: employeeEntityList) {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setSubject("Daily Digest");

            message.setTo(employeeEntity.getEmail());
            message.setText("Hello mates!\n\nMay you all have a great day ahead\n\nOn leave today." + "\n\n- " + employeeEntity.getEmp_name());

            javaMailSender.send(message);

        }

    }


    private void autoApproveLeave () {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                List<LeaveEntity> leaveEntityList = repo.findAll();

                List<LeaveEntity> leaveNotApprovedList = new ArrayList<>();

                for (LeaveEntity entity: leaveEntityList) {
                    if (entity.getStatus().equals("Pending")) {
                        leaveNotApprovedList.add(entity);
                    }
                }

                for (LeaveEntity entity: leaveNotApprovedList) {
                    entity.setStatus("Approve");
                    System.out.println("leave approved automatically!");
                    System.out.println(timer);

                    EmployeeEntity employee = entity.getEmployeeEntity();

                    // add same details in the attendance table
                    AttendanceEntity attendanceEntity = new AttendanceEntity();

                    attendanceEntity.setLoginDateAndTime(null);
                    attendanceEntity.setLogout_date_and_time(null);
                    attendanceEntity.setLocation(null);
                    attendanceEntity.setWorkFromEntity(null);
                    attendanceEntity.setEmployeeEntity(employee);
                    attendanceEntity.setLog("On Leave");
                    attendanceEntity.setGrossHour(String.valueOf(0));
                    attendanceEntity.setLeaveDate(String.valueOf(LocalDateTime.now()));

                    attendanceRepo.save(attendanceEntity);

                    repo.save(entity);
                }

            }
        }, 259200000);  // 3 days
    }

    private void leaveTimer() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                LocalDateTime presentDate = LocalDateTime.now();

                if (presentDate.equals(dateTimeOfLeave)) {
                    sendMailOnTheDayOfLeave(listOfTeammates);
                }

            }
        }, 0, 1000);
    }

}
