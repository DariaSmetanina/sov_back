package com.sovback.sovback.controllers;

import com.sovback.sovback.pkg.mvcHandlerMappingIntrospector;
import com.sovback.sovback.repositories.NewsRepository;
import com.sovback.sovback.security.TestWebSecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NewsController.class)
@ContextConfiguration(classes= TestWebSecurityConfig.class)
@WebAppConfiguration
class NewsControllerTest {

    @MockBean
    mvcHandlerMappingIntrospector mhm;

    @MockBean
    NewsRepository newRep;

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    final String URL="/api/news";

    @Test
    @WithMockUser
    void getNew() throws Exception {

        String url = URL+"/one";
        String id="1";

        MvcResult mvcRequestResult = mvc.perform(
                (get(url))
                        .param("ID", id)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();

          assertEquals(200, mvcRequestResult.getResponse().getStatus());

    }

    @Test
    @WithMockUser
    void getAll() throws Exception {
        String url = URL+"/";

        MvcResult mvcRequestResult = mvc.perform(
                (get(url))
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();

        assertEquals(200, mvcRequestResult.getResponse().getStatus());
        assertEquals("[{\"mainPart\":\"Здесь пока ничего нет\",\"title\":\":с\"}]", mvcRequestResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    void getThreeNews() throws Exception {
        String url = URL+"/three";

        MvcResult mvcRequestResult = mvc.perform(
                (get(url))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(200, mvcRequestResult.getResponse().getStatus());
        assertEquals("[{\"mainPart\":\"Здесь пока ничего нет\",\"title\":\":с\"}]", mvcRequestResult.getResponse().getContentAsString());
    }

    @Test
    void createNews() throws Exception{

        String url = URL+"/addNews";
        String title="tt";
        String urgency="2";
        String mainpart="ttex";
        String longPart="ttextttt";
        byte[] content = {-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32,
                0, 0, 0, 32, 8, 3, 0, 0, 0, 68};

        final MockMultipartFile mockFile = new MockMultipartFile("file","testfile.txt",null, content);


        final MvcResult mvcRequestResult = mvc.perform(
                MockMvcRequestBuilders.multipart(url)
                        .file(mockFile)
                        .param("title", title)
                        .param("urgency", urgency)
                        .param("mainPart", mainpart)
                        .param("longPart", longPart)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String d = new java.sql.Date(new java.util.Date().getTime()).toString();
        File dir = new File("C:\\Users\\Dasha\\Desktop\\sovback\\files\\news\\" + d);
        File fl=new File("C:\\Users\\Dasha\\Desktop\\sovback\\files\\news\\" + d+"\\testfile.txt");
        boolean bl=fl.exists();
        assertTrue(bl);
        fl.delete();
    }


}
