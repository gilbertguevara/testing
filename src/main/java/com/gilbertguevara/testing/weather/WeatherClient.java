package com.gilbertguevara.testing.weather;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient  {
    private final RestTemplate restTemplate;
    private final String weatherServiceUrl;
    private final String weatherApiKey;

    public WeatherClient(
            RestTemplate restTemplate,
            @Value("${weather.url}") String weatherServiceUrl,
            @Value("${weather.api_key}") String weatherApiKey
    ) {
        this.restTemplate = restTemplate;
        this.weatherServiceUrl = weatherServiceUrl;
        this.weatherApiKey = weatherApiKey;
    }

    public Optional<WeatherResponse> fetchWeather(String query) {
        var url = String.format("%s/data/2.5/weather?q=%s&appid=%s&units=metric", weatherServiceUrl, query, weatherApiKey);

        try {
            return Optional.ofNullable(restTemplate.getForObject(url, WeatherResponse.class));
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
