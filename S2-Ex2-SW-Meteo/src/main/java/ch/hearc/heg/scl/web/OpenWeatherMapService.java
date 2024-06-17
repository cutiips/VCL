package ch.hearc.heg.scl.web;

import ch.hearc.heg.scl.business.Ville;

public class OpenWeatherMapService {
    private static final String API_KEY = "b65a26eae33026f6cd6954f6ebd0f7bd";
    private static final String UNITS = "metric";
    private static final String LANG = "fr";

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static String getJSON(Ville ville) {
        String service = BASE_URL + "?lat=" + ville.getLatitude() + "&lon=" + ville.getLongitude() + "&units=" + UNITS + "&lang=" + LANG + "&appid=" + API_KEY;
        return Generic.getWebResponse(service);
    }
}
