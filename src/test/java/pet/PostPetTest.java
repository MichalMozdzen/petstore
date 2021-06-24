package pet;

import helpers.DataHelper;
import helpers.TestHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class PostPetTest {

    private static RequestSpecification jsonSpec;

    @BeforeClass
    public static void initSpecs() {
        jsonSpec = TestHelper.initSpecification(ContentType.JSON, ContentType.JSON);
    }

    @Test
    public void createNewPetMandatoryFields() {
        Pet body = DataHelper.createPetBody(true);

        Response response = sendNewPetRequest(jsonSpec, body);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
        TestHelper.assertEqualPet(body, response.as(Pet.class), "id");
    }

    @Test
    public void createNewPetWithAllFields() {
        Pet body = DataHelper.createPetBody(false);

        Response response = sendNewPetRequest(jsonSpec, body);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
        TestHelper.assertEqualPet(body, response.as(Pet.class));
    }

    @Test
    public void errorRequestWithEmptyBody() {
        Response response = sendNewPetRequest(jsonSpec, "{}");

        TestHelper.assertEqualStatusCode(HttpStatus.SC_METHOD_NOT_ALLOWED, response.statusCode());
    }

    @Test
    public void errorRequestWithoutBody() {

        //@formatter:off

        Response response = given()
            .spec(jsonSpec)
        .when()
            .post("pet")
        .then()
            .extract()
            .response();

        //@formatter:on

        TestHelper.assertEqualStatusCode(HttpStatus.SC_METHOD_NOT_ALLOWED, response.statusCode());
    }

    public Response sendNewPetRequest(RequestSpecification spec, Object body) {

        //@formatter:off

        return given()
                .spec(spec)
                .body(body)
            .when()
                .post("pet")
            .then()
                .extract()
                .response();

        //@formatter:on
    }
}
