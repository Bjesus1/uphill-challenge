package com.uphill.challenge.utils;

import org.springframework.stereotype.Component;

/**
 * Helper class for split operations.
 */
@Component
public class SplitUtils {

    /**
     * Returns the reference id of the resource
     *
     * @param reference the reference of a resource
     * @return the id of the resource
     */
    protected String splitReferenceId(String reference) {
        return reference.split("/")[1];
    }

}
