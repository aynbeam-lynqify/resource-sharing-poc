package com.example.filesharing.demo.invoice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.filesharing.demo.invoice.dao.InvoiceRepository;
import com.example.filesharing.demo.invoice.entity.InvoiceEntity;
import com.example.filesharing.demo.invoice.vo.request.InvoiceShareRequest;
import com.example.filesharing.demo.share.service.ShareService;

@RestController
@RequestMapping("invoices")
public class InvoiceController {

    @Autowired
    private InvoiceRepository dao;

    @Autowired
    private ShareService shareService;
    
    @PostMapping("/share/{id}")
    public ResponseEntity<String> show(@PathVariable("id") Long id, @RequestBody InvoiceShareRequest body){
        
        Optional<InvoiceEntity> entity = dao.findById(id);
        if(entity.isPresent()){
            return ResponseEntity.ok(shareService.geShareUrl(entity.get(), body.getEmail()));
        }

        return ResponseEntity.ok("Resource not found");
    }

}
