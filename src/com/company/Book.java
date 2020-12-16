package com.company;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.*;

public class Book {
    private String title;
    private String autor;
    private int year;
    private int prize;
    private int count;

    public Book(){
        title=null;
        autor=null;
        year=0;
        prize=0;
        count=0;
    }

    public Book(String title, String autor, int year, int prize, int count){
        this.title = title;
        this.autor = autor;
        this.year = year;
        this.prize = prize; //jeżeli ktoś nie chce wyporzyczyć, tylko kupic na własność
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public String getAutor() { return autor; }

    public int getYear() {
        return year;
    }

    public int getPrize() {
        return prize;
    }

    public int getCount() { return count; }
    public void setCount(int i) { this.count = i;}

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", autor=" + autor +
                ", year=" + year +
                ", prize=" + prize +
                ", count=" + count +
                '}';
    }

    public Book Bookfake(KsiegarniaSingleton ksiegarniaSingleton){
        Random random = new Random();
        Faker faker = new Faker();
        String autor = faker.name().fullName();//losowanie autora
        String title = faker.book().title();//losowy tytuł książki
        int cena = (int) Double.parseDouble(String.valueOf(Math.pow(random.nextDouble() * 25,2)));
        while(cena < 0){
            cena = (int) Double.parseDouble(String.valueOf(Math.pow(random.nextDouble() * 25,2)));
        }
        int rok_wydania = random.nextInt(2020 - 1800) + 1800;//(max-min)+min
        int ilosc_na_stanie = random.nextInt(70);
        ksiegarniaSingleton.update_miejsca(-ilosc_na_stanie);
        return new Book(title,autor,rok_wydania,cena,ilosc_na_stanie);
    }
}
