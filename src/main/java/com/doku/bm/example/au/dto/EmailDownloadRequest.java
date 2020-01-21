package com.doku.bm.example.au.dto;

import io.swagger.annotations.ApiModelProperty;
        import lombok.Getter;
        import lombok.Setter;

/**
 *
 * @author ahmadnfirdaus
 */
@Getter
@Setter
public class EmailDownloadRequest extends MessageRequest {

    @ApiModelProperty(value = "Host", required = true)
    private String host;

    @ApiModelProperty(value = "Port", required = true)
    private Integer port;

    @ApiModelProperty(value = "Username", required = true)
    private String username;

    @ApiModelProperty(value = "Password", required = true)
    private String password;

    @ApiModelProperty(value = "Email Label Name", required = true)
    private String labelName;
}