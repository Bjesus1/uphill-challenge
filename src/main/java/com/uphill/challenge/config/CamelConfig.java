package com.uphill.challenge.config;

import ca.uhn.fhir.context.FhirContext;
import org.apache.camel.CamelContext;
import org.apache.camel.component.fhir.FhirComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Camel Context.
 */
@Configuration
public class CamelConfig implements CamelContextConfiguration {

    /**
     * The FHIR structure version to use.
     */
    @Value("${fhirVersion}")
    private String fhirVersion;

    /**
     * Additional configs to be made before the application launch.
     *
     * @param camelContext the application camel context
     */
    @Override
    public void beforeApplicationStart(CamelContext camelContext) {
        setFhirComponentVersion((FhirComponent)(camelContext.getComponent("fhir")));
    }

    /**
     * Additional configs to be made after the application launch.
     *
     * @param camelContext the application camel context
     */
    @Override
    public void afterApplicationStart(CamelContext camelContext) {
    }

    /**
     * Changes the FHIR Component configuration. Namely, the context and version for the component to use.
     *
     * @param fhirComponent the Apache Camel FHIR Component
     */
    private void setFhirComponentVersion(FhirComponent fhirComponent) {
        switch (fhirVersion){
            case "DSTU2":
                fhirComponent.getConfiguration().setFhirContext(FhirContext.forDstu2());
                fhirComponent.getConfiguration().setFhirVersion("DSTU2");
                break;
            case "DSTU2_HL7ORG":
                fhirComponent.getConfiguration().setFhirContext(FhirContext.forDstu2Hl7Org());
                fhirComponent.getConfiguration().setFhirVersion("DSTU2_HL7ORG");
                break;
            case "DSTU2_1":
                fhirComponent.getConfiguration().setFhirContext(FhirContext.forDstu2_1());
                fhirComponent.getConfiguration().setFhirVersion("DSTU2_1");
                break;
            case "DSTU3":
                fhirComponent.getConfiguration().setFhirContext(FhirContext.forDstu3());
                fhirComponent.getConfiguration().setFhirVersion("DSTU3");
                break;
            case "R4B":
                fhirComponent.getConfiguration().setFhirContext(FhirContext.forR4B());
                fhirComponent.getConfiguration().setFhirVersion("R4B");
                break;
            case "R5":
                fhirComponent.getConfiguration().setFhirContext(FhirContext.forR5());
                fhirComponent.getConfiguration().setFhirVersion("R5");
                break;
            default:
                // do nothing because default is already R4
        }
    }
}


