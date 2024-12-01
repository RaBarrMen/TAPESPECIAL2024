package org.example.tapesp2024.vistas;

public class CheckInput {



    public static String generateExtension(){
        String extension = "";
        int extensionCase = (int) (Math.random() * 4);
        switch(extensionCase){
            case 0:extension = ".pdf";
                break;
            case 1:extension = ".csv";
                break;
            case 2:extension = ".txt";
                break;
            case 3:extension = ".dat";
                break;
            default:extension = ".bin";
        }
        return extension;
    }

    public static boolean checkInt(String num){
        boolean result = true;
        if(num == null || (num.length() == 0)){
            result = false;
        }else{
            for (int i = 0; i < num.length(); i++) {
                if (((num.charAt(i) == '0') ||
                        (num.charAt(i) == '1') ||
                        (num.charAt(i) == '2') ||
                        (num.charAt(i) == '3') ||
                        (num.charAt(i) == '4') ||
                        (num.charAt(i) == '5') ||
                        (num.charAt(i) == '6') ||
                        (num.charAt(i) == '7') ||
                        (num.charAt(i) == '8') ||
                        (num.charAt(i) == '9'))){
                    result = true;
                }else{
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public static boolean checkOdd(int number) {
        boolean result = true;
        if(number % 2 == 0)result = false;
        return result;
    }
    public static boolean checkDouble(String num){
        int contPunt = 0;
        char temp = '0';
        boolean result = false;
        if((num == null) || (num.length() == 0)){
            result = false;
        }else {
            if ((num.charAt(0) != '.') && (num.charAt(num.length() - 1) != '.')) {
                for (int i = 0; i < num.length(); i++) {
                    if ((num.charAt(i) == '.') ||
                            (num.charAt(i) == '0') ||
                            (num.charAt(i) == '1') ||
                            (num.charAt(i) == '2') ||
                            (num.charAt(i) == '3') ||
                            (num.charAt(i) == '4') ||
                            (num.charAt(i) == '5') ||
                            (num.charAt(i) == '6') ||
                            (num.charAt(i) == '7') ||
                            (num.charAt(i) == '8') ||
                            (num.charAt(i) == '9')) {
                        result = true;
                        if (num.charAt(i) == '.') contPunt++;
                    }
                }
                if (contPunt > 1) result = false;
            } else {
                result = false;
            }
        }
        return result;
    }

    public static boolean checkValidFormat(String text){
        boolean result = true;
        String subTextA = "";
        String subTextB = "";
        int contOp = 0;
        if((text == null) || (text.length()==0)){
            result = false;
        }else {
            if ((text.charAt(0) == '+') || (text.charAt(text.length() - 1) == '+') ||
                    (text.charAt(0) == '-') || (text.charAt(text.length() - 1) == '-') ||
                    (text.charAt(0) == '*') || (text.charAt(text.length() - 1) == '*') ||
                    (text.charAt(0) == '/') || (text.charAt(text.length() - 1) == '/')) {
                result = false;
            } else {
                for (int i = 0; i < text.length(); i++) {
                    if ((text.charAt(i) == '+') || (text.charAt(i) == '-') || (text.charAt(i) == '*') || (text.charAt(i) == '/')) {
                        contOp++;
                    }
                }
                if (contOp > 1) {
                    result = false;
                } else {
                    int j = 0;
                    int k = 0;
                    for (j = 0; j < text.length(); j++) {
                        if ((!(text.charAt(j) == '+')) && (!(text.charAt(j) == '-')) && (!(text.charAt(j) == '*')) && (!(text.charAt(j) == '/'))) {
                            subTextA = subTextA + text.charAt(j);
                        } else {
                            break;
                        }
                    }
                    for (k = j + 1; k < text.length(); k++) {
                        subTextB = subTextB + text.charAt(k);
                    }
                    if (!checkDouble(subTextA) || !checkDouble(subTextB)) result = false;
                }
            }
        }
        return result;
    }

    public static char getOperand(String text){
        char operand='0';
        for (int i = 0; i < text.length(); i++) {
            if((text.charAt(i) == '+') || (text.charAt(i) == '-') || (text.charAt(i) == '*') || (text.charAt(i) == '/'))operand = text.charAt(i);
        }
        return operand;
    }

    public static String getOperatorA(String text){
        String operatorA = "";
        for (int i = 0; i < text.length(); i++) {
            if(!((text.charAt(i) == '+') || (text.charAt(i) == '-') || (text.charAt(i) == '*') || (text.charAt(i) == '/'))){
                operatorA = operatorA + text.charAt(i);
            }else{
                break;
            }
        }
        return operatorA;
    }

    public static String getOperatorB(String text){
        String operatorB = "";
        for (int i = 0; i < text.length(); i++) {
            if((text.charAt(i) == '+') || (text.charAt(i) == '-') || (text.charAt(i) == '*') || (text.charAt(i) == '/')){
                for(int j = i+1;j<text.length();j++){
                    operatorB = operatorB + text.charAt(j);
                }
            }
        }
        return operatorB;
    }
}