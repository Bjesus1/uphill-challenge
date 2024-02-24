package com.uphill.challenge.routes.internal;

import com.uphill.challenge.dto.EStatus;
import com.uphill.challenge.dto.ResponseDTO;
import com.uphill.challenge.validator.EncounterValidator;
import com.uphill.challenge.validator.PatientValidator;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.hl7.fhir.r4.model.Encounter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.HAPI_URL_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.ID_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.READ_PATIENT_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.RESPONSE_PROPERTY;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_ENCOUNTER_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_ENCOUNTER_RESOURCE_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.VALIDATE_ENCOUNTER_RESOURCE_ROUTE;

/**
 * Component for Encounter internal routes
 */
@Component
public class EncounterRoute extends RouteBuilder {

    /**
     * The FHIR structure version to use.
     */
    @Value("${fhirVersion}")
    private String fhirVersion;

    /**
     * Defines the <class>EncounterRoute</class> routes.
     */
    @Override
    public void configure() {
        validateEncounterResourceRoute();
        searchEncounterResourceRoute();
    }

    /**
     * Route to validate if the received fhir message is an Encounter resource, along with some extra validations
     * specified in the challenge.
     */
    private void validateEncounterResourceRoute() {
        from(VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT)
                .routeId(VALIDATE_ENCOUNTER_RESOURCE_ROUTE)
                // Check resource type
                .doTry()
                    .unmarshal().fhirJson() // Unmarshal the incoming message
                    .filter(simple("${body.getClass()} != '" + Encounter.class.getName() + "'"))
                        .log("Payload is not of type Encounter")
                        .setBody(exchange-> ResponseDTO.builder().status(EStatus.INVALID_RESOURCE_TYPE).message("Unsupported Resource Type").build())
                        .marshal().json(JsonLibrary.Jackson)
                        .stop()
                    .end()
                .endDoTry()
                .doCatch(Exception.class)
                    .setBody(exchange-> ResponseDTO.builder().status(EStatus.INVALID_RESOURCE_TYPE).message("Unsupported Resource Type").build())
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end()

                // Validate encounter resource
                .setProperty(RESPONSE_PROPERTY, method(EncounterValidator.class))
                .filter(exchangeProperty(RESPONSE_PROPERTY).isNotNull())
                    .setBody(exchangeProperty(RESPONSE_PROPERTY))
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end()

                // Get the patient resource by ID
                .process(exchange -> exchange.getIn().setHeader(ID_CONST, exchange.getIn().getBody(Encounter.class).getSubject().getReferenceElement_().getValue().split("/")[1]))
                .to(READ_PATIENT_RESOURCE_ENDPOINT)
                .filter(simple("${body.getClass()} == '" + ResponseDTO.class.getName() + "'"))
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end()

                // Validate patient resource
                .setProperty(RESPONSE_PROPERTY, method(PatientValidator.class))
                .filter(exchangeProperty(RESPONSE_PROPERTY).isNotNull())
                    .setBody(exchangeProperty(RESPONSE_PROPERTY))
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end()

                // Answer the phone!
                .setBody(exchange -> ResponseDTO.builder().status(EStatus.OK).message("Looking Good!").build())
                .marshal().json(JsonLibrary.Jackson);
    }

    /**
     * SEARCHes the Encounter resources by an identifier value.
     */
    private void searchEncounterResourceRoute() {
        from(SEARCH_ENCOUNTER_RESOURCE_ENDPOINT)
                .routeId(SEARCH_ENCOUNTER_RESOURCE_ROUTE)
                .toD("fhir://search/searchByUrl?" +
                        "serverUrl=" + HAPI_URL_CONST + "/" + fhirVersion.toLowerCase() + "/fhir" +
                        "&url=Encounter?identifier=urn:uh-encounter-id|${header.identifierValue}" +
                        "&fhirVersion=" + fhirVersion)
                .marshal().fhirJson();
    }

}
