package com.example.filesharing.demo.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.valves.rewrite.RewriteCond.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlkh.core.sql.Criteria;
import com.dlkh.core.sql.criteria.expression.Conditions;
import com.dlkh.core.sql.repository.EntityRepository;
import com.dlkh.core.sql.vo.request.PageableRequest;
import com.dlkh.core.sql.vo.response.PageableResponse;
import com.example.filesharing.demo.customer.entity.CustomerEntity;
import com.example.filesharing.demo.customer.mapper.CustomerMapper;
import com.example.filesharing.demo.customer.vo.response.CustomerResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {
    
    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private HttpServletRequest servletRequest;

    @Autowired
    private EntityRepository repository;

    public PageableResponse<CustomerResponse> list(PageableRequest request){
        Criteria<CustomerEntity> criteria = new Criteria<>(CustomerEntity.class);

        criteria.leftJoin("fields", "fieldLink");
        // criteria.orderASC("id");

        // criteria.equal("fieldLink.value", "Male");
        // criteria.orderASC("fieldLink.id");

        criteria.leftJoin("fieldLink.field", "definedField");
        // criteria.orderDESC("definedField.id");

        //?sort=definedField.id,asc:desc

        criteria.leftJoin("definedField.fieldType", "fieldType");
        criteria.orderDESC("fieldType.id");
        // criteria.equal("fieldType.name", "Date");

        criteria.or(
            Conditions.ilike("name", "m"),
            Conditions.ilike("fieldLink.value", "m")
        );

        List<CustomerResponse> items = repository.list(criteria)
            .stream().map(mapper::from).collect(Collectors.toList());

        criteria.distinct();
        Long records = repository.count(criteria);

        return new PageableResponse<CustomerResponse>(records, items, request);
    }

    public PageableResponse<CustomerResponse> listc(PageableRequest request){
        Criteria<CustomerEntity> criteria = new Criteria<>(CustomerEntity.class);

        // criteria.join("fields", "field");
        // criteria.equal("field.name", filterName);
        // criteria.equal("field.value", filterValue);

        Integer i = 0;
        for(String key : servletRequest.getParameterMap().keySet()){
            String value = servletRequest.getParameter(key);
            i++;

            if(key.contains(".")){
                String[] split = key.split("\\.");

                //"field_1"
                String alias = String.format("%s_%s", split[0], i);
                
                // criteria.join("fields", "field_1");
                criteria.join("fields", alias);

                // field_1.name
                key = String.format("%s.%s", alias, split[1]);
                log.info("======> key {}", key);
                
                // criteria.equal("field.name", filterName);
                criteria.equal(key, value);

                criteria.orderDESC(key);
            }
        }

        List<CustomerResponse> items = repository.list(criteria)
            .stream().map(mapper::from).collect(Collectors.toList());

            Long records = repository.count(criteria);
        return new PageableResponse<CustomerResponse>(records, items, request);
    }


}
