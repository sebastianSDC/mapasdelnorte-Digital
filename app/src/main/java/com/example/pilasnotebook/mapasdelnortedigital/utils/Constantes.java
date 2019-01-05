package com.example.pilasnotebook.mapasdelnortedigital.utils;

import java.util.Locale;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public final class Constantes {

    // constante universal
    public static final String TAG = "tag";
    private static final String CARPETA_RAIZ = "MAPASDELNORTE/";
    public static final String RUTA_IMAGEN = CARPETA_RAIZ + "fotos";
    public static final int COD_CAMARA = 20;  //para abrir camara
    public static final int COD_GALERIA = 10; // para abrir galeria

    // constantes para ubicacion en mapa
    public static final Locale LOCALE_ARGENTINA = new Locale("es", "ARG");

    public static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final StorageReference storage = FirebaseStorage.getInstance().getReference();


    //constantes admin cliente: datos
    public static final String NOMBRE = "nombre";
    public static final String DIRECCION = "direccion";
    public static final String TELEFONO = "telefono";
    public static final String MAIL = "mail";



    //constantes admin cliente: cuponera
    public static final String TIPO = "tipo";
    public static final String FECHA = "fecha";
    public static final String TITULO = "titulo";
    public static final String DESCRIPCION = "descripcion";// comun a todas los fragment de admin


}
