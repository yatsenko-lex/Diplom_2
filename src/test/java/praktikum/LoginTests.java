package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.service.AuthService;
import praktikum.steps.AuthSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTests {

    AuthSteps authSteps;
    String email = RandomStringUtils.randomAlphabetic(15) + "@yandex.ru";
    String name = RandomStringUtils.randomAlphabetic(10);
    String password = RandomStringUtils.randomNumeric(10);
    private String token;

    @Before
    public void setUp(){
        authSteps = new AuthSteps(new AuthService());
        ValidatableResponse response = authSteps.register(email, password, name);
        token = authSteps.getToken(response);
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void loginTest(){
        authSteps.login(email, password)
                .statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация пользователя без логина")
    public void loginWithoutEmailTest(){
        authSteps.login(null, password).statusCode(401)
                .and().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация пользователя без логина")
    public void loginWithoutPasswordTest(){
        authSteps.login(email, null).statusCode(401)
                .and().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация пользователя с неверным 'email'")
    public void loginWithWrongEmailTest(){
        authSteps.login("qweqwe", password).statusCode(401)
                .and().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация пользователя с неверным 'password'")
    public void loginWithWrongPasswordTest(){
        authSteps.login(email, "qweqweqwe").statusCode(401)
                .and().body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void tearDown(){
        authSteps.delete(token);
    }

}
