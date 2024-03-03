package com.uphill.challenge.routes.internal;

import com.uphill.challenge.dto.EResourceType;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.IDENTIFIER_SYSTEM_HEADER;
import static com.uphill.challenge.constants.UphillChallengeConstants.RESOURCE_TYPE_HEADER;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_PATIENT_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_PATIENT_RESOURCE_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_RESOURCE_ENDPOINT;

/**
 * Component for Patient internal routes
 */
@RequiredArgsConstructor
@Component
public class PatientRoute extends RouteBuilder {

    /**
     * Defines the <class>PatientRoute</class> routes.
     */
    @Override
    public void configure() {
        searchPatientResourceRoute();
    }

    /**
     * SEARCHes the Patient resource by a provided identifier value.
     */
    private void searchPatientResourceRoute() {
        from(SEARCH_PATIENT_RESOURCE_ENDPOINT)
                .routeId(SEARCH_PATIENT_RESOURCE_ROUTE)
                .setHeader(RESOURCE_TYPE_HEADER, simple(EResourceType.PATIENT.getName()))
                .setHeader(IDENTIFIER_SYSTEM_HEADER, constant("urn:uh-patient-id"))
                .to(SEARCH_RESOURCE_ENDPOINT);
    }

}
