package com.seleniumtestng.tests;

import com.seleniumtestng.config.ConfigReader;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    private RequestSpecification spec;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        RestAssured.baseURI = ConfigReader.getBaseUrl();
        spec = given()
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
            .header("Accept", "application/json, */*; q=0.01")
            .relaxedHTTPSValidation()
            .log().ifValidationFails();
    }

    @Test(groups = "smoke")
    public void testGetPostsReturns200() {
        spec
            .when().get("/wp-json/wp/v2/posts")
            .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(greaterThan(0)));
    }

    @Test(groups = "smoke")
    public void testGetPostsStructure() {
        spec
            .when().get("/wp-json/wp/v2/posts")
            .then().statusCode(200)
                .body("[0].id", instanceOf(Number.class))
                .body("[0].title.rendered", notNullValue())
                .body("[0].slug", notNullValue())
                .body("[0].type", equalTo("post"));
    }

    @Test
    public void testGetPagesReturns200() {
        spec
            .when().get("/wp-json/wp/v2/pages")
            .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(greaterThan(0)));
    }

    @Test
    public void testPostPerPagePagination() {
        spec
            .queryParam("per_page", 2)
            .when().get("/wp-json/wp/v2/posts")
            .then().statusCode(200)
                .body("$", hasSize(2));
    }

    @Test
    public void testSinglePostById() {
        int firstId =
            spec
                .when().get("/wp-json/wp/v2/posts")
                .then().extract().path("[0].id");

        spec
            .pathParam("id", firstId)
            .when().get("/wp-json/wp/v2/posts/{id}")
            .then().statusCode(200)
                .body("id", equalTo(firstId))
                .body("title.rendered", notNullValue())
                .body("content.rendered", notNullValue());
    }

    @Test
    public void testInvalidEndpointReturns404() {
        spec
            .when().get("/wp-json/wp/v2/nonexistent")
            .then().statusCode(404);
    }
}
