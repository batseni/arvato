Tests execution config:

1. Go to: Run -> Edit Configurations -> Edit Configuration templates -> JUnit
   and set following values:
   VM options: -Dauth.key=lXGp4CSPnzz9Zw5ykDDMEUPNUMAiLNGPG2txrdNs 
               -Dendpoint.uri=https://api-dev.afterpay.dev/ 
               -Drequest.uri=api/v3/validate/bank-account
   Environment variables: CUCUMBER_PUBLISH_ENABLED=true
2. When configuration is done, right click *.java and select Run
3. In case if VM options not configured, API tests are using variables 
   located in com.arvato.services.rest.Constants:
   public static final String AUTH_KEY = "lXGp4CSPnzz9Zw5ykDDMEUPNUMAiLNGPG2txrdNs";
   public static final String TEST_API_ENDPOINT = "https://api-dev.afterpay.dev/";
   public static final String BANK_ACCOUNT_URI = "api/v3/validate/bank-account";
4. In case if Environment variables not configured, in the end of test execution process 
   CUCUMBER_PUBLISH_ENABLED configuration notification is thrown

Project structure:
1. BDD API and UI tests
   Feature files located in src/main/resources/features 
   Step definitions and test runner located in src/test/java/io/cucumber/arvato
2. Regular API and Web tests
   src/test/java/com/arvato/services/rest contains to folders: 
   Tests - this folder contains 2 *.java classes for running API or UI tests
   Common/utils - this folder contains classes and methods for running tests

Test execution options:
1. Execute BDD tests
   Right click src/test/java/io/cucumber/arvato/CucumberTest.java and select Run
2. Execute regular UI tests 
   Right click src/test/java/com/arvato/services/rest/Tests/WebPortalTest.java and select Run
3. Execute regular API tests
   Right click src/test/java/com/arvato/services/rest/Tests/ValidateBankAccountTest.java and select Run

Reporting:
1. Cucumber report is saved to arvato\target\cucumber-reports project folder
2. For BDD reports additionally report link is thrown and report can be viewd at 
   https://reports.cucumber.io/reports web page

Think out of the box and add edge cases (BDD scenarios for API and UI) 
which are not explicitly stated in the assignment (no need to automate).
1. Possible BDD tests are added to src/main/resources/UiAndApiPossibleTests.feature
2. Also some additional tests are automated in src/test/java/com/arvato/services/rest/Tests/ValidateBankAccountTest.java 
   and can be converted to BDD