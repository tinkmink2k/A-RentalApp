package edu.re.estate.presenters.main;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Random;

import edu.re.estate.R;
import edu.re.estate.data.models.Post;
import edu.re.estate.databinding.FragmentExploreBinding;

public class ExploreFragment extends Fragment implements OnMapReadyCallback {

    private static ExploreFragment instance;

    public static ExploreFragment getInstance() {
        if (instance == null) {
            instance = new ExploreFragment();
        }
        return instance;
    }

    private FragmentExploreBinding binding;

    GoogleMap mGoogleMap;
    View mapView;

    private static final int MAX_RETRY = 5;
    private static final long DELAY_MS = 2000;

    private int retryCount = 0;
    private Handler locationHandler = new Handler(Looper.getMainLooper());

    private void tryGetLocation() {
        locationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!isAdded()) return;

//                if (((MainActivity) requireActivity()).currentLocation != null) {

//                    double lat = ((MainActivity) requireActivity()).currentLocation.getLatitude();
                    double lat = 21.028500; // ((MainActivity) requireActivity()).currentLocation.getLatitude();
                    double lng = 105.854200; // ((MainActivity) requireActivity()).currentLocation.getLongitude();
//                    double lng = ((MainActivity) requireActivity()).currentLocation.getLongitude();

                    Log.d("LOCATION", "Got location: " + lat + ", " + lng);

                    // onLocationReady(lat, lng);
                    LatLng latLng = new LatLng(lat, lng);
                    ExploreFragment.this.mGoogleMap.addMarker(new MarkerOptions().position(latLng));
                    ExploreFragment.this.mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
                    ExploreFragment.this.mGoogleMap.setMapType(1);

                    List<Post> posts = ((MainActivity) requireActivity()).dataFirst;
                    if (posts != null && !posts.isEmpty()) {
                        for (Post post : posts) {
                            if (post.getLat() != null && post.getLng() != null) {
                                LatLng point = new LatLng(Double.parseDouble(post.getLat()), Double.parseDouble(post.getLng()));

                                mGoogleMap.addMarker(
                                        new MarkerOptions()
                                                .position(point)
                                                .title(post.getTitle())
                                                .snippet(post.getDescription())
                                                .icon(getResizedMarker(R.drawable.ic_villa))
                                );
                            }
                        }
                    }

                    ExploreFragment.this.mGoogleMap.setOnCameraChangeListener(cameraPosition -> {
                        LatLng latLng1 = cameraPosition.target;
//                LocationTrackerActivity.this.mGoogleMap.clear();
                        try {
                            Log.d("TAG", "onMarkerDragEnd: " + latLng1.latitude + "::" + latLng1.longitude);
//                    LocationTrackerActivity.this.lat = latLng.latitude;
//                    LocationTrackerActivity.this.longi = latLng.longitude;
//                    try {
//                        LocationTrackerActivity.this.loc_func();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    });
                    return; // ✅ STOP
//                }

//                retryCount++;
//                if (retryCount < MAX_RETRY) {
//                    locationHandler.postDelayed(this, DELAY_MS);
//                } else {
//                    Log.e("LOCATION", "Failed after 5 retries");
//                    // onLocationFailed();
//                }
            }
        }, DELAY_MS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.fragment_mapview);
        if (supportMapFragment != null) {
            this.mapView = supportMapFragment.getView();
            supportMapFragment.getMapAsync(this);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void reloadData() {

//        binding.webView.getSettings().setDomStorageEnabled(true);
//        binding.webView.getSettings().setJavaScriptEnabled(true);
//        binding.webView.loadUrl("https://www.google.com/maps/search/@" + lat + "," + lng + ",13z?entry=ttu&g_ep=EgoyMDI0MTIwNC4wIKXMDSoASAFQAw%3D%3D");
//        binding.webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mGoogleMap = googleMap;

//    tab 1
//        new Handler().postDelayed(() -> requireActivity().runOnUiThread(() -> {
//            double lat = 0L, lng = 0L;
//            if (((MainActivity) requireActivity()).currentLocation != null) {
//                lat = ((MainActivity) requireActivity()).currentLocation.getLatitude();
//                lng = ((MainActivity) requireActivity()).currentLocation.getLongitude();
//
//                Log.d("LOCATION_Z", "lat = " + lat + ", lng = " + lng);
//            }
//
//            LatLng latLng = new LatLng(lat, lng);
//            ExploreFragment.this.mGoogleMap.addMarker(new MarkerOptions().position(latLng));
//            ExploreFragment.this.mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
//            ExploreFragment.this.mGoogleMap.setMapType(1);
//
//            for (int i = 0; i < 50; i++) {/// code cũ rồi, kiểm tra đoạn này đi
//                LatLng point = randomLocation(lat, lng);
//
//                mGoogleMap.addMarker(
//                        new MarkerOptions()
//                                .position(point)
//                                .title("Địa điểm " + (i + 1))
//                                .snippet("Cho thuê văn phòng, căn hộ cao cấp")
//                                .icon(getResizedMarker(R.drawable.ic_villa))
//                );
//            }
//
//            ExploreFragment.this.mGoogleMap.setOnCameraChangeListener(cameraPosition -> {
//                LatLng latLng1 = cameraPosition.target;
////                LocationTrackerActivity.this.mGoogleMap.clear();
//                try {
//                    Log.d("TAG", "onMarkerDragEnd: " + latLng1.latitude + "::" + latLng1.longitude);
////                    LocationTrackerActivity.this.lat = latLng.latitude;
////                    LocationTrackerActivity.this.longi = latLng.longitude;
////                    try {
////                        LocationTrackerActivity.this.loc_func();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//                } catch (Exception e2) {
//                    e2.printStackTrace();
//                }
//            });
//        }), 2000L);

        tryGetLocation();
    }

    private LatLng randomLocation(double lat, double lng) {
        Random random = new Random();

        double radiusInDegrees = 5000 / 111000.0;

        double u = random.nextDouble();
        double v = random.nextDouble();

        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;

        double deltaLat = w * Math.cos(t);
        double deltaLng = w * Math.sin(t) / Math.cos(Math.toRadians(lat));

        return new LatLng(lat + deltaLat, lng + deltaLng);
    }

    private BitmapDescriptor getResizedMarker(@DrawableRes int drawableId) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 60, 60, false);
        return BitmapDescriptorFactory.fromBitmap(resizedBitmap);
    }
}
