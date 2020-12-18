package com.company;

import com.github.javafaker.Faker;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.*;
import java.util.regex.Pattern;

import static com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK;

public class Student {
    private String name;
    private String nazwisko;
    private int nr_ideksu;
    private int rok_studiow;
    private int ilosc_wyporz_ksiazek;

    private Strategia strategia;

    public Student(String name, String nazwisko, int numer, int rok_studiow, int ilosc_wyporz_ksiazek){
        this.name = name;
        this.nazwisko = nazwisko;
        this.nr_ideksu = numer;
        this.rok_studiow = rok_studiow;
        this.ilosc_wyporz_ksiazek = ilosc_wyporz_ksiazek;
    }

    public Student() {
        name=null;
        nazwisko=null;
        nr_ideksu=0;
        rok_studiow=0;
        ilosc_wyporz_ksiazek=0;
    }

    public String getName(){
        return name;
    }

    public String getNazwisko(){
        return nazwisko;
    }

    public int getNr_ideksu(){
        return nr_ideksu;
    }

    public int getRok_studiow(){
        return rok_studiow;
    }

    public int getIlosc_wyporz_ksiazek() { return ilosc_wyporz_ksiazek; }

    public Student Studentfake(){
        Random random = new Random();
        Faker faker = new Faker();
        String name = faker.name().firstName();//losowe imię
        String surname = faker.name().lastName();//losowe nazwisko
        int indesk = random.nextInt(10000);
        int ilosc_wyporz_ksiazek = random.nextInt(10);
        int rok_studiow = random.nextInt(6);

        return new Student(name,surname,indesk,rok_studiow,ilosc_wyporz_ksiazek);
    }

    public static Comparator<Student>  IndeksCompare = new Comparator<Student>(){
        @Override
        public int compare(Student o1, Student o2) {
            int ind1 = o1.getNr_ideksu();
            int ind2 = o2.getNr_ideksu();
            return ind1 - ind2;
        }
    };

    public static Comparator<Student> ImieCompare = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2) {
            String imie1 = o1.getName().toUpperCase();
            String imie2 = o2.getName().toUpperCase();
            return imie1.compareTo(imie2);
        }
    };

    public List<Student> Sortuj(List<Student> lista, boolean i){
        SortowaniePoImieniu strategiaimie = new SortowaniePoImieniu();
        SortowaniePoIndeksie strategiaindeks = new SortowaniePoIndeksie();
        if(i){
            return new ArrayList<>(strategiaimie.sortowanie(lista));
        }else{
            return new ArrayList<>(strategiaindeks.sortowanie(lista));
        }
    }

    public void SzukajStudenta(WindowBasedTextGUI textGUI, TextBox szukaj_indeks, Table<String> table_student, List<Student> dane_studentow){
        if(dane_studentow.isEmpty()){
            new MessageDialogBuilder()
                    .setTitle("Informacja")
                    .setText("Nie masz czego szukać!\nBrak studentów na liście")
                    .addButton(OK)
                    .build()
                    .showDialog(textGUI);
        }else{
            String x = szukaj_indeks.getText();
            if(x.equals("")){
                new MessageDialogBuilder()
                        .setTitle("Coś poszło nie tak")
                        .setText("Nic nie zostało wpisane\ndo pola wyszukiwania")
                        .addButton(OK)
                        .build()
                        .showDialog(textGUI);
            }else{
                table_student.getTableModel().clear();
                int numer = Integer.parseInt(szukaj_indeks.getText());
                boolean bool = false;
                String imie = null;
                String nazwisko = null;
                for(Student student : dane_studentow){
                    if(numer == student.getNr_ideksu()){
                        bool = true;
                        imie = student.getName();
                        nazwisko = student.getNazwisko();
                    }
                }
                if(bool){
                    String numerind = Integer.toString(numer);
                    table_student.getTableModel().addRow(imie,nazwisko,numerind);
                }
            }
        }
    }

    public void UsunStudenta(WindowBasedTextGUI textGUI,List<Student> dane_studentow, Table<String> table_student){
        if(dane_studentow.isEmpty()){
            new MessageDialogBuilder()
                    .setTitle("Informacja")
                    .setText("Nie masz kogo usunąć!\nBrak studentów na liście")
                    .addButton(OK)
                    .build()
                    .showDialog(textGUI);
        }else{
            String nrindeks = new TextInputDialogBuilder()
                    .setTitle("Usun")
                    .setDescription("Poprzez numer indeksu")
                    .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz numer indeksu")
                    .build()
                    .showDialog(textGUI);
            int indeksnr = Integer.parseInt(nrindeks);
            boolean usun = false;
            int studnr = 0;
            for (Student student : dane_studentow) {
                int numerindeksuint = student.getNr_ideksu();
                studnr++;
                if (numerindeksuint == indeksnr) {
                    usun = true;
                    break;
                }
            }
            if(usun){
                table_student.getTableModel().removeRow(studnr - 1);
                dane_studentow.remove(studnr - 1);
                table_student.setVisibleRows(7);
                table_student.getRenderer();
            }
        }
    }
}
