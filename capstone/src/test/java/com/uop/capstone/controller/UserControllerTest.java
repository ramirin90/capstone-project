package com.uop.capstone.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testHelloReturnsHelloWorld() throws Exception {
        mockMvc.perform(get("/capstone/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }
}
