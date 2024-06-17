package ch.francillon.scl.samples;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;

public class Iguane implements Jsonable, Serializable {
    private String nom;
    private int age;
    private double taille;

    public Iguane() {
    }

    public Iguane(String nom, int age, double taille) {
        this.nom = nom;
        this.age = age;
        this.taille = taille;
    }

    @Override
    public String toString() {
        return "Iguane{" +
                "nom='" + nom + '\'' +
                ", age=" + age +
                ", taille=" + taille +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public String serialize(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.nom);
        sb.append("#");
        sb.append(this.age);
        sb.append("#");
        sb.append(this.taille);
        return sb.toString();
    }

    public void deserialize(String object){
        String[] tableIguane = object.split("#");
        this.nom = tableIguane[0];
        this.age = Integer.parseInt(tableIguane[1]);
        this.taille = Double.parseDouble(tableIguane[2]);
    }

    @Override
    public String toJson() {
        StringWriter writable = new StringWriter();
        try{
            this.toJson(writable);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return writable.toString();
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        JsonObject json = new JsonObject();
        json.put("nom", this.getNom());
        json.put("age", this.getAge());
        json.put("taille", this.getTaille());
        json.toJson(writer);
    }

    public void fromJson(String jsonInput){
        JsonObject deserializedObject = Jsoner.deserialize(jsonInput, new JsonObject());
        this.setNom((String)deserializedObject.get("nom"));
        this.setAge(((BigDecimal)deserializedObject.get("age")).intValue());
        this.setTaille(((BigDecimal)deserializedObject.get("taille")).doubleValue());
    }
}
