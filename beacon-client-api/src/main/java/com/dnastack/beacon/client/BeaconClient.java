package com.dnastack.beacon.client;

import com.dnastack.beacon.client.exceptions.InternalException;
import org.ga4gh.beacon.Beacon;
import org.ga4gh.beacon.BeaconAlleleResponse;

import java.util.List;

/**
 * Beacon client API.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @author Miro Cupak (mirocupak@gmail.com)
 * @version 1.0
 */
public interface BeaconClient {

    /**
     * Gets beacon information.
     */
    Beacon getBeacon() throws InternalException;

    /**
     * Gets response to a beacon query for allele information.
     *
     * @param referenceName           reference name (chromosome). Accepted values: 1-22, X, Y.
     * @param start                   Position, allele locus (0-based). Accepted values: non-negative integers smaller
     *                                than reference length.
     * @param referenceBases          reference bases for this variant (starting from `start`). Accepted values: see the
     *                                REF field in VCF 4.2 specification (https://samtools.github.io/hts-specs/VCFv4.2.pdf).
     * @param alternateBases          the bases that appear instead of the reference bases. Accepted values: see the ALT
     *                                field in VCF 4.2 specification (https://samtools.github.io/hts-specs/VCFv4.2.pdf).
     * @param assemblyId              assembly identifier (GRC notation, e.g. `GRCh37`).
     * @param datasetIds              identifiers of datasets. If this field is null, all datasets will be queried.
     * @param includeDatasetResponses indicator of whether responses for individual datasets should be included (not null)
     *                                in the response. If null, the default value of false is assumed.
     */
    BeaconAlleleResponse getBeaconAlleleResponse(String referenceName, long start, String referenceBases, String alternateBases, String assemblyId, List<String> datasetIds, boolean includeDatasetResponses) throws InternalException;
}
