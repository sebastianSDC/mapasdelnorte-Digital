package com.example.pilasnotebook.mapasdelnortedigital.view.fragment;


/**
 * Creada por Sebastián Suárez Da Costa y Pablo Herrera.
 */


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pilasnotebook.mapasdelnortedigital.R;
import com.example.pilasnotebook.mapasdelnortedigital.utils.Constantes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class CarteleraClienteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    String TAG = Constantes.TAG;

    private EditText fechaEd, tituloEd, descripcionEd, nombreEd, direccionEd, telefonoEd, mailEd;
    private Spinner tipos;

    private Button btnCargarFoto, btnCargar, btnTraer;
    private TextView datosCartelera;
    private String tipoCarteleraTxt;
    private ImageView fotoCartelera;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String documentId = db.collection("clientes").document().getId();


    public CarteleraClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cartelera, container, false);

        tipos = view.findViewById(R.id.spinn_tipo_cartelera);
        fechaEd = view.findViewById(R.id.ed_fecha_cartelera);
        btnCargarFoto = view.findViewById(R.id.btn_cargarFoto_cartelera);
        fotoCartelera = view.findViewById(R.id.imagen_cartelera);
        tituloEd = view.findViewById(R.id.ed_titulo_cartelera);
        descripcionEd = view.findViewById(R.id.ed_descripcion_cartelera);
        nombreEd = view.findViewById(R.id.ed_nombre_comercio_cartelera);
        direccionEd = view.findViewById(R.id.ed_direccion_comercio);
        telefonoEd = view.findViewById(R.id.ed_telefono_comercio);
        mailEd = view.findViewById(R.id.ed_mail_comercio);
        datosCartelera = view.findViewById(R.id.datos_cartelera);
        btnCargar = view.findViewById(R.id.btn_cargar_cartelera);
        btnTraer = view.findViewById(R.id.btn_traer_datos_cartelera);

        ArrayAdapter<CharSequence> adapterSpinCAtegorias = ArrayAdapter.createFromResource(getActivity(),
                R.array.combo_tipos_cartelera, android.R.layout.simple_spinner_item);

        tipos.setAdapter(adapterSpinCAtegorias);

        tipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoCarteleraTxt = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String fechaTxt = tituloEd.getText().toString().trim();
                String tituloTxt = tituloEd.getText().toString().trim();
                String descripcionTxt = descripcionEd.getText().toString().trim();
                String nombreTxt = nombreEd.getText().toString().trim();
                String direccionTxt = direccionEd.getText().toString().trim();
                String telefonoTxt = telefonoEd.getText().toString().trim();
                String mailTxt = mailEd.getText().toString().trim();

                if (tituloTxt.isEmpty() || direccionTxt.isEmpty() || telefonoTxt.isEmpty() || tipoCarteleraTxt.isEmpty()) {
                    return;
                }

                Map<String, Object> datosACargar = new HashMap<String, Object>();
                datosACargar.put(Constantes.TIPO, tipoCarteleraTxt);
                datosACargar.put(Constantes.FECHA, fechaTxt);
                datosACargar.put(Constantes.TITULO, tituloTxt);
                datosACargar.put(Constantes.DESCRIPCION, descripcionTxt);
                datosACargar.put(Constantes.NOMBRE, nombreTxt);
                datosACargar.put(Constantes.DIRECCION, direccionTxt);
                datosACargar.put(Constantes.TELEFONO, telefonoTxt);
                datosACargar.put(Constantes.MAIL, mailTxt);

                //esta instruccion es para poner un cliente con sub-coleccion cuponera...
                // db.collection("clientes").document("OPpLzJFx6CD8VilCvy4t").collection("cuponera").add(datosACargar)

                db.collection("clientes").document(documentId).collection("cartelera").add(datosACargar).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });

        btnTraer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                db.collection("clientes").document(documentId).collection("cartelera").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String tipoCarteleraTxt = document.getString(Constantes.TIPO);
                                String fechaTxt = document.getString(Constantes.FECHA);
                                String tituloTxt = document.getString(Constantes.TITULO);
                                String descripcionTxt = document.getString(Constantes.DESCRIPCION);
                                String nombreTxt = document.getString(Constantes.NOMBRE);
                                String direccionTxt = document.getString(Constantes.DIRECCION);
                                String telefonoTxt = document.getString(Constantes.TELEFONO);
                                String mailTxt = document.getString(Constantes.MAIL);

                                datosCartelera.setText("DATOS CARGADOS : "
                                        + tipoCarteleraTxt + "\n"
                                        + fechaTxt + "\n"
                                        + tituloTxt + "\n"
                                        + descripcionTxt + "\n"
                                        + nombreTxt + "\n"
                                        + direccionTxt + "   \n     "
                                        + telefonoTxt + "   \n   "
                                        + mailTxt);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

            }


        });

        btnCargarFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cargarFoto();
            }
        });

        return view;
    }

    public void cargarFoto() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent1 = intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent, "Seleccione la aplicación"), 10);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            fotoCartelera.setImageURI(path);
        }
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}