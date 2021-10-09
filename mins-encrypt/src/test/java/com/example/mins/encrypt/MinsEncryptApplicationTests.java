package com.example.mins.encrypt;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MinsEncryptApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void contextLoads2() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/test/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": 1}"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(result);
    }

    @Test
    void contextLoads3() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/test/hello2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": 11}"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(result);
    }

    @Test
    void contextLoads() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/test/world")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": 2}"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(result);
    }

}
