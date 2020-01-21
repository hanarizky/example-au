/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doku.bm.example.au.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ahmadnfirdaus
 */
@Getter
@Setter
public class SftpDownloadRequest extends MessageRequest {

    @NotBlank
    @ApiModelProperty(value = "SFTP Username", required = true, position = 4)
    private String username;

    @NotBlank
    @ApiModelProperty(value = "SFTP Password", required = true, position = 5)
    private String password;

    @NotBlank
    @ApiModelProperty(value = "SFTP Host", required = true, position = 6)
    private String host;

    @NotNull
    @ApiModelProperty(value = "SFTP Port", required = true, position = 7)
    private int port;

    @NotBlank
    @ApiModelProperty(value = "SFTP Full Path Remote", required = true, position = 8)
    private String remotePath;


}
