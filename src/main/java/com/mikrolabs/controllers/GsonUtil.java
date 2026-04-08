package com.mikrolabs.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.Strictness; // Add this import

public class GsonUtil {
    public static Gson create() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, ctx) -> {
                    // Logic to prevent the "Text '' could not be parsed" error
                    if (json.isJsonNull() || json.getAsString().trim().isEmpty()) {
                        return null;
                    }
                    try {
                        return LocalDate.parse(json.getAsString());
                    } catch (DateTimeParseException e) {
                        return null;
                    }
                })
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (date, type, ctx) -> date == null ? null
                                : new JsonPrimitive(date.toString()))
                // Use the new non-deprecated method
                .setStrictness(Strictness.LENIENT)
                .create();
    }
}