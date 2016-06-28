package com.dnastack.beacon.client;

import avro.shaded.com.google.common.base.Preconditions;
import com.dnastack.beacon.client.exceptions.InternalException;
import org.apache.commons.lang.StringUtils;
import org.ga4gh.beacon.Beacon;
import org.ga4gh.beacon.BeaconAlleleRequest;
import org.ga4gh.beacon.BeaconAlleleResponse;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Beacon client implementation.
 * The implementation is thread-safe except one operation - change of the service base url.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class BeaconClientImpl implements BeaconClient {
    private BeaconRetroService beaconRetroService;

    /**
     * Creates a Beacon client with the specified service base url.
     *
     * @param serviceBaseUrl service base url.
     */
    public BeaconClientImpl(String serviceBaseUrl) {
        setServiceBaseUrl(serviceBaseUrl);
    }

    /**
     * Creates a Beacon client with the specified service base url.
     *
     * @param serviceBaseUrl service base url.
     */
    public BeaconClientImpl(URL serviceBaseUrl) {
        Preconditions.checkNotNull(serviceBaseUrl, "serviceBaseUrl URL mustn't be null");
        setServiceBaseUrl(serviceBaseUrl.toString());
    }

    /**
     * Sets the service base url the client should be talking to.<p>
     * Note, this operation is not thread-safe.
     *
     * @param serviceBaseUrl - service base url.
     */
    public void setServiceBaseUrl(String serviceBaseUrl) {
        checkNotNullOrEmpty(serviceBaseUrl, "serviceBaseUrl");
        beaconRetroService = BeaconRetroServiceFactory.create(serviceBaseUrl);
    }

    @Override
    public Beacon getBeacon() throws InternalException {
        return executeCall(beaconRetroService.getBeacon());
    }

    @Override
    public BeaconAlleleResponse getBeaconAlleleResponse(String referenceName, long start, String referenceBases,
                                                        String alternateBases, String assemblyId, List<String> datasetIds,
                                                        boolean includeDatasetResponses) throws InternalException {
        checkNotNullOrEmpty(referenceName, "referenceName");
        checkNotNullOrEmpty(referenceBases, "referenceBases");
        checkNotNullOrEmpty(alternateBases, "alternateBases");
        checkNotNullOrEmpty(assemblyId, "assemblyId");
        Preconditions.checkArgument(start > 0, "start should be non-negative number.");
        Preconditions.checkArgument(isReferenceNameValid(referenceName),
                "referenceName is not valid. Valid values: 1-22, X, Y.");

        BeaconAlleleRequest request = BeaconAlleleRequest.newBuilder()
                .setReferenceName(referenceName)
                .setStart(start)
                .setReferenceBases(referenceBases)
                .setAlternateBases(alternateBases)
                .setAssemblyId(assemblyId)
                .setDatasetIds(datasetIds)
                .setIncludeDatasetResponses(includeDatasetResponses)
                .build();
        return executeCall(beaconRetroService.getBeaconAlleleResponse(request));
    }

    private static <T> T executeCall(Call<T> call) throws InternalException {
        Response<T> response;
        try {
            response = call.execute();
        } catch (IOException | RuntimeException e) {
            throw new InternalException("Error during communication to server.", e);
        }

        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new InternalException(String.format("Received error response from server. HTTP code: %s",
                    response.code()));
        }
    }

    /**
     * Checks if referenceName is one of the accepted values: 1-22, X, Y.
     */
    private static boolean isReferenceNameValid(String referenceName) {
        try {
            int numeric = Integer.parseInt(referenceName);
            return numeric >= 1 && numeric <= 22;
        } catch (NumberFormatException e) {
            return referenceName.equals("X") || referenceName.equals("Y");
        }
    }

    private static void checkNotNullOrEmpty(String value, String valueName) {
        Preconditions.checkArgument(StringUtils.isNotBlank(value), valueName + " mustn't be null or empty.");
    }
}
