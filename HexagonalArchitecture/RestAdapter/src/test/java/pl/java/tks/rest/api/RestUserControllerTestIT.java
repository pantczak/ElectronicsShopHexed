package pl.java.tks.rest.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.rest.aggregates.mappers.UserMapper;
import pl.java.tks.rest.model.UserDtoRest;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestUserControllerTestIT {

    private static UUID uuid;

    @Order(2)
    @Test
    void getUser() {
        JsonPath jsonPath = RestAssured.given().pathParam("uuid", uuid)
                .when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/{uuid}")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("uuid", notNullValue())
                .extract().body().jsonPath();


        UserDtoRest dtoRests = jsonPath.getObject("", UserDtoRest.class);
        assertNotNull(dtoRests);
        assertEquals(dtoRests.getUuid(), uuid.toString());
        assertEquals(dtoRests.getLogin(), "rodzyn123");
    }

    @Order(1)
    @Test
    void getAllUsers() {
        JsonPath jsonPath = RestAssured.when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/users")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("userUuid", notNullValue())
                .extract().body().jsonPath();

        List<UserDtoRest> dtoRests = jsonPath.getList("", UserDtoRest.class);
        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 3);
        assertEquals(dtoRests.get(0).getLogin(), "rodzyn123");
        assertEquals(dtoRests.get(1).getLogin(), "dfsd");
        assertEquals(dtoRests.get(2).getLogin(), "dlaCiebie");
        assertNotNull(dtoRests.get(0).getUuid());
        assertNotNull(dtoRests.get(1).getUuid());
        assertNotNull(dtoRests.get(2).getUuid());

        uuid = UUID.fromString(dtoRests.get(0).getUuid());
    }

    @Order(3)
    @Test
    void getAllClients() {
        JsonPath jsonPath = RestAssured.when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/clients")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("uuid", notNullValue())
                .extract().body().jsonPath();

        List<UserDtoRest> dtoRests = jsonPath.getList("", UserDtoRest.class);

        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 1);
        assertNotNull(dtoRests.get(0).getUuid());
        assertEquals(dtoRests.get(0).getLogin(), "rodzyn123");
        assertEquals(dtoRests.get(0).getPassword(), "pasowd123");


    }

    @Order(4)
    @Test
    void getAllEmployees() {
        JsonPath jsonPath = RestAssured.when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/employees")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("uuid", notNullValue())
                .body("borrowDate", notNullValue())
                .extract().body().jsonPath();

        List<UserDtoRest> dtoRests = jsonPath.getList("", UserDtoRest.class);

        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 1);
        assertNotNull(dtoRests.get(0).getUuid());
        assertEquals(dtoRests.get(0).getLogin(), "dfsd");
        assertEquals(dtoRests.get(0).getPassword(), "pasowd123");
    }
    @Order(5)
    @Test
    void getAllAdministrators() {

        JsonPath jsonPath = RestAssured.when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/administrators")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("uuid", notNullValue())
                .body("borrowDate", notNullValue())
                .extract().body().jsonPath();

        List<UserDtoRest> dtoRests = jsonPath.getList("", UserDtoRest.class);

        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 1);
        assertNotNull(dtoRests.get(0).getUuid());
        assertEquals(dtoRests.get(0).getLogin(), "dlaCiebie");
        assertEquals(dtoRests.get(0).getPassword(), "admin123123");
    }

    @Order(6)
    @Test
    void createAdmin(){
        UserDtoRest userDtoRest = UserMapper.INSTANCE.administratorToUserDto(new Administrator("John","Wick","Babayaga","Puppy123"));
        RestAssured.with().header("Content-Type","application/json" ).header("Accept","application/json" ).body(userDtoRest)
                .when().request("POST", "http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/admin").then().assertThat().statusCode(201);
        List<UserDtoRest> dtoRests = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/administrators").body().jsonPath().getList("",UserDtoRest.class);

        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 2);
        assertNotNull(dtoRests.get(1).getUuid());
        assertEquals(dtoRests.get(1).getName(), "John");
        assertEquals(dtoRests.get(1).getLastName(), "Wick");
        assertEquals(dtoRests.get(1).getLogin(), "Babayaga");
        assertEquals(dtoRests.get(1).getPassword(), "Puppy123");

    }
    @Order(7)
    @Test
    void createEmployee(){
        UserDtoRest userDtoRest = UserMapper.INSTANCE.employeeToUserDto(new Employee("Sherlock","Holmes","TheDetective","SherLocked"));
        RestAssured.with().header("Content-Type","application/json" ).header("Accept","application/json" ).body(userDtoRest)
                .when().request("POST", "http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/employee").then().assertThat().statusCode(201);
        List<UserDtoRest> dtoRests = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/employees").body().jsonPath().getList("",UserDtoRest.class);

        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 2);
        assertNotNull(dtoRests.get(1).getUuid());
        assertEquals(dtoRests.get(1).getName(), "Sherlock");
        assertEquals(dtoRests.get(1).getLastName(), "Holmes");
        assertEquals(dtoRests.get(1).getLogin(), "TheDetective");
        assertEquals(dtoRests.get(1).getPassword(), "SherLocked");

    }
    @Order(8)
    @Test
    void createClient(){
        UserDtoRest userDtoRest = UserMapper.INSTANCE.clientToUserDto(new Client("Sergio","Marquina","El_Professor","kJ1fGnK81SsD",42));
        RestAssured.with().header("Content-Type","application/json" ).header("Accept","application/json" ).body(userDtoRest)
                .when().request("POST", "http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/client").then().assertThat().statusCode(201);
        List<UserDtoRest> dtoRests = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/clients").body().jsonPath().getList("",UserDtoRest.class);

        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 2);
        assertNotNull(dtoRests.get(1).getUuid());
        assertEquals(dtoRests.get(1).getName(), "Sergio");
        assertEquals(dtoRests.get(1).getLastName(), "Marquina");
        assertEquals(dtoRests.get(1).getLogin(), "El_Professor");
        assertEquals(dtoRests.get(1).getPassword(), "kJ1fGnK81SsD");
        assertEquals(dtoRests.get(1).getAge(),42);

    }

}
