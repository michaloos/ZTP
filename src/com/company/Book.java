package com.company;

import com.github.javafaker.Faker;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

import static com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK;

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

    public Book Bookfake(KsiegarniaSingleton ksiegarniaSingleton,Ksiegarnia ksiegarnia){
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
        ksiegarnia.zmiejsz_ilosc_wolnego_miejsca(ilosc_na_stanie);
        return new Book(title,autor,rok_wydania,cena,ilosc_na_stanie);
    }

    public void UsunKsiazkeIWszystkieJejEgzemplarze(WindowBasedTextGUI textGUI, List<Book> ksiazki, Table<String> table2_ksiazka,
                                                    KsiegarniaSingleton ksiegarniaSingleton, Label stanlabel,Ksiegarnia ksiegarnia){
        String tytul = new TextInputDialogBuilder()
                .setTitle("Usuń wszystkie egzemplarze")
                .setDescription("Podaj tytuł")
                .setValidationPattern(Pattern.compile("[a-zA-Z0-9 ]*"),"Podaj tytuł")
                .build()
                .showDialog(textGUI);

        String autor = new TextInputDialogBuilder()
                .setTitle("Usuń wszystkie egzemplarze")
                .setDescription("Podaj autora książki")
                .setValidationPattern(Pattern.compile("[a-zA-Z0-9 ]*"),"Podaj autora")
                .build()
                .showDialog(textGUI);
        boolean usun = false;
        int ksiazkanumer = 0;
        int ile_trzeba_zwolnic_miejsca = 0;
        Stan stan;
        for(Book ksiazka : ksiazki){
            String tytul_ksiazki = ksiazka.getTitle();
            String autor_ksiazki = ksiazka.getAutor();
            ksiazkanumer++;
            if(tytul.equals(tytul_ksiazki) && autor.equals(autor_ksiazki)){
                usun = true;
                ile_trzeba_zwolnic_miejsca = ksiazka.getCount();
                break;
            }
        }
        if(usun){
            ksiegarniaSingleton.update_miejsca(ile_trzeba_zwolnic_miejsca);
            ksiegarnia.zwieksz_ilosc_wolnego_miejsca(ile_trzeba_zwolnic_miejsca);
            table2_ksiazka.getTableModel().removeRow(ksiazkanumer - 1);
            ksiazki.remove(ksiazkanumer - 1);
            table2_ksiazka.setVisibleRows(7);
            table2_ksiazka.getRenderer();
            if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.70){
                stan = new StanPrawiePusto(stanlabel);
            }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.30){
                stan = new StanZbalansowany(stanlabel);
            }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == 0){
                stan = new StanPelno(stanlabel);
            }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == ksiegarniaSingleton.ilosc_miejsc_na_poczotku()) {
                stan = new StanPusto(stanlabel);
            }else{
                stan = new StanPrawiePelno(stanlabel);
            }
            stan.color();
            stan.tekst();
        }
    }

    public void WyporzyczKsiazke(WindowBasedTextGUI textGUI, List<Book> ksiazki, KsiegarniaSingleton ksiegarniaSingleton, Label stanlabel){
        if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == 0){
            new MessageDialogBuilder()
                    .setTitle("Informacja")
                    .setText("Brak jakiejkolwiek książki!")
                    .addButton(OK)
                    .build()
                    .showDialog(textGUI);
        }else{
            String tytul = new TextInputDialogBuilder()
                    .setTitle("Usuń wszystkie egzemplarze")
                    .setDescription("Podaj tytuł")
                    .setValidationPattern(Pattern.compile("[a-zA-Z]*"),"podaj tytuł")
                    .build()
                    .showDialog(textGUI);
            String autor = new TextInputDialogBuilder()
                    .setTitle("Usuń wszystkie egzemplarze")
                    .setDescription("Podaj autora książki")
                    .setValidationPattern(Pattern.compile("[a-zA-Z0-9]*"),"podaj autora")
                    .build()
                    .showDialog(textGUI);
            Stan stan;
            for(Book ksiazka : ksiazki){
                String tytul_ksiazki = ksiazka.getTitle();
                String autor_ksiazki = ksiazka.getAutor();
                if(tytul.equals(tytul_ksiazki) && autor.equals(autor_ksiazki)){
                    if(ksiazka.getCount() != 0){
                        ksiazka.setCount(ksiazka.getCount() - 1);
                        ksiegarniaSingleton.update_miejsca(-1);
                        if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.70){
                            stan = new StanPrawiePusto(stanlabel);
                        }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.30){
                            stan = new StanZbalansowany(stanlabel);
                        }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == 0){
                            stan = new StanPelno(stanlabel);
                        }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == ksiegarniaSingleton.ilosc_miejsc_na_poczotku()) {
                            stan = new StanPusto(stanlabel);
                        }else{
                            stan = new StanPrawiePelno(stanlabel);
                        }
                        stan.color();
                        stan.tekst();
                        break;
                    }
                    else{
                        new MessageDialogBuilder()
                                .setTitle("Niepowodzenie")
                                .setText("Książka nie jest narazie dostępna")
                                .addButton(OK)
                                .build()
                                .showDialog(textGUI);
                    }
                }
            }
        }
    }

    public void ZwrotKsiazki(WindowBasedTextGUI textGUI, List<Book> ksiazki,  KsiegarniaSingleton ksiegarniaSingleton, Label stanlabel){
        if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == ksiegarniaSingleton.ilosc_miejsc_na_poczotku()){
            new MessageDialogBuilder()
                    .setTitle("Informacja")
                    .setText("W tym momencie nie masz możliwości\n zwrotu książki!")
                    .addButton(OK)
                    .build()
                    .showDialog(textGUI);
        }else{
            String tytul = new TextInputDialogBuilder()
                    .setTitle("Usuń wszystkie egzemplarze")
                    .setDescription("Podaj tytuł")
                    .setValidationPattern(Pattern.compile("[a-zA-Z]*"), "podaj tytuł")
                    .build()
                    .showDialog(textGUI);
            String autor = new TextInputDialogBuilder()
                    .setTitle("Usuń wszystkie egzemplarze")
                    .setDescription("Podaj autora książki")
                    .setValidationPattern(Pattern.compile("[a-zA-Z0-9]*"), "podaj autora")
                    .build()
                    .showDialog(textGUI);
            int ilosc_na_stanie = 0;
            Stan stan;
            for (Book ksiazka : ksiazki) {
                String tytul_ksiazki = ksiazka.getTitle();
                String autor_ksiazki = ksiazka.getAutor();
                if (tytul.equals(tytul_ksiazki) && autor.equals(autor_ksiazki)) {
                    ilosc_na_stanie = ksiazka.getCount();
                    ilosc_na_stanie++;
                    ksiegarniaSingleton.update_miejsca(1);
                    ksiazka.setCount(ilosc_na_stanie);
                    if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.70){
                        stan = new StanPrawiePusto(stanlabel);
                    }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.30){
                        stan = new StanZbalansowany(stanlabel);
                    }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == 0){
                        stan = new StanPelno(stanlabel);
                    }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == ksiegarniaSingleton.ilosc_miejsc_na_poczotku()) {
                        stan = new StanPusto(stanlabel);
                    }else{
                        stan = new StanPrawiePelno(stanlabel);
                    }
                    stan.color();
                    stan.tekst();
                    new MessageDialogBuilder()
                            .setTitle("Potwierdzenie")
                            .setText("Książka została zwrócona")
                            .addButton(OK)
                            .build()
                            .showDialog(textGUI);
                    break;
                }
            }
        }
    }

    public void KupnoKsiazki(WindowBasedTextGUI textGUI, List<Book> ksiazki,  KsiegarniaSingleton ksiegarniaSingleton, Label stanlabel){
        if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == 0){
            new MessageDialogBuilder()
                    .setTitle("Informacja")
                    .setText("W tym momencie nie masz możliwości\n kupna żadnej książki!")
                    .addButton(OK)
                    .build()
                    .showDialog(textGUI);
        }else{
            String tytul = new TextInputDialogBuilder()
                    .setTitle("Usuń wszystkie egzemplarze")
                    .setDescription("Podaj tytuł")
                    .setValidationPattern(Pattern.compile("[a-zA-Z]*"), "podaj tytuł")
                    .build()
                    .showDialog(textGUI);
            String autor = new TextInputDialogBuilder()
                    .setTitle("Usuń wszystkie egzemplarze")
                    .setDescription("Podaj autora książki")
                    .setValidationPattern(Pattern.compile("[a-zA-Z0-9]*"), "podaj autora")
                    .build()
                    .showDialog(textGUI);
            int ilosc_na_stanie = 0;
            Stan stan;
            for(Book ksiazka : ksiazki){
                String tytul_ksiazki = ksiazka.getTitle();
                String autor_ksiazki = ksiazka.getAutor();
                if(tytul.equals(tytul_ksiazki) && autor.equals(autor_ksiazki)){
                    ilosc_na_stanie = ksiazka.getCount();
                    if(ilosc_na_stanie != 0){
                        ilosc_na_stanie = ksiazka.getCount() - 1;
                        ksiazka.setCount(ilosc_na_stanie);
                        ksiegarniaSingleton.update_miejsca(1);
                        if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.70){
                            stan = new StanPrawiePusto(stanlabel);
                        }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.30){
                            stan = new StanZbalansowany(stanlabel);
                        }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == 0){
                            stan = new StanPelno(stanlabel);
                        }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == ksiegarniaSingleton.ilosc_miejsc_na_poczotku()) {
                            stan = new StanPusto(stanlabel);
                        }else{
                            stan = new StanPrawiePelno(stanlabel);
                        }
                        stan.color();
                        stan.tekst();
                        new MessageDialogBuilder()
                                .setTitle("Potwierdzenie")
                                .setText("Wkrótce dostaniesz dowód do zapłaty")
                                .addButton(OK)
                                .build()
                                .showDialog(textGUI);
                        break;
                    }
                    else{
                        new MessageDialogBuilder()
                                .setTitle("Potwierdzenie")
                                .setText("Takiej książki nie ma lub\nnie ma jej w magazynie")
                                .addButton(OK)
                                .build()
                                .showDialog(textGUI);
                    }
                }
            }
        }
    }

    public void SzukajKsiazki(WindowBasedTextGUI textGUI, Table<String> table2_ksiazka, List<Book> ksiazki, TextBox tytul_szukaj){
        if(ksiazki.isEmpty()){
            new MessageDialogBuilder()
                    .setTitle("Informacja")
                    .setText("W tym momencie nie masz możliwości\n wyszukania żadnej książki!")
                    .addButton(OK)
                    .build()
                    .showDialog(textGUI);
        }else{
            String x = tytul_szukaj.getText();
            if(x.equals("")){
                new MessageDialogBuilder()
                        .setTitle("Coś poszło nie tak")
                        .setText("Nic nie zostało wpisane\ndo pola wyszukiwania")
                        .addButton(OK)
                        .build()
                        .showDialog(textGUI);
            }else{
                table2_ksiazka.getTableModel().clear();
                String tytul = tytul_szukaj.getText();
                String autor = null;
                double cena = 0.0;
                boolean bool = false;
                for(Book ksiazka : ksiazki){
                    if(tytul.equals(ksiazka.getTitle())){
                        bool = true;
                        autor = ksiazka.getAutor();
                        cena = ksiazka.getPrize();
                    }
                }
                if(bool){
                    String prize = Double.toString(cena);
                    table2_ksiazka.getTableModel().addRow(tytul,autor,prize);
                }
            }
        }
    }
}
