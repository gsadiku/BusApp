package com.gencsadiku.BusApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.item.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class BusAppApplicationTests {

    @Test
    void contextLoads() {
    }
        
    @InjectMocks
    private BusLineService bussLineService;

    @Test
    public void testGetTopBussLineReturnsArrayList() {
        ArrayList<BusLine> bussLines = bussLineService.getTopBusLine(10);
        assertTrue(bussLines instanceof ArrayList);
    }

    @Test
    public void testGetTopBussLineReturnsNoMoreThanTopVal() {
        ArrayList<BusLine> bussLines = bussLineService.getTopBusLine(10);
        assertTrue(bussLines.size() <= 10);
    }

}
