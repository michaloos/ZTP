package com.company;

import com.github.javafaker.Faker;
import com.googlecode.lanterna.TextColor;
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
    private String title; //tytuł
    private String autor; //autor
    private int year; //rok wydania
    private int prize; //cena
    private int count; //ilość w księgarni, ilość na stanie

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

    //zwraca tytuł książki
    public String getTitle() {
        return title;
    }
    //zwraca autora książki
    public String getAutor() { return autor; }
    //zwraca rok książki
    public int getYear() {
        return year;
    }
    //zwraca cenę książki
    public int getPrize() {
        return prize;
    }
    //zwraca ilość danej książki
    public int getCount() { return count; }
    //ustawia ilość danej książki w przypadku np zwrócenia książki
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

    //do buildera tworzy listę początkowych książek
    public Book Bookfake(Ksiegarnia ksiegarnia){
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
        ksiegarnia.zmiejsz_ilosc_wolnego_miejsca(ilosc_na_stanie);
        return new Book(title,autor,rok_wydania,cena,ilosc_na_stanie);
    }

    //służy do inicjalizacji labela który pokazuje w jakim stanie są magazyny i półki księgarni
    public void inicjalizacja_labela(Ksiegarnia ksiegarnia, Label stanlabel){
        if(ksiegarnia.ilosc_wolynch_miejsc() > ksiegarnia.ilosc_miejsc_na_poczotku()*0.70){
            stanlabel.setBackgroundColor(new TextColor.RGB(0,255,0));
        }else if(ksiegarnia.ilosc_wolynch_miejsc() > ksiegarnia.ilosc_miejsc_na_poczotku()*0.30){
            stanlabel.setBackgroundColor(new TextColor.RGB(255,255,0));
        }else{
            stanlabel.setBackgroundColor(new TextColor.RGB(255,0,0));
        }
    }

    //zmienia stan labela, między innymi w momencie kiedy książki się dodaje, usuwa, lub wyporzycza
    public Stan zmiana_stanu_labela(int ilosc_wolnych_miejsc, int ilosc_miejsc_na_poczatku, Label stanlabel){
        Stan stan;
        if(ilosc_wolnych_miejsc > ilosc_miejsc_na_poczatku*0.70){
            stan = new StanPrawiePusto(stanlabel);
        }else if(ilosc_wolnych_miejsc > ilosc_miejsc_na_poczatku*0.30){
            stan = new StanZbalansowany(stanlabel);
        }else if(ilosc_wolnych_miejsc == 0){
            stan = new StanPelno(stanlabel);
        }else if(ilosc_wolnych_miejsc == ilosc_miejsc_na_poczatku) {
            stan = new StanPusto(stanlabel);
        }else{
            stan = new StanPrawiePelno(stanlabel);
        }
        return stan;
    }

    //usuwa książkę z księgarni , czyli wszystkie jej egzemplarze
    public void UsunKsiazkeIWszystkieJejEgzemplarze(WindowBasedTextGUI textGUI, List<Book> ksiazki, Table<String> table2_ksiazka,
                                                     Label stanlabel,Ksiegarnia ksiegarnia){
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
        Stan stan ;
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
            ksiegarnia.zwieksz_ilosc_wolnego_miejsca(ile_trzeba_zwolnic_miejsca);
            table2_ksiazka.getTableModel().removeRow(ksiazkanumer - 1);
            ksiazki.remove(ksiazkanumer - 1);
            table2_ksiazka.setVisibleRows(7);
            table2_ksiazka.getRenderer();
            stan = zmiana_stanu_labela(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),stanlabel);
            stan.color();
            stan.tekst();
        }
    }

    //wyporzycza książkę z księgarni
    public void WyporzyczKsiazke(WindowBasedTextGUI textGUI, List<Book> ksiazki,
                                 Label stanlabel, Ksiegarnia ksiegarnia){
        if(ksiegarnia.ilosc_wolynch_miejsc() == 0){
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
                        ksiegarnia.zwieksz_ilosc_wolnego_miejsca(1);
                        stan = zmiana_stanu_labela(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),stanlabel);
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

    //zwraca książkę do księgarni
    public void ZwrotKsiazki(WindowBasedTextGUI textGUI, List<Book> ksiazki,  Ksiegarnia ksiegarnia, Label stanlabel){
        if(ksiegarnia.ilosc_wolynch_miejsc() == ksiegarnia.ilosc_miejsc_na_poczotku()){
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
                    ksiegarnia.zmiejsz_ilosc_wolnego_miejsca(1);
                    ksiazka.setCount(ilosc_na_stanie);
                    stan = zmiana_stanu_labela(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),stanlabel);
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

    //kupuje książkę do księgarni
    public void KupnoKsiazki(WindowBasedTextGUI textGUI, List<Book> ksiazki,  Ksiegarnia ksiegarnia, Label stanlabel){
        if(ksiegarnia.ilosc_wolynch_miejsc() == 0){
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
                        ksiegarnia.zwieksz_ilosc_wolnego_miejsca(1);
                        stan = zmiana_stanu_labela(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),stanlabel);
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

    //szuka kontretnej książki w księgarni
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
