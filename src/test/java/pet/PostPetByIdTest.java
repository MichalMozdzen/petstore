package pet;

import helpers.DataHelper;
import helpers.TestConfig;
import helpers.TestHelper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class PostPetByIdTest {


    private static Long forModification;
    private Pair<String, String> formParams;


    @BeforeClass
    public static void initSpecs() {
        forModification = new PostPetTest().sendNewPetRequest(TestConfig.JSON_SPEC, DataHelper.createPetBody(true)).as(Pet.class).getId();
    }

    @Before
    public void prepareFormParams() {
        formParams = DataHelper.createFormParams();
    }

    @Test
    public void modifyWithFullForm() {
        Response response = sendPetByIdRequest(TestConfig.URLENC_SPEC, forModification, formParams);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void modifyPetWithEmptyForm() {
        Pair<String, String> emptyForm = Pair.of(null, null);
        Response response = sendPetByIdRequest(TestConfig.URLENC_SPEC, forModification, emptyForm);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void errorPetNotFound() {
        Response response = sendPetByIdRequest(TestConfig.URLENC_SPEC, -1L, formParams);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    public void errorUnsupportedMediaType() {
        Response response = sendPetByIdRequest(TestConfig.JSON_SPEC, forModification, formParams);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, response.statusCode());
    }

    @AfterClass
    public static void deleteResource() {
        new DeletePetByIdTest().deletePetByIdRequest(TestConfig.JSON_SPEC, forModification);
    }

    public Response sendPetByIdRequest(RequestSpecification spec, Long petId, Pair<String, String> formParam) {

        //@formatter:off

        return given()
                .spec(spec)
                .formParam("name",  formParam.getKey())
                .formParam("status", formParam.getValue())
            .when()
                .post("pet/" + petId)
            .then()
                .extract()
                .response();

        //@formatter:on
    }

}
