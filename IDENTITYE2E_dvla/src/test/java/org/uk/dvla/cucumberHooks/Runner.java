package org.uk.dvla.cucumberHooks;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/cucumber-html-report",
        "json:cucumber-json2report/data/cucumber.json" }, features = "classpath:features/", glue = {"org.uk.dvla.stepDefinition"},tags = {
        "@vehicle" })// ,dryRun = true



public class Runner {

}
