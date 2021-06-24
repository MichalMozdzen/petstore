package pet;

import helpers.DataHelper;
import helpers.TestConfig;
import helpers.TestHelper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.http.HttpStatus;
import org.junit.*;

import static io.restassured.RestAssured.given;

public class GetPetByIdTest {

    private static Long forModification;

    @BeforeClass
    public static void prepareNewResource() {
        forModification = new PostPetTest().sendNewPetRequest(TestConfig.JSON_SPEC, DataHelper.createPetBody(true)).as(Pet.class).getId();
    }

    @Test
    public void getPetById() {
        Response response = getPetByIdRequest(TestConfig.JSON_SPEC, forModification);

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

    @AfterClass
    public static void deleteResource() {
        new DeletePetByIdTest().deletePetByIdRequest(TestConfig.JSON_SPEC, forModification);
    }

}
