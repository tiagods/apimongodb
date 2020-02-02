package com.tiagods.apimongodb.service;

import com.tiagods.apimongodb.exception.ClienteJaExisteException;
import com.tiagods.apimongodb.exception.ClienteNotFoundException;
import com.tiagods.apimongodb.model.Cliente;
import com.tiagods.apimongodb.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    public Cliente salvar(Cliente cliente){
        try {
            Cliente cli2 = buscarPorCpf(cliente.getCpf());
            throw new ClienteJaExisteException("Cpf ja existe na base foi informado para o cliente "+cli2.getNome());
        } catch (ClienteNotFoundException e) {
            return clienteRepository.save(cliente);
        }
    }

    public Cliente buscarPorId(String id){
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if(optionalCliente.isPresent())
            return optionalCliente.get();
        else throw new ClienteNotFoundException("Cliente não existe na base de dados");
    }

    public Cliente buscarPorCpf(long cpf){
        Optional<Cliente> optional = clienteRepository.findByCpf(cpf);
        if(optional.isPresent()) return optional.get();
        else throw new ClienteNotFoundException("Cliente não encontrado");
    }
    public List<Cliente> listarPorNome(String nome){
        return clienteRepository.findByNome(nome);
    }

    public void atualizar(String id, Cliente cliente) {
        buscarPorId(id);
        cliente.setId(id);
        clienteRepository.save(cliente);
    }
}
