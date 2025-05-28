package com.example.e_blood_bank_app;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.e_blood_bank_app.ModelClass.BloodBankModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity {
    private MapView mapView;
    private MyLocationNewOverlay myLocationOverlay;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private GeoPoint userLocation;
    private Polyline roadOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Configuration.getInstance().load(getApplicationContext(),
                getSharedPreferences("osmdroid", MODE_PRIVATE));

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);


        GeoPoint indiaCenter = new GeoPoint(22.9734, 78.6569); // Approximate center of India
        mapView.getController().setZoom(6.5); // Good zoom to show whole India
        mapView.getController().setCenter(indiaCenter);


        addHospitalMarkers();

        enableUserLocation();

        ImageButton zoomInButton = findViewById(R.id.zoomInButton);
        ImageButton zoomOutButton = findViewById(R.id.zoomOutButton);
        ImageButton centerLocationButton = findViewById(R.id.centerLocationButton);

        zoomInButton.setOnClickListener(v -> mapView.getController().zoomIn());

        zoomOutButton.setOnClickListener(v -> mapView.getController().zoomOut());

        centerLocationButton.setOnClickListener(v -> {
            if (userLocation != null) {
                mapView.getController().animateTo(userLocation);
            }
        });

        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    private void addHospitalMarkers() {
        List<BloodBankModel> bloodBanks = getBloodBanks();

        for (BloodBankModel hospital : bloodBanks) {
            GeoPoint point = getGeoPoint(hospital.getAddress());

            if (point != null) {

                addMarker(point, hospital.getName(), "📞 " + hospital.getPhone());
            }
        }


        mapView.invalidate();
    }

    private List<BloodBankModel> getBloodBanks() {

        List<BloodBankModel> bloodBanks = new ArrayList<>();
        bloodBanks.add(new BloodBankModel("K.E.M. Hospital Blood Bank", "Parel,Mumbai,Maharashtra400012", "022-24136051"));
        bloodBanks.add(new BloodBankModel("JJ Hospital Blood Bank", "JJMarg,Nagpada,Mumbai,Maharashtra400008", "022-23735555"));
        bloodBanks.add(new BloodBankModel("Tata Memorial Hospital Blood Bank", "Dr.EBorgesRoad,Parel,Mumbai,Maharashtra400012", "022-24177000"));
        bloodBanks.add(new BloodBankModel("Sassoon General Hospital Blood Bank", "StationRoad,Pune,Maharashtra411001", "020-26127700"));
        bloodBanks.add(new BloodBankModel("Ruby Hall Clinic Blood Bank", "40,SassoonRoad,Pune,Maharashtra411001", "020-26163391"));
        bloodBanks.add(new BloodBankModel("Hinduja Hospital Blood Bank", "VeerSavarkarMarg,Mahim,Mumbai,Maharashtra400016", "022-24452222"));
        bloodBanks.add(new BloodBankModel("Lilavati Hospital Blood Bank", "BandraWest,Mumbai,Maharashtra400050", "022-26751000"));
        bloodBanks.add(new BloodBankModel("Kokilaben Hospital Blood Bank", "AndheriWest,Mumbai,Maharashtra400053", "022-30666666"));
        bloodBanks.add(new BloodBankModel("Breach Candy Hospital Blood Bank", "BhulabhaiDesaiRoad,Mumbai,Maharashtra400026", "022-23667788"));
        bloodBanks.add(new BloodBankModel("Nanavati Hospital Blood Bank", "VileParleWest,Mumbai,Maharashtra400056", "022-26267500"));
        bloodBanks.add(new BloodBankModel("HCG Cancer Centre Blood Bank", "BorivaliWest,Mumbai,Maharashtra400091", "022-30017000"));
        bloodBanks.add(new BloodBankModel("Rajiv Gandhi Medical College Blood Bank", "Thane-BelapurRd,Kalwa,Thane,Maharashtra400605", "022-25347784"));
        bloodBanks.add(new BloodBankModel("Apollo Hospitals Blood Bank", "CBDBelapur,NaviMumbai,Maharashtra400614", "022-33503350"));
        bloodBanks.add(new BloodBankModel("Jehangir Hospital Blood Bank", "SassoonRoad,Pune,Maharashtra411001", "020-66819999"));
        bloodBanks.add(new BloodBankModel("Sahyadri Hospital Blood Bank", "NagarRoad,Pune,Maharashtra411014", "020-67215000"));
        bloodBanks.add(new BloodBankModel("Aditya Birla Memorial Hospital Blood Bank", "Thergaon,Pune,Maharashtra411033", "020-30717500"));
        bloodBanks.add(new BloodBankModel("D Y Patil Hospital Blood Bank", "Sector5,Nerul,NaviMumbai,Maharashtra400706", "022-27735901"));
        bloodBanks.add(new BloodBankModel("Hiranandani Hospital Blood Bank", "Powai,Mumbai,Maharashtra400076", "022-25763300"));
        bloodBanks.add(new BloodBankModel("Wockhardt Hospital Blood Bank", "MiraRoad,Mumbai,Maharashtra401107", "022-28634444"));
        bloodBanks.add(new BloodBankModel("MGM Medical College & Hospital Blood Bank", "CBDBelapur,NaviMumbai,Maharashtra400614", "022-61529999"));
        bloodBanks.add(new BloodBankModel("Bharati Vidyapeeth Medical College Blood Bank", "Dhankawadi,Pune,Maharashtra411043", "020-24373129"));
        bloodBanks.add(new BloodBankModel("Smt. Kashibai Navale Medical College Blood Bank", "Narhe,Pune,Maharashtra411041", "020-24692300"));
        bloodBanks.add(new BloodBankModel("Inlaks and Budhrani Hospital Blood Bank", "KoregaonPark,Pune,Maharashtra411001", "020-66096000"));
        bloodBanks.add(new BloodBankModel("Fortis Hospital Blood Bank", "Mulund,Mumbai,Maharashtra400078", "022-41114111"));
        bloodBanks.add(new BloodBankModel("Sterling Hospital Blood Bank", "Jalgaon,Maharashtra425001", "0257-2224444"));
        bloodBanks.add(new BloodBankModel("United CIIGMA Hospital Blood Bank", "Aurangabad,Maharashtra431001", "0240-6607700"));
        bloodBanks.add(new BloodBankModel("Global Hospital Blood Bank", "Parel,Mumbai,Maharashtra400012", "022-67670202"));
        bloodBanks.add(new BloodBankModel("MIT Hospital Blood Bank", "Kothrud,Pune,Maharashtra411038", "020-30272999"));
        bloodBanks.add(new BloodBankModel("Noble Hospital Blood Bank", "Magarpatta,Hadapsar,Pune,Maharashtra411013", "020-66284500"));
        bloodBanks.add(new BloodBankModel("Shatayu Hospital Blood Bank", "Nashik,Maharashtra422005", "0253-2595000"));
        bloodBanks.add(new BloodBankModel("Lotus Multi-Speciality Hospital Blood Bank", "Thane,Maharashtra400601", "022-25380111"));
        bloodBanks.add(new BloodBankModel("Spandan Hospital Blood Bank", "Aurangabad,Maharashtra431003", "0240-2233444"));
        bloodBanks.add(new BloodBankModel("Medicare Hospital Blood Bank", "Nagpur,Maharashtra440012", "0712-2557444"));
        bloodBanks.add(new BloodBankModel("BKL Walawalkar Hospital Blood Bank", "Chiplun,Maharashtra415605", "02355-264111"));
        bloodBanks.add(new BloodBankModel("Satara Civil Hospital Blood Bank", "Satara,Maharashtra415002", "02162-234455"));
        return  bloodBanks;
    }

    private GeoPoint getGeoPoint(String address) {
        address = address.replaceAll("(\\D)(\\d{6})", "$1"); // Remove 6-digit pin
        address = address.replaceAll(",", ", ").replaceAll("\\s+", " ").trim();
        switch (address) {
            case "Parel, Mumbai, Maharashtra":
                return new GeoPoint(19.00176, 72.84224);
            case "Nagpada, Mumbai, Maharashtra":
                return new GeoPoint(18.962971, 72.833548);
            case "Dr. E Borges Road, Parel, Mumbai, Maharashtra":
                return new GeoPoint(19.0033, 72.8414);
            case "Station Road, Pune, Maharashtra":
                return new GeoPoint(18.52571, 73.87194);
            case "40, Sassoon Road, Pune, Maharashtra":
            case "Sassoon Road, Pune, Maharashtra":
                return new GeoPoint(18.5316, 73.8762);
            case "Veer Savarkar Marg, Mahim, Mumbai, Maharashtra":
                return new GeoPoint(19.033205, 72.838605);
            case "Bandra West, Mumbai, Maharashtra":
                return new GeoPoint(19.051069, 72.829209);
            case "Andheri West, Mumbai, Maharashtra":
                return new GeoPoint(19.136326, 72.827660);
            case "Bhulabhai Desai Road, Mumbai, Maharashtra":
                return new GeoPoint(18.972578, 72.804436);
            case "Vile Parle West, Mumbai, Maharashtra":
                return new GeoPoint(19.102512, 72.845367);
            case "Borivali West, Mumbai, Maharashtra":
                return new GeoPoint(19.217907, 72.847084);
            case "Thane-Belapur Rd, Kalwa, Thane, Maharashtra":
                return new GeoPoint(19.194386, 72.999199);
            case "CBD Belapur, Navi Mumbai, Maharashtra":
                return new GeoPoint(19.025806, 73.041550);
            case "Nagar Road, Pune, Maharashtra":
                return new GeoPoint(18.5645, 73.9184);
            case "Thergaon, Pune, Maharashtra":
                return new GeoPoint(18.5980, 73.7707);
            case "Sector 5, Nerul, Navi Mumbai, Maharashtra":
                return new GeoPoint(19.0330, 73.0297);
            case "Powai, Mumbai, Maharashtra":
                return new GeoPoint(19.1186, 72.9097);
            case "Mira Road, Mumbai, Maharashtra":
                return new GeoPoint(19.2813, 72.8680);
            case "Dhankawadi, Pune, Maharashtra":
                return new GeoPoint(18.4575, 73.8588);
            case "Narhe, Pune, Maharashtra":
                return new GeoPoint(18.4570, 73.8215);
            case "Koregaon Park, Pune, Maharashtra":
                return new GeoPoint(18.5362, 73.8936);
            case "Mulund, Mumbai, Maharashtra":
                return new GeoPoint(19.1726, 72.9421);
            case "Jalgaon, Maharashtra":
                return new GeoPoint(21.0077, 75.5626);
            case "Aurangabad, Maharashtra":
                return new GeoPoint(19.8762, 75.3433);
            case "Kothrud, Pune, Maharashtra":
                return new GeoPoint(18.5074, 73.8077);
            case "Magarpatta, Hadapsar, Pune, Maharashtra":
                return new GeoPoint(18.5121, 73.9254);
            case "Nashik, Maharashtra":
                return new GeoPoint(19.9975, 73.7898);
            case "Thane, Maharashtra":
                return new GeoPoint(19.2183, 72.9781);
            case "Nagpur, Maharashtra":
                return new GeoPoint(21.1458, 79.0882);
            case "Chiplun, Maharashtra":
                return new GeoPoint(17.5334, 73.5094);
            case "Satara, Maharashtra":
                return new GeoPoint(17.6805, 74.0183);
            default:
                Log.e("MAP", "Address not found in switch: " + address);
                return null;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addMarker(GeoPoint point, String title, String description) {

        if (point == null) {
            Log.e("MAP", "Skipped adding marker because GeoPoint is null for: " + title + " | " + description);
            return;
        }

        Log.d("MAP", "Adding marker for: " + title + " | " + description + " @ (" + point.getLatitude() + ", " + point.getLongitude() + ")");

        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setTitle(title);
        marker.setSubDescription(description);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.map_location_pin_24);
        Bitmap bitmap = getBitmapFromDrawable(drawable);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
        Drawable resizedDrawable = new BitmapDrawable(getResources(), resizedBitmap);

        marker.setIcon(resizedDrawable);

        marker.setOnMarkerClickListener((m, mapView) -> {
            marker.showInfoWindow();
            animateMarkerBounce(marker, R.drawable.map_location_pin_24); // Call bounce
            if (userLocation != null) {
                drawRoute(userLocation, point);
            }
            return true;
        });

        mapView.getOverlays().add(marker);
    }

    private void animateMarkerBounce(Marker marker, int drawableResId) {
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 1.3f, 1f); // Scale up and back down
        animator.setDuration(300);
        animator.setInterpolator(new BounceInterpolator());

        Drawable drawable = ContextCompat.getDrawable(this, drawableResId);

        animator.addUpdateListener(animation -> {
            float scale = (float) animation.getAnimatedValue();
            int size = (int) (80 * scale);
            Bitmap bitmap = getBitmapFromDrawable(drawable);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
            marker.setIcon(new BitmapDrawable(getResources(), scaledBitmap));
            mapView.invalidate(); // Redraw the map
        });

        animator.start();
    }



    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : 100,
                drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : 100,
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }



    private void enableUserLocation() {
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        myLocationOverlay.enableMyLocation();
        mapView.getOverlays().add(myLocationOverlay);

        myLocationOverlay.runOnFirstFix(() -> runOnUiThread(() -> userLocation = myLocationOverlay.getMyLocation()));
    }

    private void drawRoute(GeoPoint start, GeoPoint end) {
        new FetchRouteTask().execute(start, end);
    }

    private class FetchRouteTask extends AsyncTask<GeoPoint, Void, List<GeoPoint>> {
        private double distanceKm = 0;

        @Override
        protected List<GeoPoint> doInBackground(GeoPoint... points) {
            GeoPoint start = points[0];
            GeoPoint end = points[1];
            List<GeoPoint> route = new ArrayList<>();

            try {
                String urlString = "https://router.project-osrm.org/route/v1/driving/" +
                        start.getLongitude() + "," + start.getLatitude() + ";" +
                        end.getLongitude() + "," + end.getLatitude() +
                        "?overview=full&geometries=geojson";

                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                JSONObject json = new JSONObject(response.toString());

                JSONArray coordinates = json.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONArray("coordinates");

                distanceKm = json.getJSONArray("routes")
                        .getJSONObject(0)
                        .getDouble("distance") / 1000.0; // in km

                for (int i = 0; i < coordinates.length(); i++) {
                    JSONArray coord = coordinates.getJSONArray(i);
                    double lon = coord.getDouble(0);
                    double lat = coord.getDouble(1);
                    route.add(new GeoPoint(lat, lon));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return route;
        }

        @Override
        protected void onPostExecute(List<GeoPoint> route) {
            if (route == null || route.isEmpty()) {
                Toast.makeText(MapsActivity.this, "Failed to fetch route.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (roadOverlay != null) {
                mapView.getOverlayManager().remove(roadOverlay);
            }

            roadOverlay = new Polyline();
            roadOverlay.setPoints(route);
            roadOverlay.setColor(ContextCompat.getColor(MapsActivity.this, R.color.teal_700));
            roadOverlay.setWidth(8.0f);
            mapView.getOverlayManager().add(roadOverlay);
            mapView.invalidate();

            Toast.makeText(MapsActivity.this,
                    String.format("Distance: %.2f km", distanceKm),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void requestPermissionsIfNecessary(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }
    }
}
