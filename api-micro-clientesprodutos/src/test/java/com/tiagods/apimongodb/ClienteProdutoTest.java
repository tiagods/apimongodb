package com.tiagods.apimongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagods.apimongodb.exception.ClienteProdutoNotFoundException;
import com.tiagods.apimongodb.model.ClienteProduto;
import com.tiagods.apimongodb.repository.ClienteProdutoRepository;
import com.tiagods.apimongodb.service.ClienteProdutoService;
import com.tiagods.apimongodb.service.ClienteRestClient;
import com.tiagods.apimongodb.service.ProdutoRestClient;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteProdutoTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ClienteProdutoRepository mockRepository;

    @MockBean
    private ClienteRestClient restClient;

    @MockBean
    private ProdutoRestClient restProduct;

    @InjectMocks
    private ClienteProdutoService mockService;

    private static List<ClienteProduto> list;

    private String PATH = "/api/clientesprodutos";

    @BeforeClass
    public static void initClass(){
        list = Arrays.asList(
                new ClienteProduto("1", "CLIENTE A","PRODUCT A",true),
                new ClienteProduto("2", "CLIENTE B","PRODUCT B",true),
                new ClienteProduto("3", "CLIENTE C","PRODUCT C",true),
                new ClienteProduto("4", "CLIENTE D","PRODUCT D",true),
                new ClienteProduto("5", "CLIENTE E","PRODUCT E",true),
                new ClienteProduto("6", "CLIENTE F","PRODUCT F",true)
        );
    }

    @Test
    public void listarItems_200() throws Exception{
        Mockito.when(mockRepository.findAll()).thenReturn(list);
        String expected = om.writeValueAsString(list);
        ResponseEntity<String> response = restTemplate.getForEntity(PATH, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void criarClienteProduto1_201() throws Exception {
        ClienteProduto clienteProduto = list.get(0);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(restClient.buscarCliente(Mockito.anyString())).thenReturn(responseEntity);
        Mockito.when(restProduct.buscarProduto(Mockito.anyString())).thenReturn(responseEntity);
        Mockito.when(mockRepository.save(Mockito.any(ClienteProduto.class))).thenReturn(clienteProduto);
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, clienteProduto, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(response.getHeaders().getLocation().getPath(),PATH+"/1");
        Mockito.verify(restClient, Mockito.times(1)).buscarCliente(clienteProduto.getClienteId());
        Mockito.verify(restProduct,Mockito.times(1)).buscarProduto(clienteProduto.getProdutoId());
        Mockito.verify(mockRepository, Mockito.times(1)).findByClienteIdAndProdutoIdAndStatusIsTrue(clienteProduto.getClienteId(),clienteProduto.getProdutoId());
        Mockito.verify(mockRepository, Mockito.times(1)).save(Mockito.any(ClienteProduto.class));
    }
    @Test
    public void criarClienteProdutoNulo_400() throws Exception {
        ClienteProduto clienteProduto = list.get(0);
        clienteProduto.setClienteId(null);
        clienteProduto.setProdutoId(null);
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, clienteProduto, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Mockito.verify(restClient, Mockito.times(0)).buscarCliente(Mockito.anyString()) ;
        Mockito.verify(restProduct,Mockito.times(0)).buscarProduto(Mockito.anyString());
        Mockito.verify(mockRepository, Mockito.times(0)).findByClienteIdAndProdutoIdAndStatusIsTrue(clienteProduto.getClienteId(),clienteProduto.getProdutoId());
        Mockito.verify(mockRepository, Mockito.times(0)).save(Mockito.any(ClienteProduto.class));
    }
    @Test
    public void ciarClienteProdutoEAlterarExistenteDesativarStatus(){
        ClienteProduto clienteProduto = list.get(0);
        ClienteProduto clienteProduto2 = list.get(1);

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(restClient.buscarCliente(Mockito.anyString())).thenReturn(responseEntity);
        Mockito.when(restProduct.buscarProduto(Mockito.anyString())).thenReturn(responseEntity);
        Mockito.when(mockRepository.findByClienteIdAndProdutoIdAndStatusIsTrue(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.of(clienteProduto2));
        Mockito.when(mockRepository.save(Mockito.any(ClienteProduto.class))).thenReturn(clienteProduto);
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, clienteProduto, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(response.getHeaders().getLocation().getPath(),PATH+"/1");
        Mockito.verify(restClient, Mockito.times(1)).buscarCliente(clienteProduto.getClienteId());
        Mockito.verify(restProduct,Mockito.times(1)).buscarProduto(clienteProduto.getProdutoId());
        Mockito.verify(mockRepository, Mockito.times(1)).findByClienteIdAndProdutoIdAndStatusIsTrue(clienteProduto.getClienteId(),clienteProduto.getProdutoId());
        Mockito.verify(mockRepository, Mockito.times(2)).save(Mockito.any(ClienteProduto.class));
    }


    @Test
    public void criarClienteProdutoComClienteInexistente_404() throws Exception {
        ClienteProduto clienteProduto = list.get(0);
        Mockito.when(restClient.buscarCliente(Mockito.anyString())).thenThrow(ClienteProdutoNotFoundException.class);
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, clienteProduto, String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Mockito.verify(restClient, Mockito.times(1)).buscarCliente(Mockito.anyString()) ;
        Mockito.verify(restProduct,Mockito.times(0)).buscarProduto(Mockito.anyString());
        Mockito.verify(mockRepository, Mockito.times(0)).findByClienteIdAndProdutoIdAndStatusIsTrue(clienteProduto.getClienteId(),clienteProduto.getProdutoId());
        Mockito.verify(mockRepository, Mockito.times(0)).save(Mockito.any(ClienteProduto.class));
    }
    @Test
    public void buscarPorId_200() throws Exception {
        Mockito.when(mockRepository.findById(Mockito.anyString())).thenReturn(Optional.of(list.get(0)));
        ResponseEntity<String> response = restTemplate.getForEntity(PATH+"/1", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(mockRepository, Mockito.times(1)).findById("1");

    }
    @Test
    public void buscarPorIdInexistente_404() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(PATH+"/a213s1df5", String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Mockito.verify(mockRepository, Mockito.times(1)).findById("a213s1df5");
    }


}
