package com.arvato.services.rest.Common.utils;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.arvato.services.rest.Common.Constants.*;
import static io.restassured.RestAssured.*;

public class APIBaseUtil {

    /*API base util*/
    public JsonPath postRequest(String object, int expectedStatusCode, ContentType responseContentType, String authKey) {

        RequestSpecification spec = given()
                .headers("X-Auth-Key", authKey(authKey), "Content-Type", responseContentType)
                .contentType(ContentType.JSON)
                .body(object)
                .log().all();

        Response httpResponse = spec.post(endPointUri(TEST_API_ENDPOINT) + bankAccountUri(BANK_ACCOUNT_URI));

        if (expectedStatusCode != httpResponse.getStatusCode()) {
            httpResponse.prettyPrint();
        }

        System.out.println("Request statusCode is " + httpResponse.getStatusCode());

        return new JsonPath(httpResponse.asString());
    }


    public static String authKey(String key) {
        String authKey = key;
        if (key == null) {
            authKey = System.getProperty("auth.key");
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
