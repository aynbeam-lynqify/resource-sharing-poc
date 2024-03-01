package com.example.filesharing.demo.share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.filesharing.demo.share.service.ShareService;
import com.example.filesharing.demo.share.vo.response.OTPResponse;
import com.example.filesharing.demo.share.vo.response.SharingResponse;

@RestController
@RequestMapping("share")
public class ShareController {

    @Autowired
    private ShareService service;

    @GetMapping("/access/{id}")
    public ResponseEntity<SharingResponse> access(@PathVariable("id") String id){
        return ResponseEntity.ok(service.access(id));
    }

    @GetMapping("/otp/{id}")
    public ResponseEntity<OTPResponse> opt(@PathVariable("id") String id){
        return ResponseEntity.ok(service.otp(id));
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<SharingResponse> verify(@PathVariable("code") Long code){
        return ResponseEntity.ok(service.verify(code));
    }
    
}
