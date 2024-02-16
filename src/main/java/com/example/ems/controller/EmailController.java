package com.example.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


@RestController
@CrossOrigin
public class EmailController {

    private final JavaMailSender javaMailSender;

    Random random = new Random();
    int otp = 0;
    String emailAdd;

    @Autowired
    public EmailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/sendEmail/{emailAdd}")
    public String sendEmail(@PathVariable String emailAdd) {

        this.emailAdd = emailAdd;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailAdd);
        message.setSubject("One Time Password");
        message.setText("Your One Time Password (OTP) is : " + generateOtp());

        javaMailSender.send(message);

        otpTimer();

        return "Email sent successfully!";
    }

    public int generateOtp() {
        otp = random.nextInt(9999);

        return otp;
    }

    @GetMapping("/verifyOtp/{otp}")
    public String verifyOtp(@PathVariable int otp) {
        if (this.otp == otp) {
            this.otp = -1;

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(this.emailAdd);
            message.setSubject("Set/Reset Password");
            message.setText("Click on the below link to set/reset your password :\n https://www.pexels.com/");

            javaMailSender.send(message);

            return "Mail with set/rest password link sent!";
        }
        return "Invalid OTP";
    }

    private void otpTimer () {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("OTP expired");
                otp = -1;
            }
        }, 600000); // 10 mins
    }

}
