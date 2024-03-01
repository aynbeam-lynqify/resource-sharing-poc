package com.example.filesharing.demo.share.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.filesharing.demo.invoice.entity.InvoiceEntity;
import com.example.filesharing.demo.resource.entity.ResourceEntity;
import com.example.filesharing.demo.resource.service.ResourceService;
import com.example.filesharing.demo.services.CookieService;
import com.example.filesharing.demo.services.EmailService;
import com.example.filesharing.demo.share.dao.ShareRepository;
import com.example.filesharing.demo.share.entity.ShareEntity;
import com.example.filesharing.demo.share.vo.response.OTPResponse;
import com.example.filesharing.demo.share.vo.response.SharingResponse;

@Service
public class ShareService {
    
    @Autowired
    private ShareRepository dao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private ResourceService resourceService;

    public String geShareUrl(InvoiceEntity invoiceEntity, String email){
        String uuid = UUID.randomUUID().toString();

        ShareEntity entity = new ShareEntity();
        entity.setHash(uuid);
        entity.setInvoice(invoiceEntity);
        entity.setCreatedAt(new Date());
        entity.setExpiredAt(getExpiretionDate(10));
        entity.setEmail(email);

        dao.save(entity);

        String uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost:8000")
            .path(String.format("/share/access/%s", uuid))
            .build()
            .toUriString();

        String subject = "ERP Resource Sharing";
        String content = String.format("ERP Share you : %s", uriComponents);
        emailService.sendEmail(email, subject, content);

        return uriComponents;
    }

    public SharingResponse access(String uuid){
        Optional<ShareEntity> find = dao.findByHash(uuid);
        if(find.isPresent()){
            ShareEntity entity = find.get();

            if(Boolean.TRUE.equals(entity.getVerified()) && cookieService.cookiesContain(entity.getOtp(), entity.getHash())){
                return generateResources(entity);
            }
        }

        String uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost:8000")
            .path(String.format("/share/otp/%s", uuid))
            .build()
            .toUriString();

        return SharingResponse.builder()
            .message("access_denied")
            .optUrl(uriComponents)
            .build();
    }

    public OTPResponse otp(String uuid){
        Optional<ShareEntity> find = dao.findByHash(uuid);

        if(find.isPresent()){
            ShareEntity entity = find.get();
            
            String recipient = entity.getEmail();
            String subject = "ERP one-time password";
            Long optCode = geOTPCode();
            String content = String.format("This is your ERP one-time password: %s", optCode);

            entity.setOtp(optCode);

            dao.save(entity);
            emailService.sendEmail(recipient, subject, content);
            
            cookieService.setCookie(uuid, String.valueOf(optCode));

            return OTPResponse.builder()
                .message("Please check your email for a one-time password (OTP)")
                .status(true)
                .build();
        }

        return OTPResponse.builder()
            .status(false)
            .message("Resource not found")
            .build();
    }

    public SharingResponse verify(Long code){
        Optional<ShareEntity> find = dao.findByOtp(code);

        if(find.isPresent()){
            ShareEntity entity = find.get();
            entity.setVerified(true);
            dao.save(entity);

            cookieService.setCookie(String.valueOf(code), entity.getHash());

            return generateResources(entity);
        }

        return SharingResponse.builder()
            .message("access granted")
            .build();
    }

    private SharingResponse generateResources(ShareEntity entity){
        
        ResourceEntity resource = resourceService.createResource(entity);

        String uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost:8000")
                    .path(String.format("/resources/%s", resource.getHash()))
                    .build()
                    .toUriString();

        return SharingResponse.builder()
            .message("access granted")
            .optUrl(uriComponents)
            .build();
    }

    private Date getExpiretionDate(int minutesToAdd){
        LocalDateTime newDateTime = LocalDateTime.now().plusMinutes(minutesToAdd);
        return new Date(Timestamp.valueOf(newDateTime).getTime());
    }

    private Long geOTPCode(){
        long min = 10000L; // Minimum value (5 digits)
        long max = 99999L; // Maximum value (5 digits)
        return min + new Random().nextLong() % (max - min + 1);
    }

}
