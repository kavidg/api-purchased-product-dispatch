package co.com.asgard.core.controller;

import co.com.asgard.core.config.LoggerContext;
import co.com.asgard.core.model.Product;
import co.com.asgard.core.service.IStockService;
import co.com.asgard.core.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${app.api.base-path}/stock")
public class StockController {

    private final IStockService stockService;

    @Autowired
    public StockController(IStockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/search")
    public ResponseEntity<Product> searchProduct(
            @RequestParam String query,
            @RequestHeader(value = Constants.X_ID_USER, required = false) String userId,
            @RequestHeader(value = Constants.X_ORIGIN_CHANNEL, required = false) String originChannel,
            @RequestHeader(value = Constants.X_CORRELATION_ID, required = false) String correlationId) {

        String correlation = correlationId != null ? correlationId : UUID.randomUUID().toString();
        LoggerContext.setUuid(correlation);
        LoggerContext.setBusiness(Constants.PURCHASED_PRODUCT_DISPATCH);
        LoggerContext.setApp(Constants.APP);

        Product product = stockService.searchProduct(query);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/update")
    public ResponseEntity<Product> updateStock(
            @RequestBody Map<String, Object> body,
            @RequestHeader(value = Constants.X_ID_USER, required = false) String userId,
            @RequestHeader(value = Constants.X_ORIGIN_CHANNEL, required = false) String originChannel,
            @RequestHeader(value = Constants.X_CORRELATION_ID, required = false) String correlationId) {

        String correlation = correlationId != null ? correlationId : UUID.randomUUID().toString();
        LoggerContext.setUuid(correlation);
        LoggerContext.setBusiness(Constants.PURCHASED_PRODUCT_DISPATCH);
        LoggerContext.setApp(Constants.APP);

        String productCode = (String) body.get("productCode");
        int newQuantity = (int) body.get("newQuantity");

        Product updatedProduct = stockService.updateStock(productCode, newQuantity);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/historical")
    public ResponseEntity<Map<String, String>> saveHistory(
            @RequestBody Map<String, String> body,
            @RequestHeader(value = Constants.X_ID_USER, required = false) String userId,
            @RequestHeader(value = Constants.X_ORIGIN_CHANNEL, required = false) String originChannel,
            @RequestHeader(value = Constants.X_CORRELATION_ID, required = false) String correlationId) {

        String correlation = correlationId != null ? correlationId : UUID.randomUUID().toString();
        LoggerContext.setUuid(correlation);
        LoggerContext.setBusiness(Constants.PURCHASED_PRODUCT_DISPATCH);
        LoggerContext.setApp(Constants.APP);

        stockService.saveQuery(body.get("user"), body.get("searchedProduct"));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "History saved successfully."));
    }
}