package com.uphill.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * The request response DTO.
 */
@Data
@Builder
public class ResponseDTO {

    /**
     * Status of the request
     */
    @JsonProperty("status")
    private EStatus status;

    /**
     * Message status of the request.
     */
    @JsonProperty("message")
    private String message;

}
