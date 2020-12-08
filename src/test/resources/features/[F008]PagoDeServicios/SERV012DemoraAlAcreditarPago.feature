@service_payment @delayed_payment @regression
Feature: Mostrar mensajes de demora en el pago
  Casos de prueba de la pantalla demora de pago.

  #Sin el mock, no hay factura que permita llegar a la pantalla de pago demorado (24/06/2020)
  @walletint @ignore
  Scenario: Validar elementos de la pantalla demora en acreditar pago (C1633, C1620, C1621 y C1622)

    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa sobre la tarjeta paga tus facturas
    And Usuario esta en pantalla "pago_de_servicios"
    And Presiona sobre el boton escanear codigo de barras
    And Permitir el acceso a la camara "si"
    And Usuario esta en pantalla "escanear_codigo_de_barras"
    And Presiona sobre el boton ingresar codigo manualmente en la pantalla "escaner_factura"
    And Usuario esta en pantalla "ingresar_codigo_manual"
    And Ingresa manualmente el codigo de la factura de tipo "multiples_facturas" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    And Selecciona una factura de "monto_fijo" en la pantalla de multiples facturas
    And Usuario esta en pantalla "confirmacion_pago_servicio"
    And Presiona el boton pagar en la pantalla confirmacion de pago de servicio
    And se cambia la conexion movil a "gsm"
    And Usuario esta en pantalla "pin"
    When Usuario ingresa un numero de pin "valido"
    Then Usuario esta en pantalla "demora_de_pago_servicio"
    And Valida los elementos de la pantalla demora de pago hasta "5_segundos"
    And Valida los elementos de la pantalla demora de pago hasta "10_segundos"
    And Valida los elementos de la pantalla demora de pago hasta "15_segundos"
    And Valida los elementos de la pantalla demora de pago hasta "20_segundos"
    And se cambia la conexion movil a "full"







