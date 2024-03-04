package com.uphill.challenge.utils;

import com.uphill.challenge.dto.EResourceType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Utils class for resource operations
 */
@Component
public class ResourceUtils {

    /**
     * Compares the two received arguments representing the resource names.
     *
     * @param resourceType the received resource type
     * @param optionToCompare the resource type to compare with.
     * @return true if the received arguments are invalid or if the resource types are not equal. Otherwise, true.
     */
    protected boolean isNotResourceType(String resourceType, EResourceType optionToCompare) {
        if (StringUtils.isEmpty(resourceType) || resourceType == null ) {
            return true;
        }
        return !resourceType.equalsIgnoreCase(optionToCompare.getName());
    }

}
