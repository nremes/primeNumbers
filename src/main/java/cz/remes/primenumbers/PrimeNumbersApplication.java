package cz.remes.primenumbers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrimeNumbersApplication {

    public static void main(String[] args) {
        List<Double> numbers = new ArrayList<>();
        List<Double> primeNumbers = new ArrayList<>();
        String filepath = "";
        if(args.length > 0) {
            filepath = args[0];
        }

        try {
            FileInputStream file = new FileInputStream(filepath);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case NUMERIC -> {
                            Double num = cell.getNumericCellValue();
                            if (!isFloatingPointNumber(num)) {
                                break;
                            }
                            numbers.add(num);
                        }

                        case STRING -> {
                            String str = cell.getStringCellValue();
                            if (isAlphabet(str)) {
                                break;
                            }
                            str = removeNonAlphabeticChars(str);
                            str = str.replace(",", ".");
                            double num = Double.parseDouble(str);
                            if (num < 1) {
                                break;
                            }
                            if (!isFloatingPointNumber(num)) {
                                break;
                            }
                            numbers.add(num);
                        }
                    }
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage() + ". Enter it as an input argument!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }

        for(double num : numbers) {
            if(isNumberPrime(num)) {
                primeNumbers.add(num);
            }
        }

        System.out.println("-------------------");
        for(double num : primeNumbers) {
            System.out.println((int) num);
        }
        System.out.println("-------------------");
    }


    static private boolean isAlphabet(String str) {
        return str != null && str.matches("^[a-zA-Z]*$");
    }


    static private boolean isFloatingPointNumber(double num) {
        return num == Math.floor(num);
    }


    static private boolean isNumberPrime(double num) {
        if(num == 1) {
            return false;
        }
        for(int i = 2; i < num; i++) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }


    static private String removeNonAlphabeticChars(String str) {
        String finalString = "";
        for(char ch : str.toCharArray()) {
            if(Character.isLetterOrDigit(ch)) {
                finalString += ch;
            }
        }
        return finalString;
    }

}
