package org.home.kata01;

import org.home.kata01.product.Price;
import org.home.kata01.product.Product;
import org.home.kata01.product.ProductsManager;
import org.home.kata01.product.scanned.ScannedProductsKeeper;


public class CheckOut {
    private final ProductsManager       productsManager;
    private final ScannedProductsKeeper scannedProductsKeeper;

    private CheckOut( ProductsManager productsManager) {
        this.productsManager = productsManager;
        scannedProductsKeeper = new ScannedProductsKeeper();
    }

    public void scan( String name) {
        scannedProductsKeeper.addScannedProduct(name);
    }

    
    public Price getPrice() {
        final Price price = Price.zero();
        scannedProductsKeeper.iterateProducts(
                scannedProduct ->
                        productsManager.findProductByName(scannedProduct.name).ifPresent(currentProduct -> {
                            Price productPrice = currentProduct.getPriceForAmount(scannedProduct.amount);
                            price.add(productPrice);
                        }));
        return price;
    }

    public static class Builder {
        private final ProductsManager productsManager;

        private Builder() {
            productsManager = new ProductsManager();
        }

        
        public static Builder aCheckOut() {
            return new Builder();
        }

        
        public CheckOut create() {
            return new CheckOut(productsManager);
        }

        
        public Builder withProduct( Product product) {
            productsManager.addProduct(product);
            return this;
        }
    }
}