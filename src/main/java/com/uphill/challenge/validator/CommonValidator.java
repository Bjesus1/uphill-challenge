package com.uphill.challenge.validator;

import org.hl7.fhir.r4.model.Identifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A common validator for all resources.
 */
@Component
public abstract class CommonValidator {

    /**
     * Validates the existence of at least one identifier and if its system is equal to the one received.
     *
     * @param identifiers a list of identifiers
     * @param system      the system to compare
     * @return true if there is one identifier with the received system. Otherwise, false.
     */
    protected boolean validateIdentifier(List<Identifier> identifiers, String system) {
        boolean isValid;

        isValid = !(identifiers == null || identifiers.size() == 0);
        for (Identifier identifier : identifiers) {
            if (identifier.hasSystem()
                    && identifier.getSystem().equals(system)
                    && identifier.hasValue()) {
                isValid = true;
                break;
            }
        }

        return isValid;
    }

}
