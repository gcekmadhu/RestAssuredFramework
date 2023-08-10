package com.qa.gorest.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class ExcelUtil {

    private static Workbook wb;
    private static Sheet sh;

    public static Iterator<Object[]> getDataFromExcel(String fileName,String sheetName) throws IOException, InvalidFormatException {
        String filePath="src/test/resources/testdata/"+fileName+".xlsx";
        FileInputStream fis=new FileInputStream(new File(filePath));
        wb=WorkbookFactory.create(fis);
        sh=wb.getSheet(sheetName);

        List<Map<String,String>> listOfMap=new ArrayList<>();
        Map<String,String> dataMap = new HashMap<>();
        for (int i = 0; i < sh.getLastRowNum(); i++) {
            for (int j = 0; j < sh.getRow(0).getLastCellNum(); j++) {
                dataMap.put(sh.getRow(0).getCell(j).toString(),sh.getRow(i + 1).getCell(j).toString());

            }

        }
        listOfMap.add(dataMap);

        System.out.println("------------------------------"+listOfMap);
        Collection<Object[]> dp = new ArrayList<Object[]>();
        for(Map<String,String> e:listOfMap){
            dp.add(new Object[]{e});
        }
        return dp.iterator();



    }



//    private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/APITestData.xlsx";
//    private static Workbook book;
//    private static Sheet sheet;
//
//    public static Object[][] getTestData(String sheetName) {
//
//        Object data[][] = null;
//
//        try {
//            FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
//            book = WorkbookFactory.create(ip);
//            sheet = book.getSheet(sheetName);
//            data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
//
//            for (int i = 0; i < sheet.getLastRowNum(); i++) {
//                for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
//                    data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return data;
//
//    }

}
