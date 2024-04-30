package com.gilbertguevara.testing;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import com.gilbertguevara.testing.helper.FileLoader;
import com.gilbertguevara.testing.person.PersonRepository;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import io.restassured.http.ContentType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 8089)
public class ExampleControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PersonRepository personRepository;

    @Value("${weather.api_key}")
    private String weatherApiKey;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @AfterEach
    public void tearDown() throws Exception {
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return an standard greeting")
    public void shouldReturnHelloWorld() {
        when()
                .get(String.format("http://localhost:%s/greet", port))
                .then()
                .statusCode(is(200))
                .body(containsString("Hello World!"));
    }

    @Test
    @DisplayName("Should return a greeting with first name and last name")
    public void shouldReturnGreeting() {
        when()
                .get(String.format("http://localhost:%s/greet/Pan", port))
                .then()
                .statusCode(is(200))
                .body(containsString("Hello Peter Pan!"));
    }

    @Test
    @DisplayName("Should return the weather for a given city")
    public void shouldGetWeather() {
        stubFor(
                get(anyUrl()).willReturn(
                                aResponse()
                                        .withBody(FileLoader.read("classpath:weatherApiResponse.json"))
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withStatus(200)
                        )
        );

        given().contentType(ContentType.JSON)
                .when()
                .get(String.format("http://localhost:%s/weather/San Vicente,co", port))
                .then()
                .statusCode(is(200))
                .log().all()
                .body("coord.lat", equalTo(6.2328F))
                .body("coord.lon", equalTo(-75.3361F))
                .body("weather[0].main", equalTo("Clouds"))
                .body("weather[0].description", equalTo("broken clouds"))
                .body("weather[0].icon", equalTo("04d"))
                .body("base", equalTo("stations"))
                .body("main.temp", equalTo(16.97F))
                .body("main.feels_like", equalTo(17.02F))
                .body("main.temp_min", equalTo(16.94F))
                .body("main.temp_max", equalTo(19.89F))
                .body("main.humidity", equalTo(88))
                .body("main.pressure", equalTo(1028))
                .body("visibility", equalTo(8000))
                .body("wind.speed", equalTo(1.54F))
                .body("wind.deg", equalTo(30.0F))
                .body("clouds.all", equalTo(75))
                .body("dt", notNullValue())
                .body("sys.sunrise", notNullValue())
                .body("sys.sunset", notNullValue())
                .body("timezone", equalTo(-18000))
                .body("name", equalTo("San Vicente"))
                .body("cod", equalTo(200));
    }
}
