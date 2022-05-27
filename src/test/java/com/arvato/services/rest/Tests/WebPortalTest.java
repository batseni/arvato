package com.arvato.services.rest.Tests;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.arvato.services.rest.Common.utils.BaseUtil.*;
import static com.arvato.services.rest.Common.utils.WebPortalUtil.*;

public class WebPortalTest {

    @BeforeClass
    public static void setUp() {
        setDriverAndOpenBrowser();
    }

    @AfterClass
    public static void tearDown() {
        AndGivenWebPageIsClosed();
    }

    public void main(String[] args) {
        CustomerSeesMessageThatEmailIsSent();
        CustomerSeesErrorMessageWhenInvalidInformationInserted();
        CustomerSeesErrorMessageWhenInformationOfNonExistingCustomerIsInserted();
    }

    @Test
    public void CustomerSeesMessageThatEmailIsSent() {
        GivenCustomerIsInLoginPage();
        AndPrivacySettingsAccepted();
        WhenCustomerSelectsCreateAccountLink();
        AndCustomerInsertsEmailAddress();
        ThenCustomerIsRedirectedToAuthenticationConfirmationPage();
        AndYouGotMailMessageIsShown();
    }
    @Test
    public void CustomerSeesErrorMessageWhenInvalidInformationInserted() {
        GivenCustomerIsInLoginPage();
        WhenCustomerSelectsLoginWithEmailAndPassword();
        AndCustomerInsertsInvalidEmailAddress();
        ThenErrorValidationMessageIsShown();
    }
    @Test
    public void CustomerSeesErrorMessageWhenInformationOfNonExistingCustomerIsInserted() {
        GivenCustomerIsInLoginPage();
        WhenCustomerSelectsLoginWithEmailAndPassword();
        AndCustomerInsertsEmailAddressAndPassword();
        ThenCustomerIsRedirectedToTheEmailandpasswordErrorPage();
        AndErrorMessageIsShown();
    }

}
