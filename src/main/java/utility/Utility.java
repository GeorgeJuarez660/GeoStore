package src.main.java.utility;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Utility {

    static Scanner input = new Scanner(System.in);

    public static void msgInf(String owner, String text) //metodo di messaggio info
    {
        System.out.println(owner + ": " + text);
    }

    public static int insertInt(String title){
        System.out.println(title);
        int num = 0;
        boolean flag = false;

        do{ //controlla ripetutamente se è un numero
            try{
                num = Integer.parseInt(input.nextLine());
            }catch(NumberFormatException err){
                msgInf("GEOSTORE", "devi inserire un valore numerico");
                flag = true;
            }
            catch (Exception err){
                msgInf("GEOSTORE", "errore");
                flag = true;
            }
        }while(flag);

        return num;

    }

    public static BigDecimal insertBigDecimal(String title){
        System.out.println(title);
        BigDecimal num = new BigDecimal(0);
        boolean flag = false;

        do{ //controlla ripetutamente se è un numero
            try{
                num = new BigDecimal(input.nextLine());
            }catch(NumberFormatException err){
                msgInf("GEOSTORE", "devi inserire un valore decimale");
                flag = true;
            }
            catch (Exception err){
                msgInf("GEOSTORE", "errore");
                flag = true;
            }
        }while(flag);

        return num;

    }

    public static String insertString(String title){
        System.out.println(title);
        String word = input.nextLine();

        return word;

    }
}
