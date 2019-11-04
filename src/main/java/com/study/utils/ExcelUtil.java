package com.study.utils;

import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.impl.StreamingCell;
import com.monitorjbl.xlsx.impl.StreamingRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtil {
    public static void testLoad() throws Exception {
        FileInputStream in = new FileInputStream("E:\\temp\\aaa.xlsx");
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(in);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
        Sheet sheet = wk.getSheetAt(10);
        for (Row temp : sheet) {
            StreamingRow row = (StreamingRow) temp;
            StreamingCell cell = (StreamingCell) row.getCell(0);
            if (cell != null && !"".equals(cell.getStringCellValue())){
                System.err.println(cell.getStringCellValue());
            }
        }
        /*for (int i = 1; i<= 15; i++) {
            Sheet sheet = wk.getSheetAt(i);
            for(Row row : sheet) {
                StreamingRow row1 = (StreamingRow) row;
                Iterator it = row1.getCellMap().keySet().iterator();
                while (it.hasNext()) {
                    //it.next()得到的是key，tm.get(key)得到obj
                    StreamingCell cell = (StreamingCell) row1.getCellMap().get(it.next());
                }
                *//*if (row.getCell(0) != null){
                    System.out.println(row.getCell(0).getStringCellValue() + "");
                }*//*
            }
            String name = sheet.getSheetName();
            System.err.println(name.substring(name.indexOf(".")+1, name.length()));
        }*/
       /* for (Sheet sheet : wk.get) {

        }*/
        //遍历所有的行
        /*for (Row row : sheet) {
            System.out.println("开始遍历第" + row.getRowNum() + "行数据：");
            System.out.println(row.getCell(1).getStringCellValue() + "");
        }*/
    }

    public static void main(String[] args) throws Exception {
        testLoad();
    }
}
