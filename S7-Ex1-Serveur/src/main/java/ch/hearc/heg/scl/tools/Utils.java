package ch.hearc.heg.scl.tools;

public class Utils {
    public static int countWords(String line){
        if (line == null || line.isEmpty()){
            return 0;
        }
        String words[] = line.trim().split("\\s+");
        return words.length;
    }
}
