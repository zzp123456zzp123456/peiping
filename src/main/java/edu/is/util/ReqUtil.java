package edu.is.util;

import edu.is.entity.Requirements;

public class ReqUtil {
    public static Requirements copyRequirements(Requirements req) {
        return new Requirements().setNum(req.getNum())
                .setFlag(req.getFlag())
                .setWide(req.getWide());
    }

}
