package co.com.asgard.core.service.impl;

import co.com.asgard.core.config.EntityServiceException;
import co.com.asgard.core.dto.ProductOutboundRequestDTO;
import co.com.asgard.core.dto.ProductOutboundResponseDTO;
import co.com.asgard.core.model.AppUser;
import co.com.asgard.core.model.Product;
import co.com.asgard.core.model.ProductOutbound;
import co.com.asgard.core.repository.AppUserRepository;
import co.com.asgard.core.repository.ProductOutboundRepository;
import co.com.asgard.core.repository.ProductRepository;
import co.com.asgard.core.service.ProductOutboundService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import co.com.asgard.core.dto.ProductOutboundStatusUpdateDTO;

import java.util.UUID;

@Service
public class ProductOutboundServiceImpl implements ProductOutboundService {

    private final ProductRepository productRepo;
    private final AppUserRepository userRepo;
    private final ProductOutboundRepository outboundRepo;

    public ProductOutboundServiceImpl(ProductRepository productRepo, AppUserRepository userRepo, ProductOutboundRepository outboundRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.outboundRepo = outboundRepo;
    }

    public ProductOutboundResponseDTO registerOutbound(ProductOutboundRequestDTO dto) {
        Product product = productRepo.findByCode(dto.getProductCode())
                .orElseThrow(() -> new EntityServiceException(HttpStatus.NOT_FOUND, "Producto con código " + dto.getProductCode() + " no encontrado"));

        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new EntityServiceException(HttpStatus.BAD_REQUEST, "Cantidad debe ser mayor a cero");
        }

        if (product.getCurrentStock() < dto.getQuantity()) {
            throw new EntityServiceException(HttpStatus.BAD_REQUEST, "Stock insuficiente para esta salida");
        }

        AppUser user = userRepo.findById(dto.getResponsibleId())
                .orElseThrow(() -> new EntityServiceException(HttpStatus.NOT_FOUND, "Responsable no encontrado"));

        // Disminuir el stock
        product.setCurrentStock(product.getCurrentStock() - dto.getQuantity());
        productRepo.save(product);

        String codeRegister = "OUT" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        ProductOutbound outbound = new ProductOutbound();
        outbound.setProduct(product);
        outbound.setQuantity(dto.getQuantity());
        outbound.setDestination(dto.getDestination());
        outbound.setDate(dto.getDate());
        outbound.setResponsible(user);
        outbound.setCodeRegister(codeRegister);

        outboundRepo.save(outbound);

        ProductOutboundResponseDTO responseDTO = new ProductOutboundResponseDTO();
        responseDTO.setCodeRegister(codeRegister);
        responseDTO.setProductName(product.getName());
        responseDTO.setQuantity(dto.getQuantity());
        responseDTO.setDestination(dto.getDestination());
        responseDTO.setDate(dto.getDate());
        responseDTO.setResponsibleName(user.getFullName());

        return responseDTO;
    }
    @Override
    public void updateStatus(ProductOutboundStatusUpdateDTO dto) {
    ProductOutbound outbound = outboundRepo.findById(dto.getId())
        .orElseThrow(() -> new EntityServiceException(HttpStatus.NOT_FOUND, "Registro no encontrado con id: " + dto.getId()));

    outbound.setStatus(dto.getStatus());
    outboundRepo.save(outbound);
}
}

