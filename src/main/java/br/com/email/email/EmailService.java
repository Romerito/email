package br.com.email.email;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.email.enums.EmailStatus;

/**
 * @author Romerito Alencar
 */

@Service
public class EmailService {

  @Autowired
  EmailRepository emailRepository;

  @Autowired
  JavaMailSender emailSender;

  public Email send(Email email) {
    email.setSendDateEmail(LocalDateTime.now());
        try {
          SimpleMailMessage message = new SimpleMailMessage();
          message.setFrom(email.getEmailFrom());
          message.setTo(email.getEmailTo());
          message.setSubject(email.getSubject());
          message.setText(email.getText());
          emailSender.send(message);

          email.setStatus(EmailStatus.SENT);
        } catch (Exception e) {
          email.setStatus(EmailStatus.ERROR);
        } 
        return emailRepository.save(email);

  }
}
