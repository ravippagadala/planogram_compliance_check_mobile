package org.tensorflow.codelabs.objectdetection;

import java.util.List;

public class ShelfDetails {
    String shelf_id;
    String number_of_racks;

    @Override
    public String toString() {
        return "ShelfDetails{" +
                "shelf_id='" + shelf_id + '\'' +
                ", number_of_racks='" + number_of_racks + '\'' +
                ", rack_details=" + rack_details +
                '}';
    }

    public String getNumber_of_racks() {
        return number_of_racks;
    }

    public void setNumber_of_racks(String number_of_racks) {
        this.number_of_racks = number_of_racks;
    }

    public List<RackDetails> getRack_details() {
        return rack_details;
    }

    public void setRack_details(List<RackDetails> rack_details) {
        this.rack_details = rack_details;
    }

    public String getShelf_id() {
        return shelf_id;
    }

    public void setShelf_id(String shelf_id) {
        this.shelf_id = shelf_id;
    }

    List<RackDetails> rack_details;
}
