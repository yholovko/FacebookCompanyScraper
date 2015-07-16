import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Excel {
    public static List<FacebookCompany> getInformation(String path) {
        List<FacebookCompany> facebookCompanies = new ArrayList<FacebookCompany>();

        try {
            FileInputStream fis = new FileInputStream(path);

            Workbook workbook = new XSSFWorkbook(fis); // Using XSSF for xlsx format, for xls use HSSF
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) { //iterating over each row
                FacebookCompany facebookCompany = new FacebookCompany();
                Iterator cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) { //Iterating over each cell (column wise)  in a particular row.
                    Cell cell = (Cell) cellIterator.next();

                    if (cell.getColumnIndex() == 0) {
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                            facebookCompany = null;
                            break;
                        }
                        facebookCompany.setId((int) cell.getNumericCellValue());
                    } else if (cell.getColumnIndex() == 1) {
                        facebookCompany.setName(String.valueOf(cell.getStringCellValue()));
                    } else if (cell.getColumnIndex() == 2) {
                        String link = String.valueOf(cell.getStringCellValue());
                        if (link.startsWith("https")) {
                            facebookCompany.setLink(link);
                        } else {
                            facebookCompany = null;
                            break;
                        }
                    }
                }
                if (facebookCompany != null) {
                    facebookCompanies.add(facebookCompany);
                }
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return facebookCompanies;
    }

    public static void writeToFile(FacebookCompany facebookCompany, Result result) {
        Workbook workbook = new XSSFWorkbook(); // Using XSSF for xlsx format, for xls use HSSF
        Sheet resultSheet = workbook.createSheet("Sheet1");

        int rowIndex = 0;
        Row row = resultSheet.createRow(rowIndex++);

        row.createCell(0).setCellValue("Name of the company");
        row.createCell(1).setCellValue("Total Likes on Company FB Page");
        row.createCell(2).setCellValue("Total Number of People who have been here");
        row.createCell(3).setCellValue("Text of Post");
        row.createCell(4).setCellValue("Date of Post");
        row.createCell(5).setCellValue("Time of Post");
        row.createCell(6).setCellValue("Who made the post (Company or someone else)");
        row.createCell(7).setCellValue("Number of likes for the post");
        row.createCell(8).setCellValue("Number of shares for the post");
        row.createCell(9).setCellValue("Number of comments for the post");

        for (int i = 0; i < result.getPosts().size(); i++) {
            int cellIndex = 0;

            row = resultSheet.createRow(++rowIndex);
            row.createCell(cellIndex++).setCellValue(result.getNameOfTheCompany());
            row.createCell(cellIndex++).setCellValue(result.getTotalLikesOnCompanyFBPage());
            row.createCell(cellIndex++).setCellValue(result.getTotalNumberOfPeopleWhoHaveBeenHere());

            FacebookPost post = result.getPosts().get(i);
            row.createCell(cellIndex++).setCellValue(post.getTextOfPost());
            row.createCell(cellIndex++).setCellValue(post.getDateOfPost());
            row.createCell(cellIndex++).setCellValue(post.getTimeOfPost());
            row.createCell(cellIndex++).setCellValue(post.getWhoMadeThePost());
            row.createCell(cellIndex++).setCellValue(post.getNumberOfLikesForThePost());
            row.createCell(cellIndex++).setCellValue(post.getNumberOfSharesForThePost());
            row.createCell(cellIndex).setCellValue(post.getNumberOfCommentsForThePost());
        }

        try {
            new File("result").mkdirs();

            File file = new File("result/" + facebookCompany.getId() + ".xlsx");
            if (file.exists()) {
                file.delete();
            }

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}