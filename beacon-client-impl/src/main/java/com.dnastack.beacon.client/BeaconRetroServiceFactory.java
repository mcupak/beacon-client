package com.dnastack.beacon.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for configuring and creating actual Beacon http clients that will be communicating
 * to the server directly. These http clients are implemented by Retrofit at runtime.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class BeaconRetroServiceFactory {
    /**
     * GsonConverterFactory is thread-safe. Can declare it static.
     */
    private static final GsonConverterFactory CONVERTER_FACTORY = GsonConverterFactory.create();

    /**
     * OkHttpClient is thread-safe. Can declare it static.
     * Set read timeout to 5 minutes as querying beacons may take quite a long time.
     */
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .addNetworkInterceptor(chain -> {
                Request request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(request);
            }).build();

    private BeaconRetroServiceFactory() {
    }

    public static BeaconRetroService create(String serviceBaseUrl) {
        return new Retrofit.Builder()
                .client(HTTP_CLIENT)
                .addConverterFactory(CONVERTER_FACTORY)
                .baseUrl(serviceBaseUrl)
                .build()
                .create(BeaconRetroService.class);
    }
}
