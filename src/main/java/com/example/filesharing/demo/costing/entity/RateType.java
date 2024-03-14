package com.example.filesharing.demo.costing.entity;

import com.example.filesharing.demo.costing.types.Expression;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rate_type")
public class RateType {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coporate_id")
    private Long coporateId;

    private String name;
    private double value;
    private Expression expression;
}
