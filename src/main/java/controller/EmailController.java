package controller;

import model.Email;

import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailController {
    public String sendEmail(Email email, String subject){
        String info;
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host","smtp-mail.outlook.com");
        prop.put("mail.smtp.port","587");
        prop.put("mail.smtp.user",email.getFromEmail());
        prop.put("mail.smtp.password", email.getPass());
        Session sess = Session.getInstance(prop, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(email.getFromEmail(), email.getPass());
            }
        });
        MimeMessage message = new MimeMessage(sess);
        try {
            message.setFrom(new InternetAddress(email.getFromEmail()));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email.getToEmail()));
            message.setSubject("Evaluation OOP, with files");
            Multipart content = new MimeMultipart();
            MimeBodyPart body = new MimeBodyPart();
            body.setText("Good morning, I leave you the reports of the students.");
            MimeBodyPart attach2 = new MimeBodyPart();
            attach2.attachFile("./"+subject+".pdf");
            content.addBodyPart(body);
            content.addBodyPart(attach2);
            message.setContent(content);
            Transport.send(message);
            info  = "Submitted successfully!";
        } catch (Exception e) {
            info = "An error occurred: "+e.getMessage();
        }
        return info;
    }
}
