package com.dnstack.beacon.client.response

import com.dnstack.beacon.client.BaseBeaconTest
import com.dnstack.beacon.client.utils.Gson

import static com.dnastack.beacon.client.BeaconRetroService.BEACON_REQUEST_PATH
import static com.dnstack.beacon.client.TestData.TEST_ALLELE_REQUEST
import static com.dnstack.beacon.client.TestData.TEST_ALLELE_RESPONSE
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class AlleleResponseSuccessTest extends BaseBeaconTest {
    @Override
    void setupMappings() {
        MOCK_BOB_SERVER.stubFor(post(urlPathEqualTo("/$BEACON_REQUEST_PATH"))
                .withRequestBody(equalToJson(Gson.toJson(TEST_ALLELE_REQUEST)))

                .willReturn(aResponse()
                .withBody(Gson.toJson(TEST_ALLELE_RESPONSE))))
    }

    @Override
    void doTest() {
        def response = CLIENT.getBeaconAlleleResponse(
                TEST_ALLELE_REQUEST.referenceName,
                TEST_ALLELE_REQUEST.start,
                TEST_ALLELE_REQUEST.referenceBases,
                TEST_ALLELE_REQUEST.alternateBases,
                TEST_ALLELE_REQUEST.assemblyId,
                TEST_ALLELE_REQUEST.datasetIds,
                TEST_ALLELE_REQUEST.includeDatasetResponses,
        )
        assertThat(response).isEqualTo(TEST_ALLELE_RESPONSE)
    }
}
