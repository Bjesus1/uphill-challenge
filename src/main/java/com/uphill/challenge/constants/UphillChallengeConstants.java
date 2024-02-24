package com.uphill.challenge.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Definition of the service constants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UphillChallengeConstants {

    /**
     * The base direct constant.
     */
    private static final String BASE_DIRECT_CONST = "direct:";

    // Routes Endpoints

    /**
     * FHIR Rest endpoint (Tag)
     */
    public static final String BASE_REST_ENDPOINT = "/fhir";

    /**
     * Rest endpoint to be use on message validation.
     */
    public static final String MESSAGE_REST_ENDPOINT = "/message";

    /**
     * Rest endpoint to be use on encounter resources.
     */
    public static final String ENCOUNTER_REST_ENDPOINT = "/encounter";

    /**
     * Rest endpoint to be use on patient resources.
     */
    public static final String PATIENT_REST_ENDPOINT = "/patient";

    /**
     * Internal endpoint for the validate encounter resource.
     */
    public static final String VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "validateEncounterResourceEndpoint";

    /**
     * Internal endpoint for the search encounter resource.
     */
    public static final String SEARCH_ENCOUNTER_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "searchEncounterResourceEndpoint";

    /**
     * Internal endpoint for the search patient resource.
     */
    public static final String SEARCH_PATIENT_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "searchPatientResourceEndpoint";

    /**
     * Internal endpoint for the read patient resource.
     */
    public static final String READ_PATIENT_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "readPatientResourceEndpoint";

    // Route Ids

    /**
     * The POST FHIR messages route identification
     */
    public static final String POST_FHIR_MESSAGES_ROUTE = "postFhirMessagesRoute";

    /**
     * The GET encounter by identifier route identification.
     */
    public static final String GET_ENCOUNTER_BY_IDENTIFIER_VALUE_ROUTE = "getEncounterByIdentifierValue";

    /**
     * The GET patient by identifier value route identification.
     */
    public static final String GET_PATIENT_BY_IDENTIFIER_VALUE_ROUTE = "getPatientByIdentifierValueRoute";

    /**
     * The search encounter resource route identification.
     */
    public static final String SEARCH_ENCOUNTER_RESOURCE_ROUTE = "searchEncounterResourceRoute";

    /**
     * The search patient resource route identification.
     */
    public static final String SEARCH_PATIENT_RESOURCE_ROUTE = "searchPatientResourceRoute";

    /**
     * The read patient resource route identification.
     */
    public static final String READ_PATIENT_RESOURCE_ROUTE = "readPatientResourceRoute";

    /**
     * The validate encounter resource route identification.
     */
    public static final String VALIDATE_ENCOUNTER_RESOURCE_ROUTE = BASE_DIRECT_CONST + "validateEncounterResourceRoute";

    // Properties

    /**
     * The base property.
     */
    private static final String BASE_PROPERTY = "UphillChallenge_";

    /**
     * A property for the response.
     */
    public static final String RESPONSE_PROPERTY = BASE_PROPERTY + "response";

    // Constants

    /**
     * A param definition: id
     */
    public static final String ID_CONST = "id";

    /**
     * A param definition: identifierValue
     */
    public static final String IDENTIFIER_VALUE_CONST = "identifierValue";

    /**
     * The media type fhir+json
     */
    public static final String MEDIA_TYPE_FHIR_JSON_CONST = "application/fhir+json";

    /**
     * The server url constant for FHIR operations.
     */
    public static final String HAPI_URL_CONST = "https://fhir.hl7.pt";

    /**
     * The service type system constant.
     */
    public static final String SERVICE_TYPE_SYSTEM_CONST = "http://hl7.org/fhir/ValueSet/service-type";

}
