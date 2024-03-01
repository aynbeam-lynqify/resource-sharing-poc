package com.example.filesharing.demo.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.filesharing.demo.resource.service.ResourceService;

@RestController
@RequestMapping("resources")
public class ResourceController {

    @Autowired
    private ResourceService service;
    
    @GetMapping("/{hash}")
    public ResponseEntity<?> showInvoice(@PathVariable("hash") String hash){
        return ResponseEntity.ok(service.getResource(hash));
    }
}
