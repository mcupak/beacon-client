package com.dnstack.beacon.client.beacon

import com.dnstack.beacon.client.BaseBeaconTest
import com.dnstack.beacon.client.utils.Gson

import static com.dnstack.beacon.client.TestData.TEST_BEACON
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class BeaconSuccessTest extends BaseBeaconTest {
    @Override
    void setupMappings() {
        MOCK_BEACON_SERVER.stubFor(get(urlEqualTo("/"))

                .willReturn(aResponse()
                .withBody(Gson.toJson(TEST_BEACON))))
    }

    @Override
    void doTest() {
        def beacon = CLIENT.getBeacon();
        assertThat(beacon).isEqualTo(TEST_BEACON);
    }
}
