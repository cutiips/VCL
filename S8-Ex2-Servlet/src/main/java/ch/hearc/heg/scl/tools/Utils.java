package ch.hearc.heg.scl.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static int countWords(String line){
        if (line == null || line.isEmpty()){
            return 0;
        }
        String words[] = line.trim().split("\\s+");
        return words.length;
    }

    public static String formatDate(Date dt){
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        return df.format(dt);
    }
}
