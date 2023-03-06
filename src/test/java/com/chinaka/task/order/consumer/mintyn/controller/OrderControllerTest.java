package com.chinaka.task.order.consumer.mintyn.controller;


import com.chinaka.task.order.consumer.mintyn.MintynTaskApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MintynTaskApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
class OrderApplicationTests {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getOrderShouldBeSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/{id}", "10"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("00")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("SUCCESS")))
                .andExpect(jsonPath("$.respBody.orderId", Matchers.is("1370408342")))
                .andExpect(jsonPath("$.respBody.productId", Matchers.is("1")))
                .andExpect(jsonPath("$.respBody.quantity", Matchers.is(1)))
                .andExpect(jsonPath("$.respBody.price", Matchers.is(150.00)))
                .andExpect(jsonPath("$.respBody.total", Matchers.is(150.00)))
                .andExpect(jsonPath("$.respBody.customerName", Matchers.is("John Doe")))
                .andExpect(jsonPath("$.respBody.customerPhone", Matchers.is("08033329329")))
                .andExpect(jsonPath("$.respBody.customerAddress", Matchers.is("Victoria Island")));
    }


    @Test
    void getOrderShouldThrow417() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/{id}", "190"))
                .andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("301")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("ORDER NOT FOUND")));
    }

    @Test
    void lisOrderShouldThrowSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("00")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("SUCCESS")));
    }

}