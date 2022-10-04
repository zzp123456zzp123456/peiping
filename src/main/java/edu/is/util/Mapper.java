package edu.is.util;

import edu.is.entity.Requirements;

import java.util.LinkedList;

public class Mapper {
    public static LinkedList<Requirements> changeDoubleArrToRequirements(LinkedList<double[]> doubles) {
        LinkedList<Requirements> requirements = new LinkedList<>();
        for (double[] i : doubles) {
            if (i.length == 3) {
                requirements.add(new Requirements().setWide(i[0])
                        .setNum(i[1])
                        .setFlag(i[2]));
            } else {
                requirements.add(new Requirements().setWide(i[0])
                        .setNum(i[1]));
            }
        }
        return requirements;
    }
}
