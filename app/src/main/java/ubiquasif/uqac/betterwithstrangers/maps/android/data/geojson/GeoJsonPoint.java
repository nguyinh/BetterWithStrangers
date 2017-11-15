package ubiquasif.uqac.betterwithstrangers.maps.android.data.geojson;

import com.google.android.gms.maps.model.LatLng;
import ubiquasif.uqac.betterwithstrangers.maps.android.data.Point;

/**
 * A GeoJsonPoint geometry contains a single {@link LatLng}.
 */
public class GeoJsonPoint extends Point {

    /**
     * Creates a new GeoJsonPoint
     *
     * @param coordinates coordinates of GeoJsonPoint to store
     */
    public GeoJsonPoint(LatLng coordinates) {
        super(coordinates);
    }

    /**
     * Gets the type of geometry. The type of geometry conforms to the GeoJSON 'type'
     * specification.
     *
     * @return type of geometry
     */
    public String getType() {
        return getGeometryType();
    }

    /**
     * Gets the coordinates of the GeoJsonPoint
     *
     * @return coordinates of the GeoJsonPoint
     */
    public LatLng getCoordinates() {
        return getGeometryObject();
    }

}