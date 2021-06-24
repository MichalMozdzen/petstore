package pet;

import helpers.DataHelper;
import helpers.TestHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetPetByIdTest {

    private static RequestSpecification jsonSpec;
    private Pet createdResource;

    @BeforeClass
    public static void initSpecs() {
        jsonSpec = TestHelper.initSpecification(ContentType.JSON, ContentType.JSON);
    }

    @Before
    public void prepareNewResource() {
        Response response = new PostPetTest().sendNewPetRequest(jsonSpec, DataHelper.createPetBody(false));

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());

        createdResource = response.as(Pet.class);
    }

    @Test
    public void getPetById() {
        Response response = getPetByIdRequest(jsonSpec, createdResource.getId());

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void errorPetNotFound() {
        Response response = getPetByIdRequest(jsonSpec, -1L);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    public Response getPetByIdRequest(RequestSpecification spec, Long petId) {

        //@formatter:off

        return given()
                .spec(spec)
            .when()
                .get("pet/" + petId)
            .then()
                .extract()
                .response();

        //@formatter:on
    }

    @After
    public void deleteResource() {
        Response response = new DeletePetByIdTest().deletePetByIdRequest(jsonSpec, createdResource.getId());

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

}
