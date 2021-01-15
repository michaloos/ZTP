package com.company;

import com.github.javafaker.Faker;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;

import javax.swing.*;
import java.awt.*;
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
        return title + " | " + autor + " | " + prize;
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

    //służy do inicjalizacji labela który pokazuje w jakim stanie są magazyny i półki księgarni w trybie tekstowym
    public void inicjalizacja_labela(Ksiegarnia ksiegarnia, Label stanlabel){
        if(ksiegarnia.ilosc_wolynch_miejsc() > ksiegarnia.ilosc_miejsc_na_poczotku()*0.70){
            stanlabel.setBackgroundColor(new TextColor.RGB(0,255,0));
        }else if(ksiegarnia.ilosc_wolynch_miejsc() > ksiegarnia.ilosc_miejsc_na_poczotku()*0.30){
            stanlabel.setBackgroundColor(new TextColor.RGB(255,255,0));
        }else{
            stanlabel.setBackgroundColor(new TextColor.RGB(255,0,0));
        }
    }

    //służy do inicjalizacji labela który pokazuje w jakim stanie są magazyny i półki księgarni w trybie graficznym
    public void inicjalizacja_labela_GUI(Ksiegarnia ksiegarnia, JLabel jLabelstan){
        if(ksiegarnia.ilosc_wolynch_miejsc() > ksiegarnia.ilosc_miejsc_na_poczotku()*0.70){
            jLabelstan.setBackground(Color.GREEN);
        }else if(ksiegarnia.ilosc_wolynch_miejsc() > ksiegarnia.ilosc_miejsc_na_poczotku()*0.30){
            jLabelstan.setBackground(Color.YELLOW);
        }else{
            jLabelstan.setBackground(Color.RED);
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

    public StanGUI zmiana_stanu_labela_GUI(int ilosc_wolnych_miejsc, int ilosc_miejsc_na_poczatku,JLabel jLabelstan){
        StanGUI stanGUI;
        if(ilosc_wolnych_miejsc > ilosc_miejsc_na_poczatku*0.70){
            stanGUI = new StanPrawiePustoGUI(jLabelstan);
        }else if(ilosc_wolnych_miejsc > ilosc_miejsc_na_poczatku*0.30){
            stanGUI = new StanZbalansowanyGUI(jLabelstan);
        }else if(ilosc_wolnych_miejsc == 0){
            stanGUI = new StanPelnoGUI(jLabelstan);
        }else if(ilosc_wolnych_miejsc == ilosc_miejsc_na_poczatku) {
            stanGUI = new StanPustoGUI(jLabelstan);
        }else{
            stanGUI = new StanPrawiePelnoGUI(jLabelstan);
        }
        return stanGUI;
    }

    //pole do wpisania czegoś w trybie tekstowym
    public String input(WindowBasedTextGUI textGUI,String title,String description,String validation){
        return new TextInputDialogBuilder()
                .setTitle(title)
                .setDescription(description)
                .setValidationPattern(Pattern.compile("[a-zA-Z0-9 ]*"),validation)
                .build()
                .showDialog(textGUI);
    }

    //wiadomość / informacja w trybie tekstowym
    public void message(WindowBasedTextGUI textGUI, String title, String description){
        new MessageDialogBuilder()
                .setTitle(title)
                .setText(description)
                .addButton(OK)
                .build()
                .showDialog(textGUI);
    }

    //usuwa książkę z księgarni , czyli wszystkie jej egzemplarze w trybie tekstowym
    public void UsunKsiazkeIWszystkieJejEgzemplarze(WindowBasedTextGUI textGUI, List<Book> ksiazki, Table<String> table2_ksiazka,
                                                     Label stanlabel,Ksiegarnia ksiegarnia){
        String tytul = input(textGUI,"Usuń wszystkie egzemplarze","Podaj tytuł","Podaj tytuł");
        String autor = input(textGUI,"Usuń wszystkie egzemplarze","Podaj autora książki","Podaj autora");
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


    //usuwa książkę z księgarni , czyli wszystkie jej egzemplarze w trybie graficznym
    public void Usun_ksiazke_GUI(DefaultListModel<String> lista, List<Book> dane, JFrame frame,
                                 Ksiegarnia ksiegarnia, JLabel jLabelstan){
        String tytul = JOptionPane.showInputDialog(frame, "Podaj tytuł książki do usunięcia:");
        String autor = JOptionPane.showInputDialog(frame, "Podaj autora książki do usunięcia:");
        boolean usun = false;
        int booknr = 0;
        int ile_trzeba_zwolnic_miejsca = 0;
        StanGUI stanGUI;
        for(Book book : dane){
            String tytul_ksiazki = book.getTitle();
            String autor_ksiazki = book.getAutor();
            booknr++;
            if(tytul.equals(tytul_ksiazki) && autor.equals(autor_ksiazki)){
                usun = true;
                ile_trzeba_zwolnic_miejsca = book.getCount();
                break;
            }
        }
        if(usun){
            ksiegarnia.zwieksz_ilosc_wolnego_miejsca(ile_trzeba_zwolnic_miejsca);
            lista.removeElementAt(booknr - 1);
            dane.remove(booknr - 1);
            stanGUI = zmiana_stanu_labela_GUI(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),jLabelstan);
            stanGUI.tekst();
            stanGUI.color();
            JOptionPane.showMessageDialog(frame,"Książka została pomyślnie usunięta.");
        }else{
            JOptionPane.showMessageDialog(frame,"Nie posiadamy takiej książki.");
        }
    }

    //wyporzycza książkę z księgarni w trybie tekstowym
    public void WyporzyczKsiazke(WindowBasedTextGUI textGUI, List<Book> ksiazki,
                                 Label stanlabel, Ksiegarnia ksiegarnia){
        if(ksiegarnia.ilosc_wolynch_miejsc() == ksiegarnia.ilosc_miejsc_na_poczotku()){
            message(textGUI,"Informacja","Brak jakiejkolwiek książki!");
        }else{
            String tytul = input(textGUI,"Wyporzycz książkę","Podaj tytuł","podaj tytuł");
            String autor = input(textGUI,"Wyporzycz książkę","Podaj autora książki","podaj autora");
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
                        message(textGUI,"Niepowodzenie","Książka nie jest narazie dostępna!");
                    }
                }
            }
        }
    }

    //zwraca książkę do księgarni w trybie tekstowym
    public void ZwrotKsiazki(WindowBasedTextGUI textGUI, List<Book> ksiazki,  Ksiegarnia ksiegarnia, Label stanlabel){
        if(ksiegarnia.ilosc_wolynch_miejsc() == 0){
            message(textGUI,"Informacja","W tym momencie nie masz możliwości\n zwrotu książki!");
        }else{
            String tytul = input(textGUI,"Zwróć książkę","Podaj tytuł","podaj tytuł");
            String autor = input(textGUI,"Zwróć książkę","Podaj autora książki","podaj autora");
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
                    message(textGUI,"Potwierdzenie","Książka została zwrócona!");
                    break;
                }
            }
        }
    }

    //kupuje książkę do księgarni w trybie tekstowym
    public void KupnoKsiazki(WindowBasedTextGUI textGUI, List<Book> ksiazki,  Ksiegarnia ksiegarnia, Label stanlabel){
        if(ksiegarnia.ilosc_wolynch_miejsc() == ksiegarnia.ilosc_miejsc_na_poczotku()){
            message(textGUI,"Informacja","W tym momencie nie masz możliwości\n kupna żadnej książki!");
        }else{
            String tytul = input(textGUI,"Kup książkę","Podaj tytuł","podaj tytuł");
            String autor = input(textGUI,"Kup książkę","Podaj autora książki","podaj autora");
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
                        message(textGUI,"Potwierdzenie","Wkrótce dostaniesz dowód do zapłaty");
                        break;
                    }
                    else{
                        message(textGUI,"Potwierdzenie","Takiej książki nie ma lub\nnie ma jej w magazynie");
                    }
                }
            }
        }
    }

    //szuka kontretnej książki w księgarni w trybie tekstowym
    public void SzukajKsiazki(WindowBasedTextGUI textGUI, Table<String> table2_ksiazka, List<Book> ksiazki, TextBox tytul_szukaj){
        if(ksiazki.isEmpty()){
            message(textGUI,"Informacja","W tym momencie nie masz możliwości\n wyszukania żadnej książki");
        }else{
            String x = tytul_szukaj.getText();
            if(x.equals("")){
                message(textGUI,"Coś poszło nie tak","Nic nie zostało wpisane\ndo pola wyszukiwania!");
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

    //szukanie książki w trybie graficznym
    public void SzukajKsiazkiGUI(DefaultListModel<String> lista, List<Book> dane,JTextField jTextField){
        String tytul = jTextField.getText();
        lista.clear();
        for(Book book : dane){
            String tytul_ksiazki = book.getTitle();
            if(tytul.equals(tytul_ksiazki)){
                lista.addElement(book.toString());
            }
        }
    }

    //wyporzyczanie książki w trybie graficznym
    public void wyporzycz_ksiazke_GUI(List<Book> dane, JFrame frame, Ksiegarnia ksiegarnia, JLabel jLabelstan){
        if(ksiegarnia.ilosc_wolynch_miejsc() == ksiegarnia.ilosc_miejsc_na_poczotku()){
            JOptionPane.showMessageDialog(frame,"Brak książek do wyporzyczenia.\nPrzepraszamy.");
        }else{
            String tytul = JOptionPane.showInputDialog(frame, "Podaj tytuł książki do wyporzyczenia:");
            String autor = JOptionPane.showInputDialog(frame, "Podaj autora książki do wyporzyczenia:");
            boolean wyporzycz = false;
            StanGUI stanGUI;
            for(Book book : dane){
                String tytul_ksiazki = book.getTitle();
                String autor_ksiazki = book.getAutor();
                if(tytul.equals(tytul_ksiazki) && autor.equals(autor_ksiazki)){
                    wyporzycz = true;
                    if(book.getCount() != 0){
                        book.setCount(book.getCount() - 1);
                        ksiegarnia.zwieksz_ilosc_wolnego_miejsca(1);
                        stanGUI = zmiana_stanu_labela_GUI(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),jLabelstan);
                        stanGUI.color();
                        stanGUI.tekst();
                        JOptionPane.showMessageDialog(frame,"Książka została wyporzyczona.");
                        break;
                    }else{
                        JOptionPane.showMessageDialog(frame,"Takiej książki nie\n ma w magazynie");
                        break;
                    }
                }
            }
            if(!wyporzycz){
                JOptionPane.showMessageDialog(frame,"Nie posiadamy takiej książki");
            }
        }
    }

    //zwracanie książki w trybie graficznym
    public void zwroc_ksiazke_GUI(List<Book> dane, JFrame frame,Ksiegarnia ksiegarnia, JLabel jLabelstan){
        if(ksiegarnia.ilosc_wolynch_miejsc() == 0){
            JOptionPane.showMessageDialog(frame,"W tym momencie nie masz możliwości\n zwrotu książki!");
        }else {
            String tytul = JOptionPane.showInputDialog(frame, "Podaj tytuł książki do zwrotu:");
            String autor = JOptionPane.showInputDialog(frame, "Podaj autora książki do zwrotu:");
            boolean zwroc = false;
            StanGUI stanGUI;
            for (Book book : dane) {
                String tytul_ksiazki = book.getTitle();
                String autor_ksiazki = book.getAutor();
                if (tytul.equals(tytul_ksiazki) && autor.equals(autor_ksiazki)) {
                    zwroc = true;
                    int ilosc = book.getCount();
                    book.setCount(ilosc + 1);
                    ksiegarnia.zmiejsz_ilosc_wolnego_miejsca(1);
                    stanGUI = zmiana_stanu_labela_GUI(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),jLabelstan);
                    stanGUI.tekst();
                    stanGUI.color();
                    JOptionPane.showMessageDialog(frame, "Dziękujemy za skorzystanie z naszych usług.\nZapraszmy ponownie");
                    break;
                }
            }
            if (!zwroc) {
                JOptionPane.showMessageDialog(frame, "Nie mamy takich książek. Sprawdź w innej księgarni");
            }
        }
    }

    //kupowanie książki w trybie graficznym
    public void kup_ksiazke_GUI(List<Book> dane, JFrame frame, Ksiegarnia ksiegarnia, JLabel jLabelstan){
        if(ksiegarnia.ilosc_wolynch_miejsc() == ksiegarnia.ilosc_miejsc_na_poczotku()){
            JOptionPane.showMessageDialog(frame,"W tym momencie nie masz możliwości\n kupna żadnej książki!");
        }else{
            String tytul = JOptionPane.showInputDialog(frame, "Podaj tytuł książki do kupienia:");
            String autor = JOptionPane.showInputDialog(frame, "Podaj autora książki do kupienia:");
            boolean wyporzycz = false;
            StanGUI stanGUI;
            for(Book book : dane){
                String tytul_ksiazki = book.getTitle();
                String autor_ksiazki = book.getAutor();
                if(tytul.equals(tytul_ksiazki) && autor.equals(autor_ksiazki)){
                    wyporzycz = true;
                    if(book.getCount() != 0){
                        book.setCount(book.getCount() - 1);
                        ksiegarnia.zwieksz_ilosc_wolnego_miejsca(1);
                        stanGUI = zmiana_stanu_labela_GUI(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),jLabelstan);
                        stanGUI.color();
                        stanGUI.tekst();
                        JOptionPane.showMessageDialog(frame,"Książka została kupiona.\nNiedługo dostaniesz dowód do zapłaty.\nDziękujemy");
                        break;
                    }else{
                        JOptionPane.showMessageDialog(frame,"Książki tej nie ma aktualnie w magazynie.");
                        break;
                    }
                }
            }
            if(!wyporzycz){
                JOptionPane.showMessageDialog(frame,"Nie posiadamy takiej książki");
            }
        }
    }
}
