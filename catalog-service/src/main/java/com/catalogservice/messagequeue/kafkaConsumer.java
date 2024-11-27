package com.catalogservice.messagequeue;

import com.catalogservice.entity.CatalogEntity;
import com.catalogservice.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class kafkaConsumer {
    CatalogRepository catalogRepository;

    @Autowired
    public kafkaConsumer(CatalogRepository catalogRepository){
        this.catalogRepository = catalogRepository;
    };

    @KafkaListener(topics = "example-catalog-topic",groupId = "consumerGroupId")
    public void updateQty(String kafkaMessage){
        log.info("kafka Message: ->"+kafkaMessage);
        Map<Object,Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        CatalogEntity entity = catalogRepository.findByProductId((String)map.get("productId"));
        if(entity !=null){
            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
            catalogRepository.save(entity);
        }
    }
}
