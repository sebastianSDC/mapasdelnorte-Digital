package com.example.pilasnotebook.mapasdelnortedigital.view.fragment;


/**
 * Creada por Sebastián Suárez Da Costa y Pablo Herrera.
 */


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pilasnotebook.mapasdelnortedigital.R;
import com.example.pilasnotebook.mapasdelnortedigital.model.POJO.Cliente;
import com.example.pilasnotebook.mapasdelnortedigital.model.POJO.DatosAdicionales;
import com.example.pilasnotebook.mapasdelnortedigital.model.POJO.DatosDeContacto;
import com.example.pilasnotebook.mapasdelnortedigital.model.POJO.DatosDeFacturacion;
import com.example.pilasnotebook.mapasdelnortedigital.model.POJO.Delivery;
import com.example.pilasnotebook.mapasdelnortedigital.model.POJO.Reservas;
import com.example.pilasnotebook.mapasdelnortedigital.model.POJO.Zona;
import com.example.pilasnotebook.mapasdelnortedigital.utils.Constantes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.support.constraint.ConstraintSet.VISIBLE;
import static android.support.v4.content.ContextCompat.checkSelfPermission;


public class DatosClienteFragment extends Fragment implements OnMapReadyCallback {

    String TAG = Constantes.TAG;
    private OnFragmentInteractionListener mListener; // INTERFACE
    protected Spinner categorias;
    private String categoriaTxt;
    private Switch switchDireccion;

    //ATRIBUTOS PARA FOTO
    private String path;
    private Uri fotoPerfilUri;
    protected ImageView fotodeContacto, fotoPerfilOculto;

    //MODEL POJO USADOS
    private Zona zona;
    private Cliente cliente;
    private DatosDeContacto datosDeContacto;
    private DatosAdicionales datosAdicionales;
    private DatosDeFacturacion datosDeFacturacion;
    private Delivery servicioDelivery;
    private Reservas servicioReservas;

    //EDITTEXT DEL FORMULARIO ADMIN CLIENTE
    private EditText nombreEd, descripcionEd,
            direccionEd, localidadED, provinciaEd, paisEd,
            telefonoEd, mailEd, faceEd, instaEd, twitEd, watsapEd, webEd,
            telResEd, toleranciaEd, señaEd,
            telDelivEd, radioDelivEd,
            titularEd, bancoEd, tipoCuentaEd, numCuentaEd, cbuEd, cuitEd;

    //CHEQUED-TXV DEL FORMULARIO ADMIN CLIENTE
    private CheckedTextView face, insta, twit, watsap, reservas, delivery, vtaOnLine;

    //BOTONES DEL FORMULARIO ADMIN CLIENTE
    private Button btnCargarFoto, btnCargarDatosPerfil, btnVerMapa, btnCargarDatosZona, btnCargarDatosContacto, btnCargarDatosAdicionales,
            btnModifDatosCliente, btnCargarCliente;

    //CARDVIEW DEL FORMULARIO ADMIN CLIENTE
    private CardView perfilCardView, zonaCardView, contactoCardView, adicionalesCardView,
            perfilOcultoCardView, zonaOcultaCardView, contactoOcultoCardView, adicionalesOcultoCardView; //GONE

    //LINEAR LAYOUT DEL FORMULARIO ADMIN CLIENTE
    private LinearLayout reservasLL, deliveryLL, vtaOnlineLL, reservasOcultoLL, deliveryOcultoLL, vtaOnlineOcultoLL, direccionFijaLL;

    //TXV DEL FORMULARIO ADMIN CLIENTE - GONE
    private TextView nombretxv, categoriatxv, descripciontxv,
            direcciontxv, localidadtxv, provinciatxv, paistxv,
            telefonotxv, mailtxv, webtxv, usFacetxv, usInstatxv, usTwittxv, usWhatstxv,
            telReservtxv, toleranciatxv, señatxv,
            telDelivtxv, radioDelivtxv,
            titulartxv, bancotxv, tipotxv, cuentatxv, cbutxv, cuittxv;

    //ATRIBUTOS DE MAPA ................... agregado.
    private GoogleMap mGoogleMap;
    private MapView mapView, mapViewOculto;
    private LinearLayout contenedorDeMapview;
    private Geocoder geocoder;
    private LatLng coordenadas;

    //ATRIBUTOS FIREBASE
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storage = FirebaseStorage.getInstance().getReference();


    public DatosClienteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datos, container, false);

        // CAMPOS DEL FORMULARIO
        nombreEd = (EditText) view.findViewById(R.id.ed_nombre_comercio);
        descripcionEd = (EditText) view.findViewById(R.id.ed_descripcion_comercio);
        direccionEd = (EditText) view.findViewById(R.id.ed_direccion_comercio);
        localidadED = (EditText) view.findViewById(R.id.ed_localidad_comercio);
        provinciaEd = (EditText) view.findViewById(R.id.ed_provincia_comercio);
        paisEd = (EditText) view.findViewById(R.id.ed_pais_comercio);
        telefonoEd = (EditText) view.findViewById(R.id.ed_telefono_comercio);
        mailEd = (EditText) view.findViewById(R.id.ed_mail_comercio);
        webEd = (EditText) view.findViewById(R.id.ed_web_comercio);
        faceEd = view.findViewById(R.id.ed_facebook_comercio);
        instaEd = view.findViewById(R.id.ed_instagram_comercio);
        twitEd = view.findViewById(R.id.ed_twitter_comercio);
        watsapEd = view.findViewById(R.id.ed_whatsapp_comercio);
        telResEd = view.findViewById(R.id.ed_telefono_reservas_comercio);
        toleranciaEd = view.findViewById(R.id.ed_tolerancia_reserva_comercio);
        señaEd = view.findViewById(R.id.ed_seña_reserva_comercio);
        telDelivEd = view.findViewById(R.id.ed_telefono_delivery_comercio);
        radioDelivEd = view.findViewById(R.id.ed_cobertura_delivery_comercio);
        titularEd = view.findViewById(R.id.ed_titular_cuenta_facturacion);
        bancoEd = view.findViewById(R.id.ed_banco_cuenta_facturacion);
        tipoCuentaEd = view.findViewById(R.id.ed_tipo_cuenta_facturacion);
        numCuentaEd = view.findViewById(R.id.ed_numero_cuenta_facturacion);
        cbuEd = view.findViewById(R.id.ed_cbu_cuenta_facturacion);
        cuitEd = view.findViewById(R.id.ed_tipo_cuit_facturacion);
        btnVerMapa = view.findViewById(R.id.btn_ver_mapa_comercio);
        mapView = view.findViewById(R.id.GoogleMap_container);
        reservasLL = view.findViewById(R.id.linearlayout_servicio_reservas);
        reservasOcultoLL = view.findViewById(R.id.linearlayout_reservas_gone);
        deliveryLL = view.findViewById(R.id.linearlayout_servicio_delivery);
        deliveryOcultoLL = view.findViewById(R.id.linearlayout_delivery_gone);
        vtaOnlineLL = view.findViewById(R.id.linearlayout_servicio_vtaOnLine);
        vtaOnlineOcultoLL = view.findViewById(R.id.linearlayout_vtaOnline_gone);
        switchDireccion = view.findViewById(R.id.switch_direccion_fija);
        direccionFijaLL = view.findViewById(R.id.linearlayout_direccion_fisica);


        //DATOS DE OCULTO
        nombretxv = view.findViewById(R.id.txv_nombre_comercio_gone);
        categoriatxv = view.findViewById(R.id.txv_categoria_comercio_gone);
        descripciontxv = view.findViewById(R.id.txv_descripcion_comercio_gone);
        perfilCardView = view.findViewById(R.id.cardview_datos_de_perfil);
        perfilOcultoCardView = view.findViewById(R.id.cardview_datos_de_perfil_gone);
        fotoPerfilOculto = view.findViewById(R.id.imagen_contacto_comercio_gone);
        contenedorDeMapview = view.findViewById(R.id.layout_contenedor_mapa);
        mapViewOculto = view.findViewById(R.id.GoogleMap_container_oc);
        direcciontxv = view.findViewById(R.id.txv_direccion_comercio_gone);
        localidadtxv = view.findViewById(R.id.txv_localidad_comercio_gone);
        provinciatxv = view.findViewById(R.id.txv_provincia_comercio_gone);
        paistxv = view.findViewById(R.id.txv_pais_comercio_gone);
        zonaCardView = view.findViewById(R.id.cardview_datos_de_zona);
        zonaOcultaCardView = view.findViewById(R.id.cardview_datos_de_zona_gone);
        direcciontxv = view.findViewById(R.id.txv_direccion_comercio_gone);
        localidadtxv = view.findViewById(R.id.txv_localidad_comercio_gone);
        provinciatxv = view.findViewById(R.id.txv_provincia_comercio_gone);
        paistxv = view.findViewById(R.id.txv_pais_comercio_gone);
        telefonotxv = view.findViewById(R.id.txv_telefono_comercio_gone);
        mailtxv = view.findViewById(R.id.txv_mail_comercio_gone);
        webtxv = view.findViewById(R.id.txv_web_comercio_gone);
        usFacetxv = view.findViewById(R.id.txv_facebook_gone);
        usInstatxv = view.findViewById(R.id.txv_instagram_gone);
        usTwittxv = view.findViewById(R.id.txv_twitter_gone);
        usWhatstxv = view.findViewById(R.id.txv_whatsapp_gone);
        telReservtxv = view.findViewById(R.id.txv_telefono_reservas_gone);
        toleranciatxv = view.findViewById(R.id.txv_tolerancia_reservas_gone);
        señatxv = view.findViewById(R.id.txv_seña_reservas_gone);
        telDelivtxv = view.findViewById(R.id.txv_telefono_delivery_gone);
        radioDelivtxv = view.findViewById(R.id.txv_radio_entrega_delivery_gone);
        titulartxv = view.findViewById(R.id.txv_titular_facturacion_gone);
        bancotxv = view.findViewById(R.id.txv_banco_facturacion_gone);
        tipotxv = view.findViewById(R.id.txv_tipo_facturacion_gone);
        cuentatxv = view.findViewById(R.id.txv_cuenta_facturacion_gone);
        cbutxv = view.findViewById(R.id.txv_cbu_facturacion_gone);
        cuittxv = view.findViewById(R.id.txv_cuit_facturacion_gone);
        contactoCardView = view.findViewById(R.id.cardview_datos_de_contacto);
        contactoOcultoCardView = view.findViewById(R.id.cardview_datos_de_contacto_gone);
        adicionalesCardView = view.findViewById(R.id.cardview_datos_adicionales);
        adicionalesOcultoCardView = view.findViewById(R.id.cardview_datos_adicionales_gone);


        // CAMPOS DE CHEQTEXTVIEW
        face = view.findViewById(R.id.cheq_facebook);
        insta = view.findViewById(R.id.cheq_instagram);
        twit = view.findViewById(R.id.cheq_twitter);
        watsap = view.findViewById(R.id.cheq_whatsapp);
        reservas = view.findViewById(R.id.cheq_reservas);
        delivery = view.findViewById(R.id.cheq_delivery);
        vtaOnLine = view.findViewById(R.id.cheq_venta_online);


        // LOGICA DE LOS CHEQTEXTVIEW
        face.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                faceEd.setVisibility(VISIBLE);
                face.setPadding(16, 4, 16, 4);
                face.setBackgroundResource(R.drawable.edit_text_borde);
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                instaEd.setVisibility(VISIBLE);
                insta.setPadding(16, 4, 16, 4);
                insta.setBackgroundResource(R.drawable.edit_text_borde);
            }
        });

        twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitEd.setVisibility(VISIBLE);
                twit.setPadding(16, 4, 16, 4);
                twit.setBackgroundResource(R.drawable.edit_text_borde);
            }
        });

        watsap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watsapEd.setVisibility(VISIBLE);
                watsap.setPadding(16, 4, 16, 4);
                watsap.setBackgroundResource(R.drawable.edit_text_borde);
            }
        });

        reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservasLL.setVisibility(VISIBLE);
                reservas.setPadding(16, 4, 16, 4);
                reservas.setBackgroundResource(R.drawable.edit_text_borde);
            }
        });

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryLL.setVisibility(VISIBLE);
                delivery.setPadding(16, 4, 16, 4);
                delivery.setBackgroundResource(R.drawable.edit_text_borde);
            }
        });

        vtaOnLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vtaOnlineLL.setVisibility(VISIBLE);
                vtaOnLine.setPadding(16, 4, 16, 4);
                vtaOnLine.setBackgroundResource(R.drawable.edit_text_borde);
            }
        });

        //GEOCODE DIR A LATLNG
        geocoder = new Geocoder(getActivity(), Constantes.LOCALE_ARGENTINA);

        categorias = (Spinner) view.findViewById(R.id.spinn_categorias_comercio);
        fotodeContacto = (ImageView) view.findViewById(R.id.imagen_contacto_comercio);

        //INICIALIZO LOS OBJETOS QUE COMPONEN CLIENTE PARA QUE SE PUEDAN LLAMAR DE TODOS LADOS...
        cliente = new Cliente();
        zona = new Zona();
        datosDeContacto = new DatosDeContacto();
        datosAdicionales = new DatosAdicionales();
        datosDeFacturacion = new DatosDeFacturacion();
        servicioReservas = new Reservas();
        servicioDelivery = new Delivery();

        // BOTONES DE FORMULARIO
        btnCargarCliente = (Button) view.findViewById(R.id.btn_cargar_cliente);
        btnCargarDatosZona = (Button) view.findViewById(R.id.btn_cargar_datos_de_zona_comercio);
        btnCargarDatosPerfil = view.findViewById(R.id.btn_cargar_datos_de_perfil_comercio);
        btnCargarDatosContacto = view.findViewById(R.id.btn_cargar_datos_contacto_comercio);       //por ahora no hace nada
        btnCargarDatosAdicionales = view.findViewById(R.id.btn_cargar_datos_adicionales_comercio); //por ahora no hace nada
        btnModifDatosCliente = view.findViewById(R.id.btn_modificar_cliente);
        btnCargarFoto = (Button) view.findViewById(R.id.btn_cargarFoto_comercio);
        if (validaPermisos()) {
            btnCargarFoto.setEnabled(true);
        } else {
            btnCargarFoto.setEnabled(false);
        }

        // LOGICA DEL SPINNER
        ArrayAdapter<CharSequence> adapterSpinCAtegorias = ArrayAdapter.createFromResource(getActivity(),
                R.array.combo_categorias, android.R.layout.simple_spinner_item);
        categorias.setAdapter(adapterSpinCAtegorias);
        categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaTxt = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position) == parent.getItemAtPosition(0)) {
                    categoriaTxt = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        direccionFijaLL.setVisibility(View.GONE);
        //LOGICA DEL SWITCH
        switchDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direccionFijaLL.setVisibility(View.GONE);
                if (switchDireccion.isChecked()) {
                    direccionFijaLL.setVisibility(View.VISIBLE);
                }
            }
        });


// LOGICA DEL BOTON CARGAR FOTOS
        btnCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarFoto();
            }
        });

//LOGICA DEL BOTON CARGAR DATOS DE PERFIL
        btnCargarDatosPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatosPerfil();
            }
        });

//LOGICA DEL BOTON CARGAR MAPA
        btnVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarMapa();
            }
        });

//LOGICA DEL BOTON CARGAR DATOS DE ZONA
        btnCargarDatosZona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatosZona();
            }
        });

//LOGICA DEL BOTON CARGAR DATOS DE CONTACTO
        btnCargarDatosContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatosDeContacto();
            }
        });

//LOGICA DEL BOTON CARGAR DATOS ADICIONALES
        btnCargarDatosAdicionales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatosAdicionales();
            }
        });

//LOGICA DEL BOTON MODIFICAR CLIENTE AL FIRESTORE
        btnModifDatosCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarDatosCliente();
            }
        });

//LOGICA DEL BOTON CARGAR CLIENTE AL FIRESTORE
        btnCargarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarCliente();
            }
        });

        return view;
    }

    private void modificarDatosCliente() {
        adicionalesCardView.setVisibility(View.VISIBLE);
        contactoCardView.setVisibility(View.VISIBLE);
        zonaCardView.setVisibility(View.VISIBLE);
        perfilCardView.setVisibility(View.VISIBLE);
        adicionalesOcultoCardView.setVisibility(View.GONE);
        contactoOcultoCardView.setVisibility(View.GONE);
        zonaOcultaCardView.setVisibility(View.GONE);
        perfilOcultoCardView.setVisibility(View.GONE);
    }

    private void cargarDatosAdicionales() {
        datosAdicionales = new DatosAdicionales(cargarReservas(), cargarDelivery(), cargarDatosFacturacion());
        cliente.setDatosAdicionales(datosAdicionales);
        adicionalesOcultoCardView.setVisibility(View.VISIBLE);
        adicionalesCardView.setVisibility(View.GONE);
    }

    private DatosDeFacturacion cargarDatosFacturacion() {
        String titularTxt = titularEd.getText().toString().trim();
        String bancoTxt = bancoEd.getText().toString().trim();
        String tipoTxt = tipoCuentaEd.getText().toString().trim();
        String cuentaTxt = numCuentaEd.getText().toString().trim();
        String cbuTxt = cbuEd.getText().toString().trim();
        String cuitTxt = cuitEd.getText().toString().trim();
        if (!titularTxt.isEmpty() && !bancoTxt.isEmpty() && !tipoTxt.isEmpty() && !cuentaTxt.isEmpty() &&
                !cbuTxt.isEmpty() && !cuitTxt.isEmpty()) {
            datosDeFacturacion = new DatosDeFacturacion(bancoTxt, titularTxt, cuentaTxt, tipoTxt, cbuTxt, cuitTxt);
            bancotxv.setText("Banco: " + bancoTxt);
            titulartxv.setText("Titular: " + titularTxt);
            tipotxv.setText("Tipo de Cuenta: " + tipoTxt);
            cuentatxv.setText("Número de Cuenta: " + cuentaTxt);
            cbutxv.setText("CBU: " + cbuTxt);
            cuittxv.setText("Cuit/Cuil: " + cuitTxt);
        } else {
            vtaOnlineOcultoLL.setVisibility(View.GONE);
        }
        return datosDeFacturacion;
    }

    private Delivery cargarDelivery() {
        String telefonoDeliTxt = telDelivEd.getText().toString().trim();
        String radioTxt = radioDelivEd.getText().toString().trim();
        if (!telefonoDeliTxt.isEmpty() && !radioTxt.isEmpty()) {
            servicioDelivery = new Delivery(telefonoDeliTxt, radioTxt);
            telDelivtxv.setText(telefonoDeliTxt);
            radioDelivtxv.setText(radioTxt);
        } else {
            deliveryOcultoLL.setVisibility(View.GONE);
        }
        return servicioDelivery;
    }

    private Reservas cargarReservas() {
        String telefonoResTxt = telResEd.getText().toString().trim();
        String toleranciaTxt = toleranciaEd.getText().toString().trim();
        String señaTxt = señaEd.getText().toString().trim();
        if (!telefonoResTxt.isEmpty() && !toleranciaTxt.isEmpty()) {
            servicioReservas = new Reservas(telefonoResTxt, toleranciaTxt, señaTxt);
            telReservtxv.setText(telefonoResTxt);
            toleranciatxv.setText(toleranciaTxt);
            if (señaTxt.isEmpty() || señaTxt == "0") {
                señatxv.setText("sin seña");
            } else {
                señatxv.setText(señaTxt);
            }
        } else {
            reservasOcultoLL.setVisibility(View.GONE);
        }
        return servicioReservas;
    }

    private void cargarMapa() {
        String direccionTxt = direccionEd.getText().toString().trim();
        String localidadTxt = localidadED.getText().toString().trim();
        String provinciaTxt = provinciaEd.getText().toString().trim();
        String paisTxt = paisEd.getText().toString().trim();
        if (!direccionTxt.isEmpty() && !localidadTxt.isEmpty() && !provinciaTxt.isEmpty() && !paisTxt.isEmpty()) {
            String direccionAConvertir = direccionTxt + ", " + localidadTxt + ", " + provinciaTxt
                    + ", " + paisTxt;
            coordenadas = convertirDirEnLatlang(direccionAConvertir);
            onMapReady(mGoogleMap);
            contenedorDeMapview.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getActivity(), "Debe completar todos los campos de Datos de Zona para generar el punto en el mapa...", Toast.LENGTH_LONG).show();
        }
    }

    private void cargarDatosPerfil() {
        String nombreTxt = nombreEd.getText().toString().trim();
        String descripcionTxt = descripcionEd.getText().toString().trim();
        if (nombreTxt.isEmpty() || categoriaTxt == null) {
            Toast.makeText(getActivity(), "debe completar el nombre y seleccionar una categoría ", Toast.LENGTH_LONG).show();
        } else {
            nombretxv.setText(nombreTxt);
            categoriatxv.setText(categoriaTxt);
            descripciontxv.setText(descripcionTxt);
            fotoPerfilOculto.setImageURI(fotoPerfilUri);
            cliente.setNombreComercio(nombreTxt);
            subirAStorageUri(fotoPerfilUri);
            cliente.setDescripcionComercio(descripcionTxt);
            cliente.setCategoria(categoriaTxt);
            perfilOcultoCardView.setVisibility(View.VISIBLE);
            perfilCardView.setVisibility(View.GONE);
        }
    }

    public void cargarCliente() {
        if (perfilOcultoCardView.getVisibility() == View.GONE || direccionFijaLL.getVisibility() == View.VISIBLE && zonaOcultaCardView.getVisibility() == View.GONE
                || contactoOcultoCardView.getVisibility() == View.GONE || adicionalesOcultoCardView.getVisibility() == View.GONE) {
            Toast.makeText(getActivity(), "antes de cargar el Cliente debe guardar los Datos.", Toast.LENGTH_SHORT).show();
        } else {
            if (cliente.getNombreComercio() == null && categoriaTxt == null) {
                Toast.makeText(getActivity(), "el Cliente no se puede cargar sin Nombre ni Categoría.", Toast.LENGTH_SHORT).show();
            } else {
                db.collection("clientes").document(cliente.getNombreComercio()).set(cliente).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(getActivity(), "Cliente cargado correctamente...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
            }
        }
    }

    private void cargarDatosZona() {
        if (direccionFijaLL.getVisibility() == View.GONE) {
            zona = null;
            direcciontxv.setText("sin dirección física");
            cliente.setZona(zona);
        } else {
            String direccionTxt = direccionEd.getText().toString().trim();
            String localidadTxt = localidadED.getText().toString().trim();
            String provinciaTxt = provinciaEd.getText().toString().trim();
            String paisTxt = paisEd.getText().toString().trim();
            direcciontxv.setText(direccionTxt);
            localidadtxv.setText(localidadTxt);
            provinciatxv.setText(provinciaTxt);
            paistxv.setText(paisTxt);
            if (direccionTxt.isEmpty() || localidadTxt.isEmpty() || provinciaTxt.isEmpty() || paisTxt.isEmpty()) {
                Toast.makeText(getActivity(), "Debe completar todos los campos de Datos de Zona " +
                        "para generar el punto en el mapa...", Toast.LENGTH_LONG).show();
            } else {
                if (coordenadas == null) {
                    String direccionAConvertir = direccionTxt + ", " + localidadTxt + ", " + provinciaTxt
                            + ", " + paisTxt;
                    coordenadas = convertirDirEnLatlang(direccionAConvertir);
                }
                zona = new Zona(direccionTxt, localidadTxt, provinciaTxt, paisTxt);
                zona.setLatlang(coordenadas);
                cliente.setZona(zona);
                if (mapViewOculto != null) {
                    mapViewOculto.onCreate(null);
                    mapViewOculto.onResume();
                    mapViewOculto.getMapAsync(this);
                }
                zonaOcultaCardView.setVisibility(View.VISIBLE);
                zonaCardView.setVisibility(View.GONE);
            }
        }
    }

    private void cargarDatosDeContacto() {
        String telefonoTxt = telefonoEd.getText().toString().trim();
        String mailTxt = mailEd.getText().toString().trim();
        String webTxt = webEd.getText().toString().trim();
        List<String> redesTxt = cargarRedesCliente();
        telefonotxv.setText(telefonoTxt);
        mailtxv.setText(mailTxt);
        webtxv.setText(webTxt);
        usFacetxv.setText(redesTxt.get(0));
        usInstatxv.setText(redesTxt.get(1));
        usTwittxv.setText(redesTxt.get(2));
        usWhatstxv.setText(redesTxt.get(3));
        datosDeContacto.setMail(mailTxt);
        datosDeContacto.setTelefono(telefonoTxt);
        datosDeContacto.setRedes(redesTxt);
        cliente.setDatosDeContacto(datosDeContacto);
        contactoOcultoCardView.setVisibility(View.VISIBLE);
        contactoCardView.setVisibility(View.GONE);
    }

    private List<String> cargarRedesCliente() {
        List<String> redesExistentes = new ArrayList<>();
        String faceTxt = faceEd.getText().toString().trim();
        String instaTxt = instaEd.getText().toString().trim();
        String twitTxt = twitEd.getText().toString().trim();
        String watsapTxt = watsapEd.getText().toString().trim();
        if (!faceTxt.isEmpty()) {
            redesExistentes.add("facebook: \n" + faceTxt);
        } else {
            redesExistentes.add("facebook: \n sin registro");
        }
        if (!instaTxt.isEmpty()) {
            redesExistentes.add("instagram: \n" + instaTxt);
        } else {
            redesExistentes.add("instagram: \n sin registro");
        }
        if (!twitTxt.isEmpty()) {
            redesExistentes.add("twitter: \n" + twitTxt);
        } else {
            redesExistentes.add("twitter: \n sin registro");
        }
        if (!watsapTxt.isEmpty()) {
            redesExistentes.add("whatsapp: \n" + watsapTxt);
        } else {
            redesExistentes.add("whatsapp: \n sin registro");
        }
        return redesExistentes;
    }

    private void seleccionarFoto() {
        final CharSequence[] option = {"Tomar foto", "Abrir Galería", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case 0://"Tomar foto"
                        openCamera();
                        //pickerPath = cameraPicker.pickImage();
                        break;
                    case 1: //"Abrir Galeriía"
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent.createChooser(intent, "Abrir con..."), Constantes.COD_GALERIA);
                        //imagePicker.pickImage();
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), Constantes.RUTA_IMAGEN);
        boolean isDirectoryCreated = file.exists();
        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();
        if (isDirectoryCreated) {
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";
            path = Environment.getExternalStorageDirectory() + File.separator + Constantes.RUTA_IMAGEN
                    + File.separator + imageName;
            File newFile = new File(path);
            fotoPerfilUri = Uri.fromFile(newFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoPerfilUri);
            startActivityForResult(intent, Constantes.COD_CAMARA);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path"/*"pickerPath"*/, path);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("file_path")) {
                path = savedInstanceState.getString("file_path");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constantes.COD_CAMARA:
                    MediaScannerConnection.scanFile(getActivity(),
                            new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                    fotoPerfilUri = Uri.parse(path);
                                    fotodeContacto.setImageURI(fotoPerfilUri);
                                }
                            });
                    break;
                case Constantes.COD_GALERIA:
                    fotoPerfilUri = data.getData();
                    fotodeContacto.setImageURI(fotoPerfilUri);
                    break;
            }
        }
    }

    public void subirAStorageUri(Uri uri) {
        if (uri == null) {
            Toast.makeText(getContext(), "sin foto de perfil", Toast.LENGTH_SHORT).show();
        } else {
            /*String nombreFoto = "";
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm-ss", Locale.getDefault());
            nombreFoto = simpleDateFormat.format(date);*/
            final StorageReference storageRef = storage.child("fotos/").child("foto de Perfil: " + cliente.getNombreComercio());
            storageRef.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {

                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {

                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Toast.makeText(getContext(), "cargando foto...", Toast.LENGTH_SHORT).show();
                    if (task.isSuccessful()) {
                        Uri urifoto = task.getResult();
                        cliente.setFoto(urifoto.toString());
                        Toast.makeText(getContext(), "foto cargada correctamente!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // LOGICA DE CONVERTIR DIRECCION A LATLNG
    public LatLng convertirDirEnLatlang(String direccion) {
        List<Address> address;
        try {
            address = geocoder.getFromLocationName(direccion, 1);
            Address direccionAConvertir = address.get(0);
            double latitud = direccionAConvertir.getLatitude();
            double longitud = direccionAConvertir.getLongitude();
            coordenadas = new LatLng(latitud, longitud);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordenadas;
    }

    private boolean validaPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if ((checkSelfPermission(getActivity(), CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }
        if ((shouldShowRequestPermissionRationale(CAMERA)) || (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnCargarFoto.setEnabled(true);
            } else {
                solicitarPermisosManual();
            }
        }


    }

    private void solicitarPermisosManual() {
        final CharSequence[] OPCIONES_CARGAR_FOTO = {"Si", "No"};
        final AlertDialog.Builder alertOPCIONES = new AlertDialog.Builder(getActivity());
        alertOPCIONES.setTitle("Desea configurar los permisos de forma manual?");
        alertOPCIONES.setItems(OPCIONES_CARGAR_FOTO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (OPCIONES_CARGAR_FOTO[i].equals("Si")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), "DatosClienteFragment");
                    intent.setData(uri);
                    startActivity(intent);
                } else {

                    Toast.makeText(getActivity(), "Los Permisos no fueron Aceptados", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        alertOPCIONES.show();
    }

    private void cargarDialogoRecomendacion() {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
            }
        });
        dialogo.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DatosClienteFragment.OnFragmentInteractionListener) {
            mListener = (DatosClienteFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            try {
                InputMethodManager mImm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mImm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                mImm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                Log.e(TAG, "setUserVisibleHint: ", e);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        } else {
            Toast.makeText(getActivity(), "mapView es null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (coordenadas != null) {
            LatLng comercio = new LatLng(coordenadas.latitude, coordenadas.longitude);
            mGoogleMap.setMinZoomPreference(15);
            mGoogleMap.addMarker(new MarkerOptions().position(comercio).title(cliente.getNombreComercio()));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(comercio));
        } else {
            LatLng comercio = new LatLng(-34.5312443, -58.4816567);
            mGoogleMap.setMinZoomPreference(15);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(comercio));
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }

//////////////////////fin de metodos
}