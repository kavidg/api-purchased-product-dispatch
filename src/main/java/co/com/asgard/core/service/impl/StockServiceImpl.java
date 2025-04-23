package co.com.asgard.core.service.impl;

import co.com.asgard.core.model.Historical;
import co.com.asgard.core.enums.StatusProduct;
import co.com.asgard.core.model.Product;
import co.com.asgard.core.repository.HistoricalRepository;
import co.com.asgard.core.repository.ProductRepository;
import co.com.asgard.core.service.IStockService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class StockServiceImpl implements IStockService {

    private final ProductRepository productRepository;
    private final HistoricalRepository historialRepository;

    @Autowired
    public StockServiceImpl(ProductRepository productRepository, HistoricalRepository historialRepository) {
        this.productRepository = productRepository;
        this.historialRepository = historialRepository;
    }

    public Product searchProduct(String query) {
        return productRepository.findByCodeOrName(query, query)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Producto no encontrado, verifique el código o nombre ingresado."));
    }

    public Product updateStock(String code, int cantidadNueva) {
        Product product = productRepository.findByCodeOrName(code, code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se pudo actualizar el stock. Producto no encontrado."));

        product.setQuantityAvailable(cantidadNueva);
        product.setStatus(cantidadNueva == 0 ? StatusProduct.AGOTADO : StatusProduct.DISPONIBLE);

        return productRepository.save(product);
    }

    public void saveQuery(String user, String codeProduct) {
        Optional<Product> optionalProduct = productRepository.findByCode(codeProduct);
        Product product = optionalProduct.orElseThrow(() ->
            new EntityNotFoundException("Producto no encontrado con código: " + codeProduct));
    
        log.info("product: {}", product);
    
        Historical historical = new Historical(10L, product, user, LocalDateTime.now());
        historialRepository.save(historical);
    }
    

}