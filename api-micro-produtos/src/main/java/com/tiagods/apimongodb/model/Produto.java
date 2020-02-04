package com.tiagods.apimongodb.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
    @ApiModelProperty(value = "Código do produto")
    @Id
    private String id;
    @ApiModelProperty(value = "Nome do produto")
    @Field(value = "name")
    @NotNull(message = "Nome é obrigatorio")
    @NotBlank(message = "Nome invalido")
    private String nome;
}
