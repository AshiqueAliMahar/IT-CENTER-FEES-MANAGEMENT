package Ali.ashique.ITCENTERFEESMANAGEMENT.service;

public class StringUtil {
    public static boolean isNumber(String number) {
        boolean isNumber = true;
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return isNumber;
    }
}
