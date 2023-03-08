/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gencsadiku.BusApp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author gencsadiku
 */
@Service
public class BusLineService {  
    
    @Value("${busApp.chunk-size}")
    private int CHUNK_SIZE;
    @Value("${busApp.bus-line-url}")
    private String BUS_LINES_URL;
    @Value("${busApp.bus-line-file-name}")
    private String BUS_LINES_FILE_NAME;
    @Value("${busApp.bus-stop-url}")
    private String STOP_AREAS_URL;
    @Value("${busApp.bus-stop-file-name}")
    private String STOP_AREAS_FILE_NAME;
    
    private ArrayList<BusLine> filterTopBusLines(int topValue){
        String filePath = fetchApiToFile(BUS_LINES_URL,BUS_LINES_FILE_NAME);       
        ArrayList<BusLine> busLines = new ArrayList<>();
        if(filePath != null){
            ObjectMapper objectMapper = new ObjectMapper();
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
                JsonParser jsonParser = objectMapper.getFactory().createParser(bufferedReader);
                BusLine currentBusLine = null;
                while(jsonParser.nextToken() != null){                    
                    if(jsonParser.currentToken() == JsonToken.START_ARRAY){
                        while(jsonParser.nextToken() != JsonToken.END_ARRAY){
                            JsonNode node = jsonParser.readValueAsTree();
                            int lineNumber = node.get("LineNumber").asInt();
                            int stopNumber = node.get("JourneyPatternPointNumber").asInt();
                            
                            if(currentBusLine == null){
                                currentBusLine = new BusLine();
                                currentBusLine.setLineNumber(lineNumber);
                                currentBusLine.addStopArea(new StopArea(stopNumber));
                            }
                            else if(currentBusLine.getLineNumber() == lineNumber){ // Add new StopArea to the current BusLine Object
                                currentBusLine.addStopArea(new StopArea(stopNumber));
                            }
                            else if(currentBusLine.getLineNumber() != lineNumber){ // Add new BusLine Object 
                                
                                if(busLines.size() < topValue){ 
                                     busLines.add(currentBusLine);
                                }
                                else if(currentBusLine.getStopAreaList().size() > Collections.min(busLines).getStopAreaList().size()){ // compare list size of stopAreaList
                                     busLines.remove(Collections.min(busLines));
                                     busLines.add(currentBusLine);
                                }                               
                                currentBusLine = new BusLine();
                                currentBusLine.setLineNumber(lineNumber);
                                currentBusLine.addStopArea(new StopArea(stopNumber));
                            }       
                        }
                    }
                }
            }
            catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return busLines;
    }
    
    public ArrayList<BusLine> getTopBusLine(int topVal){
        ArrayList<BusLine> busLines = filterTopBusLines(topVal);
        String filePath = fetchApiToFile(STOP_AREAS_URL,STOP_AREAS_FILE_NAME);
        if(filePath != null){
            Map<Integer,String> stopAreaMap = new HashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            int chunkCounter = 0;
            try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){ // read StopArea File to fetch names
                JsonParser jsonParser = objectMapper.getFactory().createParser(reader);
                while(jsonParser.nextToken() != null){                    
                    if(jsonParser.currentToken() == JsonToken.START_ARRAY){
                        while(jsonParser.nextToken() != JsonToken.END_ARRAY){
                            JsonNode node = jsonParser.readValueAsTree();   
                            int stopNumber = node.get("StopPointNumber").asInt();
                            String stopName = node.get("StopPointName").asText();
                            stopAreaMap.put(stopNumber, stopName);
                            chunkCounter++; 

                            if(chunkCounter >= CHUNK_SIZE){
                              processChunk(busLines, stopAreaMap);
                              stopAreaMap.clear();
                              chunkCounter = 0;
                            }
                        }
                        // process any remaining stop areas in the last chunk
                        if (!stopAreaMap.isEmpty()) {
                             processChunk(busLines, stopAreaMap);
                             stopAreaMap.clear();
                        }
                    }
                }

            }catch(Exception e){ 
                System.err.println("Error: " + e.getMessage());
            }
        }
        return busLines;
    }
    
    private void processChunk(ArrayList<BusLine> busLines, Map<Integer, String> stopAreaMap) {
        for (BusLine busLine : busLines) {
            for (StopArea stopArea : busLine.getStopAreaList()) {
                if (stopArea.getStopPointName().isEmpty()) {
                    String stopAreaName = stopAreaMap.getOrDefault(stopArea.getStopPointNumber(), "");
                    stopArea.setStopPointName(stopAreaName);
                }
            }
        }
    }
   
    public String fetchApiToFile(String url, String fileName){
        String path = System.getProperty("user.home") + "/" + fileName;
        try(FileOutputStream fileOutputStream = new FileOutputStream(path)){
            WebClient webclient = WebClient.builder().codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
            Mono<byte[]> mono = webclient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(byte[].class);
            byte[] fetchedData = mono.block();

            fileOutputStream.write(fetchedData);
            return path;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            
        }
        return null;
    }
    
}
