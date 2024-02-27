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

public class CreateOrderTests {

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
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrderWithTokenTest(){
        orderSteps.createOrderWithToken(token, ingredients)
                .statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без токена")
    public void createOrderWithoutTokenTest(){
        orderSteps.createOrderWithoutToken(ingredients)
                .statusCode(403).and().body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Создание заказа с пустым 'ingredients'")
    public void createOrderWithoutIngredientsTest(){
        List<String> emptyIngredients = Arrays.asList();
        orderSteps.createOrderWithoutToken(emptyIngredients)
                .statusCode(400).and().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверными 'ingredients'")
    public void createOrderWithWrongIngredientsTest(){
        List<String> emptyIngredients = Arrays.asList("QWE", "qwe");
        orderSteps.createOrderWithoutToken(emptyIngredients)
                .statusCode(500);
    }

    @After
    public void tearDown(){
        authSteps.delete(token);
    }
}
