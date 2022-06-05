package com.arvato.services.rest.Common.utils;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import jakarta.json.Json;


public class BankAccountUtil extends APIBaseUtil {

    public JsonPath validateBankAccount(String bankAccount, int statusCode, String authKey) {

        String jsonObject = Json.createObjectBuilder()
                .add("bankAccount", bankAccount)
                .build().toString();

        return super.postRequest(jsonObject, statusCode, ContentType.JSON, authKey);
    }

}
