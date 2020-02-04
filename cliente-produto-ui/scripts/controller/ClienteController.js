import {getClienteHost} from '../enviropment/server.js';
import Cliente from '../model/Cliente.js'

$clientesTabela.addEventListener('click', event =>{
    let indice = event.target.dataset.indice;
    if(indice!==undefined){
        excluirCliente(indice);
    }
})

listarClientes(false);

function excluirCliente(id){
    fetch(getClienteHost()+'/'+id,{
        method: "delete",
    }).then(function(res){ 
        listarClientes(true);
    })
}

function listarClientes(alertVisible) {
    
    let linhasTabelas = '';
    $clientesTabela.innerHTML = linhasTabelas;

    fetch(getClienteHost()).then(response=>response.json()).then(datas=> 
        datas.forEach(cliente=>{
            linhasTabelas += `
            <tr>
                <td>${cliente.nome}</td>
                <td>${cliente.cpf}</td>
                <td>${cliente.endereco}</td>
                <td>${cliente.telefone}</td>
                <td>${cliente.email}</td>
                <td>
                    <button data-indice="${cliente.id}" class="btn btn-danger">
                    x
                    </button>
                </td>
            </tr>
            `
            $clientesTabela.innerHTML = linhasTabelas;
        })
    );    
}

$btnSalvarCLiente.addEventListener('click', () => {
        const cliente = new Cliente('',$clienteNome.value,$clienteCpf.value,$clienteEndereco.value,
            $clienteTelefone.value, $clienteEmail.value
        );
        salvar(cliente);
        
})

function salvar(cliente){
    fetch(getClienteHost(), {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(cliente)
    }).then(response =>  response.json()).then(data=>{
        if(data.status==201){
            listarClientes(false);
            limparFormulario();
            $msgErroPerso.classList.add('d-none'); 
        }
        else{
            let message = '';
            data.errors.forEach(msg=>message+=`${msg}<br>`) ;
            $msgErroPerso.innerHTML = message;
            $msgErroPerso.classList.remove('d-none');
        }
        }
    )
}

function limparFormulario(){
    $clienteNome.value ='';
    $clienteCpf.value ='';
    $clienteEndereco.value ='';
    $clienteTelefone.value ='';
    $clienteEmail.value ='';
}