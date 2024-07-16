package com.jamesanyabine.virtusfinancemanager.plaid;

import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.util.Arrays;

@Service
public class PlaidService {

    @Autowired
    private PlaidApi plaidApi;

    public LinkTokenCreateResponse createLinkToken() {
        try {
            LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser()
                    .clientUserId("unique_user_id");

            LinkTokenCreateRequest request = new LinkTokenCreateRequest()
                    .withClientName("Virtus Finance Manager")
                    .withCountryCodes(Arrays.asList(CountryCode.US))
                    .withLanguage("en")
                    .withUser(user);

            Response<LinkTokenCreateResponse> response = plaidApi
                    .linkTokenCreate(request)
                    .execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new RuntimeException("Failed to create link token: " + response.errorBody().string());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create link token", e);
        }
    }

    public AccountsGetResponse getAccounts(String publicToken) {
        try {
            ItemPublicTokenExchangeRequest request = new ItemPublicTokenExchangeRequest()
                    .publicToken(publicToken);
            Response<ItemPublicTokenExchangeResponse> response = plaidApi
                    .itemPublicTokenExchange(request)
                    .execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to exchange public token: " + response.errorBody().string());
            }

            String accessToken = response.body().getAccessToken();

            AccountsGetRequest accountsRequest = new AccountsGetRequest()
                    .accessToken(accessToken);
            Response<AccountsGetResponse> accountsResponse = plaidApi
                    .accountsGet(accountsRequest)
                    .execute();

            if (accountsResponse.isSuccessful()) {
                return accountsResponse.body();
            } else {
                throw new RuntimeException("Failed to get accounts: " + accountsResponse.errorBody().string());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get accounts", e);
        }
    }

    // Add other necessary methods for your application
}
