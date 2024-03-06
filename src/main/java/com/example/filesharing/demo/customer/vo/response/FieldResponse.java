package com.example.filesharing.demo.customer.vo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldResponse {
    private Long id;
    private String value;
    private UserDefinedFieldResponse field;
}
