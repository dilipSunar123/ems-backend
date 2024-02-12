package com.example.ems.controller;


import com.example.ems.entity.AttendanceEntity;
import com.example.ems.entity.EmployeeEntity;
import com.example.ems.entity.WorkFromEntity;
import com.example.ems.repository.AttendanceRepo;
import com.example.ems.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class AttendanceController {

    @Autowired
    AttendanceRepo attendanceRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    @GetMapping("/getAllAttendance")
    private List<AttendanceEntity> getAllAttendance () {
        return attendanceRepo.findAll();
    }

    @PostMapping("/addAttendance/{emp_id}/{location}/{workFromId}/{log}")
    private ResponseEntity<?> addAttendance(@PathVariable int emp_id, @PathVariable String location, @PathVariable int workFromId, @PathVariable String log) {
        LocalDateTime localDateTime = LocalDateTime.now();

        AttendanceEntity entity = new AttendanceEntity();

        EmployeeEntity employeeEntity = employeeRepo.getReferenceById(emp_id);
        entity.setEmployeeEntity(employeeEntity);
        entity.setLoginDateAndTime(localDateTime);
        entity.setLocation(location);
        entity.setLog(log);

        WorkFromEntity workFrom = new WorkFromEntity();
        workFrom.setWorkFromId(workFromId);

        entity.setWorkFromEntity(workFrom);

        // Create a LocalTime representing 9:30 AM
//        LocalTime comparisonTime = LocalTime.of(9, 30);
//
//        Map<Boolean, Boolean> lateOrOnTimeMap = new HashMap<>();
//
//        if (localDateTime.toLocalTime().isAfter(comparisonTime)) {
//            lateOrOnTimeMap = addLateOrOnTime(emp_id, true);
//        } else {
//            lateOrOnTimeMap = addLateOrOnTime(emp_id, false);
//        }
//
//        // employee on time
//        if (lateOrOnTimeMap.containsKey(true)) {
//            entity.setOnTime(true);
//            entity.setLateArrival(false);
//        } else {
//            entity.setOnTime(false);
//            entity.setLateArrival(true);
//        }

        attendanceRepo.save(entity);

//        lateOrOnTimeMap.clear();

        return ResponseEntity.ok("Attendance recorded");
    }

//    private Map<Boolean, Boolean> addLateOrOnTime(int empId, boolean isLate) {
//        List<AttendanceEntity> entity = attendanceRepo.findByEmployeeEntityEmpIdOrderByLoginDateAndTimeDesc(empId);
//
//        // key -> onTime && value -> lateArrival
//        Map<Boolean, Boolean> lateOrOnTimeMap = new HashMap<>();
//
//        for (AttendanceEntity attendanceEntity: entity) {
//            if (attendanceEntity.getEmployeeEntity().getEmp_id() == empId) {
//                if (isLate) {
////                    attendanceEntity.setLateArrival(true);
////                    attendanceEntity.setOnTime(false);
//
//                    lateOrOnTimeMap.put(false, true);
//                } else {
////                    attendanceEntity.setLateArrival(false);
////                    attendanceEntity.setOnTime(true);
//
//                    lateOrOnTimeMap.put(true, false);
//                }
//                break;
//            }
//        }
//        return lateOrOnTimeMap;
//    }

    @PutMapping("/addLogoutDateAndTime/{emp_id}/{grossHour}")
    private ResponseEntity addLogoutDateAndTime(@PathVariable int emp_id, @PathVariable String grossHour) {
        List<AttendanceEntity> attendanceEntity = attendanceRepo.findByEmployeeEntityEmpIdOrderByLoginDateAndTimeDesc(emp_id);

        for (AttendanceEntity entity: attendanceEntity) {
            if (entity.getLogout_date_and_time() == null) {
                entity.setLogout_date_and_time(LocalDateTime.now());
                entity.setGrossHour(grossHour);

                attendanceRepo.save(entity);
            }
        }

        return ResponseEntity.ok("Logout time added!");

    }

    @GetMapping("/getAttendanceOfEmp/{empId}")
    public List<AttendanceEntity> getAttendanceOfEmp(@PathVariable int empId) {
        return attendanceRepo.findByEmployeeEntityEmpId(empId);
    }

    @GetMapping("/filterAttendanceByMonth/{monthId}/{empId}")
    private String filterAttendanceByMonth(@PathVariable String monthId, @PathVariable int empId) {

        List<AttendanceEntity> attendanceEntityList = attendanceRepo.findByEmployeeEntityEmpId(empId);

        List<AttendanceEntity> recordOfParticularMonth = new ArrayList<>();

        for (AttendanceEntity entity: attendanceEntityList) {

            String dateData = String.valueOf(entity.getLoginDateAndTime());

            System.out.println(dateData);

            String month = dateData.substring(5, 7);

            System.out.println("Month ID: " + monthId);
            System.out.println("Month : " + month);

            if (month.equals(String.valueOf(monthId))) {
                recordOfParticularMonth.add(entity);
            }
        }

        int onTime = 0;
        int lateArrival = 0;
        int onLeave = 0;

        for (AttendanceEntity entity: recordOfParticularMonth) {

            if (entity.getLog().equals("On Time")) onTime++;
            else if (entity.getLog().equals("Late Arrival")) lateArrival++;
            else onLeave++;

        }
        return "On Leave : " + onLeave + "\nLate Arrival : " + lateArrival + "\nOn Time : " + onTime;
    }


    @GetMapping("/fetchNoOfOnLeaveOnTimeAndLateArrival")
    private ResponseEntity<?> fetchNoOfOnLeaveOnTimeAndLateArrival () {
        List<AttendanceEntity> attendanceEntityList = attendanceRepo.findAll();

        System.out.println(attendanceEntityList);

        int onTime = 0;
        int lateArrival = 0;
        int onLeave = 0;

        for (AttendanceEntity entity: attendanceEntityList) {
            if (entity.getLog().equals("On Time")) {
                onTime++;
            } else if (entity.getLog().equals("Late Arrival")) {
                lateArrival++;
            } else {
                onLeave++;
            }
        }

        return ResponseEntity.ok("On Time : " + onTime + "\n" + "Late Arrival : " + lateArrival + "\n" + "On Leave : " + onLeave);
    }

    @GetMapping("/fetchDataByEmpId/{empId}")
    private String fetchDataByEmpId (@PathVariable int empId) {

        List<AttendanceEntity> list = attendanceRepo.findByEmployeeEntityEmpId(empId);

        int onTime = 0;
        int onLeave = 0;
        int lateArrival = 0;

        for (AttendanceEntity entity: list) {
            if (entity.getLog().equals("Late Arrival")) {
                lateArrival++;
            } else if (entity.getLog().equals("On Leave")) {
                onLeave++;
            } else {
                onTime++;
            }
        }

        return "On Time: " + onTime + "\nOn Leave: " + onLeave + "\nLate Arrival: " + lateArrival;
    }

//    @GetMapping("/getTodayStats")
//    private List<AttendanceEntity> getTodayStats () {
//
//        List<AttendanceEntity> attendanceEntityList = attendanceRepo.findAll();
//
//        LocalDateTime todayDate = LocalDateTime.now();
//
//        List<AttendanceEntity> empList = new ArrayList<>();
//
//        for (AttendanceEntity entity: attendanceEntityList) {
//            if (entity.getLeaveDate().equals(todayDate)) {
//                empList.add(entity);
//            }
//
//        }
//
//        return empList;
//    }

    @GetMapping("/filterAttendanceByYear/{year}/{empId}")
    private String filterAttendanceByYear(@PathVariable String year, @PathVariable int empId) {

        int onTime = 0;
        int lateArrival = 0;
        int onLeave = 0;

        List<AttendanceEntity> attendanceEntityList = attendanceRepo.findByEmployeeEntityEmpId(empId);

        List<AttendanceEntity> recordOfParticularYear = new ArrayList<>();

        for (AttendanceEntity entity: attendanceEntityList) {

            String yearData = String.valueOf(entity.getLoginDateAndTime());

            String month = yearData.substring(0, 4);

//            System.out.println("Month ID: " + monthId);
//            System.out.println("Month : " + month);

            if (month.equals(String.valueOf(year))) {
                recordOfParticularYear.add(entity);
            }
        }

        for (AttendanceEntity entity: recordOfParticularYear) {
            if (entity.getLog().equals("On Leave")) onLeave++;
            else if (entity.getLog().equals("Late Arrival")) lateArrival++;
            else onTime++;
        }

        return "On Leave : " + onLeave + "\nLate Arrival : " + lateArrival + "\nOn Time : " + onTime;
    }

}
