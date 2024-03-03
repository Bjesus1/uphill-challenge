package com.uphill.challenge.routes.internal;

import com.uphill.challenge.dto.EStatus;
import com.uphill.challenge.dto.ResponseDTO;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.HAPI_URL_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.READ_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.READ_RESOURCE_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_RESOURCE_ROUTE;

/**
 * Generic class for CRUD operations to the FHIR server
 */
@Component
public class FhirCrudRoute extends RouteBuilder {

    /**
     * The FHIR structure version to use.
     */
    @Value("${fhirVersion}")
    private String fhirVersion;

    /**
     * Defines the <class>FhirCrudRoute</class> routes.
     */
    @Override
    public void configure() {
        readResourceRoute();
        searchResourceByIdentifierValueRoute();
    }

    /**
     * Reads a resource by its ID from the fhir server.
     */
    private void readResourceRoute(){
        from(READ_RESOURCE_ENDPOINT)
                .routeId(READ_RESOURCE_ROUTE)
                .doTry()
                    .toD("fhir://read/resourceById" +
                            "?serverUrl=" + HAPI_URL_CONST + "/" + fhirVersion.toLowerCase() + "/fhir" +
                            "&resourceClass=${header.resourceType}&stringId=${header.id}" +
                            "&fhirVersion=" + fhirVersion)
                .endDoTry()
                .doCatch(Exception.class)
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("404"))
                    .setBody(exchange-> ResponseDTO.builder().status(EStatus.REFERENCE_NOT_FOUND).message(
                            exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class).getMessage()
                    ).build())
                .end();
    }

    /**
     * SEARCHes a resource by a provided identifier value.
     */
    private void searchResourceByIdentifierValueRoute() {
        from(SEARCH_RESOURCE_ENDPOINT)
                .routeId(SEARCH_RESOURCE_ROUTE)
                .toD("fhir://search/searchByUrl" +
                        "?serverUrl=" + HAPI_URL_CONST + "/" + fhirVersion.toLowerCase() + "/fhir" +
                        "&url=${header.resourceType}?identifier=${header.identifierSystem}|${header.identifierValue}" +
                        "&fhirVersion=" + fhirVersion)
                .marshal().fhirJson();
    }

}
