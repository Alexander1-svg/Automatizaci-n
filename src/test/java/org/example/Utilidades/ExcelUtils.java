package org.example.Utilidades;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    public static List<Map<String, String>> getData(String excelFilePath, String sheetName) throws IOException {
        List<Map<String, String>> dataList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        // Leer la primera fila como cabeceras (headers)
        Row headerRow = sheet.getRow(0);
        int totalColumns = headerRow.getLastCellNum();

        // Iterar desde la segunda fila (la primera son los títulos)
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow == null) continue;

            Map<String, String> rowMap = new HashMap<>();

            for (int j = 0; j < totalColumns; j++) {
                Cell cell = currentRow.getCell(j);
                String header = headerRow.getCell(j).getStringCellValue();
                String value = "";

                if (cell != null) {
                    // Usamos DataFormatter para manejar números y textos correctamente
                    DataFormatter formatter = new DataFormatter();
                    value = formatter.formatCellValue(cell);
                }
                rowMap.put(header, value);
            }
            dataList.add(rowMap);
        }

        workbook.close();
        fis.close();
        return dataList;
    }
}
