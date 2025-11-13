package br.jw.crc.logica;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Método utilitario para fazer hash da senha
 */
public class PasswordUtil {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String hashPassword(String plainTextPassword) {
        return ENCODER.encode(plainTextPassword);
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPasswordFromDB) {
        return ENCODER.matches(plainTextPassword, hashedPasswordFromDB);
    }
}