package helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class TestConfig {
    public static final RequestSpecification JSON_SPEC;
    public static final RequestSpecification URLENC_SPEC;
    public static final RequestSpecification MULTIPART_SPEC;

    static {
        JSON_SPEC = initSpecification(ContentType.JSON, ContentType.JSON);
        URLENC_SPEC = initSpecification(ContentType.URLENC, ContentType.JSON);
        MULTIPART_SPEC = initSpecification(ContentType.MULTIPART, ContentType.JSON);
    }

    public static RequestSpecification initSpecification(ContentType contentType, ContentType accept) {
        return new RequestSpecBuilder()
                .setContentType(contentType)
                .setAccept(accept)
                .setBaseUri("https://petstore.swagger.io/v2/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

}
