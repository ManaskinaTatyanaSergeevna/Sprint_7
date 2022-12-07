package testClasses;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import pojo.Courier;

import static io.restassured.RestAssured.given;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Successful create courier.")
    @Description("Check status code and field value for /api/v1/courier(successful request).")
    public void createCourierTest(){
        String login = RandomStringUtils.randomAlphanumeric(1,10);
        String password = RandomStringUtils.randomAlphanumeric(6,8);
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier(login, password, firstName);
        Response response = sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("ok", Matchers.is(true)).and().statusCode(201);

    }

    @Test
    @DisplayName("Create two identical couriers.")
    @Description("Check status code and message existence when create two identical couriers.")
    public void createTwoIdenticalCouriersTest(){
        String login = RandomStringUtils.randomAlphanumeric(1,10);
        String password = RandomStringUtils.randomAlphanumeric(6,8);
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier(login, password, firstName);
        sendPostRequestApiV1Courier(courier);
        Response response = sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(409);
    }

    @Test
    @DisplayName("Create two couriers with identical logins.")
    @Description("Check status code and message existence when create two couriers with identical logins.")
    public void createTwoIdenticalLoginTest(){
        String login = RandomStringUtils.randomAlphanumeric(1,10);
        String password = RandomStringUtils.randomAlphanumeric(6,8);
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier(login, password, firstName);
        given().log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
        courier.setPassword("abracadabra");
        Response response = sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(409);
    }

    @Test
    @DisplayName("Create courier without login and password.")
    @Description("Check status code and message existence when create courier without login and password(Bad request).")
    public void createCourierWithoutLoginAndPassword(){
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        Response response = sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Create courier without login and first name.")
    @Description("Check status code and message existence in create courier without login and first name(Bad request).")
    public void createCourierWithoutLoginAndFirstName(){
        String password = RandomStringUtils.randomAlphabetic(6,8);
        Courier courier = new Courier();
        courier.setPassword(password);
        Response response = sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Create courier without password and first name.")
    @Description("Check status code and message existence in create courier without password and first name(Bad request).")
    public void createCourierWithoutPasswordAndFirstName(){
        String login = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier();
        courier.setLogin(login);
        Response response = sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Create courier without password.")
    @Description("Check status code and message existence in create courier without password(Bad request).")
    public void createCourierWithoutPassword(){
        String login = RandomStringUtils.randomAlphabetic(1,10);
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setFirstName(firstName);
        Response response = sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Create courier without login.")
    @Description("Check status code and message existence in create courier without login(Bad request).")
    public void createCourierWithoutLogin(){
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        String password = RandomStringUtils.randomAlphanumeric(6,8);
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        courier.setPassword(password);
        Response response = sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

        @Step("Send POST request to /api/v1/courier")
        public Response sendPostRequestApiV1Courier(Courier courier){
            return given().log().all()
                    .header("Content-type", "application/json")
                    .body(courier)
                    .when()
                    .post("/api/v1/courier");
        }

}
