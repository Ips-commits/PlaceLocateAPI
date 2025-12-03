package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/features/PlaceValidations.feature",glue={"stepDefinitions"},tags="@DeletePlace")
public class TestRunner {

}
