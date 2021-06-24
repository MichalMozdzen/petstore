package pet;

import helpers.DataHelper;
import helpers.TestHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.http.HttpStatus;
import org.junit.*;

import static io.restassured.RestAssured.given;

public class PutPetTest {

    private static RequestSpecification jsonSpec;

    private static Long forModification;

    @BeforeClass
    public static void initSpecs() {
        jsonSpec = TestHelper.initSpecification(ContentType.JSON, ContentType.JSON);
        forModification = new PostPetTest().sendNewPetRequest(jsonSpec, DataHelper.createPetBody(true)).as(Pet.class).getId();
    }

    @Test
    public void modifyPetWithMandatoryFields() {
        Pet body = DataHelper.createPetBody(true);
        body.setId(forModification);

        Response response = modifyPetRequest(jsonSpec, body);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
        TestHelper.assertEqualPet(body, response.as(Pet.class));
    }

    @Test
    public void modifyPetWithAllFields() {
        Pet body = DataHelper.createPetBody(false);
        body.setId(forModification);

        Response response = modifyPetRequest(jsonSpec, body);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
        TestHelper.assertEqualPet(body, response.as(Pet.class));
    }

    @Test
    public void errorPetNotFound() {
        Pet body = DataHelper.createPetBody(true);
        body.setId(-1L);

        Response response = modifyPetRequest(jsonSpec, body);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    public void errorRequestWithoutBody() {

        //@formatter:off

        Response response = given()
                .spec(jsonSpec)
            .when()
                .put("pet")
            .then()
                .extract()
                .response();

        //@formatter:on

        TestHelper.assertEqualStatusCode(HttpStatus.SC_METHOD_NOT_ALLOWED, response.statusCode());
    }

    @AfterClass
    public static void deleteResource() {
        new DeletePetByIdTest().deletePetByIdRequest(jsonSpec, forModification);
    }

    public Response modifyPetRequest(RequestSpecification spec, Object body) {

        //@formatter:off

        return given()
                .spec(spec)
                .body(body)
            .when()
                .put("pet")
            .then()
                .extract()
                .response();

        //@formatter:on

    }
}
