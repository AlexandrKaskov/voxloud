package com.voxloud.provisioning.controller;

import com.voxloud.provisioning.service.ProvisioningService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProvisioningControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProvisioningService service;

    @Test
    public void getProvisioning_macAddressPresent_shouldReturnCorrectFile() throws Exception {
        String expectedResult = "Expected result";
        when(this.service.getProvisioningFile("aa-bb-cc-dd-ee")).thenReturn(expectedResult);
        this.mockMvc.perform(get("/api/v1/provisioning/aa-bb-cc-dd-ee"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedResult)));
    }

    @Test
    public void getProvisioning_macAddressAbsent_shouldReturnErrorNotFound() throws Exception {
        when(this.service.getProvisioningFile("aa-bb-cc-dd-ee")).thenReturn(null);
        this.mockMvc.perform(get("/api/v1/provisioning/aa-bb-cc-dd-ee"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

}
