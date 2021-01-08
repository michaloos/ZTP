package com.company;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;

import javax.swing.*;
import java.util.List;
import java.util.regex.Pattern;

import static com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK;

//uproszczenie wywoływanie funkcji do pobierania danych o nowym obiekcie
//stąd dziwne nazwy jak "dodaj_imie_tytul"
public interface FasadaToAdd {
    String dodaj_imie_tytul(WindowBasedTextGUI textGUI);
    String dodaj_nazwisko_autor(WindowBasedTextGUI textGUI);
    int dodaj_indeks_rok(WindowBasedTextGUI textGUI);
    int dodaj_rok_studiow_cena(WindowBasedTextGUI textGUI);
    int dodaj_iloscwyp_ilosc_nastanie(WindowBasedTextGUI textGUI);
    String dodaj_imie_tytulGUI(JFrame frame);
    String dodaj_nazwisko_autorGUI(JFrame frame);
    int dodaj_indeks_rokGUI(JFrame frame);
    int dodaj_rok_studiow_cenaGUI(JFrame frame);
    int dodaj_iloscwyp_ilosc_nastanieGUI(JFrame frame);
}

//klasa która łączy wszystkie "prośby" o informacje w jedno
class Polacz{
    private AddStudentFasada studentFasada = new AddStudentFasada();
    private AddBookFasada bookFasada = new AddBookFasada();

    public void dodajstudenta(WindowBasedTextGUI textGUI, Table<String> table_student, List<Student> dane_studentow){
        String imie = studentFasada.dodaj_imie_tytul(textGUI);
        String nazwisko = studentFasada.dodaj_nazwisko_autor(textGUI);
        int indeks = studentFasada.dodaj_indeks_rok(textGUI);
        int rok_studiow = studentFasada.dodaj_rok_studiow_cena(textGUI);
        int ilosc_wyporz = studentFasada.dodaj_iloscwyp_ilosc_nastanie(textGUI);
        Student student = new Student(imie,nazwisko,indeks,rok_studiow,ilosc_wyporz);
        dane_studentow.add(student);
        String stringindeks = Integer.toString(indeks);
        table_student.getTableModel().addRow(imie,nazwisko,stringindeks);
    }

    public void dodajksiakze(WindowBasedTextGUI textGUI, Table<String> table2_ksiazki,
                             List<Book> ksiazki, Label stanlabel,Ksiegarnia ksiegarnia){
        String tytul = bookFasada.dodaj_imie_tytul(textGUI);
        String autor = bookFasada.dodaj_nazwisko_autor(textGUI);
        int rok = bookFasada.dodaj_indeks_rok(textGUI);
        int cena = bookFasada.dodaj_rok_studiow_cena(textGUI);
        int ilosc_na_stanie = bookFasada.dodaj_iloscwyp_ilosc_nastanie(textGUI);
        Stan stan;
        if(ilosc_na_stanie > ksiegarnia.ilosc_wolynch_miejsc()){
            new MessageDialogBuilder()
                    .setTitle("Coś poszło nie tak")
                    .setText("Nie będzie miejsca na tyle książek!")
                    .addButton(OK)
                    .build()
                    .showDialog(textGUI);
        }else{
            Book book = new Book(tytul,autor,rok,cena,ilosc_na_stanie);
            ksiazki.add(book);
            String stringcena = Integer.toString(cena);
            table2_ksiazki.getTableModel().addRow(tytul,autor,stringcena);
            ksiegarnia.zmiejsz_ilosc_wolnego_miejsca(ilosc_na_stanie);
            stan = book.zmiana_stanu_labela(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),stanlabel);
            stan.color();
            stan.tekst();
        }
    }

    public void dodajStudentaGUI(JFrame frame, List<Student> dane_studentow,DefaultListModel<String> lista){
        String imie = studentFasada.dodaj_imie_tytulGUI(frame);
        String nazwisko = studentFasada.dodaj_nazwisko_autorGUI(frame);
        int indeks = studentFasada.dodaj_indeks_rokGUI(frame);
        int rok_studiow = studentFasada.dodaj_rok_studiow_cenaGUI(frame);
        int ilosc_wyporz_ksiazek = studentFasada.dodaj_iloscwyp_ilosc_nastanieGUI(frame);
        Student student = new Student(imie,nazwisko,indeks,rok_studiow,ilosc_wyporz_ksiazek);
        dane_studentow.add(student);
        lista.addElement(student.toString());
    }

    public void dodajKsiazkeGUI(JFrame frame,List<Book> ksiazki, DefaultListModel<String> lista, JLabel jLabelstan,Ksiegarnia ksiegarnia){
        String tytul = bookFasada.dodaj_imie_tytulGUI(frame);
        String autor = bookFasada.dodaj_nazwisko_autorGUI(frame);
        int rok_wydania = bookFasada.dodaj_indeks_rokGUI(frame);
        int cena = bookFasada.dodaj_rok_studiow_cenaGUI(frame);
        int ilosc_na_stanie = bookFasada.dodaj_iloscwyp_ilosc_nastanieGUI(frame);
        StanGUI stanGUI;
        if(ilosc_na_stanie > ksiegarnia.ilosc_wolynch_miejsc()){
            JOptionPane.showMessageDialog(frame,"Coś poszło nie tak.\n Nie ma miejsca na tyle książek.");
        }else{
            Book book = new Book(tytul,autor,rok_wydania,cena,ilosc_na_stanie);
            ksiazki.add(book);
            lista.addElement(book.toString());
            ksiegarnia.zmiejsz_ilosc_wolnego_miejsca(ilosc_na_stanie);
            stanGUI = book.zmiana_stanu_labela_GUI(ksiegarnia.ilosc_wolynch_miejsc(),ksiegarnia.ilosc_miejsc_na_poczotku(),jLabelstan);
            stanGUI.color();
            stanGUI.tekst();
        }


    }
}