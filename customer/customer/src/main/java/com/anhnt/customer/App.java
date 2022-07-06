package com.anhnt.customer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.anhnt.common.domain.customer.request.CreateCustomerRequest;
import com.anhnt.common.domain.customer.response.CreateCustomerResponse;
import com.anhnt.common.domain.customer.response.GetCustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class App{
  private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()
  .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());


  public APIGatewayProxyResponseEvent createCustomer(final APIGatewayProxyRequestEvent input, final Context context) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Content-Type", "application/json");
      APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(headers);
      try {
        CreateCustomerRequest request = objectMapper.readValue(input.getBody(), CreateCustomerRequest.class);
        Table customerTable = dynamoDB.getTable("customer");
        Item item = new Item().withPrimaryKey("id",UUID.randomUUID().toString()).withString("username", request.getUsername())
        .withNumber("balance", request.getBalance()).withLong("date_of_birth", request.getDateOfBirth().toEpochDay());
        customerTable.putItem(item);
          return response
                  .withStatusCode(200)
                  .withBody(objectMapper.writeValueAsString(new CreateCustomerResponse(4l)));
      } catch (IOException e) {
          e.printStackTrace();
          return response.withStatusCode(500).withBody("{}");
      }
  }

  public APIGatewayProxyResponseEvent getCustomer(final APIGatewayProxyRequestEvent input, final Context context) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(headers);
    try {
      String id = input.getPathParameters().get("id");
      System.out.println("Log ID" + id);
      QuerySpec spec = new QuerySpec().withKeyConditionExpression("id=:v_id").withValueMap(new ValueMap()
      .withString(":v_id", id));
      ItemCollection<QueryOutcome> items = dynamoDB.getTable("customer").query(spec);
      Item item = items.iterator().next();
      GetCustomerResponse customer = new GetCustomerResponse();
      customer.setId(item.getString("id"));
      customer.setBalance(item.getNumber("balance"));
      customer.setUsername(item.getString("username"));
      customer.setDateOfBirth(LocalDate.ofEpochDay(item.getLong("date_of_birth")));
      return response
                .withStatusCode(200)
                .withBody(objectMapper.writeValueAsString(customer));
    } catch (IOException e) {
        return response.withStatusCode(500).withBody("{}");
    }
}
}