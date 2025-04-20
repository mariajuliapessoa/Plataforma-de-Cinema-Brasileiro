package com.cesar.bracine;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.ActiveProfiles;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.cesar.bracine.steps,com.cesar.bracine.config")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ActiveProfiles("test")
public class RunCucumberTest {
}