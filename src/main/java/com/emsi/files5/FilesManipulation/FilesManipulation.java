package com.emsi.files5.FilesManipulation;

import com.emsi.files5.entities.Author;
import com.emsi.files5.entities.Book;
import com.emsi.files5.entities.Category;
import com.emsi.files5.service.AuthorService;
import com.emsi.files5.service.CategoryService;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FilesManipulation {
    private static AuthorService authorService = new AuthorService();
    private static CategoryService categoryService = new CategoryService();

    // Txt Format
    public static void stockBooksInTxtFile(List<Book> books) throws Exception {
        try (FileOutputStream fout = new FileOutputStream("/home/hamza/IdeaProjects/files5/src/main/resources/outputData")) {
            for (Book book : books) {
                fout.write(book.toString().getBytes());
                fout.write('\n');
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
    public static List<Book> extractBooksFromFileFormatTxt() throws Exception
    {
        List<Book> books = new ArrayList<>() ;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/hamza/IdeaProjects/files5/src/main/resources/inputData")) ;
        String readLine = bufferedReader.readLine();
        while(readLine != null){
            if (readLine.startsWith("//")) {readLine = bufferedReader.readLine();continue;}
            String[] livreStr = readLine.split("\\*\\*");
            Book book = buildBookFromStringArray(livreStr);
            books.add(book);
            readLine = bufferedReader.readLine();
        }
        return books ;
    }
    // Excel Format
    public static void stockBooksInExcelSheet(List<Book> books) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet();
        XSSFRow row;
        int rowId = 0 ;
        String[] tableHeader =  initialiseTableHeader() ;
        row = spreadsheet.createRow(rowId++);
        int cellidh = 0;
        for (String str : tableHeader) {
            Cell cell = row.createCell(cellidh++);
            cell.setCellValue(str);
        }
        for (Book book : books) {
            row = spreadsheet.createRow(rowId++);
            String[] stringArr = convertBookToStringArr(book) ;
            int cellid = 0;
            for (String str : stringArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(str);
            }
        }
        FileOutputStream out = new FileOutputStream(new File("/home/hamza/IdeaProjects/files5/src/main/resources/outputDataExcel.xlsx"));
        workbook.write(out);
        out.close();
      //  System.out.println("DONE");
}
    public static List<Book> extractBooksFromFileFormatExcel()
    {
        List<Book> books = new ArrayList<>() ;
        XSSFRow row ;
        try(FileInputStream fis = new FileInputStream(new File("/home/hamza/IdeaProjects/files5/src/main/resources/inputDataExcel.xlsx")))
        {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = spreadsheet.iterator();
            while (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                Book book = buildBookFromRow(row);
                books.add(book) ;
            }
        }
        catch (FileNotFoundException e) {}
        catch (IOException e) {}
        return books ;
    }


    // Helper Functions

    private static String[] convertBookToStringArr(Book book)
    {
        String[] objects = new String[12] ;
        objects[0] = book.getId()+"";
        objects[1] = book.getName() ;
        objects[2] = book.getIsbn() ;
        objects[3] = book.getNumberOfPages()+"" ;
        objects[4] = book.getPrice()+" $" ;
        objects[5] = book.getPublishedAt().toString() ;
        objects[6] = (book.getAuthor()!=null ? book.getAuthor().getId()+"" : "null") ;
        objects[7] = (book.getAuthor()!=null ? book.getAuthor().getName() : " ") ;
        objects[8] = (book.getAuthor()!=null ? book.getAuthor().getAboutTheAuthor(): " ") ;
        objects[9] = (book.getCategory()!=null ? book.getCategory().getId()+"" : "null") ;
        objects[10] = (book.getCategory()!=null ? book.getCategory().getName() : " ") ;
        objects[11] = (book.getCategory()!=null ? book.getCategory().getDescription() : " ") ;
        return objects ;
     }
    private static Book buildBookFromStringArray(String[] strings) throws Exception
    {
       Book book = new Book() ;
       book.setName(strings[0]);
       book.setIsbn(strings[1]);
       book.setNumberOfPages(Integer.parseInt(strings[2]));
       book.setPrice(Float.parseFloat(strings[3]));
       book.setPublishedAt(new SimpleDateFormat("dd/MM/yyyy").parse(strings[4]));
       book.setAuthor(authorService.findByName(strings[5])) ;
       book.setCategory(categoryService.findByName(strings[6]));
       Author author = authorService.findByName(strings[5]);
     //  System.out.println(author);
       return book ;
    }

    private static String[] initialiseTableHeader() {
        String[] TableHeader = new String[12] ;
        TableHeader[0] = "Book ID";
        TableHeader[1] = "Book Name" ;
        TableHeader[2] = "Isbn" ;
        TableHeader[3] = "Number Of Pages" ;
        TableHeader[4] = "Price" ;
        TableHeader[5] = "Publication Date" ;
        TableHeader[6] = "Author ID" ;
        TableHeader[7] = "Author Name" ;
        TableHeader[8] = "About the Author" ;
        TableHeader[9] = "Category ID" ;
        TableHeader[10] = "Category" ;
        TableHeader[11] = "Description" ;
        return TableHeader ;
    }
    private static Book buildBookFromRow(XSSFRow row)
    {
        Book book = new Book() ;
        Iterator < Cell >  cellIterator = row.cellIterator();
        book.setId((int)cellIterator.next().getNumericCellValue());
        book.setName(cellIterator.next().toString());
        book.setIsbn(cellIterator.next().toString());
        book.setNumberOfPages((int)cellIterator.next().getNumericCellValue());
        book.setPrice((float)cellIterator.next().getNumericCellValue());
        book.setPublishedAt(cellIterator.next().getDateCellValue());
        Author author = new Author((int)cellIterator.next().getNumericCellValue(),cellIterator.next().toString(),cellIterator.next().toString()) ;
        book.setAuthor(author);
        Category category = new Category((int)cellIterator.next().getNumericCellValue(),cellIterator.next().toString(),cellIterator.next().toString()) ;
        book.setCategory(category);
        return book ;
    }

    public static List<Author> extractAuthorsFromFileFormatTxt() throws Exception{
        List<Author> authors = new ArrayList<>() ;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/hamza/IdeaProjects/files5/src/main/resources/inputDataAuthor")) ;
        String readLine = bufferedReader.readLine();
        while(readLine != null){
            String [] livreStr  = readLine.split("\\*\\*");
            Author author =  buildAuthorFromStringArray(livreStr) ;
            authors.add(author) ;
            readLine = bufferedReader.readLine();
        }
        return authors ;
    }

    private static Author buildAuthorFromStringArray(String[] strings) {
        Author author = new Author() ;
        author.setName(strings[0]);
        author.setAboutTheAuthor(strings[1]);
        return author ;
    }

    public static List<Category> extractCategoriesFromFileFormatTxt() throws Exception{
        List<Category> categories = new ArrayList<>() ;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/hamza/IdeaProjects/files5/src/main/resources/inputDataCategory")) ;
        String readLine = bufferedReader.readLine();
        while(readLine != null){
            String [] livreStr  = readLine.split("\\*\\*");
            Category category =  buildCategoryFromStringArray(livreStr) ;
            categories.add(category) ;
            readLine = bufferedReader.readLine();
        }
        return categories ;
    }
    private static Category buildCategoryFromStringArray(String[] strings) {
        Category category = new Category() ;
        category.setName(strings[0]);
        category.setDescription(strings[1]);
        return category ;
    }


//    private static String book_to_string(Book book)
//    {
//        return  "Book : " + book.getId() + "\n" +
//                "   name : " + book.getName()  + "\n" +
//                "   isbn : " + book.getIsbn() + "\n" +
//                "   number of pages : " + book.getNumberOfPages() + "\n" +
//                "   price : " + book.getPrice() + " $ \n" +
//                "   publication date  : " + book.getPublishedAt() + "\n" +
//                "   Author : " + book.getAuthor().getId() + "\n" +
//                "       name  : " + book.getAuthor().getName() + "\n" +
//                "       about the author : " + book.getAuthor().getAboutTheAuthor() + "\n" +
//                "   Category : " + book.getCategory().getId() + "\n" +
//                "       name  : " + book.getCategory().getName() + "\n" +
//                "       description  : " + book.getCategory().getDescription() + "\n";
//
//    }
}
