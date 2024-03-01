package com.example.filesharing.demo.resource.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filesharing.demo.invoice.service.InvoiceService;
import com.example.filesharing.demo.resource.dao.ResourceRepository;
import com.example.filesharing.demo.resource.entity.ResourceEntity;
import com.example.filesharing.demo.share.entity.ShareEntity;

@Service
public class ResourceService {
    
    @Autowired
    private ResourceRepository repository;

    @Autowired
    private InvoiceService service;

    public ResourceEntity createResource(ShareEntity entity){
        String uuid = UUID.randomUUID().toString();

        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setHash(uuid);
        resourceEntity.setResourceId(entity.getInvoice().getId());
        resourceEntity.setCreatedAt(new Date());
        resourceEntity.setExpiredAt(getExpiretionDate(10));
        resourceEntity.setResourceType("invoice");

        resourceEntity = repository.save(resourceEntity);
        return resourceEntity;
    }

    public String getResource(String hash){
        Optional<ResourceEntity> find = repository.findByHash(hash);
        if(find.isPresent()){
            ResourceEntity entity = find.get();

            if(entity.getExpiredAt().after(new Date())){
                if(entity.getResourceType().equals("invoice")){
                    return service.getInvoiceTitle(entity.getResourceId());
                }
            }

        }

        return "Not found";
    }

    private Date getExpiretionDate(int minutesToAdd){
        LocalDateTime newDateTime = LocalDateTime.now().plusMinutes(minutesToAdd);
        return new Date(Timestamp.valueOf(newDateTime).getTime());
    }
}
