package com.example.filesharing.demo.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EncryptMessage {
    private String encrypted;
    private String ivBase64;
}
