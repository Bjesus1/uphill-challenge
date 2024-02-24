package com.uphill.challenge.routes.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.BASE_REST_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.GET_PATIENT_BY_IDENTIFIER_VALUE_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.IDENTIFIER_VALUE_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.MEDIA_TYPE_FHIR_JSON_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.PATIENT_REST_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_PATIENT_RESOURCE_ENDPOINT;

/**
 * Component for Patient FHIR Rest APIs
 */
@Component
public class PatientRestRoute extends RouteBuilder {

    /**
     * Defines the <class>PatientRestRoute</class> routes.
     */
    @Override
    public void configure() {
        getPatientByIdentifierValue();
    }

    /**
     * GETs the Patient resource by the provided identifier value.
     */
    private void getPatientByIdentifierValue() {
        rest(BASE_REST_ENDPOINT)
                .get(PATIENT_REST_ENDPOINT)
                .description("Searches for Patient Resources with the received identifier value")
                .consumes(MEDIA_TYPE_FHIR_JSON_CONST)
                .produces(MEDIA_TYPE_FHIR_JSON_CONST)
                //.outType(Bundle.class)
                .responseMessage("200", "Request Ok")
                .responseMessage("401", "Unauthorized")
                .responseMessage("500", "Black magic")
                .param()
                    .name(IDENTIFIER_VALUE_CONST).type(RestParamType.query).description("Patient identifier value")
                .endParam()
                .routeId(GET_PATIENT_BY_IDENTIFIER_VALUE_ROUTE)
                .to(SEARCH_PATIENT_RESOURCE_ENDPOINT);
    }
}
