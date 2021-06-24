package pet;

import helpers.DataHelper;
import helpers.TestConfig;
import helpers.TestHelper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class GetPetFindByStatusTest {

    @Test
    public void getPetsWithRandomStatus() {
        String status = DataHelper.generateRandomStatus().label;

        Response response = getPetsByStatusRequest(TestConfig.JSON_SPEC, status);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
        TestHelper.assertMatchStatuses(response, status);
    }

    @Test
    public void getPetsWithFewStatuses() {
        String[] statuses = DataHelper.getTwoRandomStatuses();

        Response response = getPetsByStatusRequest(TestConfig.JSON_SPEC, statuses);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_OK, response.statusCode());
        TestHelper.assertMatchStatuses(response, statuses);
    }

    @Test
    public void errorInvalidStatus() {
        String status = RandomStringUtils.randomAlphabetic(20);

        Response response = getPetsByStatusRequest(TestConfig.JSON_SPEC, status);

        TestHelper.assertEqualStatusCode(HttpStatus.SC_BAD_REQUEST, response.statusCode());
    }

    @Test
    public void errorCheckMandatoryParam() {

        //@formatter:off

        Response response = given()
                    .spec(TestConfig.JSON_SPEC)
                .when()
                    .get("pet/findByStatus" )
                .then()
                    .extract()
                    .response();

        //@formatter:on

        TestHelper.assertEqualStatusCode(HttpStatus.SC_BAD_REQUEST, response.statusCode());
    }

    public Response getPetsByStatusRequest(RequestSpecification spec, String... statuses) {

        //@formatter:off

        return given()
                .spec(spec)
                .queryParam("status", (statuses.length > 1) ? DataHelper.prepareStatusWithCommas(statuses) : Arrays.stream(statuses).findFirst().get())
            .when()
                .get("pet/findByStatus" )
            .then()
                .extract()
                .response();

        //@formatter:on
    }
}
