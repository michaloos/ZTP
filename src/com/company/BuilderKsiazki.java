package com.company;

import java.util.ArrayList;
import java.util.List;

public class BuilderKsiazki implements  Builder {

    List<Book> listaksiazek = new ArrayList<>();
    @Override
    public void createElement() {
        Book book = new Book().Bookfake(Terminala.ksiegarnia);
        listaksiazek.add(book);
    }

    //budowniczy zwraca listę utworzonych książek na początku do Directora
    public List<Book> pobierzliste(){
        return listaksiazek;
    }
}
