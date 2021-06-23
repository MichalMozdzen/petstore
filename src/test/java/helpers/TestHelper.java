package helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.Pet;

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
        assertThat(requestBody)
                .usingRecursiveComparison()
                .ignoringFields(ignoredFields)
                .isEqualTo(responseBody);
    }

}