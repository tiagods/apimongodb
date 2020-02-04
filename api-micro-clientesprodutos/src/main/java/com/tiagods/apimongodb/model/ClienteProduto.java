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

@Document(collection = "clients_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteProduto {
    @ApiModelProperty(value = "Id do autogerado")
    @Id
    private String id;
    @ApiModelProperty(value = "Id que vem do cliente")
    @NotNull
    private String clienteId;
    @ApiModelProperty(value = "Id que vem do produto")
    @NotNull
    private String produtoId;
    private boolean status;
}
