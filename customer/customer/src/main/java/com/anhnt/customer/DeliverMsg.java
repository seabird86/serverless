package com.anhnt.customer;

import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.anhnt.common.domain.customer.request.CreateCustomerRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DeliverMsg {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()
  .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
    public void onMessage(S3Event event){
        event.getRecords().forEach(record-> {
            S3ObjectInputStream stream = s3.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getKey()).getObjectContent();
            try {
                List<CreateCustomerRequest> list = Arrays.asList(objectMapper.readValue(stream, CreateCustomerRequest[].class));
                System.out.println(list);
                list.forEach(e->{
                    try {
                        System.out.println("Environment: "+System.getenv("TOPIC_ANH"));
                        sns.publish(System.getenv("TOPIC_ANH"), objectMapper.writeValueAsString(e));
                    } catch (JsonProcessingException e1) {                        
                        e1.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            } 
        });
    }

    public void onSNSMessage(SNSEvent event){
        event.getRecords().forEach(e -> {
            try {
                CreateCustomerRequest request = objectMapper.readValue(e.getSNS().getMessage(), CreateCustomerRequest.class);
                System.out.println(request);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
    
}
