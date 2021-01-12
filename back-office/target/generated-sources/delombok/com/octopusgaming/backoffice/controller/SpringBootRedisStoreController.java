package com.octopusgaming.backoffice.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SpringBootRedisStoreController {

    @GetMapping("/viewSessionData")                     
    // it will handle all request for /welcome
    public java.util.Map<String,Integer> start(HttpServletRequest request) {
        Integer integer =(Integer) request.getSession().getAttribute("hitCounter");
        if(integer==null){
            integer=0;
            integer++;
            request.getSession().setAttribute("hitCounter",integer);
        }else{
            integer++;
            request.getSession().setAttribute("hitCounter",integer);
        }
        java.util.Map<String,Integer> hitCounter=new HashMap<>();
        hitCounter.put("Hit Counter",integer);
        return hitCounter;
    }

}
