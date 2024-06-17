package ch.francillon.scl.samples;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Iguane ig = new Iguane("Zigi",23,125.4);

        System.out.println("Sérialisation personnalisée : ");
        String serializedIguane = ig.serialize();
        System.out.println(serializedIguane);

        Iguane bl = new Iguane();
        bl.deserialize(serializedIguane);
        System.out.println(bl);

        System.out.println();
        System.out.println("Sérialisation JSON : ");
        String jsonIguane = ig.toJson();
        System.out.println(jsonIguane);

        Iguane ee = new Iguane();
        ee.fromJson(jsonIguane);
        System.out.println(ee.toString());
    }

}