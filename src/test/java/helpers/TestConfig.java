package helpers;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class TestConfig {
    public static final RequestSpecification JSON_SPEC;
    public static final RequestSpecification URLENC_SPEC;
    public static final RequestSpecification MULTIPART_SPEC;

    static {
        JSON_SPEC = TestHelper.initSpecification(ContentType.JSON,ContentType.JSON);
        URLENC_SPEC = TestHelper.initSpecification(ContentType.URLENC,ContentType.JSON);
        MULTIPART_SPEC = TestHelper.initSpecification(ContentType.MULTIPART,ContentType.JSON);
    }
}
