@service_payment @bills_scanner @regression
Feature: Escanear el codigo de barra de una factura
  Casos de prueba para la pantalla que permite escanear codigos de facturas.

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa sobre la tarjeta paga tus facturas
    And Usuario esta en pantalla "pago_de_servicios"
    When Presiona sobre el boton escanear codigo de barras

  @walletint
  Scenario: Validar pop up permisos a camara y elementos de pantalla Escanear factura Permiso (C1467 y C1537)
    Then Valida que existe el pop-up para permitir o denegar los permisos de la camara
    When Permitir el acceso a la camara "si"
    And Usuario esta en pantalla "escanear_codigo_de_barras"
    Then Valida los elementos de la pantalla escanear codigo de factura

  @walletint
  Scenario: Validar redireccionamiento del back pantalla escaneo de factura (C1538)
    And Permitir el acceso a la camara "si"
    And Usuario esta en pantalla "escanear_codigo_de_barras"
    When Pulsar back en "app" en pantalla "escanear_codigo_de_barras"
    Then Usuario esta en pantalla "pago_de_servicios"
    Given Presiona sobre el boton escanear codigo de barras
    When Usuario esta en pantalla "escanear_codigo_de_barras"
    And Pulsar back en "dispositivo" en pantalla "escanear_codigo_de_barras"
    Then Usuario esta en pantalla "pago_de_servicios"

  @walletint
  Scenario: Validar redireccionamiento boton Ingresar codigo manualmente (C1521)
    And Permitir el acceso a la camara "si"
    And Usuario esta en pantalla "escanear_codigo_de_barras"
    When Presiona sobre el boton ingresar codigo manualmente en la pantalla "escaner_factura"
    Then Usuario esta en pantalla "ingresar_codigo_manual"