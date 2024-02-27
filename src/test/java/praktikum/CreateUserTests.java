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

public class CreateUserTests {

    AuthSteps authSteps;
    String email = RandomStringUtils.randomAlphabetic(15) + "@yandex.ru";
    String name = RandomStringUtils.randomAlphabetic(10);
    String password = RandomStringUtils.randomNumeric(10);
    private String token;

    @Before
    public void setUp(){
        authSteps = new AuthSteps(new AuthService());
    }

    @Test
    @DisplayName("Регистрация пользователя")
    public void createUserTest(){
        ValidatableResponse response = authSteps.register(email, password, name)
                .statusCode(200).and().body("success", equalTo(true));
        token = authSteps.getToken(response);
    }

    @Test
    @DisplayName("Регистрация пользователя")
    public void createExistedUserTest(){
        ValidatableResponse response = authSteps.register(email, password, name);
        authSteps.register(email, password, name)
                .statusCode(403).and().body("message", equalTo("User already exists"));
        token = authSteps.getToken(response);
    }

    @Test
    @DisplayName("Регистрация пользователя без поля 'email'")
    public void createUserWithoutEmailTest(){
        authSteps.register(null, password, name).statusCode(403).and()
                .body("message", equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Регистрация пользователя без поля 'password'")
    public void createUserWithoutPasswordTest(){
        authSteps.register(email, null, name).statusCode(403).and()
                .body("message", equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Регистрация пользователя без поля 'name'")
    public void createUserWithoutNameTest(){
        authSteps.register(email, password, null).statusCode(403).and()
                .body("message", equalTo("Email, password and name are required fields"));

    }

    @After
    public void tearDown(){
        authSteps.delete(token);
    }
}
