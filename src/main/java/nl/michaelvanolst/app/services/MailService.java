package nl.michaelvanolst.app.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import nl.michaelvanolst.app.dtos.EmailDto;
import nl.michaelvanolst.app.dtos.TaskDto;

@Getter
@Setter
public class MailService {

  private EmailDto emailDto;
  private TaskDto taskDto;
  private Session session;
  
  public MailService() {
    this.createSession();
  }

  public void createSession() {
    final String username = Config.getString("mail.username");
    final String password = Config.getString("mail.password");

    Properties prop = new Properties();
    prop.put("mail.smtp.host", Config.getString("mail.host"));
    prop.put("mail.smtp.port", Config.getString("mail.port"));
    prop.put("mail.smtp.auth", Config.getString("mail.auth"));

    if(Config.getBoolean("mail.ssl")) {
      prop.put("mail.smtp.starttls.enable", "true"); //TLS
      prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
    }
    
    Authenticator authenticator = new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    };
    
    this.session = Session.getInstance(prop, authenticator);
  }


  public void send() throws MessagingException,IOException {
    Message message = new MimeMessage(this.session);
    message.setFrom(new InternetAddress(this.emailDto.getFrom()));
    message.setRecipients(
      Message.RecipientType.TO,
      InternetAddress.parse(this.emailDto.getTo())
    );
    message.setSubject(this.emailDto.getTitle());
    message.setText(getContentFromTemplate());
    message.setHeader("Content-Type", "text/html");

    Transport.send(message);
  }


  public String getContentFromTemplate() throws IOException {
    String emailHtmlPath = StringUtils.stripEnd(System.getProperty("user.dir"), "/") + "/templates/notify_email.html";
    File file = new File(emailHtmlPath);
    String template = Files.readString(file.toPath());

    template = template.replace("{{taskTitle}}", this.taskDto.getTitle());
    template = template.replace("{{taskUrl}}", this.taskDto.getUrl());
    template = template.replace("{{emailTitle}}", this.emailDto.getTitle());

    return template;
  }

}
