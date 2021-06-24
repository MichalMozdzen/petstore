package pet;

import helpers.DataHelper;
import helpers.TestConfig;
import helpers.TestHelper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class DeletePetByIdTest {

    private static Long forDeletion;

    @BeforeClass
    public static void prepareNewResource() {
        forDeletion = new PostPetTest().sendNewPetRequest(TestConfig.JSON_SPEC, DataHelper.createPetBody(true)).as(Pet.class).getId();
    }

    @Test
    public void deletePetById() {
        Response response = deletePetByIdRequest(TestConfig.JSON_SPEC, forDeletion);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void errorNotFound() {
        Response response = deletePetByIdRequest(TestConfig.JSON_SPEC, -1L);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    public Response deletePetByIdRequest(RequestSpecification spec, Long petId) {

        //@formatter:off

        return given()
                .spec(spec)
            .when()
                .delete("pet/" + petId)
            .then()
                .extract()
                .response();

        //@formatter:on
    }

}