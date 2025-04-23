package co.com.asgard.core.service;

import co.com.asgard.core.model.Product;

public interface IStockService {

    Product searchProduct(String query);

    Product updateStock(String code, int cantidadNueva);

    void saveQuery(String user, String product);

}