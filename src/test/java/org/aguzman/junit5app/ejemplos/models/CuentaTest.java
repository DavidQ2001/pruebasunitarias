package org.aguzman.junit5app.ejemplos.models;
import static org.junit.jupiter.api.Assertions.*;
import org.aguzman.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class CuentaTest {

    Cuenta cuenta;
    @BeforeEach//Este metodo se usa para la inicializacion de todos los metodos
    void initMetodoTest(){
        this.cuenta = new Cuenta("Andres", new BigDecimal("1000.2322"));
        System.out.println("iniciando el metodo");

    }

    @AfterEach//Este metodo se utiliza para la finalizacion de todos los metodos
    public void tearDown(){
        System.out.println("se ha terminado el metodo");
    }

    @BeforeAll
    //Este metodo se utiliza para la inicializacion del proyecto y se
    //ejecuta una sola vez
    static void beforeAll() {
        System.out.println("inicializando el test");
    }

    @AfterAll
    //Este metodo se utiliza para la finalizacion del proyecto y se
    //ejecuta una sola vez
    static void afterAll() {
        System.out.println("finalizando el test");
    }

    @Test
    @DisplayName("descripcion del contenido del metodo")
    void testNombreCuenta() {
        //cuenta.setPersona("Andres");
        String esperado = "Andres";
        String real = cuenta.getPersona();
        assertNotNull(real,() ->"la cuenta no puede ser nula");
        assertEquals(esperado, real,() -> "el nombre de la cuenta no es el que se esperaba");
        assertTrue(real.equals("Andres"),() -> "nombre de la cuenta esperada debe ser igual a la real o actual ");
    }

    @Test
    void testSaldoCuenta() {
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.2322, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferenciaCuenta() {
        cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

        //assertNotEquals(cuenta,cuenta2);
        assertEquals(cuenta, cuenta2);
    }

    @Test
    void testDebitoCuenta() {
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.2322", cuenta.getSaldo().toPlainString());

    }


    @Test
    void testCreditoCuenta() {
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.2322", cuenta.getSaldo().toPlainString());

    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());

    }

    @Test
    @Disabled // esta anotacion se usa para desabilitar el metodo
    void testRelacionBancoCuentas() {
        //fail(); se usa para causar un error aproposito del metodo
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertAll(() -> {
                    assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());

                }, () -> {
                    assertEquals("3000", cuenta1.getSaldo().toPlainString());

                }, () -> {
                    assertEquals(2, banco.getCuentas().size());

                }, () -> {
                    assertEquals("Banco del Estado", cuenta1.getBanco().getNombre());

                }, () -> {
                    assertEquals("Andres", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Andres"))
                            .findFirst()
                            .get().getPersona());
                }, () -> {
                    assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("Jhon Doe")));

                }

        );


    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows(){

    }

    @Test
    @EnabledOnOs({OS.LINUX,OS.MAC})
    void testSoloLinuxMac(){

    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows(){

    }

    @Test//este metodo se ejecuta
    @EnabledOnJre(JRE.JAVA_8)
    void soloJdk8(){

    }

    @Test//este metodo no se ejecuta,queda desabilitado
    @EnabledOnJre(JRE.JAVA_10)
    void soloJdk10(){

    }

    @Test
    @DisabledOnJre(JRE.JAVA_8)
    void testNoJDK8(){

    }

}