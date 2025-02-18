package com.vemser.rest.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private String nome;
    private String email;
    private String password;
    private String administrador;
    @JsonProperty("_id")
    private String id;
    private String message;

}
