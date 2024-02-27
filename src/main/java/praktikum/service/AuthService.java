package praktikum.service;

import io.restassured.response.Response;
import praktikum.Specifications;
import praktikum.pojo.AuthLoginRequest;
import praktikum.pojo.AuthRegisterRequest;

import static io.restassured.RestAssured.given;
import static praktikum.UrlPaths.AUTH_LOGIN;
import static praktikum.UrlPaths.AUTH_REGISTER;
import static praktikum.UrlPaths.AUTH_USER;

public class AuthService extends Specifications {

    public Response register(AuthRegisterRequest request) {
        return specification()
                .body(request)
                .when()
                .post(AUTH_REGISTER);
    }

    public Response login(AuthLoginRequest request) {
        return specification()
                .body(request)
                .when()
                .post(AUTH_LOGIN);
    }

    public Response updateWithToken(String token, String email, String password, String name) {
        AuthRegisterRequest request = new AuthRegisterRequest(email, password, name);
        return specification()
                .header("authorization", token)
                .body(request)
                .when()
                .patch(AUTH_USER);
    }

    public Response updateWithoutToken(String email, String password, String name) {
        AuthRegisterRequest request = new AuthRegisterRequest(email, password, name);
        return specification()
                .body(request)
                .when()
                .patch(AUTH_USER);
    }


    public void deleteUser(String token) {
        if (token != null) {
            given().header("authorization", token)
                    .spec(specification()).when().delete(AUTH_USER);
        } else {
            given().spec(specification())
                    .when()
                    .delete(AUTH_USER);
        }
    }
}
