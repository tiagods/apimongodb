package com.tiagods.apimongodb.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "${configuracao.pedido.service.url}", name = "pedido")
public interface ClienteRestClient {
    @GetMapping("/pedidos/{pedidoId}/pago")
    void avisaQueFoiPago(@PathVariable("pedidoId") Long pedidoId);

}
