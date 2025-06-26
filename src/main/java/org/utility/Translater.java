package org.utility;

public class Translater {

    public static String chosenLanguage = "it";


    public static void setLanguage(String language){
        chosenLanguage = language;
    }

    public static String getLanguage(){
        return chosenLanguage;
    }
}
