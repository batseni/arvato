package com.arvato.services.rest.Common.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jakarta.json.Json;

import static com.arvato.services.rest.Common.Constants.CONTENT_TYPE_JSON;

public class BankAccountUtil extends BaseUtil {

    public JsonPath validateBankAccount(String bankAccount, int statusCode, String authKey) {

        String jsonObject = Json.createObjectBuilder()
                .add("bankAccount", bankAccount)
                .build().toString();

        return super.postRequest(jsonObject, statusCode, CONTENT_TYPE_JSON, authKey);

    }

}
