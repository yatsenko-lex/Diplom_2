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

public class UpdateUserTests {

    AuthSteps authSteps;
    String emailYandex = RandomStringUtils.randomAlphabetic(15) + "@yandex.ru";
    String emailGoogle = RandomStringUtils.randomAlphabetic(15) + "@gmail.com";
    String name = RandomStringUtils.randomAlphabetic(10);
    String password = RandomStringUtils.randomNumeric(10);
    private String token;

    @Before
    public void setUp() {
        authSteps = new AuthSteps(new AuthService());
        ValidatableResponse response = authSteps.register(emailYandex, password, name);
        token = authSteps.getToken(response);
        authSteps.login(emailYandex, password);
    }

    @Test
    @DisplayName("Изменение данных пользователя с 'token'")
    public void updateUserDataTest(){
        authSteps.updateWithToken(token, emailGoogle, 1 + password, name + "_updated")
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя без 'token'")
    public void updateUserDataWithoutTokenTest(){
        authSteps.updateWithoutToken(emailGoogle, 1 + password, name + "_updated")
                .statusCode(401)
                .and()
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void tearDown(){
        authSteps.delete(token);
    }
}
