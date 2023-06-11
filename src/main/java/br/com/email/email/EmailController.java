package br.com.email.email;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.email.dtos.EmailDTO;
import br.com.email.responses.EmailResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author  Romerito Alencar
 */

@Validated
@RestController
@RequestMapping("/api") 
@Api(value = "Rest API Email")
@CrossOrigin(origins = "*")
public class EmailController {

    private static final Logger LOGGER = LogManager.getLogger(EmailController.class);
    
    @Autowired
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * @param EmailDTO
     * @return EmailResponse
    */
    @ApiOperation(value = "Salvar email")
        @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Email criado", response = Email.class),
        @ApiResponse(code = 400, message = "Erro ao criar email")
    })
    @PostMapping(path = "/email/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> send(@RequestBody @Valid EmailDTO emailDTO) {
        try {
            LOGGER.info("Send Email to... {} " ,  emailDTO.getEmailTo());
            Email email = new Email();
            BeanUtils.copyProperties(emailDTO, email);
            email = emailService.send(email);
            email.add(linkTo(methodOn((EmailController.class)).send(emailDTO)).withSelfRel());
            LOGGER.info("Sent to... {} " ,  emailDTO.getEmailTo());
            return EmailResponse.responseBuilder(HttpStatus.CREATED,  email);
        } catch (Exception e) {
            LOGGER.info("Email ... {} " , emailDTO.getEmailTo());
            LOGGER.info("Error ... {} " , e.getMessage());
            return EmailResponse.responseBuilder(HttpStatus.BAD_REQUEST, emailDTO);
        }
    }

}
