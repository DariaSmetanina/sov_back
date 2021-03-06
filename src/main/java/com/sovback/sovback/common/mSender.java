package com.sovback.sovback.common;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class mSender {

    public static String sendEmailToReference(JavaMailSender mailSender, List<String> emailS, String subject, String mtext){

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");

        for(String em : emailS) {
            try {
                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mail, true);
                messageHelper.setTo(em);
                messageHelper.setSubject(subject);
                messageHelper.setText(mtext, true);
                mailSender.send(mail);

            } catch (MailException | MessagingException e) {
                e.printStackTrace();
            }
        }
        return "sucsess";
    }
}
