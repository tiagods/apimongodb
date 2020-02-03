package com.tiagods.apimongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagods.apimongodb.model.Cliente;
import com.tiagods.apimongodb.repositoy.ClienteRepository;
import com.tiagods.apimongodb.service.ClienteService;
import org.junit.*;
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
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ClienteRepository mockRepository;

    @InjectMocks
    private ClienteService mockService;

    private static List<Cliente> list;

    private String PATH = "/api/clientes";

    @BeforeClass
    public static void initClass(){
        list = Arrays.asList(
                new Cliente("1", "Joao Barbosa", 10055599912L, "Rua A", "1133335555", "joao@mail.com"),
                new Cliente("2", "Marcos Barbosa", 50055599913L, "Rua B", "1133335588", "marcos@mail.com"),
                new Cliente("3", "Fabiano Araujo", 10055596912L, "Rua A", "1133335555", "joao@mail.com"),
                new Cliente("4", "Juliano", 55555599913L, "Rua B", "1133335588", "marcos@mail.com"),
                new Cliente("5", "Joao Silva", 99955599912L, "Rua A", "1133335555", "joao@mail.com"),
                new Cliente("6", "Marcos Felix", 70055599913L, "Rua B", "1133335588", "marcos@mail.com")
        );
    }

    @Test
    public void listarClientes_200() throws Exception{
        Mockito.when(mockRepository.findAll()).thenReturn(list);
        String expected = om.writeValueAsString(list);
        ResponseEntity<String> response = restTemplate.getForEntity(PATH, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }
    @Test
    public void buscarPorId_200() throws Exception {
        String expected = "{\"id\":\"1\",\"nome\":\"Joao Barbosa\",\"cpf\":10055599912,\"endereco\":\"Rua A\",\"telefone\":\"1133335555\",\"email\":\"joao@mail.com\"}";
        Mockito.when(mockRepository.findById(Mockito.anyString())).thenReturn(Optional.of(list.get(0)));
        ResponseEntity<String> response = restTemplate.getForEntity(PATH+"/1", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
        Mockito.verify(mockRepository, Mockito.times(1)).findById("1");

    }
    @Test
    public void buscarPorIdInexistente_404() throws Exception {
        String expected = "{status:404,error:\"Not Found\",message: \"Cliente não existe na base de dados\"}";
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
    public void criarCliente1_201() throws Exception {
        Cliente cliente = list.get(0);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(mockRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, cliente, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(response.getHeaders().getLocation().getPath(),PATH+"/1");
        Mockito.verify(mockRepository, Mockito.times(1)).findByCpf(cliente.getCpf());
        Mockito.verify(mockRepository, Mockito.times(1)).save(Mockito.any(Cliente.class));
    }
    @Test
    public void criarClienteComNomeNulo_400() throws Exception {
        Cliente cliente = list.get(0);
        cliente.setNome(null);
        Mockito.when(mockRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, cliente, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Mockito.verify(mockRepository, Mockito.times(0)).findByCpf(cliente.getCpf());
        Mockito.verify(mockRepository, Mockito.times(0)).save(Mockito.any(Cliente.class));
    }

    @Test
    public void criarClienteCpfDuplicado_403() throws Exception {
        Cliente cliente1 = list.get(0);
        Cliente cliente2 = list.get(1);
        cliente2.setCpf(cliente1.getCpf());
        Mockito.when(mockRepository.findByCpf(Mockito.anyLong())).thenReturn(Optional.of(cliente1));
        ResponseEntity<String> response = restTemplate.postForEntity(PATH, cliente2, String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        Mockito.verify(mockRepository, Mockito.times(1)).findByCpf(cliente2.getCpf());
        Mockito.verify(mockRepository, Mockito.times(0)).save(Mockito.any(Cliente.class));
    }
    @Test
    public void atualizarCliente_204(){
        Cliente cliente = list.get(0);
        Mockito.when(mockRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        Mockito.when(mockRepository.findById(Mockito.anyString())).thenReturn(Optional.of(cliente));
        String patchInJson = "{\"nome\":\"Joao Barbosa\",\"cpf\":10055599912,\"endereco\":\"Rua A\",\"telefone\":\"1133335555\",\"email\":\"joao@mail.com\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);
        ResponseEntity<String> response = restTemplate.exchange(PATH+"/1", HttpMethod.PUT, entity, String.class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(mockRepository, Mockito.times(1)).findById("1");
        Mockito.verify(mockRepository, Mockito.times(1)).save(Mockito.any(Cliente.class));

    }
    @Test
    public void buscarPorCpf_200(){
       Mockito.when(mockRepository.findByCpf(Mockito.anyLong())).thenReturn(Optional.of(list.get(0)));
       ResponseEntity<String> response = restTemplate.getForEntity(PATH+"/10055599912/cpf", String.class);
       Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
       Mockito.verify(mockRepository, Mockito.times(1)).findByCpf(10055599912L);
    }
    @Test
    public void buscarPorNome_200(){
        Mockito.when(mockRepository.findAllByNome(Mockito.anyString())).thenReturn(
                list.stream().filter(c->c.getNome().contains("Barbosa")).collect(Collectors.toList())
        );
        ResponseEntity<String> response = restTemplate.getForEntity(PATH+"/Barbosa/nome", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        Mockito.verify(mockRepository, Mockito.times(1)).findAllByNome("Barbosa");
    }
}
