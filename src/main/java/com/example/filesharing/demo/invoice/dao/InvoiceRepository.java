package com.example.filesharing.demo.invoice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.filesharing.demo.invoice.entity.InvoiceEntity;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
}
