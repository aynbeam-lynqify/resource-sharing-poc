package com.example.filesharing.demo.share.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.example.filesharing.demo.invoice.entity.InvoiceEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "shares")
public class ShareEntity {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hash;

    private String email;

    private Long otp;

    private Boolean verified;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(nullable = false, updatable = false)
    private Date expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private InvoiceEntity invoice;
}
