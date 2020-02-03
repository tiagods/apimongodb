package com.tiagods.apimongodb.primary;

import com.tiagods.apimongodb.Cliente;
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

    public void atualizar(String id, Cliente cliente) {
        buscarPorId(id);
        cliente.setId(id);
        clienteRepository.save(cliente);
    }

    public void deletar(String id) {
        buscarPorId(id);
        clienteRepository.deleteById(id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findAllByNome(nome);
    }
}
