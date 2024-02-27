package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.service.AuthService;
import praktikum.service.OrderService;
import praktikum.steps.AuthSteps;
import praktikum.steps.OrderSteps;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrderTests {

    AuthSteps authSteps;
    OrderSteps orderSteps;
    String emailYandex = RandomStringUtils.randomAlphabetic(15) + "@yandex.ru";
    String name = RandomStringUtils.randomAlphabetic(10);
    String password = RandomStringUtils.randomNumeric(10);
    private String token;

    private List<String> ingredients = Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");

    @Before
    public void setUp() {
        authSteps = new AuthSteps(new AuthService());
        orderSteps = new OrderSteps(new OrderService());
        ValidatableResponse response = authSteps.register(emailYandex, password, name);
        token = authSteps.getToken(response);
        authSteps.login(emailYandex, password);
        orderSteps.createOrderWithToken(token, ingredients);
    }

    @Test
    @DisplayName("")
    public void getOrdersWithTokenTest(){
        orderSteps.getOrdersWithToken(token).statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("")
    public void getOrdersWithoutTokenTest(){
        orderSteps.getOrdersWithoutToken().statusCode(401).and().body("message", equalTo("You should be authorised"));
    }


    @After
    public void tearDown(){
        authSteps.delete(token);
    }
}
