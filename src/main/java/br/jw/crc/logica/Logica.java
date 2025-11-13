package br.jw.crc.logica;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * Interface para fazer o contrato com as outras classes
 */
public interface Logica {
	void executa(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
