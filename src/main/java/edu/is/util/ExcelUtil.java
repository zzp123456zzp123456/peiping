package edu.is.util;

import edu.is.entity.Requirements;
import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.LogManager;
/*
静态代码块会随着类的加载而执行,
而且只执行一次,并且优先于各种代码块以及构造函数。
一个类中若有多个静态代码块，则顺序执行它们
 */

@Log
public class ExcelUtil {

    static {//读取日志配置文件
        LogManager manager = LogManager.getLogManager();
        try {
            manager.readConfiguration(ExcelUtil.class.getClassLoader().
                    getResourceAsStream("log.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static LinkedList<double[]> readExcel(File file) {//读取一个sheet
        log.info("开始读取excel文件" + file.getName());
        LinkedList<double[]> linkedList = new LinkedList<>();
        try {
            //创建工作簿对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(
                    new FileInputStream(file));
            //获取工作簿下sheet的个数
            int sheetNum = xssfWorkbook.getNumberOfSheets();
            if (sheetNum != 1) {
                log.severe("读取失败");
                System.exit(-1);
            }

            //读取第一个sheet
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            //获取总行数
            int rowNum = sheet.getPhysicalNumberOfRows();
            if (rowNum != 0) {
                for (int i = 1; i < rowNum; i++) {
                    //获取总列数
                    int cellNum = sheet.getRow(i).getPhysicalNumberOfCells();
                    double[] arr = new double[3];
                    for (int j = 0; j < cellNum; j++) {
                        arr[j] = Double.parseDouble(sheet.getRow(i).getCell(j).toString());
                    }
                    linkedList.add(arr);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("读取成功");
        return linkedList;
    }

    public static LinkedList<double[]> readExcel(InputStream inputStream) {//读取一个sheet
        log.info("开始读取excel文件" );
        LinkedList<double[]> linkedList = new LinkedList<>();
        try {
            //创建工作簿对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(
                    inputStream);
            //获取工作簿下sheet的个数
            int sheetNum = xssfWorkbook.getNumberOfSheets();
            if (sheetNum != 1) {
                log.severe("读取失败");
                System.exit(-1);
            }

            //读取第一个sheet
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            //获取总行数
            int rowNum = sheet.getPhysicalNumberOfRows();
            if (rowNum != 0) {
                for (int i = 1; i < rowNum; i++) {
                    //获取总列数
                    int cellNum = sheet.getRow(i).getPhysicalNumberOfCells();
                    double[] arr = new double[3];
                    for (int j = 0; j < cellNum; j++) {
                        arr[j] = Double.parseDouble(sheet.getRow(i).getCell(j).toString());
                    }
                    linkedList.add(arr);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("读取成功");
        return linkedList;
    }

    public static void writeExcel(String pathName, List<double[]> linkedList, LinkedList<Requirements> requirements) throws Exception {
        log.info("开始写入excel" + pathName.substring(pathName.lastIndexOf("/")+1));
        try (FileOutputStream outputStream = new FileOutputStream(pathName)) {
            //创建工作簿对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            Sheet sheet = xssfWorkbook.createSheet("output");
            String fileType = pathName.substring(pathName.lastIndexOf(".") + 1, pathName.length());
            if (fileType.equals("xlsx")) {
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(0);
                cell0.setCellValue("总幅宽");
                for (int i = 1; i <= 5; i++) {
                    cell0 = row0.createCell(i);
                    cell0.setCellValue(i);
                }
                cell0 = row0.createCell(6);
                cell0.setCellValue("总数量");
                int i = 1;
                for (double[] doubles : linkedList) {
                    //创建第i行
                    if (doubles[6] == 0) {
                        continue;
                    }
                    Row row = sheet.createRow(i);
                    for (int j = 0; j < doubles.length; j++) {
                        //创建第j列
                        Cell cell = row.createCell(j);
                        cell.setCellValue(doubles[j]);
                    }
                    i++;
                }
                i++;
                i++;
                Row row2 = sheet.createRow(i);
                Cell cell2 = row2.createCell(0);
                cell2.setCellValue("未匹配出的规格");
                cell2 = row2.createCell(1);
                cell2.setCellValue("总数量");
                i++;
                for (Requirements req : requirements) {
                    if (req.getNum() != 0) {
                        Row row = sheet.createRow(i);
                        Cell cell = row.createCell(0);
                        cell.setCellValue(req.getWide());
                        cell = row.createCell(1);
                        cell.setCellValue(req.getNum());
                        i++;
                    }
                }

                xssfWorkbook.write(outputStream);//写入输出流
            } else {
                log.severe("文档格式错误");
                throw new Exception("文档格式错误");
            }
        }
        log.info("写入成功");

    }

}

