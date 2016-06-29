package com.dnstack.beacon.client.utils

/**
 * Gson helper to reuse one Gson instance.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class Gson {
    public static final com.google.gson.Gson GSON = new com.google.gson.Gson()

    public static String toJson(Object src) {
        return GSON.toJson(src)
    }
}
