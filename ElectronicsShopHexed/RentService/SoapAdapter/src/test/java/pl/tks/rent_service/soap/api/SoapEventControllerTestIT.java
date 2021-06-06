package pl.tks.rent_service.soap.api;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SoapEventControllerTestIT {

    private static UUID deviceUuid;

    private static UUID userUuid;

    private static UUID eventUuid;

    @Order(2)
    @Test
    void getEvent() throws FileNotFoundException {

        String content = new Scanner(new File(getClass().getClassLoader().getResource("getEvent.xml").getFile()))
                .useDelimiter("\\Z").next();

        content = content.replaceAll("uuidToChange", eventUuid.toString());

        RestAssured.baseURI = "http://localhost:8080/soap/EventAPI";

        Response response = given()
                .header("Content-Type", "text/xml")
                .and()
                .body(content)
                .when()
                .post()
                .then()
                .statusCode(200).and().log().all().extract().response();

        XmlPath xmlPath = new XmlPath(response.asString());
        assertTrue(xmlPath.getString("Envelope.Body.getEventResponse.return[0].device.brand").contains("Xiaomi"));
        assertTrue(xmlPath.getString("Envelope.Body.getEventResponse.return[0].device.model").contains("MiAir"));

    }

    @Order(1)
    @Test
    void getAllEvents() throws IOException {

        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("getAllEvents.xml");

        RestAssured.baseURI = "http://localhost:8080/soap/EventAPI";

        assert fileInputStream != null;
        Response response = given()
                .header("Content-Type", "text/xml")
                .and()
                .body(IOUtils.toString(fileInputStream, "UTF-8"))
                .when()
                .post()
                .then()
                .statusCode(200).and().log().all().extract().response();

        XmlPath xmlPath = new XmlPath(response.asString());
        assertTrue(xmlPath.getString("Envelope.Body.getAllEventsResponse.return[0].device.brand").contains("Xiaomi"));
        assertTrue(xmlPath.getString("Envelope.Body.getAllEventsResponse.return[0].device.model").contains("MiAir"));

        eventUuid = UUID.fromString(xmlPath.getString("Envelope.Body.getAllEventsResponse.return[0].uuid"));
        deviceUuid = UUID.fromString(xmlPath.getString("Envelope.Body.getAllEventsResponse.return[0].device.uuid"));
        userUuid = UUID.fromString(xmlPath.getString("Envelope.Body.getAllEventsResponse.return[0].client.uuid"));
    }

    @Order(3)
    @Test
    void getEventsByDevice() throws IOException {

        String content = new Scanner(new File(getClass().getClassLoader().getResource("getEventByDevice.xml").getFile()))
                .useDelimiter("\\Z").next();

        content = content.replaceAll("uuidToChange", deviceUuid.toString());

        RestAssured.baseURI = "http://localhost:8080/soap/EventAPI";

        Response response = given()
                .header("Content-Type", "text/xml")
                .and()
                .body(content)
                .when()
                .post()
                .then()
                .statusCode(200).and().log().all().extract().response();

        XmlPath xmlPath = new XmlPath(response.asString());
        assertTrue(xmlPath.getString("Envelope.Body.getEventsByDeviceResponse.return[0].device.brand").contains("Xiaomi"));
        assertTrue(xmlPath.getString("Envelope.Body.getEventsByDeviceResponse.return[0].device.model").contains("MiAir"));
    }


    @Order(4)
    @Test
    void getEventsByUser() throws IOException {
        String content = new Scanner(new File(getClass().getClassLoader().getResource("getEventByUser.xml").getFile()))
                .useDelimiter("\\Z").next();

        content = content.replaceAll("uuidToChange", userUuid.toString());

        RestAssured.baseURI = "http://localhost:8080/soap/EventAPI";

        Response response = given()
                .header("Content-Type", "text/xml")
                .and()
                .body(content)
                .when()
                .post()
                .then()
                .statusCode(200).and().log().all().extract().response();

        XmlPath xmlPath = new XmlPath(response.asString());
        assertTrue(xmlPath.getString("Envelope.Body.getEventsByUserResponse.return[0].device.brand").contains("Xiaomi"));
        assertTrue(xmlPath.getString("Envelope.Body.getEventsByUserResponse.return[0].device.model").contains("MiAir"));
    }
}