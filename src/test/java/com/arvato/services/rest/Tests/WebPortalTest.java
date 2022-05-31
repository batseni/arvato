package com.arvato.services.rest.Tests;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.annotations.Listeners;

import static com.arvato.services.rest.Common.utils.BaseUtil.*;
import static com.arvato.services.rest.Common.utils.WebPortalUtil.*;

@Listeners(com.arvato.services.rest.Common.utils.Listeners.class)
public class WebPortalTest {

    @BeforeClass
    public static void setUp() {
        setDriverAndOpenBrowser();
    }

    @AfterClass
    public static void tearDown() {
        AndGivenWebPageIsClosed();
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
