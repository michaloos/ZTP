package com.company;

import java.util.ArrayList;
import java.util.List;

public class BuilderKsiazki implements  Builder {

    List<Book> listaksiazek = new ArrayList<>();
    @Override
    public void createElement() {
        Book book = new Book().Bookfake(KsiegarniaSingleton.getInstance(),Ksiegarnia.getInstance());
        listaksiazek.add(book);
    }

    public List<Book> pobierzliste(){
        return listaksiazek;
    }
}
