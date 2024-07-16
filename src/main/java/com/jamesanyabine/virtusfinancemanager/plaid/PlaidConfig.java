package com.jamesanyabine.virtusfinancemanager.plaid;

import com.plaid.client.ApiClient;
import com.plaid.client.request.PlaidApi;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaidConfig {

    @Value("${plaid.clientId}")
    private String clientId;

    @Value("${plaid.secret}")
    private String secret;

    @Value("${plaid.environment}")
    private String environment;

    @Bean
    public PlaidApi plaidApi(){
        ApiClient apiClient = new ApiClient();
        apiClient.setPlaidAdapter(environment);
        apiClient.setCredentials(clientId, secret);
        return apiClient.createService(PlaidApi.class);
    }
}