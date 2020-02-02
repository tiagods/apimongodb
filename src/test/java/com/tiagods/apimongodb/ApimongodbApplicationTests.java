package com.tiagods.apimongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagods.apimongodb.model.Cliente;
import com.tiagods.apimongodb.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApimongodbApplicationTests {

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


	@Before
	public void init() {
		Mockito.when(mockRepository.findById("1")).thenReturn(Optional.of(list.get(0)));
	}

	@Test
	public void listarTudo() throws Exception{
		Mockito.when(mockRepository.findAll()).thenReturn(list);

		String expected = om.writeValueAsString(list);

		ResponseEntity<String> response = restTemplate.getForEntity("/api/clientes", String.class);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		JSONAssert.assertEquals(expected, response.getBody(), false);

		Mockito.verify(mockRepository, Mockito.times(2)).findAll();
//		Mockito.verify(mockRepository, Mockito.times(0)).save(ArgumentMatchers.any(Cliente.class));
	}

}
