package com.tester;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.testng.Assert;

/*
 * This is a utility for reading from writing to excel files.
 * it works with xls and xlsx files.
 */
public class ExcelUtils {

    private Sheet workSheet;
    private Workbook workBook;
    private String path;

    public ExcelUtils(String path, String sheetName) {
        this.path = path;
        try {
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(path);
            // Access the required test data sheet
            workBook = WorkbookFactory.create(ExcelFile);
            workSheet = workBook.getSheet(sheetName);
            // check if sheet is null or not. null means  sheetname was wrong
            Assert.assertNotNull(workSheet, "Sheet: \""+sheetName+"\" does not exist\n");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * If you have more than one sheet on your excel file, you might need this.
     * Before starting to work on other sheet that you set first time when you call
     * constructor, you should use this method to set the different sheet
     *
     * @param sheetName
     */
    public void setWorkSheet(String sheetName) {
        this.workSheet = workBook.getSheet(sheetName);
    }


    public String getCellData(int rowNum, int colNum) {
        Cell cell;
        try {
            cell = workSheet.getRow(rowNum).getCell(colNum);
            String cellData = cell.toString();
            return cellData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getDataArray() {

        String[][] data = new String[rowCount()][columnCount()];

        for (int i = 0; i <rowCount(); i++) {
            for (int j = 0; j < columnCount(); j++) {
                String value = getCellData(i, j);
                data[i][j] = value;
            }
        }
        return data;

    }

    //this method will return data table as 2d array
    //so we need this format because of data provider.
    public String[][] getDataArrayWithoutFirstRow() {

        String[][] data = new String[rowCount()-1][columnCount()];

        for (int i = 1; i < rowCount(); i++) {
            for (int j = 0; j < columnCount(); j++) {
                String value = getCellData(i, j);
                data[i-1][j] = value;
            }
        }
        return data;

    }

    /**
     *
     * @return returns Object 2-D array. If excel cell stores numerical value, it gets value as numeric and
     *         convert it to String. So you can use this value without loosing data. Especially phone numbers!
     */
    public Object[][] getDataArrayAsObjectWithoutFirstRow() {

        Object[][] data = new Object[rowCount() - 1][columnCount()];

        for (int i = 1; i < rowCount(); i++) {
            Row row = workSheet.getRow(i);
            for (int j = 0; j < columnCount(); j++) {
                Cell cell = row.getCell(j);
                switch (cell.getCellType()) {
                    case STRING:
                        data[i - 1][j]=workSheet.getRow(i).getCell(j).getStringCellValue();
                        break;
                    case NUMERIC:
                        Double doubleValue =  workSheet.getRow(i).getCell(j).getNumericCellValue();
                        BigDecimal bd = new BigDecimal(doubleValue.toString());
                        Long longValue = bd.longValue();
                        String strValue = Long.toString(longValue).trim();
                        data[i - 1][j]= strValue;
                        break;
                }
            }
        }
        return data;

    }

    public List<Map<String, String>> getDataList() {
        // get all columns
        List<String> columns = getColumnsNames();
        // this will be returned
        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 1; i < rowCount(); i++) {
            // get each row
            Row row = workSheet.getRow(i);
            // create map of the row using the column and value
            // column map key, cell value --> map bvalue
            Map<String, String> rowMap = new HashMap<String, String>();
            for (Cell cell : row) {
                int columnIndex = cell.getColumnIndex();
                rowMap.put(columns.get(columnIndex), cell.toString());
            }

            data.add(rowMap);
        }

        return data;
    }

    public List<String> getColumnsNames() {
        List<String> columns = new ArrayList<>();

        for (Cell cell : workSheet.getRow(0)) {
            columns.add(cell.toString());
        }
        return columns;
    }

    public void setCellData(String value, int rowNum, int colNum) {
        Cell cell;
        Row row;

        try {
            row = workSheet.getRow(rowNum);
            cell = row.getCell(colNum);

            if (cell == null) {
                cell = row.createCell(colNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            workBook.write(fileOut);

            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCellData(String value, String columnName, int row) {
        int column = getColumnsNames().indexOf(columnName);
        setCellData(value, row, column);
    }

    public int columnCount() {
        return workSheet.getRow(0).getLastCellNum();
    }

    public int rowCount() {
        return workSheet.getLastRowNum()+1;
    }

    /**
     *
     * @return returns whether there exist any row except for header
     */
    public boolean doesExistAnyRowExceptForHeader(){
        if (rowCount() == 1){
            System.out.println(rowCount());
            return false;
        }
        System.out.println(rowCount());
        return true;
    }

    /**
     *
     * @param wantedColumnList
     * @return returns a list of map which consists of data related with specified columns only
     */
    public List<Map<String, String>> getDataListForSpecifiedColumns(List<String> wantedColumnList) {
        // getting all columns
        List<String> columns = getColumnsNames();
        // method will return this
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 1; i < rowCount(); i++) {
            // get each row
            Row row = workSheet.getRow(i);
            // creating map of the row using the column and value
            // key=column, value=cell
            Map<String, String> rowMap = new HashMap<String, String>();
            for (Cell cell : row) {
                int columnIndex = cell.getColumnIndex();
                if (wantedColumnList.contains(columns.get(columnIndex))){
                    rowMap.put(columns.get(columnIndex), cell.toString());
                }
            }
            data.add(rowMap);
        }
        return data;
    }

    /**
     *
     * @param columnName
     * @return returns all column data without including header (physical first row).
     */
    public List<String> getGivenColumnValues(String columnName){
        List<String> columnValueList = new ArrayList<>();
        int colNum = getColumnsNames().indexOf(columnName);
        int rowNum = workSheet.getLastRowNum();
        for (int i = 0; i < rowNum; i++) {
            columnValueList.add(getCellData(i + 1,colNum));
        }
        return columnValueList;
    }

    /**
     *
     * @return returns a map which represents last row's data
     */
    public Map<String, String> getLastRow(){
        List<String> columns = getColumnsNames();
        int lastRow = rowCount();
        Row row = workSheet.getRow(lastRow - 1);
        // creating map of the row using the column and value
        // key=column, value=cell
        Map<String, String> rowMap = new HashMap<String, String>();
        for (Cell cell : row) {
            int columnIndex = cell.getColumnIndex();
            rowMap.put(columns.get(columnIndex), cell.toString());
        }
        return rowMap;
    }

    /**
     *
     * @param columnName
     * @param repeatedValue
     * @return returns a random selected row number within a specified columnName
     *         and with specified repeatedValue. (mesela dusun ki farkli satirlarda
     *         ayni degerler tekrar edebiliyor. Tekrar eden bu degerler icinden
     *         rastgele bir secim yapar ve o degerin satir numarasini doner.) 0-based
     */
    public int getRandomRowNumWithinSameProfile(String columnName, String repeatedValue){
        List<String> allValues = getGivenColumnValues(columnName);
        List<Integer> wantedValuesIndexNumberList = new ArrayList<>();
        for (int i = 0; i < allValues.size(); i++) {
            if (allValues.get(i).equals(repeatedValue)){
                wantedValuesIndexNumberList.add(i);
            }
        }
        int max = wantedValuesIndexNumberList.size();
        Random random = new Random();
        int rndNum = random.nextInt(max);
        return wantedValuesIndexNumberList.get(rndNum) + 1;
    }

    /**
     *
     * @param columnName
     * @return returns a randomly selected cell's row number from a specified
     *         column.
     */
    public String getRandomValueFromGivenColumn(String columnName){
        List<String> valueList = getGivenColumnValues(columnName);
        int max = valueList.size();
        Random random = new Random();
        int rndNum = random.nextInt(max);
        return valueList.get(rndNum);
    }

    /**
     *
     * @param columnName
     * @param value
     * @return returns row number (0-based) of a specified value inside a
     *         specified column
     */
    public int getRowIndexNumForGivenValue(String columnName ,String value){
        List<String> columnValueList = getGivenColumnValues(columnName);
        for (int i = 0; i < columnValueList.size(); i++) {
            if (columnValueList.get(i).equals(value)){
                return i + 1;
            }
        }
        return -1;
    }

    /**
     *
     * @param rowIndex
     * @return returns a row as map for a specified row index. (0-based)
     */
    public Map<String, String> getSpecifiedRow(int rowIndex){
        List<String> columns = getColumnsNames();
        Row row = workSheet.getRow(rowIndex);
        // creating map of the row using the column and value
        // key=column, value=cell
        Map<String, String> rowMap = new HashMap<String, String>();
        for (Cell cell : row) {
            int columnIndex = cell.getColumnIndex();
            rowMap.put(columns.get(columnIndex), cell.toString());
        }
        return rowMap;
    }

    /**
     * remove last row if there exist any except for header row.
     */
    public void removeLastRow() {
        int lastRowNumIndex = rowCount() - 1;
        if (lastRowNumIndex != 0) {
            Row row = workSheet.getRow(lastRowNumIndex);
            workSheet.removeRow(row);
        } else {
            System.out.println("just headers exist!!");
        }

        //saving changes
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            workBook.write(fileOutputStream);
            fileOutputStream.close();

            System.out.println(path + " i[enter link description here][1]s successfully written");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * after some changes, you need to save workbook to keep them.
     */
    public void saveWorkBook() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            workBook.write(fileOutputStream);
            fileOutputStream.close();

            System.out.println(path + " changes are saved successfully to the given file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
