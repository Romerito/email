package br.com.email.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author  Romerito Alencar
 */

@Data
public class EmailDTO {
    
    @NotBlank
    private String ownerRef;
    @NotBlank
    @Email
    private String emailFrom;
    @NotBlank
    @Email
    private String emailTo;
    @NotBlank
    private String subject;
    @NotBlank
    private String text;
}
