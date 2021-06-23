package pet;

import helpers.DataHelper;
import helpers.TestHelper;
import io.restassured.http.ContentType;
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
        jsonSpec = TestHelper.initSpecification(ContentType.JSON);
    }

    @Test
    public void createNewPetMandatoryFields() {
        Pet body = DataHelper.createPetBody(true);

        Pet response = sendNewPetRequest(jsonSpec, body, HttpStatus.SC_OK);

        TestHelper.assertEqualPet(body, response, "id");
    }

    @Test
    public void createNewPetWithAllFields() {
        Pet body = DataHelper.createPetBody(false);

        Pet response = sendNewPetRequest(jsonSpec, body, HttpStatus.SC_OK);

        TestHelper.assertEqualPet(body, response);
    }

    @Test
    public void errorRequestWithEmptyBody() {
        sendNewPetRequest(jsonSpec, "{}", HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void errorRequestWithoutBody() {

        //@formatter:off

        given()
            .spec(jsonSpec)
        .when()
            .post("pet")
        .then()
            .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);

        //@formatter:on

    }

    public Pet sendNewPetRequest(RequestSpecification spec, Object body, int expectedStatusCode) {

        //@formatter:off

        return given()
                .spec(spec)
                .body(body)
            .when()
                .post("pet")
            .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(Pet.class);

        //@formatter:on
    }
}
