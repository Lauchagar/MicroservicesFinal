package com.sistemasactivos.apirest.account.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;


@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    
    
    @Bean
    public OpenAPI getApiInfo() {
    
        ApiResponse okAPI = new ApiResponse()
            .description("Respuesta Exitosa.")
            .content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                        new Example().value("{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" : \"Traido con exito!\"}")))
        );
        
        ApiResponse created = new ApiResponse()
            .description("Creacion Exitosa.")
            .content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                        new Example().value("{\"code\" : 201, \"Status\" : \"Created!\", \"Message\" : \"Creado con exito!\"}")))
        );
        
        ApiResponse noContent = new ApiResponse()
            .description("Respuesta Exitosa.")
            .content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                        new Example().value("{\"code\" : 204, \"Status\" : \"No Content!\", \"Message\" : \"Proceso exitoso!\"}")))
        );
        
        ApiResponse badRequest = new ApiResponse()
            .description("Solicitud Invalida.")
            .content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, 
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                        new Example().value("{\"code\" : 400, \"Status\" : \"Bad Request!\", \"Message\" : \"Formato incorrecto.\"}")))
        );
        
        ApiResponse notFound = new ApiResponse()
            .description("No Encontrado.")
            .content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, 
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                        new Example().value("{\"code\" : 404, \"Status\" : \"Not Found!\", \"Message\" : \"Ruta incorrecta.\"}")))
        );        
        
        ApiResponse serverError = new ApiResponse()
            .description("Error Interno del Servidor.")
            .content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, 
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                        new Example().value("{\"code\" : 500, \"Status\" : \"Internal Server Error!\", \"Message\" : \"Problemas con el servidor.\"}")))
        );
        
        Components components = new Components();
        components.addResponses("okAPI", okAPI);
        components.addResponses("created", created);
        components.addResponses("noContent", noContent);
        components.addResponses("badRequest", badRequest);
        components.addResponses("notFound", notFound);
        components.addResponses("serverError", serverError);

        
        return new OpenAPI()
                            .components(components)
                            .info(new Info().title("MS Account")
                            .version("v1")
                            .description("Manejo de Cuentas")
                            .termsOfService("http://www.sistemasactivos.com/terminos")
                            .contact(new Contact()
                            .name("Lautaro")
                            .email("lautaroo.coria@gmail.com")
                            .url("http://www.sistemasactivos.com/lautaro"))
                );
        
    }
}

