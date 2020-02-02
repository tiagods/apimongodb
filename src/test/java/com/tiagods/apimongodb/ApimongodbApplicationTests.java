package com.tiagods.apimongodb;

import com.tiagods.apimongodb.primary.model.Cliente;
import com.tiagods.apimongodb.primary.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApimongodbApplicationTests {

    @Autowired
    private ClienteRepository repository;

    //@Before
    public void init(){
        if(repository.findAll().isEmpty()) {
            List list = Arrays.asList(
                    new Cliente(null, "Joao Barbosa", 10055599912L, "Rua A", "1133335555", "joao@mail.com"),
                    new Cliente(null, "Marcos Barbosa", 50055599913L, "Rua B", "1133335588", "marcos@mail.com"),
                    new Cliente(null, "Fabiano Araujo", 10055596912L, "Rua A", "1133335555", "joao@mail.com"),
                    new Cliente(null, "Juliano", 55555599913L, "Rua B", "1133335588", "marcos@mail.com"),
                    new Cliente(null, "Joao Silva", 99955599912L, "Rua A", "1133335555", "joao@mail.com"),
                    new Cliente(null, "Marcos Felix", 70055599913L, "Rua B", "1133335588", "marcos@mail.com")
            );
            repository.saveAll(list);
        }
    }

    //@Test
    public void test(){
        Optional<Cliente> byCpf = repository.findByCpf(50055599913L);
        System.out.println(byCpf.get());
        Assert.assertTrue(byCpf.isPresent());
    }
    //@Test
    public void test2(){
        List<Cliente> list = repository.findAllByNome("Barbosa");
        Assert.assertEquals(2,list.size());
        list = repository.findAllByNome("Joao");
        Assert.assertEquals(2,list.size());
        list = repository.findAllByNome("Felix");
        Assert.assertEquals(1,list.size());
        list = repository.findAllByNome("Tiago");
        Assert.assertEquals(0,list.size());
    }
}
