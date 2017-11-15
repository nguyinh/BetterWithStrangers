package ubiquasif.uqac.betterwithstrangers.maps.android.data.kml;

import com.google.android.gms.maps.model.LatLng;
import ubiquasif.uqac.betterwithstrangers.maps.android.data.Point;

/**
 * Represents a KML Point. Contains a single coordinate.
 */
public class KmlPoint extends Point {

    /**
     * Creates a new KmlPoint
     *
     * @param coordinates coordinates of the KmlPoint
     */
    public KmlPoint(LatLng coordinates) {
        super(coordinates);
    }

}