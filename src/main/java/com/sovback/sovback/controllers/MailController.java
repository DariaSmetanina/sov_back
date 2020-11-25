package com.sovback.sovback.controllers;

import com.sovback.sovback.mail.mSender;
import com.sovback.sovback.model.Notification;
import com.sovback.sovback.model.Organization;
import com.sovback.sovback.payload.request.AddNewsRequest;
import com.sovback.sovback.payload.request.MailRequest;
import com.sovback.sovback.payload.response.MessageResponse;
import com.sovback.sovback.repositories.AccessRepository;
import com.sovback.sovback.repositories.NotificationRepository;
import com.sovback.sovback.repositories.OrganizationRepository;
import com.sovback.sovback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    NotificationRepository notRep;

    @Autowired
    OrganizationRepository orgRep;

    @Autowired
    AccessRepository acsRep;

    @Autowired
    UserRepository usrRep;

    @Autowired
    JavaMailSender mailSender;

    @PostMapping("/newMail")
    public ResponseEntity<?> sendMail(@RequestBody final MailRequest newMail) {

        List<String> l=new ArrayList<>();
        l.add("smetanina.03@mail.ru");
        mSender.sendEmailToReference(mailSender,l,newMail.getSubject(),("От:"+newMail.getFrom()+System.lineSeparator()+newMail.getText()));
        return ResponseEntity.ok(new MessageResponse("Письмо отправлено, наш бухгалтер вскоре ответит Вам"));
    }

    @PostMapping("/notification")
    public ResponseEntity<?> sendNotification(@RequestBody final MailRequest newMail) {

        for(String inn:newMail.getTo()){
            Notification not = new Notification();
            not.setDate(new java.sql.Date(new java.util.Date().getTime()).toString());
            not.setImportance(newMail.getImportance());
            not.setText(newMail.getText());
            not.setOrganization(orgRep.findOneByInn(inn).getId());
            notRep.save(not);
        }

        List<Long> orgId=orgRep.getOrgIds(newMail.getTo());
        List<Long> usrId=acsRep.getUsrIds(orgId);
        List<String> emails=usrRep.getUsrs(usrId);

        mSender.sendEmailToReference(mailSender,emails,newMail.getSubject(),newMail.getText());
        return ResponseEntity.ok(new MessageResponse("Уведомление отправлено"));
    }

}
