package com.tiagods.apimongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagods.apimongodb.secundary.ProdutoRepository;
import com.tiagods.apimongodb.secundary.ProdutoService;
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
public class ProdutoTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ProdutoRepository mockRepository;

    @InjectMocks
    private ProdutoService mockService;

    private static List<Produto> list;

    private String PATH = "/api/produtos";

    @BeforeClass
    public static void initClass(){
        list = Arrays.asList(
                new Produto("1", "PRODUCT A"),
                new Produto("2", "PRODUCT B"),
                new Produto("3", "PRODUCT C"),
                new Produto("4", "PRODUCT D"),
                new Produto("5", "PRODUCT E"),
                new Produto("6", "PRODUCT F")
        );
    }

    @Test
    public void listarProdutos_200() throws Exception{
        Mockito.when(mockRepository.findAll()).thenReturn(list);
        String expected = om.writeValueAsString(list);
        ResponseEntity<String> response = restTemplate.getForEntity(PATH, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }
    @Test
    public void buscarPorId_200() throws Exception {
        String expected = "{\"id\":\"1\",\"nome\":\"PRODUCT A\"}";
        Mockito.when(mockRepository.findById(Mockito.anyString())).thenReturn(Optional.of(list.get(0)));
        ResponseEntity<String> response = restTemplate.getForEntity(PATH+"/1", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        Mockito.verify(mockRepository, Mockito.times(1)).findById("1");

    }
    @Test
    public void buscarPorIdInexistente_404() throws Exception {
        String expected = "{status:404,error:\"Not Found\",message: \"Produto não existe na base de dados\"}";
        ResponseEntity<String> response = restTemplate.getForEntity(PATH+"/a213s1df5", String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody() ,false);
        Mockito.verify(mockRepository, Mockito.times(1)).findById("a213s1df5");
    }

    @Test
    public void deletar_204(){
        Mockito.when(mockRepository.findById("1")).thenReturn(Optional.of(list.get(0)));
        Mockito.doNothing().when(mockRepository).deleteById(Mockito.anyString());
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(PATH+"/1", HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(mockRepository,Mockito.times(1)).findById("1");
        Mockito.verify(mockRepository, Mockito.times(1)).deleteById("1");
    }
    @Test
    public void deletarNaoExistente_404(){
        Mockito.when(mockRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.doNothing().when(mockRepository).deleteById(Mockito.anyString());
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(PATH+"/1212", HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getBody());
        Mockito.verify(mockRepository,Mockito.times(1)).findById("1212");
        Mockito.verify(mockRepository, Mockito.times(0)).deleteById("1212");
    }
    @Test
    public void criarProduto1_201() throws Exception {
        Produto produto = list.get(0);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(mockRepository.save(Mockito.any(Produto.class))).thenReturn(produto);
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, produto, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(response.getHeaders().getLocation().getPath(),PATH+"/1");
        Mockito.verify(mockRepository, Mockito.times(1)).findByNome(produto.getNome());
        Mockito.verify(mockRepository, Mockito.times(1)).save(Mockito.any(Produto.class));
    }
    @Test
    public void criarProdutoComNomeNulo_400() throws Exception {
        Produto produto = list.get(0);
        produto.setNome(null);
        Mockito.when(mockRepository.save(Mockito.any(Produto.class))).thenReturn(produto);
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, produto, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Mockito.verify(mockRepository, Mockito.times(0)).findByNome(produto.getNome());
        Mockito.verify(mockRepository, Mockito.times(0)).save(Mockito.any(Produto.class));
    }

    @Test
    public void criarProdutoComNomeDuplicado_403() throws Exception {
        Produto produto1 = list.get(0);
        Produto produto2 = list.get(1);
        produto2.setNome(produto1.getNome());
        Mockito.when(mockRepository.findByNome(Mockito.anyString())).thenReturn(Optional.of(produto1));
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, produto2, String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        Mockito.verify(mockRepository, Mockito.times(1)).findByNome(produto2.getNome());
        Mockito.verify(mockRepository, Mockito.times(0)).save(Mockito.any(Produto.class));
    }
    @Test
    public void atualizarProduto_204(){
        Produto produto = list.get(0);
        Mockito.when(mockRepository.save(Mockito.any(Produto.class))).thenReturn(produto);
        Mockito.when(mockRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produto));
        String patchInJson = "{\"nome\":\"PRODUCT ZZZ\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);
        ResponseEntity<String> response = restTemplate.exchange(PATH+"/1", HttpMethod.PUT, entity, String.class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(mockRepository, Mockito.times(1)).findById("1");
        Mockito.verify(mockRepository, Mockito.times(1)).save(Mockito.any(Produto.class));

    }
    @Test
    public void buscarPorNome_200(){
        Produto produto = list.get(0);
        Mockito.when(mockRepository.findByNome(Mockito.anyString())).thenReturn(Optional.of(produto));
        ResponseEntity<String> response = restTemplate.getForEntity(PATH+"/PRODUCT A/nome", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        Mockito.verify(mockRepository, Mockito.times(1)).findByNome("PRODUCT A");
    }
}
