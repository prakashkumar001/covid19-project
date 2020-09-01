package com.virus.covid19.utilities;

public class ProductAvailable {
    public boolean isProductAvailable;

    public ProductAvailable(boolean isProductAvailable, int indexofProduct) {
        this.isProductAvailable = isProductAvailable;
        this.indexofProduct = indexofProduct;
    }

    public int indexofProduct;

}