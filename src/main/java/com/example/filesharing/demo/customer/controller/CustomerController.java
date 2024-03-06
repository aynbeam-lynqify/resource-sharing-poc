package com.example.filesharing.demo.customer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dlkh.core.sql.vo.request.PageableRequest;
import com.dlkh.core.sql.vo.response.PageableResponse;
import com.example.filesharing.demo.customer.service.CustomerService;
import com.example.filesharing.demo.customer.vo.response.CustomerResponse;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService service;
    
    @GetMapping
    public PageableResponse<CustomerResponse> list(PageableRequest request){
        return Optional.of(request)
            .map(service::list)
            .orElseThrow();
    }

}
