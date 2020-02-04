const clienteHost = "http://localhost:8080/api/clientes";
const produtoHost = "http://localhost:8081/api/produtos";
const clienteprodutoHost = "http://localhost:8082/api/clienteprodutos";

export function getClienteHost(){
    return clienteHost;
}

export function getProdutoHost(){
    return produtoHost;
}

export function getClienteProdutoHost(){
    return clienteprodutoHost;
}