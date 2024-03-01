package com.example.filesharing.demo.invoice.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.filesharing.demo.share.entity.ShareEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "invoices")
public class InvoiceEntity {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "invoice")
    private Set<ShareEntity> shares = new HashSet<>();
}
