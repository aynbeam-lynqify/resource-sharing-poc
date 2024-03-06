package com.example.filesharing.demo.customer.vo.response;

import lombok.Data;

@Data
public class UserDefinedFieldResponse {
    private Long id;
    private String name;
    private String entity;
    private FieldTypeResponse fieldType;
}
