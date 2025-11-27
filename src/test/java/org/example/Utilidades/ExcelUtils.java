package org.example.Utilidades;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    //Archivo Excel
    private static String testDataExcelFile;
    //Excel WorkBook
    private static XSSFWorkbook excelWBook;
    //Excel Sheet
    private static XSSFSheet excelWSheet;
    //Excel cell
    private static XSSFCell cell;
    //Excel row
    private static XSSFRow row;
    //Row Number
    public static int rowNumber;
    //Column Number
    public static int columnNumber;
    //Setter and Getters of row and columns
    public static void setRowNumber(int pRowNumber) {
        rowNumber = pRowNumber;
    }

    public static int getRowNumber() {
        return rowNumber;
    }

    public static void setColumnNumber(int pColumnNumber) {
        columnNumber = pColumnNumber;
    }

    public static int getColumnNumber() {
        return columnNumber;
    }

    // Este metodo establece la página del archivo excel que se usará
    // Extrae el libro excelWBook y el contenido de la página excelWSheet
    public static void setExcelFileSheet(String testDataExcelPath, String sheetName) throws Exception {

        try {
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(testDataExcelPath);
            testDataExcelFile=testDataExcelPath;
            excelWBook = new XSSFWorkbook(ExcelFile);
            System.out.println(sheetName);
            excelWSheet = excelWBook.getSheet(sheetName);

        } catch (Exception e) {
            try {
                throw (e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //Este método lee datos desde una celda Excel.
    //Se le pasa como parámetro el  número de la fila y el número de la columna.
    public static String getCellData(int RowNum, int ColNum) throws Exception {
        try {
            cell = excelWSheet.getRow(RowNum).getCell(ColNum);
            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
        } catch (Exception e) {
            throw (e);
        }
    }

    //This method takes row number as a parameter and returns the data of given row number.
    public static XSSFRow getRowData(int RowNum) throws Exception {
        try {
            row = excelWSheet.getRow(RowNum);
            return row;
        } catch (Exception e) {
            throw  (e);
        }
    }

    //This method gets excel file, row and column number and set a value to the that cell.
    public static void setCellData(String value, int RowNum, int ColNum) throws Exception {
        try {
            row = excelWSheet.getRow(RowNum);
            cell = row.getCell(ColNum);
            if (cell == null) {
                cell = row.createCell(ColNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            // Constant variables Test Data path and Test Data file name
            FileOutputStream fileOut = new FileOutputStream(testDataExcelFile);
            excelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            try {
                throw (e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
