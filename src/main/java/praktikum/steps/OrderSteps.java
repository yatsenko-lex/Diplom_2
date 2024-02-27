package praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.pojo.OrdersRequest;
import praktikum.service.OrderService;

import java.util.List;

public class OrderSteps {

    private final OrderService orderService;

    public OrderSteps(OrderService orderService) {
        this.orderService = orderService;
    }


    @Step("Создание заказа. Авторизованный пользователь")
    public ValidatableResponse createOrderWithToken(String token, List<String> ingredients){
        OrdersRequest request = new OrdersRequest(ingredients);
        request.setIngredients(ingredients);
        return orderService.createOrderWithToken(token, new OrdersRequest(ingredients)).then().log().all();
    }

    @Step("Создание заказа. Неавторизованный пользователь")
    public ValidatableResponse createOrderWithoutToken(List<String> ingredients){
        OrdersRequest request = new OrdersRequest(ingredients);
        request.setIngredients(ingredients);
        return orderService.createOrderWithoutToken(new OrdersRequest(ingredients)).then().log().all();
    }

    @Step("Получение заказов. Авторизованный пользователь")
    public ValidatableResponse getOrdersWithToken(String token){
        return orderService.getOrdersWithToken(token).then();
    }

    @Step("Получение заказов. Неавторизованный пользователь")
    public ValidatableResponse getOrdersWithoutToken(){
        return orderService.getOrdersWithoutToken().then().log().all();
    }
}
