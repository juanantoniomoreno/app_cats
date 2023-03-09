package org.example;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int opcion_menu = -1;
        String[] botones = {
                "1. Ver Gatos",
                "2. Favoritos",
                "3. Salir"
        };

        do {
            //Menú Principal
            String opcion = (String) JOptionPane.showInputDialog(null, "Gatitos Java", "Menú Principal", JOptionPane.INFORMATION_MESSAGE, null, botones, botones[0]);

            //Validamos que opcion selecciona el usuario
            for( int i = 0; i < botones.length; i++){
                if(opcion.equals(botones[i])){
                    opcion_menu = i;
                }
            }

            switch (opcion_menu){
                case 0:
                    GatosService.verGatos();
                    break;
                case 1:
                    Gatos gato = new Gatos();
                    String apiKey = gato.getApikey();
                    GatosService.verFavoritos( apiKey );
                default:
                    break;
            }
        }while (opcion_menu != 1);


    }
}