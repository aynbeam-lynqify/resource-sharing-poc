package com.example.filesharing.demo.share.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OTPResponse {
    private Boolean status;
    private String message;
}
