package org.tensorflow.codelabs.objectdetection;

public class RackDetails {
    int rack_id;
    int x_min;
    int y_min;
    int x_max;

    public int getRack_id() {
        return rack_id;
    }

    public int getX_min() {
        return x_min;
    }

    public void setX_min(int x_min) {
        this.x_min = x_min;
    }

    public int getY_min() {
        return y_min;
    }

    public void setY_min(int y_min) {
        this.y_min = y_min;
    }

    public int getX_max() {
        return x_max;
    }

    public void setX_max(int x_max) {
        this.x_max = x_max;
    }

    public int getY_max() {
        return y_max;
    }

    public void setY_max(int y_max) {
        this.y_max = y_max;
    }

    @Override
    public String toString() {
        return "RackDetails{" +
                "rack_id=" + rack_id +
                ", x_min=" + x_min +
                ", y_min=" + y_min +
                ", x_max=" + x_max +
                ", y_max=" + y_max +
                '}';
    }

    public void setRack_id(int rack_id) {
        this.rack_id = rack_id;
    }

    int y_max;
}
