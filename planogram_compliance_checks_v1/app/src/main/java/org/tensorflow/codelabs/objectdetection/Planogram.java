package org.tensorflow.codelabs.objectdetection;

import java.util.List;

public class Planogram {
    String store_name;
    int store_id;

    public int getStore_contact() {
        return store_contact;
    }



    @Override
    public String toString() {
        return "Planogram{" +
                "store_name='" + store_name + '\'' +
                ", store_id=" + store_id +
                ", store_address='" + store_address + '\'' +
                ", store_contact=" + store_contact +
                ", shelf_details=" + shelf_details +
                ", product_details=" + product_details +
                '}';
    }

    public void setStore_contact(int store_contact) {
        this.store_contact = store_contact;
    }

    String store_address;
    int store_contact;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public List<ShelfDetails> getShelf_details() {
        return shelf_details;
    }

    public void setShelf_details(List<ShelfDetails> shelf_details) {
        this.shelf_details = shelf_details;
    }

    public List<ProductDetails> getProduct_details() {
        return product_details;
    }

    public void setProduct_details(List<ProductDetails> product_details) {
        this.product_details = product_details;
    }

    List<ShelfDetails> shelf_details;
    List<ProductDetails> product_details;
}
