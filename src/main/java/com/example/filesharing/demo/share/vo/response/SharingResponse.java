package com.example.filesharing.demo.share.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SharingResponse {
    private String message;
    private String optUrl;
}
