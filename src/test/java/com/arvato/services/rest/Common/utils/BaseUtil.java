package com.arvato.services.rest.Common.utils;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.arvato.services.rest.Common.Constants.*;
import static io.restassured.RestAssured.*;

public class BaseUtil {

    public JsonPath postRequest(String object, int expectedStatusCode, String responseContentType, String authKey) {


        RequestSpecification spec = given()
                .headers("X-Auth-Key", authKey(authKey), "Content-Type", responseContentType)
                .contentType(ContentType.JSON)
                .body(object)
                .log().all();


        Response httpResponse = spec.post(endPointUri(TEST_API_ENDPOINT) + bankAccountUri(BANK_ACCOUNT_URI));

        if (expectedStatusCode != httpResponse.getStatusCode()) {
            System.out.println(httpResponse.asString());

        }

        System.out.println("Request statusCode is " + httpResponse.getStatusCode());

        return new JsonPath(httpResponse.asString());

    }

    public static String authKey(String key) {

        String authKey = System.getProperty("auth.key");
        if (authKey == null) {
            authKey = key;
        }
        return authKey;

    }

    public static String bankAccountUri(String accountUri) {

        String basePath = System.getProperty("request.uri");
        if (basePath == null) {
            basePath = accountUri;
        }
        return basePath;
    }

    public static String endPointUri(String endpoint) {

        String endPointPath = System.getProperty("endpoint.uri");
        if (endPointPath == null) {
            endPointPath = endpoint;
        }
        return endPointPath;
    }

}
