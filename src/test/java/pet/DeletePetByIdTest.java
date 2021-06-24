package pet;

import helpers.DataHelper;
import helpers.TestHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class DeletePetByIdTest {

    private Pet forDeletion;

    private static RequestSpecification jsonSpec;

    @BeforeClass
    public static void initSpecs() {
        jsonSpec = TestHelper.initSpecification(ContentType.JSON, ContentType.JSON);
    }

    @Before
    public void createPetForDeletion () {
        Response response = new PostPetTest().sendNewPetRequest(jsonSpec, DataHelper.createPetBody(true));

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());

        forDeletion = response.as(Pet.class);
    }

    @Test
    public void deletePetById() {
        Response response = deletePetByIdRequest(jsonSpec, forDeletion.getId());

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void errorNotFound() {
        Response response = deletePetByIdRequest(jsonSpec, -1L);

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