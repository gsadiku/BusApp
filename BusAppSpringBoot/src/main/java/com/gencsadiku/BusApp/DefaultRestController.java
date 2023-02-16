/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gencsadiku.BusApp;

import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gencsadiku
 */
@RestController
public class DefaultRestController {
    
    @Autowired
    BusLineService busLineService;
    
    @GetMapping(path ="/api/linedata", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> getTopLineData(@RequestParam("top") int value){
        if(value < 1){
            return ResponseEntity.badRequest().build();
        }
        
        ArrayList<BusLine> busLines = busLineService.getTopBusLine(value);
        Collections.sort(busLines,Collections.reverseOrder());
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(busLines);
        return ResponseEntity.ok(new MappingJacksonValue(mappingJacksonValue));
    }
}
 