package com.uphill.challenge.validator;

import com.uphill.challenge.dto.EResourceType;
import com.uphill.challenge.dto.EStatus;
import com.uphill.challenge.dto.ResponseDTO;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Encounter;
import org.springframework.stereotype.Component;

import static com.uphill.challenge.constants.UphillChallengeConstants.SERVICE_TYPE_SYSTEM_CONST;

/**
 * A validator for the Encounter resource.
 */
@Component
public class EncounterValidator extends CommonValidator {

    /**
     * Handler method to validate the received Encounter resource.
     *
     * @param encounter the encounter resource
     * @return a <class>ResponseDTO</class> object to be returned to the client request if any validation fails. Otherwise,
     * a null is returned;
     */
    @Handler
    public ResponseDTO validateEncounter(@Body Encounter encounter) {

        if (!this.validateIdentifier(encounter.getIdentifier(), "urn:uh-encounter-id")) {
            return ResponseDTO.builder()
                    .status(EStatus.ERROR_ON_ENCOUNTER_FIELDS)
                    .message("Invalid identifier")
                    .build();
        }

        if (!encounter.hasStatus()) {
            return ResponseDTO.builder()
                    .status(EStatus.ERROR_ON_ENCOUNTER_FIELDS)
                    .message("Missing status")
                    .build();
        }

        if (!isEncounterServiceTypeValid(encounter)) {
            return ResponseDTO.builder()
                    .status(EStatus.ERROR_ON_ENCOUNTER_FIELDS)
                    .message("Invalid service type")
                    .build();
        }

        if (!encounter.getSubject().hasReference()
                || !encounter.getSubject().getReferenceElement_().getValue().contains("Patient")) {
            return ResponseDTO.builder()
                    .status(EStatus.ERROR_ON_ENCOUNTER_FIELDS)
                    .message("Invalid subject reference")
                    .build();
        }

        return null;
    }

    /**
     * Validates if the service type of the encounter resource is equal to
     * the <constant>SERVICE_TYPE_SYSTEM_CONST</constant> value.
     *
     * @param encounter the encounter resource
     * @return true if the service type is equal to the <constant>SERVICE_TYPE_SYSTEM_CONST</constant> value.
     * Otherwise, false.
     */
    private boolean isEncounterServiceTypeValid(Encounter encounter) {

        // Check if the Encounter contains a service type bound to the specified system
        boolean containsServiceType = false;
        for (Coding coding : encounter.getServiceType().getCoding()) {
            if (coding.hasSystemElement()
                    && SERVICE_TYPE_SYSTEM_CONST.equals(coding.getSystemElement().getValueAsString())
            ) {
                containsServiceType = true;
                break;
            }
        }

        return containsServiceType;
    }

    protected boolean isEncounterType(String resourceType) {
        return !StringUtils.isEmpty(resourceType) && resourceType.equals(EResourceType.ENCOUNTER.getName());
    }

}
