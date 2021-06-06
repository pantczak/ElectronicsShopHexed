package pl.tks.rent_service.soap.api;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class SoapUserControllerTestIT {
    private static UUID uuid;

    @Order(1)
    @Test
    void getAllUsers() throws Exception {
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("getAllUsers.xml");

        RestAssured.baseURI = "http://localhost:8080/soap/UserAPI";

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
        assertTrue(xmlPath.getString("etAllUsersResponse").contains("Dokor"));
        assertTrue(xmlPath.getString("etAllUsersResponse").contains("Adam"));
        assertTrue(xmlPath.getString("etAllUsersResponse").contains("Pan"));
        assertTrue(xmlPath.getString("etAllUsersResponse").contains("Admin"));

        uuid = UUID.fromString(xmlPath.getString("Envelope.Body.getAllUsersResponse.return[0].uuid"));
    }

    @Order(4)
    @Test
    void getUserByUuid() throws Exception {
        String content = new Scanner(new File(getClass().getClassLoader().getResource("getUserByUuid.xml").getFile())).useDelimiter("\\Z").next();

        content = content.replaceAll("uuidToChange", uuid.toString());

        RestAssured.baseURI = "http://localhost:8080/soap/UserAPI";

        Response response = given()
                .header("Content-Type", "text/xml")
                .and()
                .body(content)
                .when()
                .post()
                .then()
                .statusCode(200).and().log().all().extract().response();

        XmlPath xmlPath = new XmlPath(response.asString());

        assertTrue(xmlPath.getString("getUserResponse").contains("Dokor"));
        assertTrue(xmlPath.getString("getUserResponse").contains("Nauk"));
        assertTrue(xmlPath.getString("getUserResponse").contains("true"));
        assertTrue(xmlPath.getString("getUserResponse").contains("rodzyn123"));
        assertTrue(xmlPath.getString("getUserResponse").contains("pasowd123"));
    }

    @Order(5)
    @Test
    void getUserByLogin() throws Exception {

        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("getUserByLogin.xml");

        RestAssured.baseURI = "http://localhost:8080/soap/UserAPI";

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
        assertTrue(xmlPath.getString("getUserResponse").contains("Dokor"));
        assertTrue(xmlPath.getString("getUserResponse").contains("Nauk"));
        assertTrue(xmlPath.getString("getUserResponse").contains("true"));
        assertTrue(xmlPath.getString("getUserResponse").contains("rodzyn123"));
        assertTrue(xmlPath.getString("getUserResponse").contains("pasowd123"));
    }

    @Order(2)
    @Test
    void getAllEmployees() throws Exception {
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("getAllEmployees.xml");

        RestAssured.baseURI = "http://localhost:8080/soap/UserAPI";

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
        assertTrue(xmlPath.getString("etAllEmployeesResponse").contains("Adam"));
        assertTrue(xmlPath.getString("etAllEmployeesResponse").contains("true"));
        assertTrue(xmlPath.getString("etAllEmployeesResponse").contains("dfsd"));
        assertTrue(xmlPath.getString("etAllEmployeesResponse").contains("pasowd123"));

    }

    @Order(3)
    @Test
    void getAllAdministrators() throws Exception {
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("getAllAdministrators.xml");

        RestAssured.baseURI = "http://localhost:8080/soap/UserAPI";

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
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("Pan"));
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("Admin"));
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("true"));
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("dlaCiebie"));
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("admin123123"));
    }

    @Order(4)
    @Test
    void getAllClients() throws Exception {
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("getAllClients.xml");

        RestAssured.baseURI = "http://localhost:8080/soap/UserAPI";

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
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("Dokor"));
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("Nauk"));
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("true"));
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("rodzyn123"));
        assertTrue(xmlPath.getString("etAllAdministratorsResponse").contains("pasowd123"));
    }
}