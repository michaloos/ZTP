package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import static com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK;

public interface WypiszInfo {
    void wypisz(WindowBasedTextGUI textGUI, String a, String b, String x, String y, String z);
}

class WypiszStudenta implements WypiszInfo {

    @Override
    public void wypisz(WindowBasedTextGUI textGUI, String imie,String nazwisko,String indeks,String rok_studiow,String ilosc_wuporzyczen ) {
        new MessageDialogBuilder()
                .setTitle("Dane studenta")
                .setText("Imię: " + imie + "" +
                        "\nNazwisko: " + nazwisko + "" +
                        "\nNumer Indeksu:  " + indeks + "" +
                        "\nRok studiów: " + rok_studiow + "" +
                        "\nIlość wyporzyczonych książek:  " + ilosc_wuporzyczen)
                .addButton(OK)
                .build()
                .showDialog(textGUI);

    }
}

class WypiszKsiazke implements WypiszInfo {

    @Override
    public void wypisz(WindowBasedTextGUI textGUI,String tytul,String autor,String rok,String cena,String count) {
        new MessageDialogBuilder()
                .setTitle("Dane książki")
                .setText("Tytul książki: " + tytul + "" +
                        "\nAutor: " + autor + "" +
                        "\nRok wydania:  " + rok + "" +
                        "\nCena: " + cena + "" +
                        "\nDostępna ilość:  " + count)
                .addButton(OK)
                .build()
                .showDialog(textGUI);
    }
}