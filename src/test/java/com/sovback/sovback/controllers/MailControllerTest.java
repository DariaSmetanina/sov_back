package com.sovback.sovback.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sovback.sovback.payload.request.MailRequest;
import com.sovback.sovback.pkg.mvcHandlerMappingIntrospector;
import com.sovback.sovback.repositories.AccessRepository;
import com.sovback.sovback.repositories.NotificationRepository;
import com.sovback.sovback.repositories.OrganizationRepository;
import com.sovback.sovback.repositories.UserRepository;
import com.sovback.sovback.security.TestWebSecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MailController.class)
@ContextConfiguration(classes= TestWebSecurityConfig.class)

class MailControllerTest {

    @MockBean
    NotificationRepository notRep;

    @MockBean
    OrganizationRepository orgRep;

    @MockBean
    AccessRepository acsRep;

    @MockBean
    UserRepository usrRep;

    @MockBean
    JavaMailSender mailSender;

    @MockBean
    PasswordEncoder encoder;

    @MockBean
    mvcHandlerMappingIntrospector mhm;

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    final String URL="/api/mail";

    @Test
    void sendMail() throws Exception {

//        String url = URL+"/newMail";
//        MailRequest mr=new MailRequest();
//        mr.setFrom("em@m.ru");
//        mr.setImportance("2");
//        mr.setText("text");
//        mr.setSubject("help");
//        mr.setTo(new String[]{"mml@mml.ru"});
//
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(mr);
//
//        mvc.perform( MockMvcRequestBuilders
//                .post(url)
//                .content(json)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk()).andReturn();


    }

    @Test
    void sendNotification() {
    }
}
