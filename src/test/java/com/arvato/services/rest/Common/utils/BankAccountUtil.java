package com.arvato.services.rest.Common.utils;

import io.restassured.path.json.JsonPath;
import jakarta.json.Json;

public class BankAccountUtil extends BaseUtil {

    public JsonPath validateBankAccount(String bankAccount, int statusCode, String contentType, String authKey) {

        String jsonObject = Json.createObjectBuilder()
                .add("bankAccount", bankAccount)
                .build().toString();

        return super.postRequest(jsonObject, statusCode, contentType, authKey);

    }

}
