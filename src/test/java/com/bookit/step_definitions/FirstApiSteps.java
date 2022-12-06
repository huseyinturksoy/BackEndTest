package com.bookit.step_definitions;

import com.bookit.utils.ConfigurationReader;
import com.bookit.utils.DBUtils;
import com.bookit.utils.GetTokenUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstApiSteps {
    String actualToken;
    Response response;

    @Given("I logged Bookit api using {string} and {string}")
    public void logged_bookit_api_using_and(String email, String password) {

        actualToken = GetTokenUtil.getToken(email,password);
        System.out.println("actualToken = " + actualToken);


    }
    @When("I get the current user information from api")
    public void get_the_current_user_information_from_api() {
        response = RestAssured.given().accept(ContentType.JSON)
                .and().header("Authorization", actualToken)
                .get(ConfigurationReader.get("base_url") + "/api/users/me");

        response.prettyPrint();

        String firstname = response.path("firstName");
        System.out.println("firstname = " + firstname);
        String lastName = response.path("lastName");
        System.out.println("lastName = " + lastName);
        String role = response.path("role");
        System.out.println("role = " + role);


    }
    @Then("status code should be {int}")
    public void status_code_should_be(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());

    }


    @Then("the information about current user from api and database should match")
    public void theInformationAboutCurrentUserFromApiAndDatabaseShouldMatch() throws SQLException {
        String query = "select * from users where email='blyst6@si.edu'";
        ResultSet resultSet = DBUtils.executeQuery(query);

        resultSet.next();
        String dbfirstName = resultSet.getString(4);
        System.out.println("dbfirstName = " + dbfirstName);
        String dblastName = resultSet.getString(6);
        System.out.println("dblastname = " + dblastName);
        String dbrole = resultSet.getString(7);
        System.out.println("dbrole = " + dbrole);

        String apifirstname = response.path("firstName");
        System.out.println("apifirstname = " + apifirstname);
        String apilastName = response.path("lastName");
        System.out.println("apilastName = " + apilastName);
        String apirole = response.path("role");
        System.out.println("apirole = " + apirole);

        Assert.assertEquals(apifirstname,dbfirstName);
        Assert.assertEquals(apilastName,dblastName);
        Assert.assertEquals(apirole,dbrole);

    }
}
