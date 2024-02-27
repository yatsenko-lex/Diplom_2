package praktikum.service;

import io.restassured.response.Response;
import praktikum.Specifications;
import praktikum.pojo.OrdersRequest;

import static praktikum.UrlPaths.INGREDIENTS;
import static praktikum.UrlPaths.ORDERS;

public class OrderService extends Specifications {

    public Response createOrderWithoutToken(OrdersRequest request) {
        return specification().log().all()
                .body(request)
                .when()
                .post(ORDERS);
    }

    public Response createOrderWithToken(String token, OrdersRequest request) {
        return specification().log().all()
                .header("authorization", token)
                .body(request)
                .when()
                .post(ORDERS);
    }

    public Response getOrdersWithoutToken() {
        return specification()
                .get(ORDERS);
    }

    public Response getOrdersWithToken(String token) {
        return specification()
                .header("authorization", token)
                .get(ORDERS);
    }


    public Response getIngredients() {
        return specification()
                .get(INGREDIENTS);
    }

}
