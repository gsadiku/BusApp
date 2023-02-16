/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gencsadiku.BusApp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gencsadiku
 */
public class BusLine implements Comparable<BusLine> {
    
    private int lineNumber;
    private List<StopArea> stopAreaList = new ArrayList<>();
    
    public void addStopArea(StopArea value){
        this.stopAreaList.add(value);
    }
    
    public StopArea getStopArea(int index){
        return this.stopAreaList.get(index);
    }

    public List<StopArea> getStopAreaList(){
        return this.stopAreaList;
    }
    
    public void setStopAreaList(List<StopArea> value){
        this.stopAreaList = value;
    }
    
    public int getLineNumber(){
        return this.lineNumber;
    }
    
    public void setLineNumber(int value){
        this.lineNumber = value;
    }
    
    @Override
    public String toString(){
        return "LineNumber: " + lineNumber + " Stops: " + stopAreaList.size();  
    }

    @Override
    public int compareTo(BusLine o) {
        return Integer.compare(this.getStopAreaList().size(), o.getStopAreaList().size());   
    }
} 
