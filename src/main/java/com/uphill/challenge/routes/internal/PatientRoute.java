package com.uphill.challenge.routes.internal;

import com.uphill.challenge.dto.EResourceType;
import com.uphill.challenge.dto.ResponseDTO;
import com.uphill.challenge.utils.SplitUtils;
import com.uphill.challenge.validator.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.ERROR_FLAG_PROPERTY;
import static com.uphill.challenge.constants.UphillChallengeConstants.IDENTIFIER_SYSTEM_HEADER;
import static com.uphill.challenge.constants.UphillChallengeConstants.ID_HEADER;
import static com.uphill.challenge.constants.UphillChallengeConstants.READ_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.RESOURCE_TYPE_HEADER;
import static com.uphill.challenge.constants.UphillChallengeConstants.RESPONSE_PROPERTY;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_PATIENT_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_PATIENT_RESOURCE_ROUTE;
import static com.uphill.challenge.constants.UphillChallengeConstants.SEARCH_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.SPLIT_RESOURCE_ID_METHOD_NAME_CONST;
import static com.uphill.challenge.constants.UphillChallengeConstants.VALIDATE_PATIENT_RESOURCE_ENDPOINT;
import static com.uphill.challenge.constants.UphillChallengeConstants.VALIDATE_PATIENT_RESOURCE_ROUTE;

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
        validatePatientResourceRoute();
        searchPatientResourceRoute();
    }

    /**
     * Route to validate if a patient reference exists and if it is valid according to the validations
     * specified in the challenge.
     */
    private void validatePatientResourceRoute() {
        from(VALIDATE_PATIENT_RESOURCE_ENDPOINT)
                .routeId(VALIDATE_PATIENT_RESOURCE_ROUTE)
                // Get the patient resource by ID
                .setHeader(ID_HEADER).method(SplitUtils.class, SPLIT_RESOURCE_ID_METHOD_NAME_CONST + "(${body.subject.reference})")
                .setHeader(RESOURCE_TYPE_HEADER, simple(EResourceType.PATIENT.getName()))
                .to(READ_RESOURCE_ENDPOINT)
                .filter(simple("${body.getClass()} == '" + ResponseDTO.class.getName() + "'"))
                    .setProperty(ERROR_FLAG_PROPERTY, simple("true"))
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end()

                // Validate patient resource
                .setProperty(RESPONSE_PROPERTY, method(PatientValidator.class))
                .filter(exchangeProperty(RESPONSE_PROPERTY).isNotNull())
                    .setProperty(ERROR_FLAG_PROPERTY, simple("true"))
                    .setBody(exchangeProperty(RESPONSE_PROPERTY))
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end();
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
