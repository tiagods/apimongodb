package com.tiagods.apimongodb.primary;

import com.tiagods.apimongodb.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente,String> {

    Optional<Cliente> findByCpf(long cpf);
    @Query("{'nome': {$regex : ?0}})")
    List<Cliente> findAllByNome(String nome);
}
