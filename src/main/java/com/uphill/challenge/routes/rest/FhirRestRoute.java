package com.uphill.challenge.routes.rest;

import com.uphill.challenge.dto.ResponseDTO;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.BASE_REST_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.MEDIA_TYPE_FHIR_JSON_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.MESSAGE_REST_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.POST_FHIR_MESSAGES_ROUTE;

/**
 * Component for main FHIR Rest API
 */
@Component
public class FhirRestRoute extends RouteBuilder {

    /**
     * Defines the <class>FhirRestRoute</class> routes.
     */
    @Override
    public void configure() {
        configureApiDoc();
        postFhirMessagesRoute();
    }

    /**
     * Configures the openAPI doc with the servlet component.
     */
    private void configureApiDoc() {
        restConfiguration()
                .component("servlet")
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .contextPath("/camel")
                // turn on openapi api-doc
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Uphill Challenge API")
                .apiProperty("api.version", "1.0.0");
    }

    /**
     * A Rest route for the POST method of fhir messages.
     * Expects to receive an Encounter resource.
     */
    private void postFhirMessagesRoute() {
        rest(BASE_REST_ENDPOINT)
                .post(MESSAGE_REST_ENDPOINT)
                .description("Validates a MessageHeader Resource with a focus on an Encounter Resource")
                .consumes(MEDIA_TYPE_FHIR_JSON_CONST)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(ResponseDTO.class)
                .responseMessage("200", "Valid Encounter Resource").type(IBaseResource.class) //.type(MessageHeader.class) -> with conflicts on generating openAPI
                .responseMessage("401", "Unauthorized")
                .responseMessage("404", "Resource Not Found")
                .responseMessage("500", "Black magic")
                .param()
                    .name("body").type(RestParamType.body).description("Bundle Resource (Generic representation)")
                .endParam()
                .routeId(POST_FHIR_MESSAGES_ROUTE)
                .to(VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT);
    }

}
