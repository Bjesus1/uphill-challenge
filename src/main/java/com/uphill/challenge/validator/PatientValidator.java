package com.uphill.challenge.validator;

import com.uphill.challenge.dto.EStatus;
import com.uphill.challenge.dto.ResponseDTO;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

/**
 * A validator for the Patient resource.
 */
@Component
public class PatientValidator extends CommonValidator {

    /**
     * Handler method to validate the received Patient resource.
     *
     * @param patient the patient resource
     * @return a <class>ResponseDTO</class> object to be returned to the client request if any validation fails. Otherwise,
     * a null is returned;
     */
    @Handler
    public ResponseDTO validatePatient(@Body Patient patient) {

        if (!this.validateIdentifier(patient.getIdentifier(), "urn:uh-patient-id")) {
            return ResponseDTO.builder()
                    .status(EStatus.ERROR_ON_ENCOUNTER_FIELDS)
                    .message("Invalid identifier")
                    .build();
        }

        if (!patient.hasName()) {
            return ResponseDTO.builder()
                    .status(EStatus.ERROR_ON_ENCOUNTER_FIELDS)
                    .message("Missing name")
                    .build();
        }

        // NOTE: What is the contact interpretation required?
        if (!patient.hasContact() && !patient.hasTelecom() && !patient.hasCommunication()) {
            return ResponseDTO.builder()
                    .status(EStatus.ERROR_ON_ENCOUNTER_FIELDS)
                    .message("Missing contacts")
                    .build();
        }

        if (!patient.hasGender()) {
            return ResponseDTO.builder()
                    .status(EStatus.ERROR_ON_ENCOUNTER_FIELDS)
                    .message("Missing gender")
                    .build();
        }

        if (!patient.hasBirthDate()) {
            return ResponseDTO.builder()
                    .status(EStatus.ERROR_ON_ENCOUNTER_FIELDS)
                    .message("Missing birthdate")
                    .build();
        }

        return null;
    }

}
