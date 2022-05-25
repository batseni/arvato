package com.arvato.services.rest.Tests;

import com.arvato.services.rest.Common.utils.BankAccountUtil;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.TestName;

import static com.arvato.services.rest.Common.Constants.*;
import static org.hamcrest.Matchers.containsString;

public class ValidateBankAccountTest {

    JsonPath response;
    private String givenValidBankAccount;
    private String andValidAuthToken;

    @Rule
    public ErrorCollector collector = new ErrorCollector();
    /*SoftAssertions softly = new SoftAssertions();*/
    @Rule public TestName name = new TestName();

    @Before
    public void testSetUp() {
        System.out.println("Executing test: " + name.getMethodName());
    }

    /**
     * Positive test
     * Given the bank account validation request with a valid IBAN and valid authentication token
     * When bank account validation request is sent to the server
     * Then server responds with HTTP response code 200
     * And response body contains isValid = true
     */
    @Test
    public void BankAccountValidationIsTrue() {
        givenValidBankAccount = "NL07ABNA2847123288";
        andValidAuthToken = AUTH_KEY;
        response = WhenBankAccountValidationRequestIsSentToTheServer(givenValidBankAccount, andValidAuthToken,
                STATUS_CODE_TRUE);
        ThenServerRespondsWith200AndBodyContainsIsValidTrue(response);

    }

    /**
     * Negative test
     * Given the bank account validation request without a JWT token
     * When sample request is sent to the server
     * Then server responds with HTTP response code 401
     */
    @Test
    public void UnauthorizedBankAccountValidation() {
        givenValidBankAccount = "NL07ABNA2847123288";
        String AND_INVALID_AUTH_TOKEN = "";
        response = WhenBankAccountValidationRequestIsSentToTheServer(givenValidBankAccount, AND_INVALID_AUTH_TOKEN,
                STATUS_UNAUTHORIZED);
        ThenServerRespondsWith401AndBodyContainsDeniedMessage(response);
    }

    /**
     * Negative test
     * The purpose of this test is to verify error messages if bank account is shorter then allowed minimum length
     * bank account considered to be valid if its length is between 7 and 34 chars,
     * 400.00x error returned if validation fails
     */
    @Test
    public void ShortBankAccountValidation() {
        String givenShortBankAccount = "GB09HA";
        andValidAuthToken = AUTH_KEY;
        response = WhenBankAccountValidationRequestIsSentToTheServer(givenShortBankAccount, andValidAuthToken,
                STATUS_BAD_REQUEST);
        ThenServerRespondsWith400AndBodyContainsToShortBankAccountMessage(response);
    }

    /**
     * Negative test
     * The purpose of this test is to verify error messages if bank account is longer then allowed maximum length
     * bank account considered to be valid if its length is between 7 and 34 chars,
     * 400.00x error returned if validation fails
     */
    @Test
    public void LongBankAccountValidation() {
        String givenLongBankAccount = "GB09HAOE913118080023171234567890123";
        andValidAuthToken = AUTH_KEY;
        response = WhenBankAccountValidationRequestIsSentToTheServer(givenLongBankAccount, andValidAuthToken,
                STATUS_BAD_REQUEST);
        ThenServerRespondsWith400AndBodyContainsToLongBankAccountMessage(response);
    }

    /**
     * The purpose of this test is to verify error messages in case of invalid bank account number
     */
    @Test
    public void InvalidBankAccountValidation() {
        String givenInvalidBankAccount = "LI8808800262 172\tAC 439897313";
        andValidAuthToken = AUTH_KEY;
        response = WhenBankAccountValidationRequestIsSentToTheServer(givenInvalidBankAccount, andValidAuthToken,
                STATUS_CODE_TRUE);
        ThenServerRespondsWith200AndBodyContainsNotSupportedBankAccountMessage(response);
    }

    /*Validations*/
    private static void ThenServerRespondsWith200AndBodyContainsIsValidTrue(JsonPath response) {
        ErrorCollector collector = new ErrorCollector();
        collector.checkThat(response.get("isValid").toString(), containsString("true"));
    }

    private static void ThenServerRespondsWith401AndBodyContainsDeniedMessage
            (JsonPath response) {
        ErrorCollector collector = new ErrorCollector();
        collector.checkThat(response.get("message").toString(),
                containsString("Authorization has been denied for this request."));
    }

    private static void ThenServerRespondsWith400AndBodyContainsToShortBankAccountMessage
            (JsonPath response) {
        ErrorCollector collector = new ErrorCollector();
        collector.checkThat(response.get("type").toString(), containsString("BusinessError"));
        collector.checkThat(response.get("code").toString(), containsString("400.006"));
        collector.checkThat(response.get("message").toString(),
                containsString("A string value with minimum length 7 is required"));
        collector.checkThat(response.get("customerFacingMessage").toString(),
                containsString("A string value with minimum length 7 is required"));
        collector.checkThat(response.get("actionCode").toString(), containsString("AskConsumerToReEnterData"));
        collector.checkThat(response.get("fieldReference").toString(), containsString("bankAccount"));
    }

    private static void ThenServerRespondsWith400AndBodyContainsToLongBankAccountMessage
            (JsonPath response) {
        ErrorCollector collector = new ErrorCollector();
        collector.checkThat(response.get("type").toString(), containsString("BusinessError"));
        collector.checkThat(response.get("code").toString(), containsString("400.005"));
        collector.checkThat(response.get("message").toString(),
                containsString("A string value exceeds maximum length of 34"));
        collector.checkThat(response.get("customerFacingMessage").toString(),
                containsString("A string value exceeds maximum length of 34"));
        collector.checkThat(response.get("actionCode").toString(), containsString("AskConsumerToReEnterData"));
        collector.checkThat(response.get("fieldReference").toString(), containsString("bankAccount"));
    }

    private static void ThenServerRespondsWith200AndBodyContainsNotSupportedBankAccountMessage
            (JsonPath response) {
        ErrorCollector collector = new ErrorCollector();
        collector.checkThat(response.get("isValid").toString(), containsString("false"));
        collector.checkThat(response.get("riskCheckMessages.type").toString(), containsString("BusinessError"));
        collector.checkThat(response.get("riskCheckMessages.code").toString(), containsString("200.909"));
        collector.checkThat(response.get("riskCheckMessages.message").toString(),
                containsString("Account number from this country is not supported for this AfterPay merchant."));
        collector.checkThat(response.get("riskCheckMessages.customerFacingMessage").toString(),
                containsString("Kontonummer fra dette land støttes ikke for denne klient"));
        collector.checkThat(response.get("riskCheckMessages.actionCode").toString(),
                containsString("AskConsumerToReEnterData"));
        collector.checkThat(response.get("riskCheckMessages.fieldReference").toString(),
                containsString("bankAccount"));
    }

    private JsonPath WhenBankAccountValidationRequestIsSentToTheServer(String bankAccount, String authToken,
                                                                       int statusCode) {
        response = new BankAccountUtil().validateBankAccount(bankAccount, statusCode, authToken);
        return response;
    }


    /*
     * Possible tests:
     * 1. Negative test. Verify service with incorrect uri
     */



}
