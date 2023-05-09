package com.emsi.files5.entities;

import java.io.Serializable;
import java.util.Objects;

public class Author implements Serializable {
     private static final long serialVersionUID = 1L;
     private Integer id;
     private String name ;
     private String AboutTheAuthor ;

     public Author(Integer id, String name, String aboutTheAuthor) {
          this.id = id;
          this.name = name;
          AboutTheAuthor = aboutTheAuthor;
     }

     public Integer getId() {
          return id;
     }

     public void setId(Integer id) {
          this.id = id;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getAboutTheAuthor() {
          return AboutTheAuthor;
     }

     public void setAboutTheAuthor(String aboutTheAuthor) {
          AboutTheAuthor = aboutTheAuthor;
     }

     public Author() {
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          Author author = (Author) o;
          return Objects.equals(id, author.id);
     }

     @Override
     public int hashCode() {
          return Objects.hash(id);
     }

     @Override
     public String toString() {
          return "Author{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", AboutTheAuthor='" + AboutTheAuthor + '\'' +
                  '}';
     }
}
