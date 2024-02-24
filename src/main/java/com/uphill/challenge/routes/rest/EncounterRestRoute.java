package com.uphill.challenge.routes.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.BASE_REST_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.ENCOUNTER_REST_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.GET_ENCOUNTER_BY_IDENTIFIER_VALUE_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.IDENTIFIER_VALUE_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.MEDIA_TYPE_FHIR_JSON_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_ENCOUNTER_RESOURCE_ENDPOINT;

/**
 * Component for Encounter FHIR Rest APIs
 */
@Component
public class EncounterRestRoute extends RouteBuilder {

    /**
     * Defines the <class>EncounterRestRoute</class> routes.
     */
    @Override
    public void configure() {
        getEncounterByIdentifierValue();
    }

    /**
     * GETs the Encounter resource by the provided identifier value.
     */
    private void getEncounterByIdentifierValue() {
        rest(BASE_REST_ENDPOINT)
                .get(ENCOUNTER_REST_ENDPOINT)
                .description("Searches for Encounter Resources with the received identifier value")
                .consumes(MEDIA_TYPE_FHIR_JSON_CONST)
                .produces(MEDIA_TYPE_FHIR_JSON_CONST)
                //.outType(Bundle.class)
                .responseMessage("200", "Request Ok")
                .responseMessage("401", "Unauthorized")
                .responseMessage("500", "Black magic")
                .param()
                    .name(IDENTIFIER_VALUE_CONST).type(RestParamType.query).description("Encounter identifier value")
                .endParam()
                .routeId(GET_ENCOUNTER_BY_IDENTIFIER_VALUE_ROUTE)
                .to(SEARCH_ENCOUNTER_RESOURCE_ENDPOINT);
    }

}
