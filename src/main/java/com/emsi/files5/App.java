package com.emsi.files5;

import com.emsi.files5.entities.Author;
import com.emsi.files5.entities.Book;
import com.emsi.files5.entities.Category;
import com.emsi.files5.service.AuthorService;
import com.emsi.files5.service.BookService;
import com.emsi.files5.service.CategoryService;

import java.util.List;

import static com.emsi.files5.FilesManipulation.FilesManipulation.*;

/**
 * Hello world!
 *
 */
public class App 
{
   public static BookService bookService  = new BookService() ;
   public static AuthorService authorService  = new AuthorService();
   public static CategoryService categoryService  = new CategoryService();
   private static void ClearDataBase() {
       authorService.deleteAll();
       categoryService.deleteAll();
       bookService.deleteAll();

    }

   public static void main( String[] args ) throws Exception
    {
        // Clear DataBase
        ClearDataBase() ;

        // Read  Categories from Text File
        List<Category> categories = extractCategoriesFromFileFormatTxt() ;
        // Load Categories to DataBase
        for (Category category : categories) categoryService.insert(category);

        // Read Authors from Text File
        List<Author> authors = extractAuthorsFromFileFormatTxt() ;
        // Load Authors to DataBase
        for (Author author : authors) authorService.insert(author);

        // Read Books from Text File
        List<Book> books = extractBooksFromFileFormatTxt() ;
        // Load Books to DataBase
        for (Book book : books) bookService.insert(book) ;

        // Read All Books from DataBase and log them to the console
        bookService.findAll().forEach(System.out::println);

        //Load Books to Excel File && Txt File
        stockBooksInExcelSheet(books);
        stockBooksInTxtFile(books);
    }
}
