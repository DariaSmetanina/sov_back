package com.sovback.sovback.controllers;

import com.sovback.sovback.pkg.mvcHandlerMappingIntrospector;
import com.sovback.sovback.repositories.AccessRepository;
import com.sovback.sovback.repositories.AccountRepository;
import com.sovback.sovback.repositories.NotificationRepository;
import com.sovback.sovback.repositories.OrganizationRepository;
import com.sovback.sovback.security.TestWebSecurityConfig;
import com.sovback.sovback.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AccountController.class)
@ContextConfiguration(classes= TestWebSecurityConfig.class)
class AccountControllerTest {

    @MockBean
    mvcHandlerMappingIntrospector mhm;

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    final String URL="/api/accounts";

    @MockBean
    AccessRepository acsRep;
    @MockBean
    OrganizationRepository orgRep;
    @MockBean
    AccountRepository accRep;
    @MockBean
    NotificationRepository notRep;
    @MockBean
    UserDetailsImpl uml;

    @Test
    @WithMockUser
    void getAccount() throws Exception {
//        String url = URL+"/account";
//
//        MvcResult mvcRequestResult = mvc.perform(
//                (get(url))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//        assertEquals(200, mvcRequestResult.getResponse().getStatus());
    }

    @Test
    void getFourAccount() {
    }

    @Test
    void getOrganizations() {
    }
}
