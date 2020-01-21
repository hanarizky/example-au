package com.doku.bm.example.au.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The type Message request.
 */
@Getter
@Setter
public class MessageRequest {

    /**
     * it's similar with uniqueId on GoInet-V1
     */
    @NotBlank
    @Size(min = 1, max = 256)
    @ApiModelProperty(value = "Unique request id", example = "Request-0001", required = true, position = 1)
    private String requestId;

    /**
     * it's similar with appIdentity on GoInet-V1
     */
    @NotBlank
    @ApiModelProperty(value = "Client Id", example = "OCO", required = true, position = 2)
    private String clientId;

    /**
     * Message to be send
     */
    @ApiModelProperty(value = "Message to be send is query params as UTF-8 encoded string", example = "paramA=valueA&paramB=valueB", position = 3)
    private String message;

    /**
     * Save to Local Path
     */
    @ApiModelProperty(value = "Local Path to Save Files", position = 9)
    private String localPath;

}
