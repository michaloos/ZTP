package com.company;
import com.github.javafaker.Faker;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.Terminala.*;

public class Swing {

    public static void SwingGUI(){

        //okno dla trybu graficznego
        JFrame frame = new JFrame("Księgarnia");

        //listy ze studentami i książkami , które pochodzą z wcześniejszego wywołania terminala tekstowego
        List<Student> dane_studentow = new ArrayList<Student>(Terminala.dane_studentow);
        List<Book> ksiazki = new ArrayList<Book>(Terminala.ksiazki);

        //listy do wyświetlania list ze studentami i książkami
        DefaultListModel<String> listModelStudent = new DefaultListModel<>();
        for(Student student : dane_studentow){
            String string = student.toString();
            listModelStudent.addElement(string);
        }
        final JList<String> studnetJList = new JList<String>(listModelStudent);
        studnetJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane jScrollPanestudent = new JScrollPane();
        jScrollPanestudent.setViewportView(studnetJList);
        studnetJList.setLayoutOrientation(JList.VERTICAL);
        jScrollPanestudent.setBounds(450,40,250,250);
        frame.add(jScrollPanestudent);

        JLabel listastud = new JLabel("Lista Studentów:(Imie | Nazwisko | indeks)");
        listastud.setBounds(450,10,250,30);
        frame.add(listastud);

        //dodawanie elementów do listy książek
        DefaultListModel<String> listModelBook = new DefaultListModel<>();
        for(Book book : ksiazki){
            String string = book.toString();
            listModelBook.addElement(string);
        }
        final JList<String> bookJlist = new JList<String>(listModelBook);
        bookJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane jScrollPanebook = new JScrollPane();
        jScrollPanebook.setViewportView(bookJlist);
        bookJlist.setLayoutOrientation(JList.VERTICAL);
        jScrollPanebook.setBounds(720,40,250,250);
        frame.add(jScrollPanebook);

        //przycisk do wyświetlania informacji o studencie (dokładnych informacji)
        JButton pelne_info_student = new JButton("Informacje o studencie");
        pelne_info_student.setBounds(450,300,250,30);
        pelne_info_student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!studnetJList.isSelectionEmpty()) {
                    int selected = studnetJList.getSelectedIndex();
                    new WypiszStudenta().wypiszGUI(selected,frame,dane_studentow,ksiazki);
                }
            }
        });
        frame.add(pelne_info_student);

        //przycisk do wyświetlania informacji o książce (dokładnych informacji)
        JButton pelne_info_book = new JButton("Informcje o książce");
        pelne_info_book.setBounds(720,300,250,30);
        pelne_info_book.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!bookJlist.isSelectionEmpty()) {
                    int selected = bookJlist.getSelectedIndex();
                    new WypiszKsiazke().wypiszGUI(selected,frame,dane_studentow,ksiazki);
                }
            }
        });
        frame.add(pelne_info_book);

        JLabel listabook = new JLabel("Lista Książek:(Tytuł | autor | cena)");
        listabook.setBounds(720,10,250,30);
        frame.add(listabook);

        JLabel glowny_label = new JLabel(" Wybierz co chcesz zrobić.");
        glowny_label.setBounds(10,10,200,30);
        frame.add(glowny_label);

        //"fabryka" w trybie graficznym
        Factory elementfactory = new Factory();

        //label użyty do stanu magazynu
        JLabel jLabelstan = new JLabel("Stan magazynu");
        jLabelstan.setBounds(190,330,220,70);
        jLabelstan.setOpaque(true);
        frame.add(jLabelstan);

        //wywołanie inicjalizacji labela w trybie graficznym aby przy uruchomieniu już był odpowiedni kolor
        new Book().inicjalizacja_labela_GUI(ksiegarnia,jLabelstan);

        //dodawanie studenta
        JButton dodaj_studenta = new JButton("Dodaj studenta.");
        dodaj_studenta.setBounds(10,50,150,30);
        dodaj_studenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Element element = elementfactory.getElement("Student");
                element.dodajGUI(listModelStudent,dane_studentow,frame,ksiazki,ksiegarnia,jLabelstan);
            }
        });
        frame.add(dodaj_studenta);

        //usuwanie studenta
        JButton usun_studenta = new JButton("Usun studenta.");
        usun_studenta.setBounds(10,90,150,30);
        usun_studenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Student().UsunStudentaGUI(listModelStudent,dane_studentow,frame);
            }
        });
        frame.add(usun_studenta);

        //dodawanie książki
        JButton dodaj_ksiazke = new JButton("Dodaj książkę.");
        dodaj_ksiazke.setBounds(210,50,150,30);
        dodaj_ksiazke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Element element = elementfactory.getElement("Book");
                element.dodajGUI(listModelBook,dane_studentow,frame,ksiazki,ksiegarnia,jLabelstan);
            }
        });
        frame.add(dodaj_ksiazke);

        //usuwanie książki
        JButton usun_ksiazke = new JButton("Usuń książkę.");
        usun_ksiazke.setBounds(210,90,150,30);
        usun_ksiazke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Book().Usun_ksiazke_GUI(listModelBook,ksiazki,frame,ksiegarnia,jLabelstan);
            }
        });
        frame.add(usun_ksiazke);

        //wyporzyczenie ksiażki
        JButton wyporzycz_ksiazke = new JButton("Wyporzycz książkę.");
        wyporzycz_ksiazke.setBounds(10,130,150,30);
        wyporzycz_ksiazke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Book().wyporzycz_ksiazke_GUI(ksiazki,frame,ksiegarnia,jLabelstan);
            }
        });
        frame.add(wyporzycz_ksiazke);

        //zwracanie wyporzyczonej książki
        JButton zwroc_ksiazke = new JButton("Zwróć książkę.");
        zwroc_ksiazke.setBounds(10,170,150,30);
        zwroc_ksiazke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Book().zwroc_ksiazke_GUI(ksiazki,frame,ksiegarnia,jLabelstan);
            }
        });
        frame.add(zwroc_ksiazke);

        //kupowanie książki
        JButton kup_ksiazke = new JButton("Kup książkę.");
        kup_ksiazke.setBounds(210,130,150,30);
        kup_ksiazke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Book().kup_ksiazke_GUI(ksiazki,frame,ksiegarnia,jLabelstan);
            }
        });
        frame.add(kup_ksiazke);

        JLabel szukaj_po_tytule = new JLabel(" Wyszukaj książkę po tytule.");
        szukaj_po_tytule.setBounds(10,210,240,30);
        frame.add(szukaj_po_tytule);

        //pole tekstowe do którego wpisujemy tytuł książki którą chcemy wyszukać
        JTextField text_szukaj_po_tytule = new JTextField();
        text_szukaj_po_tytule.setBounds(250,210,150,30);
        frame.add(text_szukaj_po_tytule);

        JLabel szukaj_po_indeksie = new JLabel(" Wyszukaj studenta po numerze indeksu.");
        szukaj_po_indeksie.setBounds(10,250,240,30);
        frame.add(szukaj_po_indeksie);

        //pole tekstowe do którego wpisujemy indeks studenta którego chcemy wyszukać
        JTextField text_szukaj_po_indeksie = new JTextField();
        text_szukaj_po_indeksie.setBounds(250,250,150,30);
        frame.add(text_szukaj_po_indeksie);

        //do szukania czy to studenta czy to książki
        JButton button_szukaj = new JButton("Szukaj");
        button_szukaj.setBounds(190,290,150,30);
        button_szukaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!text_szukaj_po_indeksie.getText().equals("")){
                    new Student().SzukajStudentaGUI(listModelStudent,dane_studentow,text_szukaj_po_indeksie);
                }
                if(!text_szukaj_po_tytule.getText().equals("")){
                    new Book().SzukajKsiazkiGUI(listModelBook,ksiazki,text_szukaj_po_tytule);
                }
            }
        });
        frame.add(button_szukaj);

        //sortowanie listy studentów po imieniu
        JButton button_sortuj_po_imieniu = new JButton("Sortuj po imieniu");
        button_sortuj_po_imieniu.setBounds(450,340,150,30);
        button_sortuj_po_imieniu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Student> sortlistimie = new ArrayList<>(new Student().Sortuj(dane_studentow,true));
                listModelStudent.clear();
                for(Student student : sortlistimie){
                    listModelStudent.addElement(student.toString());
                }
            }
        });
        frame.add(button_sortuj_po_imieniu);

        //sortowanie listy studentów po indeksie
        JButton button_sortuj_po_indeksie = new JButton("Sortuj po indeksie");
        button_sortuj_po_indeksie.setBounds(450,380,150,30);
        button_sortuj_po_indeksie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Student> sortlistindeks = new ArrayList<>(new Student().Sortuj(dane_studentow,false));
                listModelStudent.clear();
                for(Student student : sortlistindeks){
                    listModelStudent.addElement(student.toString());
                }
            }
        });
        frame.add(button_sortuj_po_indeksie);

        //koniec sortowania
        JButton button_koniec_sortowania = new JButton("Anuluj sortowanie");
        button_koniec_sortowania.setBounds(710,340,150,30);
        button_koniec_sortowania.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModelStudent.clear();
                for(Student student : dane_studentow){
                    listModelStudent.addElement(student.toString());
                }
            }
        });
        frame.add(button_koniec_sortowania);

        //koniec wyszukiwania
        JButton zakoncz_szukanie = new JButton("Zakończ szukanie.");
        zakoncz_szukanie.setBounds(10,290,150,30);
        zakoncz_szukanie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text_indeks = text_szukaj_po_indeksie.getText();
                String text_tytul = text_szukaj_po_tytule.getText();
                if(!text_szukaj_po_indeksie.getText().equals("")){ listModelStudent.clear(); }
                if(!text_szukaj_po_tytule.getText().equals("")){ listModelBook.clear(); }
                text_szukaj_po_tytule.setText("");
                text_szukaj_po_indeksie.setText("");
                //żeby nie ładwać listy kiedy nie było nic szukane
                if(!text_indeks.equals("")){
                    for(Student student : dane_studentow){
                        String string = student.toString();
                        listModelStudent.addElement(string);
                    }
                }
                if(!text_tytul.equals("")) {
                    for (Book book : ksiazki) {
                        String string = book.toString();
                        listModelBook.addElement(string);
                    }
                }
            }
        });
        frame.add(zakoncz_szukanie);

        //zamknięcie aaplikacji
        JButton wyjscie = new JButton("Wyjście.");
        wyjscie.setBounds(10,370,150,30);
        wyjscie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(wyjscie);

        //wyświetlenie ile jest wolnych miejsc w księgarni
        JButton ilosc_wolnych_miejsc = new JButton("Ilość wolnych miejsc.");
        ilosc_wolnych_miejsc.setBounds(10,330,150,30);
        ilosc_wolnych_miejsc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ilosc = Integer.toString(ksiegarnia.ilosc_wolynch_miejsc());
                JOptionPane.showMessageDialog(frame,"Ilość wolnych miejsc: " + ilosc);
            }
        });
        frame.add(ilosc_wolnych_miejsc);

        frame.setSize(1000,500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
