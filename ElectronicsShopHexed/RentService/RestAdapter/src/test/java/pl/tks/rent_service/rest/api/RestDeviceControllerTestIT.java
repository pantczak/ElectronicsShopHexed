package pl.tks.rent_service.rest.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.rest.aggregates.mappers.DeviceMapper;
import pl.tks.rent_service.rest.model.DeviceDtoRest;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestDeviceControllerTestIT {

    private static UUID uuid;


    @Order(4)
    @Test
    void getDevice() {
        JsonPath jsonPath = RestAssured.given().pathParam("uuid", uuid)
                .when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/{uuid}")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("uuid", notNullValue())
                .extract().body().jsonPath();


        DeviceDtoRest dtoRests = jsonPath.getObject("", DeviceDtoRest.class);
        assertNotNull(dtoRests);
        assertEquals(dtoRests.getUuid(), uuid.toString());
        assertEquals(dtoRests.getModel(), "MacBook");

    }

    @Order(3)
    @Test
    void getAllLaptops() {
        JsonPath jsonPath = RestAssured.when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/laptops")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .extract().body().jsonPath();

        List<DeviceDtoRest> dtoRests = jsonPath.getList("", DeviceDtoRest.class);
        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 3);
        assertNotNull(dtoRests.get(0).getUuid());
        assertNotNull(dtoRests.get(1).getUuid());
        assertNotNull(dtoRests.get(2).getUuid());
        assertEquals(dtoRests.get(0).getModel(), "MacBook");
        assertEquals(dtoRests.get(1).getModel(), "MiAir");
        assertEquals(dtoRests.get(2).getModel(), "ProBook");
    }

    @Order(2)
    @Test
    void getAllSmartphones() {
        JsonPath jsonPath = RestAssured.when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/smartphones")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .extract().body().jsonPath();

        List<DeviceDtoRest> dtoRests = jsonPath.getList("", DeviceDtoRest.class);
        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 2);
        assertNotNull(dtoRests.get(0).getUuid());
        assertNotNull(dtoRests.get(1).getUuid());
        assertEquals(dtoRests.get(0).getModel(), "S29");
        assertEquals(dtoRests.get(1).getModel(), "460");
    }

    @Order(1)
    @Test
    void getAllDevices() {
        JsonPath jsonPath = RestAssured.when()
                .get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/all")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .extract().body().jsonPath();

        List<DeviceDtoRest> dtoRests = jsonPath.getList("", DeviceDtoRest.class);
        assertNotNull(dtoRests);
        assertEquals(dtoRests.size(), 5);
        assertNotNull(dtoRests.get(0).getUuid());
        assertNotNull(dtoRests.get(1).getUuid());
        assertNotNull(dtoRests.get(2).getUuid());
        assertNotNull(dtoRests.get(3).getUuid());
        assertNotNull(dtoRests.get(4).getUuid());
        assertEquals(dtoRests.get(0).getModel(), "MacBook");
        assertEquals(dtoRests.get(1).getModel(), "MiAir");
        assertEquals(dtoRests.get(2).getModel(), "ProBook");
        assertEquals(dtoRests.get(3).getModel(), "S29");
        assertEquals(dtoRests.get(4).getModel(), "460");

        uuid = UUID.fromString(dtoRests.get(0).getUuid());
    }

    @Order(5)
    @Test
    void addSmartphone() {
        DeviceDtoRest device = DeviceMapper.INSTANCE.smartphoneToDeviceDto(new Smartphone("Xiaomi", "Mi8", 1220, 22.4));
        RestAssured.with().header("Content-Type","application/json" ).header("Accept","application/json" ).body(device)
                .when().request("POST", "http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/add/smartphone").then().assertThat().statusCode(201);

        List<DeviceDtoRest> dtoRests = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/smartphones").body().jsonPath().getList("", DeviceDtoRest.class);
        assertEquals(3,dtoRests.size());
        assertEquals(dtoRests.get(2).getBrand(),"Xiaomi");
        assertEquals(dtoRests.get(2).getModel(),"Mi8");
        assertEquals(dtoRests.get(2).getWeightInGrams(),1220);
        assertEquals(dtoRests.get(2).getBatteryLifetime(),22.4);


    }

    @Order(6)
    @Test
    void addLaptop() {
        DeviceDtoRest device = DeviceMapper.INSTANCE.laptopToDeviceDto(new Laptop("Asus", "Pavilion", 12220, 2500));
        RestAssured.with().header("Content-Type","application/json" ).header("Accept","application/json" ).body(device)
                .when().request("POST", "http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/add/laptop").then().assertThat().statusCode(201);

        List<DeviceDtoRest> dtoRests = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/laptops").body().jsonPath().getList("", DeviceDtoRest.class);
        assertEquals(4,dtoRests.size());
        assertEquals(dtoRests.get(3).getBrand(),"Asus");
        assertEquals(dtoRests.get(3).getModel(),"Pavilion");
        assertEquals(dtoRests.get(3).getWeightInGrams(),12220);
        assertEquals(dtoRests.get(3).getMemoryInGb(),2500);

    }

    @Order(7)
    @Test
    void deleteDevice() {
        List<DeviceDtoRest> dtoRests = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/all").body().jsonPath().getList("", DeviceDtoRest.class);
        String  deletedUuid = dtoRests.get(0).getUuid();
        RestAssured.delete("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/delete/{uuid}",deletedUuid).then().assertThat().statusCode(204);
        dtoRests = RestAssured.get("http://localhost:8080/RestAdapter-1.0-SNAPSHOT/rest/devices/all").body().jsonPath().getList("", DeviceDtoRest.class);
        assertNotEquals(dtoRests.get(0).getUuid(), deletedUuid);


    }
}