package pet;

import helpers.DataHelper;
import helpers.TestHelper;
import io.restassured.http.ContentType;
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
        jsonSpec = TestHelper.initSpecification(ContentType.JSON);
    }

    @Before
    public void prepareNewResource() {
        createdResource = new PostPetTest().sendNewPetRequest(jsonSpec, DataHelper.createPetBody(false), HttpStatus.SC_OK);
    }

    @Test
    public void getPetById() {
        getPetByIdRequest(jsonSpec, createdResource.getId(), HttpStatus.SC_OK);
    }

    @Test
    public void errorPetNotFound() {
        getPetByIdRequest(jsonSpec, createdResource.getId(), HttpStatus.SC_NOT_FOUND);
    }

    public Pet getPetByIdRequest(RequestSpecification spec, Long petId, int expectedStatusCode) {

        //@formatter:off

        return given()
                .spec(spec)
            .when()
                .get("pet/" + petId)
            .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(Pet.class);

        //@formatter:on
    }

    @After
    public void deleteResource() {
        new DeletePetByIdTest().deletePetByIdRequest(jsonSpec, createdResource.getId(), HttpStatus.SC_OK);
    }

}
