package com.tiagods.apimongodb;

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
    @Id
    private String id;
    @NotNull(message = "Nome é obrigatorio")
    @NotBlank(message = "Nome invalido")
    @Field(value = "name")
    private String nome;
}
