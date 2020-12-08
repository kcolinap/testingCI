@service_payment @sp_first_use @regression
Feature: Primer Uso de Pago de Servicios
  Casos de prueba para el ingreso por primera vez a la pantalla de pago de servicios.

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"


  @walletint
  Scenario: Validar elementos de tarjeta paga tus servicios en el dashboard (C1434)
   # And Pulsa el boton "principal" en el menu
   # When Pulsa el boton "pagar_factura" en el menu
    Then Valida los elementos de la tarjeta pago de servicios

  @walletint
  Scenario: Redireccion a Primer Uso, validar elementos de la pantalla y validar redireccionamiento del back (C1435, C1436 y C1449)
    And Pulsa el boton "principal" en el menu
    When Pulsa el boton "pagar_factura" en el menu
    Then Usuario esta en pantalla "pago_de_servicios"
    And Valida los elementos de la pantalla primer uso de pago de servicios
    Given Usuario esta en pantalla "pago_de_servicios"
    When Pulsar back en "app" en pantalla "pago_de_servicios"
    Then Usuario esta en pantalla "dashboard"
    Given Pulsa el boton "principal" en el menu
    And Pulsa el boton "pagar_factura" en el menu
    And Usuario esta en pantalla "pago_de_servicios"
    When Pulsar back en "dispositivo" en pantalla "pago_de_servicios"
    Then Usuario esta en pantalla "dashboard"

  @walletint
  Scenario: Validar redireccionamiento click en Escanea el codigo de barra (C1535, C1450 y C1458)
    And Pulsa el boton "principal" en el menu
    When Pulsa el boton "pagar_factura" en el menu
    And Usuario esta en pantalla "pago_de_servicios"
    And Presiona sobre el boton escanear codigo de barras
    Then Permitir el acceso a la camara "si"
    And Pulsar back en "app" en pantalla "escanear_codigo_de_barras"
    And Usuario esta en pantalla "pago_de_servicios"
    When Presiona sobre el boton escanear codigo de barras
    Then Usuario esta en pantalla "escanear_codigo_de_barras"

  @walletint
  Scenario: Validar redireccionamiento al presionar el link Ver empresas habilitadas (C1451)
    And Pulsa el boton "principal" en el menu
    When Pulsa el boton "pagar_factura" en el menu
    And Usuario esta en pantalla "pago_de_servicios"
    When Presiona sobre el link ver empresas habilitadas
    Then Usuario esta en pantalla "empresas_habilitadas"














