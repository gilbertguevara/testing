package com.gilbertguevara.testing.person;

import java.util.UUID;

import org.springframework.data.annotation.Id;

public record Person(@Id UUID id, String firstName, String lastName) {
}
