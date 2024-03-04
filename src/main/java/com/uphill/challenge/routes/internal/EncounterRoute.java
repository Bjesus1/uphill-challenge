package com.uphill.challenge.routes.internal;

import com.uphill.challenge.dto.EResourceType;
import com.uphill.challenge.dto.ResponseDTO;
import com.uphill.challenge.utils.SplitUtils;
import com.uphill.challenge.validator.EncounterValidator;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.ERROR_FLAG_PROPERTY;
import static com.uphill.challenge.constants.UphillChallengeConstants.IDENTIFIER_SYSTEM_HEADER;
import static com.uphill.challenge.constants.UphillChallengeConstants.ID_HEADER;
import static com.uphill.challenge.constants.UphillChallengeConstants.READ_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.RESOURCE_TYPE_HEADER;
import static com.uphill.challenge.constants.UphillChallengeConstants.RESPONSE_PROPERTY;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_ENCOUNTER_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_ENCOUNTER_RESOURCE_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.SPLIT_RESOURCE_ID_METHOD_NAME_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.VALIDATE_ENCOUNTER_RESOURCE_ROUTE;

/**
 * Component for Encounter internal routes
 */
@Component
public class EncounterRoute extends RouteBuilder {

    /**
     * Defines the <class>EncounterRoute</class> routes.
     */
    @Override
    public void configure() {
        validateEncounterResourceRoute();
        searchEncounterResourceRoute();
    }

    /**
     * Route to validate if an encounter reference exists and if it is valid according to the validations
     * specified in the challenge.
     */
    private void validateEncounterResourceRoute() {
        from(VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT)
                .routeId(VALIDATE_ENCOUNTER_RESOURCE_ROUTE)
                // Get the encounter resource by ID
                .setHeader(ID_HEADER).method(
                        SplitUtils.class,
                        SPLIT_RESOURCE_ID_METHOD_NAME_CONST + "(${body.focus[0].reference})"
                )
                .setHeader(RESOURCE_TYPE_HEADER, simple(EResourceType.ENCOUNTER.getName()))
                .to(READ_RESOURCE_ENDPOINT)
                .filter(simple("${body.getClass()} == '" + ResponseDTO.class.getName() + "'"))
                    .marshal().json(JsonLibrary.Jackson)
                    .setProperty(ERROR_FLAG_PROPERTY, simple("true"))
                    .stop()
                .end()

                // Validate encounter resource
                .setProperty(RESPONSE_PROPERTY, method(EncounterValidator.class))
                .filter(exchangeProperty(RESPONSE_PROPERTY).isNotNull())
                    .setBody(exchangeProperty(RESPONSE_PROPERTY))
                    .marshal().json(JsonLibrary.Jackson)
                    .setProperty(ERROR_FLAG_PROPERTY, simple("true"))
                    .stop()
                .end();
    }

    /**
     * SEARCHes the Encounter resources by an identifier value.
     */
    private void searchEncounterResourceRoute() {
        from(SEARCH_ENCOUNTER_RESOURCE_ENDPOINT)
                .routeId(SEARCH_ENCOUNTER_RESOURCE_ROUTE)
                .setHeader(RESOURCE_TYPE_HEADER, simple(EResourceType.ENCOUNTER.getName()))
                .setHeader(IDENTIFIER_SYSTEM_HEADER, constant("urn:uh-encounter-id"))
                .to(SEARCH_RESOURCE_ENDPOINT);
    }


}
