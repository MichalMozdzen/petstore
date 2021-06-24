package pet;

import helpers.DataHelper;
import helpers.TestHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;

public class PostPetUploadImageByPetId {

    private static RequestSpecification multipartSpec;
    private static RequestSpecification jsonSpec;
    private static MultiPartSpecification file;
    private static Long forModification;

    @BeforeClass
    public static void initSpecs() throws URISyntaxException {
        multipartSpec = TestHelper.initSpecification(ContentType.MULTIPART, ContentType.JSON);
        jsonSpec = TestHelper.initSpecification(ContentType.JSON, ContentType.JSON);
        file = DataHelper.createMultipartData();
        forModification = new PostPetTest().sendNewPetRequest(jsonSpec,DataHelper.createPetBody(true)).as(Pet.class).getId();
    }

    @Test
    public void uploadNewImageForPet() {
        Response response = sendPetUploadImageRequest(multipartSpec, forModification, file);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void errorNotFound() {
        Response response = sendPetUploadImageRequest(multipartSpec, -1L, file);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    public void errorUnsupportedMediaType() {

        //@formatter:off

        Response response = given()
                .spec(jsonSpec)
            .when()
                .post("pet/" + forModification + "/uploadImage")
            .then()
                .extract()
                .response();

        //@formatter:on

        TestHelper.assertEqualStatusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, response.statusCode());
    }

    public Response sendPetUploadImageRequest(RequestSpecification spec, Long petId, MultiPartSpecification fileData) {

        //@formatter:off

        return given()
                .spec(spec)
                .multiPart(fileData)
            .when()
                .post("pet/" + petId + "/uploadImage")
            .then()
                .extract()
                .response();

        //@formatter:on
    }

}
