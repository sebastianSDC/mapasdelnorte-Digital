package com.example.pilasnotebook.mapasdelnortedigital.view.fragment;


/**
 * Creada por Sebastián Suárez Da Costa y Pablo Herrera.
 */


import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pilasnotebook.mapasdelnortedigital.R;
import com.example.pilasnotebook.mapasdelnortedigital.model.POJO.Cupon;
import com.example.pilasnotebook.mapasdelnortedigital.utils.Constantes;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.pilasnotebook.mapasdelnortedigital.view.fragment.DatosClienteFragment.cliente;
import static com.example.pilasnotebook.mapasdelnortedigital.view.fragment.DatosClienteFragment.path;


public class CuponeraClienteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final String TAG = "cupones";
    private Uri fotoCuponUri;

    private EditText otroTipoEd, tituloEd, descripcionEd, fechaDEd, fechaHEd, fechaUEd;
    private Spinner tipos, subTipos;
    private Switch fecha;
    private ImageView fotoCuponera, fotoCuponeraOculto;

    private Button btnCargarFoto, btnGuardarDatos, btnModificarDatos, btnCargarCupon;
    private CardView cuponeraCardView, cuponeraOcultaCardView;

    private TextView subTipotxvGone, tipoTxv, subtipoTxv, fechaUTxv, fechaDHTxv, tituloTxv, descripcionTxv, cuponDeTxv;

    private String tipoCuponTxt, subtipoCuponTxt;

    private Cupon cupon;

    final DatosClienteFragment dat = new DatosClienteFragment();

    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //String documentId= db.collection("clientes").document().getId();


    public CuponeraClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cuponera, container, false);

        tipos = view.findViewById(R.id.spinn_tipo_cup_o_cart);
        subTipos = view.findViewById(R.id.spinn_subtipo);
        subTipotxvGone = view.findViewById(R.id.subtipo);
        otroTipoEd = view.findViewById(R.id.ed_otro_tipo);
        fecha = view.findViewById(R.id.switch_fecha_cupon);
        fechaUEd = view.findViewById(R.id.ed_fecha_un_dia_cupon);
        fechaDEd = view.findViewById(R.id.ed_fecha_desde_cupon);
        fechaHEd = view.findViewById(R.id.ed_fecha_hasta_cupon);
        tituloEd = view.findViewById(R.id.ed_titulo_cupon);
        descripcionEd = view.findViewById(R.id.ed_descripcion_cupon);
        fotoCuponera = view.findViewById(R.id.imagen_cupon);
        btnCargarFoto = view.findViewById(R.id.btn_cargarFoto_cupon);
        if (dat.validaPermisos(getActivity())) {
            btnCargarFoto.setEnabled(true);
        } else {
            btnCargarFoto.setEnabled(false);
        }
        btnGuardarDatos = view.findViewById(R.id.btn_guardar_datos_cupon);
        btnModificarDatos = view.findViewById(R.id.btn_modificar_cuponera);
        btnCargarCupon = view.findViewById(R.id.btn_cargar_cuponera);

        //atributos del oculto
        tipoTxv = view.findViewById(R.id.txv_tipo_cupon_gone);
        subtipoTxv = view.findViewById(R.id.txv_subtipo_cupon_gone);
        fechaUTxv = view.findViewById(R.id.txv_fecha_unica_cuponera_gone);
        fechaDHTxv = view.findViewById(R.id.txv_fecha_desdehasta_cuponera_gone);
        tituloTxv = view.findViewById(R.id.txv_titulo_cuponera_gone);
        descripcionTxv = view.findViewById(R.id.txv_descripcion_cuponera_gone);
        cuponeraCardView = view.findViewById(R.id.cardview_cuponera);
        cuponeraOcultaCardView = view.findViewById(R.id.cardview_cupon_gone);
        fotoCuponeraOculto = view.findViewById(R.id.imagen_cuponera_gone);
        cuponDeTxv = view.findViewById(R.id.txv_cupon_de_gone);

        //inicializo el cupon y el firestore
        cupon = null;

// LOGICA DEL SPINNER
        ArrayAdapter<CharSequence> adapterSpinTipos = ArrayAdapter.createFromResource(getActivity(), R.array.combo_tipos, android.R.layout.simple_spinner_item);
        tipos.setAdapter(adapterSpinTipos);
        tipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoCuponTxt = parent.getItemAtPosition(position).toString();

                switch (position) {

                    case 0:
                        tipoCuponTxt = null;
                        break;

                    case 1:
                        tipoCuponTxt = "cuponera";
                        subTipos.setVisibility(View.VISIBLE);
                        subTipotxvGone.setVisibility(View.VISIBLE);
                        ArrayAdapter<CharSequence> adapterSpinSubtiposCup = ArrayAdapter.createFromResource(getActivity(), R.array.combo_tipos_cuponera, android.R.layout.simple_spinner_item);
                        subTipos.setAdapter(adapterSpinSubtiposCup);
                        subTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                subtipoCuponTxt = parent.getItemAtPosition(position).toString();
                                if (parent.getItemAtPosition(position) == parent.getItemAtPosition(0)) {
                                    subtipoCuponTxt = null;
                                }
                                if (parent.getItemAtPosition(position) == parent.getItemAtPosition(4)) {
                                    otroTipoEd.setVisibility(View.VISIBLE);
                                } else {
                                    otroTipoEd.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;

                    case 2:
                        tipoCuponTxt = "cartelera";
                        subTipos.setVisibility(View.VISIBLE);
                        subTipotxvGone.setVisibility(View.VISIBLE);
                        ArrayAdapter<CharSequence> adapterSpinSubtiposCart = ArrayAdapter.createFromResource(getActivity(), R.array.combo_tipos_cartelera, android.R.layout.simple_spinner_item);
                        subTipos.setAdapter(adapterSpinSubtiposCart);
                        subTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                subtipoCuponTxt = parent.getItemAtPosition(position).toString();
                                if (parent.getItemAtPosition(position) == parent.getItemAtPosition(0)) {
                                    subtipoCuponTxt = null;
                                }
                                if (parent.getItemAtPosition(position) == parent.getItemAtPosition(6)) {
                                    otroTipoEd.setVisibility(View.VISIBLE);
                                } else {
                                    otroTipoEd.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // LOGICA DEL SWITCH
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fecha.isChecked()) {
                    fechaDEd.setVisibility(View.VISIBLE);
                    fechaHEd.setVisibility(View.VISIBLE);
                    fechaUEd.setVisibility(View.GONE);
                    fecha.getTextOff();
                } else {
                    fechaUEd.setVisibility(View.VISIBLE);
                    fechaDEd.setVisibility(View.GONE);
                    fechaHEd.setVisibility(View.GONE);
                    fecha.getTextOn();
                }
            }
        });

        btnCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dat.seleccionarFoto(getActivity(), CuponeraClienteFragment.this);
            }
        });

        btnGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatosCupon();
            }
        });

        btnModificarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModificarDatosCupon();
            }
        });

        btnCargarCupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarCupon();
            }
        });

        return view;
    }

    private void ModificarDatosCupon() {
        cuponeraCardView.setVisibility(View.VISIBLE);
        cuponeraOcultaCardView.setVisibility(View.GONE);
    }

    public void guardarDatosCupon() {
        if (cliente == null) {
            Toast.makeText(getActivity(),"No se puede generar el Cupón sin un Cliente valido.", Toast.LENGTH_SHORT).show();
            cupon=null;
        } else {
            cupon = new Cupon();
            cupon.setClienteCupon(cliente);

            String tituloTxt = tituloEd.getText().toString().trim();
            String descricionTxt = descripcionEd.getText().toString().trim();

            if (tituloTxt.isEmpty() || tipoCuponTxt == null) {
                Toast.makeText(getActivity(), "No se puede generar el Cupón sin Tipo ni Titulo.", Toast.LENGTH_SHORT).show();
            } else {
                tituloTxv.setText(tituloTxt);
                cupon.setTitulo(tituloTxt);
                fotoCuponeraOculto.setImageURI(fotoCuponUri);
                subirAStorageUri(fotoCuponUri);
                descripcionTxv.setText(descricionTxt);
                cupon.setDescripcion(descricionTxt);

                String subtipoTxt = null;
                if (otroTipoEd.getVisibility() == View.VISIBLE) {
                    String otroTipoTxt = otroTipoEd.getText().toString().trim();
                    subtipoTxt = subtipoCuponTxt + ": " + otroTipoTxt;
                } else {
                    subtipoTxt = subtipoCuponTxt;
                }
                cuponDeTxv.setText("DATOS DE " + tipoCuponTxt + ": " + subtipoTxt);
                tipoTxv.setText(tipoCuponTxt);
                subtipoTxv.setText(subtipoTxt);
                cupon.setTipo(tipoCuponTxt + ": " + subtipoTxt);

                String fechatotalTxt = null;
                if (fechaUEd.getVisibility() == View.VISIBLE) {
                    fechatotalTxt = fechaUEd.getText().toString().trim();
                    fechaUTxv.setText(fechatotalTxt);

                } else if (fechaDEd.getVisibility() == View.VISIBLE && fechaHEd.getVisibility() == View.VISIBLE) {
                    fechatotalTxt = "Desde:" + fechaDEd.getText().toString().trim() + "\n Hasta:" + fechaHEd.getText().toString().trim();
                    fechaDHTxv.setText(fechatotalTxt);
                    fechaDHTxv.setVisibility(View.VISIBLE);
                    fechaUTxv.setVisibility(View.GONE);
                }
                cupon.setFecha(fechatotalTxt);
            }

            cuponeraOcultaCardView.setVisibility(View.VISIBLE);
            cuponeraCardView.setVisibility(View.GONE);
        }
    }

    public void cargarCupon() {

        if (cuponeraOcultaCardView.getVisibility() == View.GONE) {
            Toast.makeText(getActivity(),"antes de cargar el Cupón debe guardar los Datos.", Toast.LENGTH_SHORT).show();
        } else {
            DocumentReference docref = null;
            if (tipoCuponTxt.equals("cuponera")) {
                docref = Constantes.db.collection("cuponera").document(cliente.getNombreComercio() + ": " + cupon.getTitulo());
            }
            if (tipoCuponTxt.equals("cartelera")) {
                docref = Constantes.db.collection("cartelera").document(cliente.getNombreComercio() + ": " + cupon.getTitulo());
            }

            try {
                docref.set(cupon).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(getActivity(),"Cupón cargado correctamente!!!", Toast.LENGTH_SHORT).show();

                      // TODO:  mandar notificacion push a todos los usuarios...

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(getActivity(),"Cupón no cargado...", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception npe) {

                Toast.makeText(getActivity(),"Error al cargar el Cupón... \n"+npe, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constantes.COD_CAMARA:
                    MediaScannerConnection.scanFile(getActivity(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> Uri = " + uri);
                            fotoCuponUri = Uri.parse(path);
                            fotoCuponera.setImageURI(fotoCuponUri);
                        }
                    });
                    break;
                case Constantes.COD_GALERIA:
                    fotoCuponUri = data.getData();
                    fotoCuponera.setImageURI(fotoCuponUri);
                    break;
            }
            //subirAStorageUri(fotoCuponUri);
        }
    }

    public void subirAStorageUri(Uri uri) {
        if (uri == null) {
            Toast.makeText(getActivity(),"sin foto de cupon", Toast.LENGTH_SHORT).show();

        } else {
            final StorageReference storageRefCupon;
            StorageReference storageRefCupon1 = null;
            if (tipoCuponTxt.equals("cuponera")) {
                storageRefCupon1 = Constantes.storage.child("fotos/cuponera").child("foto de Cupón: " + cliente.getNombreComercio() + ": " + cupon.getTitulo());
            }
            if (tipoCuponTxt.equals("cartelera")) {
                storageRefCupon1 = Constantes.storage.child("fotos/cartelera").child("foto de Cupón: " + cliente.getNombreComercio() + ": " + cupon.getTitulo());
            }

            storageRefCupon = storageRefCupon1;
            assert storageRefCupon != null;
            storageRefCupon.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {

                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return storageRefCupon.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {

                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Toast.makeText(getActivity(),"cargando foto...", Toast.LENGTH_SHORT).show();
                    if (task.isSuccessful()) {
                        Uri urifoto = task.getResult();
                        assert urifoto != null;
                        cupon.setFoto(urifoto.toString());

                        Toast.makeText(getActivity(),"foto cargada correctamente!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /////////////////////////////////////////////////////////////////////////////////metodos por default
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CuponeraClienteFragment.OnFragmentInteractionListener) {
            mListener = (CuponeraClienteFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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