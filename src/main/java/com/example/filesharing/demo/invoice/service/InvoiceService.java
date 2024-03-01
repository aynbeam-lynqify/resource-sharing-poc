package com.example.filesharing.demo.invoice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filesharing.demo.invoice.dao.InvoiceRepository;
import com.example.filesharing.demo.invoice.entity.InvoiceEntity;

@Service
public class InvoiceService {
    
    @Autowired
    private InvoiceRepository dao;

    public String getInvoiceTitle(Long id){
        Optional<InvoiceEntity> find = dao.findById(id);

        if(find.isPresent()){
            return find.get().getTitle();
        }

        return "";
    }

}
