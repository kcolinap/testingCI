@service_payment @regression
Feature: Mostrar pantalla transaccion fallida cuando se produce un error en el pago de un factura
  Casos de prueba de la pantalla transaccion fallida de pago de servicios

  Background:
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
    And Ingresa manualmente el codigo de la factura de tipo "monto_fijo" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    And Usuario esta en pantalla "confirmacion_pago_servicio"
    And Presiona el boton pagar en la pantalla confirmacion de pago de servicio
    And Usuario esta en pantalla "pin"
    And Usuario ingresa un numero de pin "valido"
    And Usuario esta en pantalla "ps_transaccion_fallida"

  @walletint
  Scenario: Validar pantalla de transaccion fallida y sus elementos (C1607 y C1612)
    And Valida los elementos de la pantalla transaccion fallida

  @walletint
  Scenario: Validar redireccion de botón back en pantalla transaccion fallida (C1614)
    Then Usuario esta en pantalla "ps_transaccion_fallida"
    And Pulsar back en "app" en pantalla "ps_transaccion_fallida"
    And Usuario esta en pantalla "escanear_codigo_de_barras"
    And Presiona sobre el boton ingresar codigo manualmente en la pantalla "escaner_factura"
    And Usuario esta en pantalla "ingresar_codigo_manual"
    And Ingresa manualmente el codigo de la factura de tipo "monto_fijo" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    And Usuario esta en pantalla "confirmacion_pago_servicio"
    And Presiona el boton pagar en la pantalla confirmacion de pago de servicio
    And Usuario esta en pantalla "pin"
    And Usuario ingresa un numero de pin "valido"
    And Usuario esta en pantalla "ps_transaccion_fallida"
    And Pulsar back en "dispositivo" en pantalla "ps_transaccion_fallida"
    And Usuario esta en pantalla "escanear_codigo_de_barras"

  @walletint
  Scenario: Validar redireccion de boton "Volver al inicio" en pantalla transaccion fallida (C1613)
    Then Usuario esta en pantalla "ps_transaccion_fallida"
    And Pulsar back en "app" en pantalla "ps_transaccion_fallida"    And Usuario esta en pantalla "escanear_codigo_de_barras"
    And Presiona sobre el boton ingresar codigo manualmente en la pantalla "escaner_factura"
    And Usuario esta en pantalla "ingresar_codigo_manual"
    And Ingresa manualmente el codigo de la factura de tipo "monto_fijo" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    And Usuario esta en pantalla "confirmacion_pago_servicio"
    And Presiona el boton pagar en la pantalla confirmacion de pago de servicio
    And Usuario esta en pantalla "pin"
    And Usuario ingresa un numero de pin "valido"
    And Usuario esta en pantalla "ps_transaccion_fallida"
    And Presiona sobre el boton volver al inicio en la pantalla transaccion fallida
    And Usuario esta en pantalla "dashboard"