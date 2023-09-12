package org.aguzman.junit5app.ejemplos.models;
import static org.junit.jupiter.api.Assertions.*;

import org.aguzman.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
class CuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Andres",new BigDecimal("1000.2322"));
        //cuenta.setPersona("Andres");
        String esperado = "Andres";
        String real = cuenta.getPersona();
        assertNotNull(real);
        assertEquals(esperado,real);
        assertTrue(real.equals("Andres"));
    }

    @Test
    void testSaldoCuenta(){
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.2322"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.2322, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new  Cuenta("John Doe",new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new  Cuenta("John Doe",new BigDecimal("8900.9997"));

        //assertNotEquals(cuenta,cuenta2);
        assertEquals(cuenta,cuenta2);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.2322"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900,cuenta.getSaldo().intValue());
        assertEquals("900.2322", cuenta.getSaldo().toPlainString());

    }


    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.2322"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100,cuenta.getSaldo().intValue());
        assertEquals("1100.2322", cuenta.getSaldo().toPlainString());

    }

    @Test
    void testDineroInsuficienteExceptionCuenta(){
        Cuenta cuenta = new Cuenta("Andres",new BigDecimal("1000.2322"));
        Exception exception = assertThrows(DineroInsuficienteException.class,() -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado,actual);
    }
}