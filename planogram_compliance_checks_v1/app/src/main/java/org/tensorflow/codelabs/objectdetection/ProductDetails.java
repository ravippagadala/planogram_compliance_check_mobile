package org.tensorflow.codelabs.objectdetection;

public class ProductDetails {
    String product_id;
    String product_description;

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_label() {
        return product_label;
    }

    public void setProduct_label(String product_label) {
        this.product_label = product_label;
    }

    public String getShelf_id() {
        return shelf_id;
    }

    public void setShelf_id(String shelf_id) {
        this.shelf_id = shelf_id;
    }

    public String getRack_id() {
        return rack_id;
    }

    public void setRack_id(String rack_id) {
        this.rack_id = rack_id;
    }

    public String getPresence_x_start() {
        return presence_x_start;
    }

    public void setPresence_x_start(String presence_x_start) {
        this.presence_x_start = presence_x_start;
    }

    public String getPresence_x_end() {
        return presence_x_end;
    }

    public void setPresence_x_end(String presence_x_end) {
        this.presence_x_end = presence_x_end;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "product_id='" + product_id + '\'' +
                ", product_description='" + product_description + '\'' +
                ", product_label='" + product_label + '\'' +
                ", shelf_id='" + shelf_id + '\'' +
                ", rack_id='" + rack_id + '\'' +
                ", presence_x_start='" + presence_x_start + '\'' +
                ", presence_x_end='" + presence_x_end + '\'' +
                '}';
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    String product_label;
    String shelf_id;
    String rack_id;
    String presence_x_start;
    String presence_x_end;
}
