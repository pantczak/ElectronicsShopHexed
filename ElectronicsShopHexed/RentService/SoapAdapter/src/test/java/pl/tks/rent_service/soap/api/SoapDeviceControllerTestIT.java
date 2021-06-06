package pl.tks.rent_service.soap.api;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Formatter;
import java.util.Scanner;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SoapDeviceControllerTestIT {

    private static UUID uuid;

    @Order(4)
    @Test
    void getDevice() throws Exception {

        String content = new Scanner(new File(getClass().getClassLoader().getResource("getDevice.xml").getFile())).useDelimiter("\\Z").next();

        content = content.replaceAll("uuidToChange",uuid.toString());

        RestAssured.baseURI = "http://localhost:8080/soap/DeviceAPI";
        
        Response response = given()
                .header("Content-Type", "text/xml")
                .and()
                .body(content)
                .when()
                .post()
                .then()
                .statusCode(200).and().log().all().extract().response();

        XmlPath xmlPath = new XmlPath(response.asString());
        assertTrue(xmlPath.getString("getDeviceResponse").contains("AppleMacBooktrue"));

    }

    @Order(1)
    @Test
    void getAllDevices() throws Exception {

        InputStream fileInputStream =  getClass().getClassLoader().getResourceAsStream("getAllDevices.xml");

        RestAssured.baseURI = "http://localhost:8080/soap/DeviceAPI";

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
        assertTrue(xmlPath.getString("getAllDevicesResponse").contains("AppleMacBooktrue"));
        assertTrue(xmlPath.getString("getAllDevicesResponse").contains("XiaomiMiAirtrue"));
        assertTrue(xmlPath.getString("getAllDevicesResponse").contains("HPProBooktrue"));
        assertTrue(xmlPath.getString("getAllDevicesResponse").contains("SamsungS29true"));
        assertTrue(xmlPath.getString("getAllDevicesResponse").contains("Nokia460true"));

        uuid = UUID.fromString(xmlPath.getString("Envelope.Body.getAllDevicesResponse.return[0].uuid"));
        System.out.println(uuid);

    }

    @Order(2)
    @Test
    void getAllLaptops() throws Exception {
        InputStream fileInputStream =  getClass().getClassLoader().getResourceAsStream("getAllLaptops.xml");

        RestAssured.baseURI = "http://localhost:8080/soap/DeviceAPI";

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
        assertTrue(xmlPath.getString("getAllDevicesResponse").contains("AppleMacBooktrue"));
        assertTrue(xmlPath.getString("getAllDevicesResponse").contains("XiaomiMiAirtrue"));
        assertTrue(xmlPath.getString("getAllDevicesResponse").contains("HPProBooktrue"));
        assertFalse(xmlPath.getString("getAllDevicesResponse").contains("SamsungS29true"));
        assertFalse(xmlPath.getString("getAllDevicesResponse").contains("Nokia460true"));
    }

    @Order(3)
    @Test
    void getAllSmartphones() throws Exception {

        InputStream fileInputStream =  getClass().getClassLoader().getResourceAsStream("getAllSmartphones.xml");

        RestAssured.baseURI = "http://localhost:8080/soap/DeviceAPI";

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
        assertFalse(xmlPath.getString("getAllDevicesResponse").contains("AppleMacBooktrue"));
        assertFalse(xmlPath.getString("getAllDevicesResponse").contains("XiaomiMiAirtrue"));
        assertFalse(xmlPath.getString("getAllDevicesResponse").contains("HPProBooktrue"));
        assertTrue(xmlPath.getString("etAllDevicesResponse").contains("SamsungS29true"));
        assertTrue(xmlPath.getString("etAllDevicesResponse").contains("Nokia460true"));
    }
}