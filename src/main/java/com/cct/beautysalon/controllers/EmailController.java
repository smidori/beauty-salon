package com.cct.beautysalon.controllers;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.jwt.ResetPwdRequest;
import com.cct.beautysalon.services.EmailService;
import com.cct.beautysalon.utils.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;


    @PostMapping("/resetPwd")
    public ResponseEntity<Object> sendEmail(@RequestBody ResetPwdRequest request) {
        try{
            emailService.sendRecoveryEmail(request.getEmail());
            return ResponseEntity.ok().build();
        }catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Sorry, could not send recovery email, please try later"));
        }
    }
}

