package testClasses;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import pojo.Courier;

import static io.restassured.RestAssured.given;

public class CourierLoginTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Courier enters the system.")
    @Description("Check status code when the courier enters the system(successful request).")
    public void authorizationTest(){
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        courier.setPassword("1234");
        Response response = sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("id", Matchers.notNullValue()).and().statusCode(200);

    }

    @Test
    @DisplayName("Courier enters the system without login.")
    @Description("Check status code when the courier enters the system without login (bad request).")
    public void authorizationWithoutLoginTest(){
        Courier courier = new Courier();
        courier.setPassword("1234");
        Response response = sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Недостаточно данных для входа")).
                and().statusCode(400);

    }

    /**
    Тест падает по причине дефекта в сервисе Авторизации
     **/
    @Test
    @DisplayName("Courier enters the system without password.")
    @Description("Check status code when the courier enters the system without password (bad request).")
    public void authorizationWithoutPasswordTest(){
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        Response response = sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Недостаточно данных для входа")).and().statusCode(400);

    }

    @Test
    @DisplayName("Courier enters the system with wrong password.")
    @Description("Check status code when the courier enters the system with wrong password.")
    public void authorizationWithWrongPasswordTest(){
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        courier.setPassword("1234000");
        Response response = sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Courier enters the system with wrong login.")
    @Description("Check status code when the courier enters the system with wrong login.")
    public void authorizationWithWrongLoginTest(){
        Courier courier = new Courier();
        courier.setLogin("ninja1453NarutoILoveYou");
        courier.setPassword("1234");
        Response response = sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Courier enters the system with wrong login and password.")
    @Description("Check status code when the courier enters the system with wrong login and password.")
    public void authorizationWithWrongLoginAndPasswordTest(){
        Courier courier = new Courier();
        courier.setLogin("TakogoLoginaNeSushestvuet");
        courier.setPassword("TakogoParolaNet");
        Response response = sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response sendPostRequestApiV1CourierLogin(Courier courier){
        return given().log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }
}
