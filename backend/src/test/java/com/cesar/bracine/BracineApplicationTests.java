package com.cesar.bracine;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.cesar.bracine.steps", "com.cesar.bracine.config"},
    plugin = {"pretty", "json:target/cucumber-report.json"},
    monochrome = true,
    tags = "not @ignore"
)
public class BracineApplicationTests {

    @Test
    void contextLoads() {
    }

}