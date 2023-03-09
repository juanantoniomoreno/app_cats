package org.example;

import com.google.gson.Gson;
import okhttp3.*;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class GatosService {
    public static void verGatos() throws IOException {
        //1. Traer datos de la API
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String elJson = response.body().string();

        //Cortar corchetes
        elJson = elJson.substring(1, elJson.length());
        elJson = elJson.substring(0, elJson.length() - 1);

        //Crear objeto de la clase Gson
        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(elJson, Gatos.class);

        //Redimensionar la imagen si es necesario
        Image image = null;

        try {
            URL url = new URL(gatos.getUrl());
            image = ImageIO.read(url);

            ImageIcon fondoGato = new ImageIcon(image);

            if(fondoGato.getIconWidth() > 800){
                //Redimensionar
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String menu = "Opciones: \n"
                    + "1. Ver otra Imagen \n"
                    + "2. Favorito \n"
                    + "3. Volver \n";

            String[] botones = {"Ver Otra Imagen", "Favorito", "Volver"};
            String id_gato = gatos.getId();
            String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

            int seleccion = -1;

            //Validar opción seleccionada
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])){
                    seleccion = i;
                }
            }

            switch (seleccion){
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatos);
                    break;
                default:
                    break;
            }

        }catch(IIOException e) {
            System.out.println(e);
        }
    }

    public static void favoritoGato(Gatos gato){

        try{
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");

            RequestBody body = RequestBody.create(mediaType, "{\n  \"image_id\":\"" + gato.getId() + "\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApikey())
                    .build();

            Response response = client.newCall(request).execute();
        }catch (IOException e){
            System.out.println(e);
        }

    }

    public static void verFavoritos( String apiKey ) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .get()
                .addHeader("x-api-key", apiKey)
                .build();

        Response response = client.newCall(request).execute();

        //Guardamos string con la respuesta
        String elJson = response.body().string();

        //Creamos el objeto json
        Gson gson = new Gson();

        //Array de los gatos
        GatosFav[] gatosArray = gson.fromJson( elJson, GatosFav[].class );

        if( gatosArray.length > 0 ){
            int min = 1;
            int max = gatosArray.length;
            int random = (int) Math.random() * ((max - min) + 1) + min;
            int index = random - 1;

            GatosFav gatoFav = gatosArray[index];

            Image image = null;

            try {
                URL url = new URL(gatoFav.image.getUrl());
                image = ImageIO.read(url);

                ImageIcon fondoGato = new ImageIcon(image);

                if(fondoGato.getIconWidth() > 800){
                    //Redimensionar
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);
                }

                String menu = "Opciones: \n"
                        + "1. Ver otra Imagen \n"
                        + "2. Eliminar Favorito \n"
                        + "3. Volver \n";

                String[] botones = {"Ver Otra Imagen", "Eliminar Favorito", "Volver"};
                String id_gato = gatoFav.getId();
                String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

                int seleccion = -1;

                //Validar opción seleccionada
                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])){
                        seleccion = i;
                    }
                }

                switch (seleccion){
                    case 0:
                        verFavoritos(apiKey);
                        break;
                    case 1:
                        borrarFavorito(gatoFav);
                        break;
                    default:
                        break;
                }

            }catch(IIOException e) {
                System.out.println(e);
            }


        }
    }

    public static void borrarFavorito(GatosFav gatoFav){
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+ gatoFav.getId()+"")
                    .delete(null)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatoFav.getApiKey())
                    .build();

            Response response = client.newCall(request).execute();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
