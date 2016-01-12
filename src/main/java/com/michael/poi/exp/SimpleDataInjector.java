package com.michael.poi.exp;

import com.google.gson.JsonObject;

/**
 * @author Michael
 */
public class SimpleDataInjector implements DataInjector {
    private JsonObject data;

    public SimpleDataInjector(JsonObject data) {
        this.data = data;
    }

    @Override
    public JsonObject fetch(int start, int limit) {
        return this.data;
    }
}
