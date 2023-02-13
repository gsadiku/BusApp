/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gencsadiku.BussApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gencsadiku
 */
@RestController
public class DefaultRestController {
    
    @GetMapping("/")
    public String index(){
        return "Hello from genci";
    }
}
