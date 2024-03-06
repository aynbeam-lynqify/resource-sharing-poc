package com.example.filesharing.demo.customer.vo.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String name;
    List<FieldResponse> fields;
}
