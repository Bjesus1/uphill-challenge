# Uphill Challenge

My uphill challenge

## Documentation

**UC1**: As a client, I want to validate Encounter Resources according to uphill challenge requirements

**UC2**: As a client, I want to search for Encounter resources by the identifier value

**UC3**: As a client, I want to search for Patient resources by the identifier value

### Assumptions

* Using [HAPI](https://fhir.hl7.pt/r4/fhir) -> Info is persisted and can be accessed after any restart 
* To access OpenApi after running: http://localhost:15550/camel/api-doc/openapi.yaml
* Copy the above yaml to https://editor-next.swagger.io
* What is the contact interpretation required? I assumed that at least one should exist.
* Authorization as Basic Auth with admin:admin, for test purpose (OAuth was deprecated and replaced by Spring security, which I'm not so aware of its usage and development). So, as a future approach, would be to implement it and use bearer tokens

### Known problems

* Apache Camel FHIR dependency allows the use of the same resource class with different versions. However, there isn't a generic class per resource, which results (at the moment) the support of different FHIR versions on UC1.
* There are some conflicts between FHIR object classes and OpenApi when defining the input type on rest routes.
* This challenge was developed in a computer with group policies enforced. Thus, I'm not able to validate the dockerfile (Program 'docker.exe' failed to run: This program is blocked by group policy. For more information, contact your system administrator)
* Although the FHIR Component supports multiple versions (as seen in the routes with the option fhirVersion), there are some issues. First, on the /fhir/message, to validate some parameters, the class is necessary and there isn't a common one (R4 belongs to one library, R5 to another, etc.). To overcome this, we could use the fhirVersion and reflection, so that we could have the desired class according to the version. Another issue lies on the un/marshal().fhirJson(), which gives an exception that it only supports R4 parse (need further investigation on how to overcome this). **Nevertheless**, the fhir component is being set accordingly to the specified fhirVersion at application.yml (see more at CamelConfig class)

### Requirements

> - Java = 17 version
> - Apache Camel = 4.4.0 version
> - Spring Boot = 3.2.2 version
> - [Git](https://git-scm.com/downloads) >= 2.16.2 version
> - [Maven](https://maven.apache.org/download.cgi) >= 3.5.2 version
> - Windows

### Contacts

- Bruno Silva