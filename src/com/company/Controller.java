package com.company;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static com.company.Terminala.dane_studentow;
import static com.company.Terminala.ksiazki;
import static com.company.Terminala.ksiegarnia;

public class Controller {

    //widok interfejsu graficznego
    private Swing swing;

    //"uruchamianie fabryki z metody fabrykującej
    Factory elementfactory = new Factory();

    //konstruktor kontrolera
    public Controller(Swing s){
        swing = s;
        initView();
    }

    //inicjalizacja kontrolera polega w tym przypadku aby labela od stanu miał już odpowiedni kolor przy uruchomieniu
    //trybu graficznego
    public void initView(){
        new Book().inicjalizacja_labela_GUI(ksiegarnia,swing.getjLabelstan());
    }

    //kontorler do obsługi interfejsu w trybie graficznym
    public void initControllerGUI(){
        //dodawanie studenta
        swing.dodajStudentGUI().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Element element = elementfactory.getElement("Student");
                element.dodajGUI(swing.getListModelStudent(),dane_studentow,swing.getFrame(),ksiazki,ksiegarnia,swing.getjLabelstan());
            }
        });
        //usuwanie studenta
        swing.usunStudentaGUI().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Student().UsunStudentaGUI(swing.getListModelStudent(),dane_studentow,swing.getFrame());
            }
        });
        //dodawanie książki
        swing.dodajKsiazkeGUI().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Element element = elementfactory.getElement("Book");
                element.dodajGUI(swing.getListModelBook(),dane_studentow,swing.getFrame(),ksiazki,ksiegarnia,swing.getjLabelstan());
            }
        });
        //usuwanie książki
        swing.usunKsiazkeGUI().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Book().Usun_ksiazke_GUI(swing.getListModelBook(),ksiazki,swing.getFrame(),ksiegarnia,swing.getjLabelstan());
            }
        });
        //wyporzyczanie książki
        swing.wyporzyczKsiazskeGUI().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Book().wyporzycz_ksiazke_GUI(ksiazki,swing.getFrame(),ksiegarnia,swing.getjLabelstan());
            }
        });
        //zwracanie wyporzyczonej książki
        swing.zwrocKsiazkeGUI().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Book().zwroc_ksiazke_GUI(ksiazki,swing.getFrame(),ksiegarnia,swing.getjLabelstan());
            }
        });
        //kupowanie książki
        swing.kupKsiazkeGUI().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Book().kup_ksiazke_GUI(ksiazki,swing.getFrame(),ksiegarnia,swing.getjLabelstan());
            }
        });
        //sortowanie po imieniu
        swing.getButton_sortuj_po_imieniu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Student> sortlistimie = new ArrayList<>(new Student().Sortuj(dane_studentow,true));
                swing.getListModelStudent().clear();
                for(Student student : sortlistimie){
                    swing.getListModelStudent().addElement(student.toString());
                }
            }
        });
        //sortowanie po indeksie
        swing.getButton_sortuj_po_indeksie().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Student> sortlistindeks = new ArrayList<>(new Student().Sortuj(dane_studentow,false));
                swing.getListModelStudent().clear();
                for(Student student : sortlistindeks){
                    swing.getListModelStudent().addElement(student.toString());
                }
            }
        });
        //kończenie sortowania
        swing.getButton_koniec_sortowania().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swing.getListModelStudent().clear();
                for(Student student : dane_studentow){
                    swing.getListModelStudent().addElement(student.toString());
                }
            }
        });
        //informacja o ilości wolnych miejsc w księgarni
        swing.getIlosc_wolnych_miejsc().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ilosc = Integer.toString(ksiegarnia.ilosc_wolynch_miejsc());
                JOptionPane.showMessageDialog(swing.getFrame(),"Ilość wolnych miejsc: " + ilosc);
            }
        });
        //przycisk do wychodzenia z aplikacji
        swing.getWyjscie().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swing.getFrame().dispose();
            }
        });
        //szukanie studenta po indeksie
        swing.getButton_szukaj().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!swing.getText_szukaj_po_indeksie().getText().equals("")){
                    new Student().SzukajStudentaGUI(swing.getListModelStudent(),dane_studentow,swing.getText_szukaj_po_indeksie());
                }
                if(!swing.getText_szukaj_po_tytule().getText().equals("")){
                    new Book().SzukajKsiazkiGUI(swing.getListModelBook(),ksiazki,swing.getText_szukaj_po_tytule());
                }
            }
        });
        //szukanie książki po tytule
        swing.getZakoncz_szukanie().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text_indeks = swing.getText_szukaj_po_indeksie().getText();
                String text_tytul = swing.getText_szukaj_po_tytule().getText();
                if(!swing.getText_szukaj_po_indeksie().getText().equals("")){ swing.getListModelStudent().clear(); }
                if(!swing.getText_szukaj_po_tytule().getText().equals("")){ swing.getListModelBook().clear(); }
                swing.getText_szukaj_po_tytule().setText("");
                swing.getText_szukaj_po_indeksie().setText("");
                //żeby nie ładwać listy kiedy nie było nic szukane
                if(!text_indeks.equals("")){
                    for(Student student : dane_studentow){
                        String string = student.toString();
                        swing.getListModelStudent().addElement(string);
                    }
                }
                if(!text_tytul.equals("")) {
                    for (Book book : ksiazki) {
                        String string = book.toString();
                        swing.getListModelBook().addElement(string);
                    }
                }
            }
        });
        //dokładne informacje o książce
        swing.getPelne_info_book().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!swing.getBookJlist().isSelectionEmpty()) {
                    int selected = swing.getBookJlist().getSelectedIndex();
                    new WypiszKsiazke().wypiszGUI(selected,swing.getFrame(),dane_studentow,ksiazki);
                }
            }
        });
        //dokładne informacje o studencie
        swing.getPelne_info_student().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!swing.getStudentJlist().isSelectionEmpty()) {
                    int selected = swing.getStudentJlist().getSelectedIndex();
                    new WypiszStudenta().wypiszGUI(selected,swing.getFrame(),dane_studentow,ksiazki);
                }
            }
        });
    }
}
