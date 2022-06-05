package com.arvato.services.rest.Tests;

import com.arvato.services.rest.Common.utils.BankAccountUtil;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.TestName;
import org.testng.annotations.Listeners;

import static com.arvato.services.rest.Common.Constants.*;
import static org.hamcrest.Matchers.containsString;

@Listeners(com.arvato.services.rest.Common.utils.Listeners.class)
public class ValidateBankAccountTest {

    JsonPath response;

    @Rule
    public ErrorCollector collector = new ErrorCollector();
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

        response = new BankAccountUtil().validateBankAccount(VALID_BANK_ACCOUNT, STATUS_CODE_TRUE, AUTH_KEY);
        BodyContainsIsValidTrue(response);
    }

    /**
     * Negative test
     * Given the bank account validation request without a JWT token
     * When sample request is sent to the server
     * Then server responds with HTTP response code 401
     */
    @Test
    public void UnauthorizedBankAccountValidation() {

        String INVALID_AUTH_TOKEN = " ";
        response = new BankAccountUtil().validateBankAccount(VALID_BANK_ACCOUNT, STATUS_UNAUTHORIZED,
                INVALID_AUTH_TOKEN);
        BodyContainsDeniedMessage(response);
    }

    /**
     * Negative test
     * The purpose of this test is to verify error messages if bank account is shorter then allowed minimum length
     * bank account considered to be valid if its length is between 7 and 34 chars,
     * 400.00x error returned if validation fails
     */
    @Test
    public void ShortBankAccountValidation() {
        String SHORT_BANK_ACCOUNT = "GB09HA";
        response = new BankAccountUtil().validateBankAccount(SHORT_BANK_ACCOUNT, STATUS_BAD_REQUEST, AUTH_KEY);
        BodyContainsToShortBankAccountMessage(response);
    }

    /**
     * Negative test
     * The purpose of this test is to verify error messages if bank account is longer then allowed maximum length
     * bank account considered to be valid if its length is between 7 and 34 chars,
     * 400.00x error returned if validation fails
     */
    @Test
    public void LongBankAccountValidation() {
        String LONG_BANK_ACCOUNT = "GB09HAOE913118080023171234567890123";
        response = new BankAccountUtil().validateBankAccount(LONG_BANK_ACCOUNT, STATUS_BAD_REQUEST, AUTH_KEY);
        BodyContainsToLongBankAccountMessage(response);
    }

    /**
     * The purpose of this test is to verify error messages in case of invalid bank account number
     */
    @Test
    public void InvalidBankAccountValidation() {
        String INVALID_BANK_ACCOUNT = "LI8808800262 172\tAC 439897313";
        response = new BankAccountUtil().validateBankAccount(INVALID_BANK_ACCOUNT, STATUS_CODE_TRUE, AUTH_KEY);
        BodyContainsNotSupportedBankAccountMessage(response);
    }

    /*Validations*/
    private static void BodyContainsIsValidTrue(JsonPath response) {
        ErrorCollector collector = new ErrorCollector();
        collector.checkThat(response.get("isValid").toString(), containsString("true"));
    }

    private static void BodyContainsDeniedMessage(JsonPath response) {
        ErrorCollector collector = new ErrorCollector();
        collector.checkThat(response.get("message").toString(),
                containsString("Authorization has been denied for this request."));
    }

    private static void BodyContainsToShortBankAccountMessage(JsonPath response) {
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

    private static void BodyContainsToLongBankAccountMessage(JsonPath response) {
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

    private static void BodyContainsNotSupportedBankAccountMessage(JsonPath response) {
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

}
