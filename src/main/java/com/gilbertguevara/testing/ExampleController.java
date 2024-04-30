package com.gilbertguevara.testing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gilbertguevara.testing.person.PersonRepository;
import com.gilbertguevara.testing.weather.WeatherResponse;
import com.gilbertguevara.testing.weather.WeatherClient;

@RestController
public class ExampleController {

    private final PersonRepository personRepository;
    private final WeatherClient weatherClient;

    public ExampleController(PersonRepository personRepository, WeatherClient weatherClient) {
        this.personRepository = personRepository;
        this.weatherClient = weatherClient;
    }

    @GetMapping("/greet")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/greet/{lastName}")
    public String hello(@PathVariable String lastName) {
        var foundPerson = personRepository.findByLastName(lastName);

        return foundPerson
                .map(person -> String.format("Hello %s %s!", person.firstName(), person.lastName()))
                .orElse(String.format("Who is this '%s' you're talking about?", lastName));
    }

    @GetMapping("/weather/{query}")
    public WeatherResponse weather(@PathVariable String query) {
        return weatherClient.fetchWeather(query).orElse(null);
    }
}
