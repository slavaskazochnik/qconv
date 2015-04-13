package by.parfen.disptaxi.services.utils;

/*
 *  Get Distance in meters between two points.
 * */
public class GeoService {

	private final Double INVALID_VALUE = -1.0;

	private double distance(double lat1, double lng1, double lat2, double lng2) {

		final int R = 6371; // Radius of the earth

		Double latDistance = deg2rad(lat2 - lat1);
		Double lngDistance = deg2rad(lng2 - lng1);
		// correct longitude by latitude
		lngDistance = lngDistance * Math.cos(lat1 * Math.PI / 180);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(deg2rad(lat1))
				* Math.cos(deg2rad(lat2)) * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		distance = Math.pow(distance, 2);
		return Math.sqrt(distance);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private boolean validLat(Double lat) {
		return ((Double.compare(lat, -90.0) > 0) && (Double.compare(lat, 90.0) < 0));
	}

	private boolean validLon(Double lng) {
		return ((Double.compare(lng, -180.0) > 0) && (Double.compare(lng, 180.0) < 0));
	}

	public double getGeoDistance(double lat1, double lng1, double lat2, double lng2) {
		double result;
		if (validLat(lat1) && validLat(lat2) && validLon(lng1) && validLon(lng2)) {
			result = Math.round(distance(lat1, lng1, lat2, lng2));
		} else {
			result = INVALID_VALUE;
		}
		return result;
	}

	public double getGeoDistance(String lat1, String lng1, String lat2, String lng2) {
		Double result;
		try {
			result = getGeoDistance(Double.valueOf(lat1), Double.valueOf(lng1), Double.valueOf(lat2),
					Double.valueOf(lng2));
		} catch (NumberFormatException e) {
			result = INVALID_VALUE;
		}
		return result;
	}

	public static void main(String[] args) {
		GeoService ds = new GeoService();
		// http://map.by/map/getHomeNumberCoordinates/Гродно/Космонавтов/100
		String lat1 = "53.6732788076222";
		String lng1 = "23.8826319275965";
		// http://map.by/map/getHomeNumberCoordinates/Гродно/Клецкова/40
		String lat2 = "53.6504820694604";
		String lng2 = "23.8434917538399";

		Double r;
		r = ds.getGeoDistance(lat1, lng1, lat2, lng2);
		System.out.println(r);
		r = ds.getGeoDistance(53.673278, 23.882631, 53.650482, 23.843491);
		System.out.println(r);
		r = ds.getGeoDistance("we", lng1, lat2, lng2);
		System.out.println(r);
		r = ds.getGeoDistance(89.0, 223.0, 53.0, 23.0);
		System.out.println(r);
	}

}
