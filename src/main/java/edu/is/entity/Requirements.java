package edu.is.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Requirements implements Comparable<Requirements>{
    private double wide;
    private double num;
    private double flag;


    @Override
    public int compareTo(Requirements o) {
        return -(int)(this.flag-o.getFlag());
    }

    public void num_() {
        this.num--;
    }
}
