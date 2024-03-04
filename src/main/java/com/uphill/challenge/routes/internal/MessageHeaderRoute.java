package com.uphill.challenge.routes.internal;

import com.uphill.challenge.dto.EResourceType;
import com.uphill.challenge.dto.EStatus;
import com.uphill.challenge.dto.ResponseDTO;
import com.uphill.challenge.utils.ResourceUtils;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.CHECK_RECEIVED_MESSAGE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.CHECK_RECEIVED_MESSAGE_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.ERROR_FLAG_PROPERTY;
import static com.uphill.challenge.constants.UphillChallengeConstants.MESSAGE_HEADER_ORCHESTRATION_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.MESSAGE_HEADER_ORCHESTRATION_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.VALIDATE_PATIENT_RESOURCE_ENDPOINT;

@Component
public class MessageHeaderRoute extends RouteBuilder {

    /**
     * Defines the <class>MessageHeaderRoute</class> routes.
     */
    @Override
    public void configure() {
        messageHeaderOrchestrator();
        checkReceivedMessage();
    }

    /**
     * Message header orchestration route.
     */
    private void messageHeaderOrchestrator() {
        from(MESSAGE_HEADER_ORCHESTRATION_ENDPOINT)
                .routeId(MESSAGE_HEADER_ORCHESTRATION_ROUTE)

                .doTry()
                    .unmarshal().fhirJson() // Unmarshal the incoming
                .endDoTry()
                .doCatch(Exception.class)
                    .setBody(exchange-> ResponseDTO.builder().status(EStatus.UNSUPPORTED_MESSAGE)
                            .message("Unsupported Message").build())
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end()

                // Check message
                .to(CHECK_RECEIVED_MESSAGE_ENDPOINT)
                .filter(exchangeProperty(ERROR_FLAG_PROPERTY).isEqualTo(true))
                    .stop()
                .end()

                // Time for the encounter resource
                .to(VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT)
                .filter(exchangeProperty(ERROR_FLAG_PROPERTY).isEqualTo(true))
                .stop()
                .end()

                // Time for the patient resource
                .to(VALIDATE_PATIENT_RESOURCE_ENDPOINT)
                .filter(exchangeProperty(ERROR_FLAG_PROPERTY).isEqualTo(true))
                .stop()
                .end()

                // Answer the phone!
                .setBody(exchange -> ResponseDTO.builder().status(EStatus.OK).message("Looking Good!").build())
                .marshal().json(JsonLibrary.Jackson);

    }

    /**
     * Checks if the received bundle complies with the criteria of exercise 1 .
     */
    private void checkReceivedMessage() {
        from(CHECK_RECEIVED_MESSAGE_ENDPOINT)
                .routeId(CHECK_RECEIVED_MESSAGE_ROUTE)

                // Received message resource type check
                .filter().method(ResourceUtils.class, "isNotResourceType(${body.resourceType}," + EResourceType.BUNDLE + ")")
                    .log("Expecting bundle resource")
                    .setProperty(ERROR_FLAG_PROPERTY, simple("true"))
                    .setBody(exchange-> ResponseDTO.builder().status(EStatus.INVALID_RESOURCE_TYPE)
                            .message("Unexpected Resource Type").build())
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end()

                .setBody().simple("${body.entry[0].getResource()}")

                // Entry resource type check
                .filter().method(ResourceUtils.class, "isNotResourceType(${body.resourceType}," + EResourceType.MESSAGE_HEADER + ")")
                    .log("Expecting MessageHeader resource as entry")
                    .setProperty(ERROR_FLAG_PROPERTY, simple("true"))
                    .setBody(exchange-> ResponseDTO.builder().status(EStatus.INVALID_RESOURCE_TYPE)
                            .message("Unexpected Resource Type as entry").build())
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end()

                // Focus check
                .filter(simple("${body.focus[0].reference} not contains 'Encounter/'"))
                    .log("Payload focus is not Encounter")
                    .setProperty(ERROR_FLAG_PROPERTY, simple("true"))
                    .setBody(exchange-> ResponseDTO.builder().status(EStatus.INVALID_FOCUS_TYPE)
                            .message("Unsupported Focus Type").build())
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end();
    }

}
