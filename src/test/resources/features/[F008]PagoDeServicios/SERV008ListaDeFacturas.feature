@service_payment @multiple_invoices @regression
Feature: Seleccionar una factura desde un listado de facturas a pagar
  Casos de prueba para la pantalla de seleccion de facturas desde una lista.

  Background:
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
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
    When Presiona sobre el boton continuar en pantalla ingresar codigo manualmente

  @walletint
  Scenario: Validacion de elementos de la pantalla PS Multiples facturas y redireccionamiento boton back desde ingreso manual (C1484 y C1490)
    Then Usuario esta en pantalla "multifacturas"
    And Valida los elementos de la pantalla multiples facturas
    When Pulsar back en "app" en pantalla "multifacturas"
    Then Usuario esta en pantalla "ingresar_codigo_manual"

  @walletint
  Scenario: Validar redireccionamiento servicio con monto fijo (C1488)
    And Usuario esta en pantalla "multifacturas"
    And Selecciona una factura de "monto_fijo" en la pantalla de multiples facturas
    Then Usuario esta en pantalla "confirmacion_pago_servicio"

    #Actualmente no existe un codigo de factura que redirija a la pantalla monto abierto (17/06/2020)
  @walletint @ignore
  Scenario: Validar redireccionamiento servicio con monto variable (C1489)
    Given Pulsar back en "app" en pantalla "confirmacion_pago_servicio"
    And Usuario esta en pantalla "multifacturas"
    When Selecciona una factura de "monto_abierto" en la pantalla de multiples facturas
    Then Usuario esta en pantalla "factura_monto_abierto"



