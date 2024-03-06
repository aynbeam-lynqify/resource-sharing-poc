package com.example.filesharing.demo.customer.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Setter
@Getter
@FieldNameConstants
@Entity(name = "user_defined_field")
public class UserDefinedField {
    
    @Id 
    @Column(name = "id") 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_type")
    private FieldType fieldType;
 
    private String name;
    private String entity;

    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    private Set<UserDefinedFieldLink> link = new HashSet<>();
}
