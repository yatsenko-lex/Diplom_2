package praktikum;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static praktikum.UrlPaths.BASE_URI;

public class Specifications {

    protected RequestSpecification specification() {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON);
    }
}
