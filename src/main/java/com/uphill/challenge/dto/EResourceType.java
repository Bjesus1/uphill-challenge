package com.uphill.challenge.dto;

/**
 * The resource types enum
 */
public enum EResourceType {

    BUNDLE("Bundle"),
    MESSAGE_HEADER("MessageHeader"),
    ENCOUNTER("Encounter"),
    PATIENT("Patient");

    /**
     * The name of the enum option.
     */
    private final String name;

    /**
     * Builds a new instance of the present class.
     * @param name the name of the enum option.
     */
    EResourceType(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the enum option.
     *
     * @return the enum option name.
     */
    public String getName() {
        return name;
    }

}
