package com.sauce.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.sauce",
    tags = "@web",
    plugin = {
        "pretty",
        "html:reports/cucumber-report.html",
        "json:reports/cucumber.json",
        "junit:reports/cucumber.xml"
    },
    monochrome = true,
    dryRun = false
)
public class TestRunner {
}