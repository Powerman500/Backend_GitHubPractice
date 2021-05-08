package com.github.step_definitions;

import com.github.pojo.NewRepo;
import com.github.utilities.ConfigurationReader;
import com.github.utilities.GlobalDataUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateRepoStepDefs {

    private GlobalDataUtils globalDataUtils;

    public CreateRepoStepDefs(StepData stepData) {
        globalDataUtils = stepData.globalDataUtils;
    }

    @Given("request for creating new repo")
    public void request_for_creating_new_repo() {
        String repoName = "TestRepo" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMdhhmmsSSS"));
        Map<String,Object> repo = new LinkedHashMap<>();
        repo.put("name",repoName);

        given().log().all()
                .accept("application/vnd.github.v3+json")
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigurationReader.get("access_token"))
                .body(repo)
        .when()
                .post("/user/repos")
                .prettyPeek()
        .then()
                .statusCode(201)
                .contentType("application/json; charset=utf-8");

        globalDataUtils.setRepoName(repoName);
    }

    @When("request for retrieving the new repo")
    public void request_for_retrieving_the_new_repo() {
        String expectedRepoName = globalDataUtils.getRepoName();

        Response response = given().log().all()
                .accept("application/vnd.github.v3+json")
                .pathParam("owner", "Powerman500")
                .pathParam("repo", expectedRepoName)
                .when()
                .get("/repos/{owner}/{repo}")
                .prettyPeek();

        globalDataUtils.setResponse(response);
    }

    @Then("new repo information matches expected")
    public void new_repo_information_matches_expected() {
//        Response response = globalDataUtils.getResponse();
//        JsonPath jsonPath = response.jsonPath();
//        String expectedRepoName = globalDataUtils.getRepoName();
//
//        assertThat(response.statusCode(), is(200));
//        assertThat(jsonPath.getString("name"), is( expectedRepoName ));
////                     actual value                 expected value
//        assertThat(jsonPath.getString("owner.login"), is( "Powerman500" ));

        Response response = globalDataUtils.getResponse();
        String expectedRepoName = globalDataUtils.getRepoName();
        NewRepo newRepo = response.body().as(NewRepo.class);

        assertThat(response.statusCode(), is(200));
        assertThat(newRepo.getName(), is( expectedRepoName ));
        assertThat(newRepo.getOwner().getLogin(), is( "Powerman500" ));
    }

}
