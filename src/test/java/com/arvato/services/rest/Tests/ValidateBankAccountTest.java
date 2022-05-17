package com.arvato.services.rest.Tests;

import com.arvato.services.rest.Common.utils.BankAccountUtil;
import io.restassured.path.json.JsonPath;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.TestName;

import static com.arvato.services.rest.Common.Constants.*;
import static org.hamcrest.Matchers.containsString;

public class ValidateBankAccountTest {

    JsonPath response;
    private final String VALID_BANK_ACCOUNT = "NL07ABNA2847123288";

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

        response = new BankAccountUtil().validateBankAccount(VALID_BANK_ACCOUNT, STATUS_CODE_TRUE, CONTENT_TYPE_JSON,
                AUTH_KEY);
        collector.checkThat(response.get("isValid").toString(), containsString("true"));

    }

    /**
     * Negative test
     * Given the bank account validation request without a JWT token
     * When sample request is sent to the server
     * Then server responds with HTTP response code 401
     */
    @Test
    public void UnauthorizedBankAccountValidation() {

        String EMPTY_AUTH_TOKEN = "";

        response = new BankAccountUtil().validateBankAccount(VALID_BANK_ACCOUNT, STATUS_UNAUTHORIZED, "",
                EMPTY_AUTH_TOKEN);
        collector.checkThat(response.get("message").toString(),
                containsString("Authorization has been denied for this request."));

    }

    /**
     * Negative test
     * The purpose of this test is to verify error messages if bank account is shorter then allowed minimum length
     * bank account considered to be valid if its length is between 7 and 34 chars,
     * 400.00x error returned if validation fails
     */
    @Test
    public void ShortBankAccountValidation() {

        String shortBankAccount = "GB09HA";

        response = new BankAccountUtil().validateBankAccount(shortBankAccount, STATUS_BAD_REQUEST, CONTENT_TYPE_JSON,
                AUTH_KEY);
        collector.checkThat(response.get("type").toString(), containsString("BusinessError"));
        collector.checkThat(response.get("code").toString(), containsString("400.006"));
        collector.checkThat(response.get("message").toString(),
                containsString("A string value with minimum length 7 is required"));
        collector.checkThat(response.get("customerFacingMessage").toString(),
                containsString("A string value with minimum length 7 is required"));
        collector.checkThat(response.get("actionCode").toString(), containsString("AskConsumerToReEnterData"));
        collector.checkThat(response.get("fieldReference").toString(), containsString("bankAccount"));

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

        response = new BankAccountUtil().validateBankAccount(LONG_BANK_ACCOUNT, STATUS_BAD_REQUEST, CONTENT_TYPE_JSON,
                AUTH_KEY);
        collector.checkThat(response.get("type").toString(), containsString("BusinessError"));
        collector.checkThat(response.get("code").toString(), containsString("400.005"));
        collector.checkThat(response.get("message").toString(),
                containsString("A string value exceeds maximum length of 34"));
        collector.checkThat(response.get("customerFacingMessage").toString(),
                containsString("A string value exceeds maximum length of 34"));
        collector.checkThat(response.get("actionCode").toString(), containsString("AskConsumerToReEnterData"));
        collector.checkThat(response.get("fieldReference").toString(), containsString("bankAccount"));

    }

    /**
     * The purpose of this test is to verify error messages in case of invalid bank account number
     */
    @Test
    public void InvalidBankAccountValidation() {

        String invalidBankAccount = "LI8808800262 172\tAC 439897313";
        response = new BankAccountUtil().validateBankAccount(invalidBankAccount, STATUS_CODE_TRUE, CONTENT_TYPE_JSON,
                AUTH_KEY);
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

    /*
     * Possible tests:
     * 1. Negative test. Verify service with incorrect uri
     */

}
