package Model;

import java.util.Scanner;

public class Validator {
    private static final Scanner SCANNER = new Scanner(System.in);
    public Validator(){}
    public static String getString(String messageInfo, String messageError, final String REGEX){

        do {
            System.out.print(messageInfo);
            String str = SCANNER.nextLine();
            if (str.matches(REGEX)) {
                return str;
            }
            System.err.println(messageError);
        }
        while (true);
    }
    public static boolean getBoolen(String messageInfo){
        String isContinue = getString(messageInfo,"Please enter Y or N to continue or back to menu","[yYnN]");
        if (isContinue.toUpperCase().equals("Y")){
            return true;
        }
        else {
            return false;
        }
    }
}
