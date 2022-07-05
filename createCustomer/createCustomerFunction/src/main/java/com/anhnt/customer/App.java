package com.anhnt.customer;

import com.anhnt.common.domain.customer.request.CreateCustomerRequest;
import com.anhnt.common.domain.customer.response.CreateCustomerResponse;

public class App{
    
    public CreateCustomerResponse createCustomer(CreateCustomerRequest request) {        
      return new CreateCustomerResponse(4l);
    }
}
