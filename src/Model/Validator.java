package Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public static String hashInfo(String info){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(info.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
