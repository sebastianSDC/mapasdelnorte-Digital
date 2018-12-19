package com.example.pilasnotebook.mapasdelnortedigital.view.fragment;


/**
 * Creada por Sebastián Suárez Da Costa y Pablo Herrera.
 */


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.firebase.firestore.FirebaseFirestore;


public class CuponeraClienteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


    private static final String TAG = "tag";

    private EditText fechaEd, tituloEd, descripcionEd, nombreEd, direccionEd, telefonoEd, mailEd;
    private Spinner tipos;
    private ImageView fotoCuponera;
    private Button btnCargarFoto, btnCargar, btnTraer;
    private TextView datosCuponera;
    private String tipoCuponeraTxt;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String documentId= db.collection("clientes").document().getId();


    public CuponeraClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cuponera, container, false);

        tipos = view.findViewById(R.id.spinn_tipo_cuponera);
        fechaEd = view.findViewById(R.id.ed_fecha_cuponera);
        btnCargarFoto = view.findViewById(R.id.btn_cargarFoto_cuponera);
        fotoCuponera = view.findViewById(R.id.imagen_cuponera);
        tituloEd = view.findViewById(R.id.ed_titulo_cuponera);
        descripcionEd = view.findViewById(R.id.ed_descripcion_cuponera);
        nombreEd = view.findViewById(R.id.ed_nombre_comercio_cuponera);
        direccionEd = view.findViewById(R.id.ed_direccion_comercio_cuponera);
        telefonoEd = view.findViewById(R.id.ed_telefono_comercio_cuponera);
        mailEd = view.findViewById(R.id.ed_mail_comercio_cuponera);


        datosCuponera = view.findViewById(R.id.datos_cuponera);
        btnCargar = view.findViewById(R.id.btn_cargar_cuponera);
        btnTraer = view.findViewById(R.id.btn_traer_datos_cuponera);

        ArrayAdapter<CharSequence> adapterSpinCAtegorias = ArrayAdapter.createFromResource(getActivity(),
                R.array.combo_tipos_cuponera, android.R.layout.simple_spinner_item);

        tipos.setAdapter(adapterSpinCAtegorias);

        tipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoCuponeraTxt = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
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
        if (context instanceof CuponeraClienteFragment.OnFragmentInteractionListener) {
            mListener = (CuponeraClienteFragment.OnFragmentInteractionListener) context;
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