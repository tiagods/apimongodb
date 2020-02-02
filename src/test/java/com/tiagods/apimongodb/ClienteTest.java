package com.tiagods.apimongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tiagods.apimongodb.controller.ClienteController;
import com.tiagods.apimongodb.exception.ClienteNotFoundException;
import com.tiagods.apimongodb.model.Cliente;
import com.tiagods.apimongodb.repository.ClienteRepository;
import com.tiagods.apimongodb.service.ClienteService;
import org.bson.json.JsonReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ClienteRepository mockRepository;

    private static List<Cliente> list;

    @BeforeClass
    public static void initClass(){
        list = Arrays.asList(
                new Cliente("1", "Joao Barbosa", 10055599912L, "Rua A", "1133335555", "joao@mail.com"),
                new Cliente("2", "Marcos", 50055599913L, "Rua B", "1133335588", "marcos@mail.com")
        );
    }

    public void criarCliente(){

    }

    @Test
    public void listarClientes() throws Exception{
        Mockito.when(mockRepository.findAll()).thenReturn(list);
        String expected = om.writeValueAsString(list);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/clientes", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }
    @Test
    public void buscarPorId() throws Exception {
        String expected = "{\"id\":\"1\",\"nome\":\"Joao Barbosa\",\"cpf\":10055599912,\"endereco\":\"Rua A\",\"telefone\":\"1133335555\",\"email\":\"joao@mail.com\"}";
        Mockito.when(mockRepository.findById("1")).thenReturn(Optional.of(list.get(0)));
        ResponseEntity<String> response = restTemplate.getForEntity("/api/clientes/1", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        Mockito.verify(mockRepository, Mockito.times(1)).findById("1");

    }
    @Test
    public void buscarPorIdInexistente() throws Exception {
        String expected = "{status:404,error:\"Not Found\",message: \"Cliente não existe na base de dados\"}";
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        ResponseEntity<String> response = restTemplate.getForEntity("/api/clientes/a213s1df5", String.class);
        Assert.assertEquals(expectedStatus, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody() ,false);
        Mockito.verify(mockRepository, Mockito.times(1)).findById("a213s1df5");
    }
    //buscarPorCpf

    public void deletar(){

    }
    public void atualizarCliente(){

    }
    private void testeCriarComNomeVazio(){

    }
    private void testeCriarComCpfAcima11Digitos(){

    }
    private void testeComEmailNaoInformado(){

    }
}
