package pet;

import helpers.DataHelper;
import helpers.TestHelper;
import io.restassured.http.ContentType;
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

    private static RequestSpecification urlencSpec;
    private static RequestSpecification jsonSpec;
    private static Long forModification;
    private Pair<String, String> formParams;


    @BeforeClass
    public static void initSpecs() {
        urlencSpec = TestHelper.initSpecification(ContentType.URLENC, ContentType.JSON);
        jsonSpec = TestHelper.initSpecification(ContentType.JSON, ContentType.JSON);
        forModification = new PostPetTest().sendNewPetRequest(jsonSpec, DataHelper.createPetBody(true)).as(Pet.class).getId();
    }

    @Before
    public void prepareFormParams() {
        formParams = DataHelper.createFormParams();
    }

    @Test
    public void modifyWithFullForm() {
        Response response = sendPetByIdRequest(urlencSpec, forModification, formParams);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void modifyPetWithEmptyForm() {
        Pair<String, String> emptyForm = Pair.of(null, null);
        Response response = sendPetByIdRequest(urlencSpec, forModification, emptyForm);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void errorPetNotFound() {
        Response response = sendPetByIdRequest(urlencSpec, -1L, formParams);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    public void errorUnsupportedMediaType() {
        Response response = sendPetByIdRequest(jsonSpec, forModification, formParams);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, response.statusCode());
    }

    @AfterClass
    public static void deleteResource() {
        new DeletePetByIdTest().deletePetByIdRequest(jsonSpec, forModification);
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
