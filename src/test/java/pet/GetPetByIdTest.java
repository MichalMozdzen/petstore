package pet;

import helpers.DataHelper;
import helpers.TestConfig;
import helpers.TestHelper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetPetByIdTest {

    private Pet createdResource;

    @Before
    public void prepareNewResource() {
        Response response = new PostPetTest().sendNewPetRequest(TestConfig.JSON_SPEC, DataHelper.createPetBody(false));

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());

        createdResource = response.as(Pet.class);
    }

    @Test
    public void getPetById() {
        Response response = getPetByIdRequest(TestConfig.JSON_SPEC, createdResource.getId());

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void errorPetNotFound() {
        Response response = getPetByIdRequest(TestConfig.JSON_SPEC, -1L);

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
        Response response = new DeletePetByIdTest().deletePetByIdRequest(TestConfig.JSON_SPEC, createdResource.getId());

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

}
