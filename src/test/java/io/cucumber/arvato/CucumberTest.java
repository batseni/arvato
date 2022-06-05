package io.cucumber.arvato;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.runner.RunWith;

import static com.arvato.services.rest.Common.Constants.GLUE_PROPERTY_NAME;

@RunWith(Cucumber.class)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "io.cucumber.arvato")
@CucumberOptions(
        features = {"src/main/resources/features/BankAccount.feature",
                    "src/main/resources/features/WebPortal.feature"},
        monochrome = true,
        stepNotifications = true,
        plugin = {
                "pretty", "html:target/cucumber-reports/test-report", "json:target/cucumber-reports/test-report.json",
                "junit:target/cucumber-reports/test-report.xml" },
        snippets = CucumberOptions.SnippetType.UNDERSCORE
)

public class CucumberTest {

}
