package co.com.asgard.core.controller;

import co.com.asgard.core.dto.ProductOutboundRequestDTO;
import co.com.asgard.core.dto.ProductOutboundResponseDTO;
import co.com.asgard.core.service.ProductOutboundService;
import co.com.asgard.core.service.impl.ProductOutboundServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.com.asgard.core.dto.ProductOutboundStatusUpdateDTO;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/api/outbound")
public class ProductOutboundController {

    private final ProductOutboundService service;

    public ProductOutboundController(ProductOutboundService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductOutboundResponseDTO> register(@RequestBody ProductOutboundRequestDTO dto) {
        ProductOutboundResponseDTO response = service.registerOutbound(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateStatus(@RequestBody ProductOutboundStatusUpdateDTO dto) {
    service.updateStatus(dto);
    return ResponseEntity.noContent().build();
    }

}
