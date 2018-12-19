package com.example.pilasnotebook.mapasdelnortedigital.view.fragment;


/**
 * Creada por Sebastián Suárez Da Costa y Pablo Herrera.
 */


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pilasnotebook.mapasdelnortedigital.R;

;

public class MapaClienteFragment extends Fragment /*implements OnMapReadyCallback*/ {

    private OnFragmentInteractionListener mListener;

    // private GoogleMap mGoogleMap;
    //private View mView;
    //MapView mapView;
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private TextView txvDirComercio, txvCoordComercio;
    //private Cliente cliente = new Cliente();

    public MapaClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mapa, container, false);

        //txvCoordComercio = mView.findViewById(R.id.txt_coordenadas_comercio);
        //txvDirComercio = mView.findViewById(R.id.txt_direccion_comercio);

        //DocumentReference latlangRef = db.collection("clientes").document("prueba");
        /*latlangRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String direccionTxt = document.getString(Constantes.DIRECCION+","+Constantes.
                                LOCALIDAD+","+Constantes.PROVINCIA+","+Constantes.PAIS);

                        String coordenadasTxt = document.getString(Constantes.COORDENADAS);

                        txvDirComercio.setText(direccionTxt);
                        txvCoordComercio.setText(coordenadasTxt);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/
        //latlangRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        //@Override
        //public void onSuccess(DocumentSnapshot documentSnapshot) {
        //cliente = documentSnapshot.toObject(Cliente.class);
//Map<String,Object> datosCliente = documentSnapshot.getData();
//datosCliente.get(Constantes.COORDENADAS);

        //      }
        // });

        // return mView;
        return v;
    }

/*    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.GoogleMap_container);

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

        //double latitud = cliente.getZona().getLatlang().latitude;
        //double longitud = cliente.getZona().getLatlang().longitude;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng comercio = new LatLng(-34.5312443, -58.479468);
        mGoogleMap.setMinZoomPreference(15);
        mGoogleMap.addMarker(new MarkerOptions().position(comercio).title("cliente"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(comercio));
    }*/


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        //  if (mListener != null) {
        //         mListener.onFragmentInteraction(uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MapaClienteFragment.OnFragmentInteractionListener) {
            mListener = (MapaClienteFragment.OnFragmentInteractionListener) context;
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
