package io.cucumber.arvato;

import com.arvato.services.rest.Common.utils.APIBaseUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.json.Json;
import org.junit.rules.ErrorCollector;

import static com.arvato.services.rest.Common.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class StepDefinitions extends APIBaseUtil {

    @Given("the bank account validation request with a valid IBAN")
    public String TheBankAccountRequestWithValidIban() {
        String jsonObject = Json.createObjectBuilder()
                .add("bankAccount", VALID_BANK_ACCOUNT)
                .build().toString();

        return jsonObject;
    }

    @And("valid authentication token")
    public static String setAuthToken() {
        return "lXGp4CSPnzz9Zw5ykDDMEUPNUMAiLNGPG2txrdNs";
    }

    @When("bank account validation request is sent to the server")
    public Response requestIsSentToServer() {
        RequestSpecification spec = given()
                .headers("X-Auth-Key", authKey(setAuthToken()), "Content-Type", ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(TheBankAccountRequestWithValidIban())
                .log().all();

        return spec.post(endPointUri(TEST_API_ENDPOINT) + bankAccountUri(BANK_ACCOUNT_URI));
    }

    @Then("server responds with HTTP response {int} code")
    public boolean getStatusCode(int code) {

        boolean result = false;
        Response httpResponse = requestIsSentToServer();
        if (code == httpResponse.getStatusCode()) {
            result = true;
            httpResponse.prettyPrint();
        }
            return result;
    }

    @And("response body contains isValid true")
    public void isValid() {
            JsonPath httpResponse = new JsonPath(requestIsSentToServer().asString());
            ErrorCollector collector = new ErrorCollector();
            collector.checkThat(httpResponse.get("isValid").toString(), containsString("true"));
    }

    @Given("the bank account validation request without a JWT token")
    public static String setEmptyAuthToken() {
        return "";
    }

    @When("sample request is sent to the server")
    public Response sampleRequestIsSentToTheServer() {
        RequestSpecification spec = given()
                .headers("X-Auth-Key", authKey(setEmptyAuthToken()), "Content-Type", ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(TheBankAccountRequestWithValidIban())
                .log().all();

        return spec.post(endPointUri(TEST_API_ENDPOINT) + bankAccountUri(BANK_ACCOUNT_URI));
    }

    @Then("response body contains „Authorization has been denied for this request.“ message")
        public void authDenied() {
        JsonPath httpResponse = new JsonPath(sampleRequestIsSentToTheServer().asString());
        ErrorCollector collector = new ErrorCollector();
        collector.checkThat(httpResponse.get("message").toString(),
                containsString("Authorization has been denied for this request."));
    }

}
