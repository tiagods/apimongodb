package com.tiagods.apimongodb.model;

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
    @Id
    private String id;
    private String clienteId;
    private String produtoId;
    private boolean ativo;
}
