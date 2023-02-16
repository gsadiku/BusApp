/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gencsadiku.BusApp;


/**
 *
 * @author gencsadiku
 */
public class StopArea {
    
    private int stopPointNumber;
    private String stopPointName = "";
    
    public StopArea(){
    }
    
    public StopArea(int stopPointNumberValue){
        this.stopPointNumber = stopPointNumberValue;
    }
    
    public StopArea(int stopPointNumberValue, String stopPointNameValue){
        this.stopPointName = stopPointNameValue;
    }
    
    public String getStopPointName(){
        return this.stopPointName;
    }
    
    public void setStopPointName(String value){
        this.stopPointName = value;
    }
    
    public int getStopPointNumber(){
        return this.stopPointNumber;
    }
    
    public void setStopPointNumber(int value){
        this.stopPointNumber = value;
    }
    
    @Override
    public String toString(){
        return "StopArea: " + this.stopPointNumber + " Name: " + this.stopPointName;
    }
}
