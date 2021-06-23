package pet;

import helpers.DataHelper;
import helpers.TestHelper;
import io.restassured.http.ContentType;
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
        jsonSpec = TestHelper.initSpecification(ContentType.JSON);
    }

    @Before
    public void createPetForDeletion () {
        forDeletion = new PostPetTest().sendNewPetRequest(jsonSpec, DataHelper.createPetBody(true),HttpStatus.SC_OK);
    }

    @Test
    public void deletePetById() {
        deletePetByIdRequest(jsonSpec, forDeletion.getId(), HttpStatus.SC_OK);
    }

    @Test
    public void errorNotFound() {
        deletePetByIdRequest(jsonSpec, -1L, HttpStatus.SC_NOT_FOUND);
    }

    public void deletePetByIdRequest(RequestSpecification spec, Long petId, int expectedStatusCode) {

        //@formatter:off

        given()
            .spec(spec)
        .when()
            .delete("pet/" + petId)
        .then()
            .statusCode(expectedStatusCode);

        //@formatter:on
    }

}