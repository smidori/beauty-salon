package com.cct.beautysalon.services;
import com.cct.beautysalon.exceptions.InvalidUsernameException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    /**
     * send Recovery Email
     * @param email
     * @throws Exception
     */
    public void sendRecoveryEmail(String email) throws Exception {
        try{
        User user = userService.findByEmail(email);

        String NEW_PWD_STR="B3@utyS@l0n";
        String newPwd = passwordEncoder.encode(NEW_PWD_STR);
        user.setPassword(newPwd);
        userService.update(user.getId(), user);

        String bodyEmail = "" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Password Reset</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Password Reset</h1>\n" +
                "    <p>Hello, <b>" + user.getFirstName() + " " + user.getLastName() +  "</b></p>\n" +
                "    <p>Your password was changed to: <strong>" + NEW_PWD_STR + "</strong></p>\n" +
                "    <p>Log into your account and you can change with a new one.</p>"+
                "    <p>Thank you,</p>\n" +
                "    <br/>" +
                "    <p><b>Beauty Salon</b>" +
                "    <br/><b>Phone:</b>1234-5678 " +
                "    <br/><b>Address:</b>30-34 Westmoreland St</p>\n" +
                "</body>\n" +
                "</html>\n";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(user.getEmail());
        helper.setSubject("Beauty Salon - Password Reset");
        helper.setText(bodyEmail, true); // Set the content as HTML

        mailSender.send(message);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}

