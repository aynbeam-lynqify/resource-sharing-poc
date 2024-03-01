package com.example.filesharing.demo.share.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.filesharing.demo.share.entity.ShareEntity;

public interface ShareRepository extends JpaRepository<ShareEntity, Long> {
    Optional<ShareEntity> findByHash(String hash);
    Optional<ShareEntity> findByOtp(Long code);
}
