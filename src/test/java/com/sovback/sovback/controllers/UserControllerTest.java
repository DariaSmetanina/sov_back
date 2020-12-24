package com.sovback.sovback.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sovback.sovback.model.User;
import com.sovback.sovback.pkg.mvcHandlerMappingIntrospector;
import com.sovback.sovback.repositories.AccessRepository;
import com.sovback.sovback.repositories.OrganizationRepository;
import com.sovback.sovback.repositories.UserRepository;
import com.sovback.sovback.security.TestWebSecurityConfig;
import com.sovback.sovback.security.WebSecurityConfig;
import com.sovback.sovback.security.jwt.JwtAuthEntryPoint;
import com.sovback.sovback.security.jwt.JwtUtils;
import com.sovback.sovback.security.services.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import com.sovback.sovback.repositories.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sovback.sovback.payload.request.userSettingsRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserController.class)
//@WebMvcTest(value=UserController.class)
@ContextConfiguration(classes= TestWebSecurityConfig.class)

class UserControllerTest {

    @MockBean
    NewsRepository newRep;

    @MockBean
    UserRepository usgRep;

    @MockBean
    AccessRepository acsRep;

    @MockBean
    OrganizationRepository orgRep;

    @MockBean
    PasswordEncoder encoder;

    @MockBean
    mvcHandlerMappingIntrospector mhm;

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    final String URL="/api/user";

//

    //post
    @Test
    @WithMockUser
    void posttest() throws Exception {
        String url = URL+"/test";
        byte[] us={13,17,89,10,9};

        mvc.perform( MockMvcRequestBuilders
                .post(url)
                .content(us)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)

                )
                .andExpect(status().isOk())
        ;

    }

    @Test
    @WithMockUser
    void addFile() throws Exception {
        String url = URL+"/addFile";
        String inn="test";
        byte[] content = {-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32,
                0, 0, 0, 32, 8, 3, 0, 0, 0, 68};

        final MockMultipartFile mockFile = new MockMultipartFile("file","testfile.txt",null, content);

        MvcResult mvcRequestResult = mvc.perform(
                MockMvcRequestBuilders.multipart(url)
                        .file(mockFile)
                        .param("inn", inn)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        File dir = new File("C:\\Users\\Dasha\\Desktop\\sovback\\files\\user_files\\"+calendar.get(Calendar.YEAR)+"\\"+((calendar.get(Calendar.MONTH)/3)+1)+"\\"+inn);
        File[] arrFiles = dir.listFiles();
        String str = Arrays.asList(arrFiles).get(0).getAbsolutePath();
        boolean bl="C:\\Users\\Dasha\\Desktop\\sovback\\files\\user_files\\2020\\4\\test\\testfile.txt".equals(str);
        assertTrue(bl);
        new File(str).delete();
    }

    //get

    @Test
    @WithMockUser
    void getOrg() throws Exception {

        String url = URL+"/organization";
        String inn="123123123123";

        MvcResult mvcRequestResult = mvc.perform(
                (get(url))
                        .param("inn", inn)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(200, mvcRequestResult.getResponse().getStatus());
    }

    @Test
    void getFiles() throws Exception {

        String url = URL+"/files";
        String inn="123123123123";

        MvcResult mvcRequestResult = mvc.perform(
                (get(url))
                        .param("inn", inn)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(200, mvcRequestResult.getResponse().getStatus());
    }

    @Test
    void getFile() throws Exception {
        String url = URL+"/download2";
        String inn="123123123123";
        String fileName="INVOICE_PAYMENT (1).pdf";

        MvcResult mvcRequestResult = mvc.perform(
                (get(url))
                        .param("inn", inn)
                        .param("fileName", fileName)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(200, mvcRequestResult.getResponse().getStatus());
    }













//    //delete
//    @Test
//    @WithMockUser
//    void deleteOrgForUser() throws Exception{
//        String url = URL+"/settings";
//        String inn="123123123123";
//
//        MvcResult mvcRequestResult = mvc.perform(
//                (delete(url))
//                        .param("inn", inn)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//        assertEquals(200, mvcRequestResult.getResponse().getStatus());
//    }
//    //java.lang.NullPointerException

//    @Test
//    @WithMockUser
//    void userSettings() throws Exception {
//        String url = URL+"/settings";
//        userSettingsRequest ur=new userSettingsRequest();
//        ur.setEmail("em@m.ru");
//
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(ur);
//
//        mvc.perform( MockMvcRequestBuilders
//                .post(url)
//                .content(json)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//
//        )
//                .andExpect(status().isOk())
//        ;
//    }
    //Тут нужно настраивать заглушки на БД

//    @Test
//    @WithMockUser(roles="admin")
//    void addOrgToUser() {
//    }
//
//    @Test
//    @WithMockUser(roles="admin")
//    void addOrg() {
//    }

}
