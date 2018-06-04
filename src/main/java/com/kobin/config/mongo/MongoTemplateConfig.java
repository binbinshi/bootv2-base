package com.kobin.config.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by @author shibinbin on 2018/6/3.
 */
@Component
public class MongoTemplateConfig {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoTemplateConfig(MongoTemplate mongoTemplate){
            this.mongoTemplate = mongoTemplate;
    }

    public void saveObject(){
        mongoTemplate.insert("sss", "sss");
    }
}
