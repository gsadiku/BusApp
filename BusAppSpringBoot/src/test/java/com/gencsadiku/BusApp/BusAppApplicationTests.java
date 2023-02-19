package com.gencsadiku.BusApp;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BusAppApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void contextLoads() {
    }

    @Test
    public void testApiCorrectInput() throws Exception{
        mockMvc.perform(get("/api/linedata?top=10")) // test normal input
            .andExpect(status().isOk());    
    }
    
    @Test 
    public void testApiWrongInput() throws Exception{
        mockMvc.perform(get("/api/linedata?top=0"))
                .andExpect(status().isBadRequest());
    }
    
    @Test 
    public void testApiCharacterInput() throws Exception{
        mockMvc.perform(get("/api/linedata?top=g"))
                .andExpect(status().isBadRequest());
    }
}
