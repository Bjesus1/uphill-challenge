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
    public static final String MESSAGE_REST_ENDPOINT = "/MessageHeader";

    /**
     * Rest endpoint to be use on encounter resources.
     */
    public static final String ENCOUNTER_REST_ENDPOINT = "/Encounter";

    /**
     * Rest endpoint to be use on patient resources.
     */
    public static final String PATIENT_REST_ENDPOINT = "/Patient";

    /**
     * Internal endpoint to validate an encounter resource.
     */
    public static final String VALIDATE_ENCOUNTER_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "validateEncounterResourceEndpoint";


    /**
     * Internal endpoint to validate a patient resource.
     */
    public static final String VALIDATE_PATIENT_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "validatePatientResourceEndpoint";

    /**
     * Internal endpoint to validate the received message.
     */
    public static final String CHECK_RECEIVED_MESSAGE_ENDPOINT = BASE_DIRECT_CONST + "checkReceivedMessageEndpoint";

    /**
     * Internal endpoint to the received message.
     */
    public static final String MESSAGE_HEADER_ORCHESTRATION_ENDPOINT = BASE_DIRECT_CONST + "messageHeaderOrchestratorEndpoint";

    /**
     * Internal endpoint for the search encounter resource.
     */
    public static final String SEARCH_ENCOUNTER_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "searchEncounterResourceEndpoint";

    /**
     * Internal endpoint for the search patient resource.
     */
    public static final String SEARCH_PATIENT_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "searchPatientResourceEndpoint";

    /**
     * Internal endpoint for the search of a resource.
     */
    public static final String SEARCH_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "searchResourceEndpoint";

    /**
     * Internal endpoint for reading a resource.
     */
    public static final String READ_RESOURCE_ENDPOINT = BASE_DIRECT_CONST + "readResourceEndpoint";

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
     * The search resource route identification.
     */
    public static final String SEARCH_RESOURCE_ROUTE = "searchResourceRoute";

    /**
     * The read resource route identification.
     */
    public static final String READ_RESOURCE_ROUTE = "readResourceRoute";

    /**
     * The validate encounter resource route identification.
     */
    public static final String VALIDATE_ENCOUNTER_RESOURCE_ROUTE = "validateEncounterResourceRoute";

    /**
     * The validate patient resource route identification.
     */
    public static final String VALIDATE_PATIENT_RESOURCE_ROUTE = "validatePatientResourceRoute";


    /**
     * The check received message route identification.
     */
    public static final String CHECK_RECEIVED_MESSAGE_ROUTE = "checkReceivedMessageRoute";

    /**
     * The message header orchestration route.
     */
    public static final String MESSAGE_HEADER_ORCHESTRATION_ROUTE = "messageHeaderOrchestrationRoute";

    // Properties

    /**
     * The base property.
     */
    private static final String BASE_PROPERTY = "UphillChallenge_";

    /**
     * A property for the response.
     */
    public static final String RESPONSE_PROPERTY = BASE_PROPERTY + "response";

    /**
     * A property for an error flag
     */
    public static final String ERROR_FLAG_PROPERTY = BASE_PROPERTY + "errorFlag";

    // Headers

    /**
     * A header of the exchange: id
     */
    public static final String ID_HEADER = "id";

    /**
     * A header of the exchange: resourceType
     */
    public static final String RESOURCE_TYPE_HEADER = "resourceType";

    /**
     * A header of the exchange: identifierSystem
     */
    public static final String IDENTIFIER_SYSTEM_HEADER = "identifierSystem";


    // Constants

    /**
     * A param definition: identifierValue
     */
    public static final String IDENTIFIER_VALUE_CONST = "identifierValue";

    /**
     * The media type fhir+json
     */
    public static final String MEDIA_TYPE_FHIR_JSON_CONST = "application/fhir+json";

    /**
     * A constant identifying the splitReferenceId method name.
     */
    public static final String SPLIT_RESOURCE_ID_METHOD_NAME_CONST = "splitReferenceId";

    /**
     * The server url constant for FHIR operations.
     */
    public static final String HAPI_URL_CONST = "https://fhir.hl7.pt";

    /**
     * The service type system constant.
     */
    public static final String SERVICE_TYPE_SYSTEM_CONST = "http://hl7.org/fhir/ValueSet/service-type";

}
