package com.dnastack.beacon.client;

import org.ga4gh.beacon.Beacon;
import org.ga4gh.beacon.BeaconAlleleRequest;
import org.ga4gh.beacon.BeaconAlleleResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Underlying Beacon API for Retrofit.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @author Miro Cupak (mirocupak@gmail.com)
 * @version 1.0
 */
public interface BeaconRetroService {

    String BEACON_REQUEST_PATH = "query";

    @GET(".")
    Call<Beacon> getBeacon();

    @POST(BEACON_REQUEST_PATH)
    Call<BeaconAlleleResponse> getBeaconAlleleResponse(@Body BeaconAlleleRequest request);
}
