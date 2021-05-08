package com.github.step_definitions;

import com.github.utilities.ConfigurationReader;
import com.github.utilities.GlobalDataUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeleteRepoStepDefs {

    private GlobalDataUtils globalDataUtils;

    public DeleteRepoStepDefs(StepData stepData) {
        globalDataUtils = stepData.globalDataUtils;
    }

    @When("request for deleting the repo")
    public void request_for_deleting_the_repo() {
        String repoName = globalDataUtils.getRepoName();

        Response response = given().log().all()
                .accept("application/vnd.github.v3+json")
                .pathParam("owner", "Powerman500")
                .pathParam("repo", repoName)
                .header("Authorization", ConfigurationReader.get("access_token"))
                .when()
                .delete("/repos/{owner}/{repo}")
                .prettyPeek();

        globalDataUtils.setResponse(response);
    }

    @Then("verify the repo is deleted")
    public void verify_the_repo_is_deleted() {
        Response response = globalDataUtils.getResponse();

        assertThat(response.statusCode(), equalTo( 204 ));
        assertThat(response.body().asString(), is( emptyString() ));
    }

}
