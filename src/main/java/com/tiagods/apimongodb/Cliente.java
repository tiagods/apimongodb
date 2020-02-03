package com.tiagods.apimongodb;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "clientes")
public class Cliente {
    @ApiModelProperty(value = "Código do cliente")
    @Id
    private String id;
    @ApiModelProperty(value = "Nome do cliente")
    @NotNull(message = "Nome obrigatorio") @NotBlank(message = "Nome invalido")
    private String nome;
    @ApiModelProperty(value = "Cpf do cliente")
    @NotNull(message = "Cpf obrigatório")
    private long cpf;
    @ApiModelProperty(value = "Endereco completo do cliente")
    private String endereco;
    @ApiModelProperty(value = "Telefone do cliente")
    private String telefone;
    @ApiModelProperty(value = "Email do cliente")
    @NotNull(message = "Email obrigatorio") @NotBlank(message = "Email invalido")
    private String email;
}
