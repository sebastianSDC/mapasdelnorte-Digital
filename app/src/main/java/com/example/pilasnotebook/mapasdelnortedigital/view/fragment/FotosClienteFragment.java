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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pilasnotebook.mapasdelnortedigital.R;
import com.google.firebase.firestore.FirebaseFirestore;


public class FotosClienteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private static final String TIPO = "tipo";
    private static final String FECHA = "fecha";
    private static final String TITULO = "titulo";
    private static final String DESCRIPCION = "descripcion";
    private static final String DIRECCION = "direccion";
    private static final String TELEFONO = "telefono";
    private static final String MAIL = "mail";
    private static final String TAG = "tag";

    private EditText tituloEd, descripcionEd, direccionEd, telefonoEd, mailEd;
    private Spinner tipos;

    private Button btnCargar, btnTraer;
    private TextView datosCuponera;
    private String tipoCuponera;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String documentId= db.collection("clientes").document().getId();


    public FotosClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fotos, container, false);



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
        if (context instanceof FotosClienteFragment.OnFragmentInteractionListener) {
            mListener = (FotosClienteFragment.OnFragmentInteractionListener) context;
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