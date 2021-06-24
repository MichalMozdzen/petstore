package helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHelper {

    public static RequestSpecification initSpecification(ContentType contentType) {
        return new RequestSpecBuilder()
                .setContentType(contentType)
                .setAccept(contentType)
                .setBaseUri("https://petstore.swagger.io/v2/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    public static void assertEqualPet(Pet requestBody, Pet responseBody, String... ignoredFields) {
        assertThat(responseBody)
                .usingRecursiveComparison()
                .ignoringFields(ignoredFields)
                .isEqualTo(requestBody);
    }

    public static void assertEqualStatusCode(int expectedCode, int responseCode) {
        assertThat(responseCode)
                .withFailMessage("Incorrect status code.\nExpected: %s\nActual: %s", expectedCode, responseCode)
                .isEqualTo(expectedCode);
    }

    public static void assertMatchStatuses(Response response, String... expectedStatuses) {
        List<Pet> petList = response.jsonPath().getList(".", Pet.class);

        petList.forEach(p -> assertThat(p.getStatus())
                .withFailMessage("Incorrect pet status returned. Expected only: %s status(es), but found: %s", expectedStatuses, p.getStatus())
                .isIn(expectedStatuses));
    }

}