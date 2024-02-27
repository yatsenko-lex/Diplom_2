package praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Specifications;
import praktikum.pojo.AuthLoginRequest;
import praktikum.pojo.AuthRegisterRequest;
import praktikum.service.AuthService;

public class AuthSteps {

    private final AuthService authService;
    private String token;

    public AuthSteps(AuthService authService) {
        this.authService = authService;
    }

    @Step("Создание пользователя")
    public ValidatableResponse register(String email, String password, String name) {
        AuthRegisterRequest request = new AuthRegisterRequest(email, password, name);
        request.setEmail(email).setName(name).setPassword(password);
        return authService.register(request).then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse login(String email, String password) {
        AuthLoginRequest request = new AuthLoginRequest();
        request.setEmail(email).setPassword(password);
        return authService.login(request).then();
    }

    @Step("Изменение данных пользователя с Token")
    public ValidatableResponse updateWithToken(String token, String email, String password, String name) {
        return authService.updateWithToken(token, email, password, name).then();
    }

    @Step("Изменение данных пользователя без Token")
    public ValidatableResponse updateWithoutToken(String email, String password, String name) {
        return authService.updateWithoutToken(email, password, name).then();
    }

    @Step("Получение token")
    public String getToken(ValidatableResponse validatableResponse) {
        token = validatableResponse.extract().path("accessToken");
        return token;
    }

    @Step("Удаление пользователя")
    public void delete(String token) {
        authService.deleteUser(token);
    }
}
