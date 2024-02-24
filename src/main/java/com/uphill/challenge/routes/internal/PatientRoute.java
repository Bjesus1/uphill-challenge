package com.uphill.challenge.routes.internal;

import com.uphill.challenge.dto.EStatus;
import com.uphill.challenge.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.HAPI_URL_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.READ_PATIENT_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.READ_PATIENT_RESOURCE_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_PATIENT_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_PATIENT_RESOURCE_ROUTE;

/**
 * Component for Patient internal routes
 */
@RequiredArgsConstructor
@Component
public class PatientRoute extends RouteBuilder {

    /**
     * The FHIR structure version to use.
     */
    @Value("${fhirVersion}")
    private String fhirVersion;

    /**
     * Defines the <class>PatientRoute</class> routes.
     */
    @Override
    public void configure() {
        searchPatientResourceRoute();
        readPatientResourceRoute();
    }

    /**
     * SEARCHes the Patient resource by a provided identifier value.
     */
    private void searchPatientResourceRoute() {
        from(SEARCH_PATIENT_RESOURCE_ENDPOINT)
                .routeId(SEARCH_PATIENT_RESOURCE_ROUTE)
                .toD("fhir://search/searchByUrl" +
                        "?serverUrl=" + HAPI_URL_CONST + "/" + fhirVersion.toLowerCase() + "/fhir" +
                        "&url=Patient?identifier=urn:uh-patient-id|${header.identifierValue}" +
                        "&fhirVersion=" + fhirVersion)
                .marshal().fhirJson();
    }

    /**
     * READs the Patient resource by a provided id.
     */
    private void readPatientResourceRoute() {
        from(READ_PATIENT_RESOURCE_ENDPOINT)
                .routeId(READ_PATIENT_RESOURCE_ROUTE)
                .doTry()
                    .toD("fhir://read/resourceById" +
                            "?serverUrl=" + HAPI_URL_CONST + "/" + fhirVersion.toLowerCase() + "/fhir" +
                            "&resourceClass=Patient&stringId=${header.id}" +
                            "&fhirVersion=" + fhirVersion)
                .endDoTry()
                .doCatch(Exception.class)
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("404"))
                    .setBody(exchange-> ResponseDTO.builder().status(EStatus.REFERENCE_NOT_FOUND).message(
                            exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class).getMessage()
                    ).build())
                .end();
    }

}
