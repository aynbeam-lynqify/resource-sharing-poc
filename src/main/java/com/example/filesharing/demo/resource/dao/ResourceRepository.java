package com.example.filesharing.demo.resource.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.filesharing.demo.resource.entity.ResourceEntity;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {
    Optional<ResourceEntity> findByHash(String hash);
}
