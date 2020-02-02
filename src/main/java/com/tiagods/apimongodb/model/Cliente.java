package com.tiagods.apimongodb.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "clientes")
public class Cliente {
    @ApiModelProperty(value = "Código do cliente")
    @Id
    private String id;
    @ApiModelProperty(value = "Nome do cliente")
    @NotNull @NotBlank
    private String nome;
    @ApiModelProperty(value = "Cpf do cliente")
    @NotNull
    @Size(min = 11, max = 11, message = "Cpf incorreto, necessario 11 digitos")
    private long cpf;
    @ApiModelProperty(value = "Endereco completo do cliente")
    private String endereco;
    @ApiModelProperty(value = "Telefone do cliente")
    private String telefone;
    @ApiModelProperty(value = "Email do cliente")
    @NotNull @NotBlank
    private String email;
}
