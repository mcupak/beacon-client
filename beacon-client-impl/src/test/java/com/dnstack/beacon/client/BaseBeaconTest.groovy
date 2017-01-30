package com.dnstack.beacon.client

import com.dnastack.beacon.client.BeaconClientImpl
import com.github.tomakehurst.wiremock.WireMockServer
import org.apache.commons.lang.StringUtils
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

/**
 * Helper class for Beacon tests.
 * When the java property beacon.test.url is not specified, the Beacon server is mocked, otherwise tests
 * are run against the specified Beacon (usually a real one).
 * Not all tests might support integration testing against a real Beacon server - this is defined by
 * {@link com.dnstack.beacon.client.BaseBeaconTest#isIntegrationTestingSupported()}
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @author Miro Cupak (mirocupak@gmail.com)
 * @version 1.0
 */
public abstract class BaseBeaconTest {
    static final def MOCK_BEACON_PORT = 8089
    static final def MOCK_BEACON_SERVER = new WireMockServer(wireMockConfig().port(MOCK_BEACON_PORT))
    static final BeaconClientImpl CLIENT
    static final boolean MOCKED_TESTING

    /**
     * Define if the testing will be against real Beacon server, or the mocked one.
     */
    static {
        def beaconTestUrl = System.properties.getProperty("beacon.test.url")
        MOCKED_TESTING = StringUtils.isBlank(beaconTestUrl)
        CLIENT = MOCKED_TESTING ?
                new BeaconClientImpl(new URL("http", "localhost", MOCK_BEACON_PORT, "")) :
                new BeaconClientImpl(beaconTestUrl)

    }

    @BeforeSuite
    void startServer() {
        if (MOCKED_TESTING) {
            MOCK_BEACON_SERVER.start();
        }
    }

    @AfterSuite
    void stopServer() {
        if (MOCKED_TESTING) {
            MOCK_BEACON_SERVER.stop();
        }
    }

    @AfterMethod
    void resetMappings() {
        if (MOCKED_TESTING) {
            MOCK_BEACON_SERVER.resetMappings();
        }
    }

    @Test
    void test() {
        if (!MOCKED_TESTING && !isIntegrationTestingSupported()) {
            return
        }

        if (MOCKED_TESTING) {
            setupMappings()
        }
        doTest()
    }

    void setupMappings() {}

    boolean isIntegrationTestingSupported() { return true }

    abstract void doTest();
}
