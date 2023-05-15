package com.example.firstgame1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.example.firstgame1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GMapFragment extends Fragment {
    private GoogleMap mMap;
    private OnMapReadyCallback callback = googleMap -> { mMap = googleMap; };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gmap, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.gmap_MAP_view);
        supportMapFragment.getMapAsync(callback);
        return view;
    }

    public void markOnMap(double x, double y) {
        LatLng point = new LatLng(x, y);
        mMap.addMarker(new MarkerOptions().position(point).title(""));
        moveToCurrentLocation(point);

    }

    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
//        mMap.animateCamera(CameraUpdateFactory.zoomIn());
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }
}