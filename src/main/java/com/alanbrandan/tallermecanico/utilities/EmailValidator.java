package com.alanbrandan.tallermecanico.utilities;
import java.util.regex.Pattern;

public class EmailValidator {

    public static boolean CorreoValido(String correo){
        String Regex =  "^(.+)@(.+)$";
        return Pattern.compile(Regex).matcher(correo).matches();
    }
}
