@service_payment @permissions_denied @regression
Feature: Permisos de camara denegados
  Casos de prueba para la pantalla de permisos de camara denegados.

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa sobre la tarjeta paga tus facturas
    And Usuario esta en pantalla "pago_de_servicios"
    And Presiona sobre el boton escanear codigo de barras
    And Valida que existe el pop-up para permitir o denegar los permisos de la camara
    When Permitir el acceso a la camara "no"
    And Usuario esta en pantalla "permisos_camara_denegados"

  @walletint
  Scenario: Validar elementos y redireccionamiento del back de la pantalla Permisos Denegados (C1629)
    Then Valida los elementos de la pantalla permisos de camara denegados
    When Pulsar back en "app" en pantalla "permisos_camara_denegados"
    Then Usuario esta en pantalla "pago_de_servicios"
    Given Presiona sobre el boton escanear codigo de barras
    And Permitir el acceso a la camara "no"
    When Usuario esta en pantalla "permisos_camara_denegados"
    And Pulsar back en "dispositivo" en pantalla "permisos_camara_denegados"
    Then Usuario esta en pantalla "pago_de_servicios"

  @walletint
  Scenario: Validar redireccionamiento boton Ingresar codigo manualmente en pantalla Permisos Denegados (C1631)
    And Presiona sobre el boton ingresar codigo manualmente en la pantalla "permisos_denegados"
    Then Usuario esta en pantalla "ingresar_codigo_manual"

  @walletint
  Scenario: Validar redireccionamiento boton Activar permisos en pantalla Permisos Denegados (C1630)
    And Presiona sobre el boton activar permisos de acceso a la camara
    Then Valida que existe el pop-up para permitir o denegar los permisos de la camara

