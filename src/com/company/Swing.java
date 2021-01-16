package com.company;

import javax.swing.*;
import static com.company.Terminala.dane_studentow;
import static com.company.Terminala.ksiazki;

public class Swing {

    private JFrame frame;
    private JButton dodaj_studenta;
    private JButton usun_studenta;
    private JButton dodaj_ksiazke;
    private JButton usun_ksiazke;
    private JButton wyporzycz_ksiazke;
    private JButton zwroc_ksiazke;
    private JButton kup_ksiazke;
    private JButton button_szukaj;
    private JButton button_sortuj_po_imieniu;
    private JButton button_sortuj_po_indeksie;
    private JButton button_koniec_sortowania;
    private JButton zakoncz_szukanie;
    private DefaultListModel<String> listModelStudent;
    private DefaultListModel<String> listModelBook;
    private JLabel jLabelstan;
    private JButton wyjscie;
    private JButton ilosc_wolnych_miejsc;
    private JButton pelne_info_student;
    private JButton pelne_info_book;
    private JTextField text_szukaj_po_tytule;
    private JTextField text_szukaj_po_indeksie;
    private JList<String> studentJlist;
    private JList<String> bookJlist;

    public Swing(){

        listModelStudent = new DefaultListModel<>();
        listModelBook = new DefaultListModel<>();

        for(Student student : dane_studentow){
            String string = student.toString();
            listModelStudent.addElement(string);
        }
        for(Book book : ksiazki){
            String string = book.toString();
            listModelBook.addElement(string);
        }

        //okno dla trybu graficznego
        frame = new JFrame("Księgarnia");

        //listy do wyświetlania list ze studentami i książkami
        studentJlist = new JList<String>(listModelStudent);
        studentJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane jScrollPanestudent = new JScrollPane();
        jScrollPanestudent.setViewportView(studentJlist);
        studentJlist.setLayoutOrientation(JList.VERTICAL);
        jScrollPanestudent.setBounds(450,40,250,250);
        frame.add(jScrollPanestudent);

        JLabel listastud = new JLabel("Lista Studentów:(Imie | Nazwisko | indeks)");
        listastud.setBounds(450,10,250,30);
        frame.add(listastud);

        //dodawanie elementów do listy książek
        bookJlist = new JList<String>(listModelBook);
        bookJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane jScrollPanebook = new JScrollPane();
        jScrollPanebook.setViewportView(bookJlist);
        bookJlist.setLayoutOrientation(JList.VERTICAL);
        jScrollPanebook.setBounds(720,40,250,250);
        frame.add(jScrollPanebook);

        //przycisk do wyświetlania informacji o studencie (dokładnych informacji)
        pelne_info_student = new JButton("Informacje o studencie");
        pelne_info_student.setBounds(450,300,250,30);
        frame.add(pelne_info_student);

        //przycisk do wyświetlania informacji o książce (dokładnych informacji)
        pelne_info_book = new JButton("Informcje o książce");
        pelne_info_book.setBounds(720,300,250,30);
        frame.add(pelne_info_book);

        JLabel listabook = new JLabel("Lista Książek:(Tytuł | autor | cena)");
        listabook.setBounds(720,10,250,30);
        frame.add(listabook);

        JLabel glowny_label = new JLabel(" Wybierz co chcesz zrobić.");
        glowny_label.setBounds(10,10,200,30);
        frame.add(glowny_label);

        //label użyty do stanu magazynu
        jLabelstan = new JLabel("Stan magazynu");
        jLabelstan.setBounds(190,330,220,70);
        jLabelstan.setOpaque(true);
        frame.add(jLabelstan);

        //dodawanie studenta
        dodaj_studenta = new JButton("Dodaj studenta.");
        dodaj_studenta.setBounds(10,50,150,30);
        frame.add(dodaj_studenta);

        //usuwanie studenta
        usun_studenta = new JButton("Usun studenta.");
        usun_studenta.setBounds(10,90,150,30);
        frame.add(usun_studenta);

        //dodawanie książki
        dodaj_ksiazke = new JButton("Dodaj książkę.");
        dodaj_ksiazke.setBounds(210,50,150,30);
        frame.add(dodaj_ksiazke);

        //usuwanie książki
        usun_ksiazke = new JButton("Usuń książkę.");
        usun_ksiazke.setBounds(210,90,150,30);
        frame.add(usun_ksiazke);

        //wyporzyczenie ksiażki
        wyporzycz_ksiazke = new JButton("Wyporzycz książkę.");
        wyporzycz_ksiazke.setBounds(10,130,150,30);
        frame.add(wyporzycz_ksiazke);

        //zwracanie wyporzyczonej książki
        zwroc_ksiazke = new JButton("Zwróć książkę.");
        zwroc_ksiazke.setBounds(10,170,150,30);
        frame.add(zwroc_ksiazke);

        //kupowanie książki
        kup_ksiazke = new JButton("Kup książkę.");
        kup_ksiazke.setBounds(210,130,150,30);
        frame.add(kup_ksiazke);

        JLabel szukaj_po_tytule = new JLabel(" Wyszukaj książkę po tytule.");
        szukaj_po_tytule.setBounds(10,210,240,30);
        frame.add(szukaj_po_tytule);

        //textfield do wpisania tytułu książki do wyszukania
        text_szukaj_po_tytule = new JTextField();
        text_szukaj_po_tytule.setBounds(250,210,150,30);
        frame.add(text_szukaj_po_tytule);

        JLabel szukaj_po_indeksie = new JLabel(" Wyszukaj studenta po numerze indeksu.");
        szukaj_po_indeksie.setBounds(10,250,240,30);
        frame.add(szukaj_po_indeksie);

        //textfield do wpisania indeksu studenta do wyszukania
        text_szukaj_po_indeksie = new JTextField();
        text_szukaj_po_indeksie.setBounds(250,250,150,30);
        frame.add(text_szukaj_po_indeksie);

        //do szukania czy to studenta czy to książki
        button_szukaj = new JButton("Szukaj");
        button_szukaj.setBounds(190,290,150,30);
        frame.add(button_szukaj);

        //sortowanie listy studentów po imieniu
        button_sortuj_po_imieniu = new JButton("Sortuj po imieniu");
        button_sortuj_po_imieniu.setBounds(450,340,150,30);
        frame.add(button_sortuj_po_imieniu);

        //sortowanie listy studentów po indeksie
        button_sortuj_po_indeksie = new JButton("Sortuj po indeksie");
        button_sortuj_po_indeksie.setBounds(450,380,150,30);
        frame.add(button_sortuj_po_indeksie);

        //koniec sortowania
        button_koniec_sortowania = new JButton("Anuluj sortowanie");
        button_koniec_sortowania.setBounds(710,340,150,30);
        frame.add(button_koniec_sortowania);

        //koniec wyszukiwania
        zakoncz_szukanie = new JButton("Zakończ szukanie.");
        zakoncz_szukanie.setBounds(10,290,150,30);
        frame.add(zakoncz_szukanie);

        //zamknięcie aaplikacji
        wyjscie = new JButton("Wyjście.");
        wyjscie.setBounds(10,370,150,30);
        frame.add(wyjscie);

        //wyświetlenie ile jest wolnych miejsc w księgarni
        ilosc_wolnych_miejsc = new JButton("Ilość wolnych miejsc.");
        ilosc_wolnych_miejsc.setBounds(10,330,150,30);
        frame.add(ilosc_wolnych_miejsc);

        frame.setSize(1000,500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public void setText_szukaj_po_indeksie(String string){ text_szukaj_po_indeksie.setText(string); }
    public void setText_szukaj_po_tytule(String string){ text_szukaj_po_tytule.setText(string); }
    public JFrame getFrame(){ return frame; }
    public JButton dodajStudentGUI(){ return dodaj_studenta; }
    public JButton usunStudentaGUI(){ return usun_studenta; }
    public JButton dodajKsiazkeGUI(){ return dodaj_ksiazke; }
    public JButton usunKsiazkeGUI(){ return usun_ksiazke; }
    public JButton wyporzyczKsiazskeGUI(){ return wyporzycz_ksiazke; }
    public JButton zwrocKsiazkeGUI(){ return zwroc_ksiazke; }
    public JButton kupKsiazkeGUI(){ return kup_ksiazke; }
    public DefaultListModel<String> getListModelStudent(){ return listModelStudent; }
    public DefaultListModel<String> getListModelBook(){ return listModelBook; }
    public void setDefaultListModelStudent(DefaultListModel<String> model){this.listModelStudent = model;}
    public void setDefaultListModelBook(DefaultListModel<String> model){this.listModelBook = model;}
    public JLabel getjLabelstan(){ return jLabelstan; }
    public JButton getWyjscie(){ return wyjscie; }
    public JButton getIlosc_wolnych_miejsc(){ return ilosc_wolnych_miejsc; }
    public JButton getButton_szukaj(){ return button_szukaj;}
    public JButton getZakoncz_szukanie(){ return zakoncz_szukanie; }
    public JButton getButton_sortuj_po_imieniu(){ return button_sortuj_po_imieniu; }
    public JButton getButton_sortuj_po_indeksie(){ return button_sortuj_po_indeksie; }
    public JButton getButton_koniec_sortowania(){ return button_koniec_sortowania; }
    public JButton getPelne_info_student(){ return pelne_info_student; }
    public JButton getPelne_info_book(){ return pelne_info_book; }
    public JTextField getText_szukaj_po_tytule(){ return text_szukaj_po_tytule; }
    public JTextField getText_szukaj_po_indeksie(){ return text_szukaj_po_indeksie; }
    public JList<String> getStudentJlist(){ return studentJlist; }
    public JList<String> getBookJlist(){ return bookJlist; }
}
