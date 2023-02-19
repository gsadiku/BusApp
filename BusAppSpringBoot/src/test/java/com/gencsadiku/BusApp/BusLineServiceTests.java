/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gencsadiku.BusApp;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * @author gencsadiku
 */
@SpringBootTest
public class BusLineServiceTests {
 
    @MockBean
    BusLineService busLineService;
    
    @Test
    public void testBrokenLinkFetchApiToFile(){
        assertEquals(null, busLineService.fetchApiToFile("http:///gggg", "file.json"));
    }
    
    @Test
    public void testGetTopBusLineReturnsArrayList() {
        ArrayList<BusLine> busLines = busLineService.getTopBusLine(10);
        assertTrue(busLines instanceof ArrayList);
    }

    @Test
    public void testGetTopBusLineReturnsNoMoreThanTopVal() {
        ArrayList<BusLine> busLines = busLineService.getTopBusLine(10);
        assertTrue(busLines.size() <= 10);
    }
    
    
}
