package pl.tks.rent_service.rest.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.rest.aggregates.mappers.DeviceMapper;
import pl.tks.rent_service.rest.aggregates.mappers.EventMapper;
import pl.tks.rent_service.rest.aggregates.mappers.UserMapper;
import pl.tks.rent_service.rest.model.DeviceDtoRest;
import pl.tks.rent_service.rest.model.EventDtoRest;
import pl.tks.rent_service.rest.model.UserDtoRest;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestEventControllerTestIT {

    private static UUID device;

    private static UUID user;
    @Test
    void getEvent() {

    }

    @Order(1)
    @Test
    void getAllEvents() {
        JsonPath jsonPath = RestAssured.when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/events/all")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("uuid", notNullValue())
                .extract().body().jsonPath();

        List<EventDtoRest> dtoRests = jsonPath.getList("", EventDtoRest.class);

        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 2);
        assertNotNull(dtoRests.get(0).getUuid());
        assertNotNull(dtoRests.get(0).getClient());
        assertNotNull(dtoRests.get(0).getDevice());

        user = UUID.fromString(dtoRests.get(0).getClient().getUuid());

        device = UUID.fromString(dtoRests.get(0).getDevice().getUuid());

    }

    @Order(2)
    @Test
    void getEventsByDevice() {
        JsonPath jsonPath = RestAssured.given().pathParam("uuid", device)
                .when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/events/device/{uuid}")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("uuid", notNullValue())
                .extract().body().jsonPath();


        List<EventDtoRest> dtoRests = jsonPath.getList("", EventDtoRest.class);
        assertNotNull(dtoRests);
        assertEquals(dtoRests.get(0).getDevice().getUuid(), device.toString());
        assertEquals(dtoRests.get(0).getDevice().getModel(), "MiAir");

    }

    @Order(3)
    @Test
    void getEventsByUser() {
        JsonPath jsonPath = RestAssured.given().pathParam("uuid", user)
                .when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/events/user/{uuid}")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("uuid", notNullValue())
                .extract().body().jsonPath();


        List<EventDtoRest> dtoRests = jsonPath.getList("", EventDtoRest.class);
        System.out.println(dtoRests.size());
        assertNotNull(dtoRests);
        assertEquals(dtoRests.get(0).getClient().getUuid(), user.toString());
        assertEquals(dtoRests.get(0).getClient().getLogin(), "rodzyn123");
    }

    @Order(4)
    @Test
    void borrowDevice(){
        List<DeviceDtoRest> dtoRests = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/laptops").body().jsonPath().getList("", DeviceDtoRest.class);
        Laptop device = DeviceMapper.INSTANCE.toLaptop(dtoRests.get(0));
        assertTrue(device.isAvailable());

        List<UserDtoRest> users = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/users/clients").body().jsonPath().getList("", UserDtoRest.class);
        Client client = UserMapper.INSTANCE.toClient(users.get(0));

        EventDtoRest eventDtoRest = EventMapper.INSTANCE.eventToEventDto(new Event(device,client));

        RestAssured.with().header("Content-Type","application/json" ).header("Accept","application/json" ).body(eventDtoRest).when().post("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/events/add/event").then().assertThat().statusCode(200);

        List<EventDtoRest> events = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/events/all").body().jsonPath().getList("", EventDtoRest.class);
        assertNotNull(dtoRests);
        assertTrue(dtoRests.size()>=2);
        EventDtoRest result = events.get(events.size()-1);
        assertEquals(eventDtoRest.getDevice().getUuid(),result.getDevice().getUuid());
        assertEquals(eventDtoRest.getClient().getUuid(),result.getClient().getUuid());
       // assertEquals(eventDtoRest.getBorrowDate(),result.getBorrowDate().);





    }
}