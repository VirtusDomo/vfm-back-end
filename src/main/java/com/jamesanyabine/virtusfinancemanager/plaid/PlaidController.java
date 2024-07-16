package com.jamesanyabine.virtusfinancemanager.plaid;

import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plaid")
public class PlaidController {

    @Autowired
    private PlaidService plaidService;

    @PostMapping("/link")
    public LinkTokenCreateResponse createLinkToken() {
        return plaidService.createLinkToken();
    }

    @PostMapping("/accounts")
    public AccountsGetResponse getAccounts(@RequestBody String publicToken) {
        return plaidService.getAccounts(publicToken);
    }

    // Add other necessary endpoints for your application
}