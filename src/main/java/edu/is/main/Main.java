package edu.is.main;

import edu.is.util.ExcelUtil;
import edu.is.util.LkUtil;
import edu.is.util.Mapper;
import edu.is.entity.Requirements;
import edu.is.util.ExcelUtil;
import edu.is.util.LkUtil;
import edu.is.util.Mapper;
import lombok.extern.java.Log;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Log
public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\配平项目\\peiping.xlsx");
        LinkedList<double[]> sheet = ExcelUtil.readExcel(file);
        LinkedList<Requirements> requirements = Mapper.changeDoubleArrToRequirements(sheet);

        Collections.sort(requirements);
        List<double[]> result ;
        result = LkUtil.pingDan(requirements);
        ExcelUtil.writeExcel("D:\\配平项目\\output.xlsx", result,requirements);

        log.info("完成主类");
    }

}
