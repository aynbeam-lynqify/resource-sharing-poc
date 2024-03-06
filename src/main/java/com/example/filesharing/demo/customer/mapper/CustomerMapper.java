package com.example.filesharing.demo.customer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.example.filesharing.demo.customer.entity.CustomerEntity;
import com.example.filesharing.demo.customer.vo.response.CustomerResponse;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerResponse from(CustomerEntity entity);
}
