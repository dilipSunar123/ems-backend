package com.example.ems.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

// register
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;
    @Column(nullable = false)
    private String emp_name;
    @Column(unique = true, nullable = false)
    private String email;
    private String skills;
    @Column(nullable = false)
    private Long contact_no;

    @Column(nullable = false)
    private Long alternate_contact_no;

    @Column(nullable = false)
    private String alternate_contact_name;

    @Column(nullable = false)
    private String permanent_address;

    @Column(name = "dob", nullable = false)
    private String dob;

    @Column(name = "doj")
    private LocalDateTime doj;

    @Column(name = "shift")
    private String shift = "9:30am - 6:30pm";

    @Column(nullable = false)
    private String correspondence_address;

    private String password;

    @Column(name = "marital_status", nullable = false)
    private String maritalStatus;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "approval_status")
    private boolean approvalStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private JobRoleEntity jobRoleEntity;

    // employee active
    private boolean flag = true;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deptId")
    private DepartmentEntity departmentEntity;

    @Column(name = "reporting_manager_id", nullable = false)
    private int reportingManagerId;

    @ManyToOne
    @JoinColumn(name = "employeeTypeId")
    private EmployeeTypeEntity employeeType;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Long getContact_no() {
        return contact_no;
    }

    public void setContact_no(Long contact_no) {
        this.contact_no = contact_no;
    }

    public Long getAlternate_contact_no() {
        return alternate_contact_no;
    }

    public void setAlternate_contact_no(Long alternate_contact_no) {
        this.alternate_contact_no = alternate_contact_no;
    }

    public String getAlternate_contact_name() {
        return alternate_contact_name;
    }

    public void setAlternate_contact_name(String alternate_contact_name) {
        this.alternate_contact_name = alternate_contact_name;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public LocalDateTime getDoj() {
        return doj;
    }

    public void setDoj(LocalDateTime doj) {
        this.doj = doj;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getCorrespondence_address() {
        return correspondence_address;
    }

    public void setCorrespondence_address(String correspondence_address) {
        this.correspondence_address = correspondence_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public JobRoleEntity getJobRoleEntity() {
        return jobRoleEntity;
    }

    public void setJobRoleEntity(JobRoleEntity jobRoleEntity) {
        this.jobRoleEntity = jobRoleEntity;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public DepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(DepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public int getReportingManagerId() {
        return reportingManagerId;
    }

    public void setReportingManagerId(int reportingManagerId) {
        this.reportingManagerId = reportingManagerId;
    }

    public EmployeeTypeEntity getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypeEntity employeeType) {
        this.employeeType = employeeType;
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "empId=" + empId +
                ", emp_name='" + emp_name + '\'' +
                ", email='" + email + '\'' +
                ", skills='" + skills + '\'' +
                ", contact_no=" + contact_no +
                ", alternate_contact_no=" + alternate_contact_no +
                ", alternate_contact_name='" + alternate_contact_name + '\'' +
                ", permanent_address='" + permanent_address + '\'' +
                ", dob='" + dob + '\'' +
                ", doj=" + doj +
                ", shift='" + shift + '\'' +
                ", correspondence_address='" + correspondence_address + '\'' +
                ", password='" + password + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", personalEmail='" + personalEmail + '\'' +
                ", approvalStatus=" + approvalStatus +
                ", jobRoleEntity=" + jobRoleEntity +
                ", flag=" + flag +
                ", profilePicture=" + Arrays.toString(profilePicture) +
                ", departmentEntity=" + departmentEntity +
                ", reportingManagerId=" + reportingManagerId +
                ", employeeType=" + employeeType +
                '}';
    }
}
