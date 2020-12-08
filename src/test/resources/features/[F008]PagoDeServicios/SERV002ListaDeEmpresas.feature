@service_payment @available_companies @regression
Feature: Consultar lista de empresas habilitadas para el pago de servicios
  Casos de prueba para la consulta del listado de empresas habilitadas por Rapipago para el pago de facturas.

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa sobre la tarjeta paga tus facturas
    And Usuario esta en pantalla "pago_de_servicios"
    When Presiona sobre el link ver empresas habilitadas

  @walletint
  Scenario: Validar elementos de la pantalla empresas habilitadas (C1453)
    Then Usuario esta en pantalla "empresas_habilitadas"
    And Valida los elementos de la pantalla empresas habilitadas de pago de servicios con 10 empresas

  @walletint
  Scenario: Validar redireccionamiento back desde pantalla empresas habilitadas (C1455)
    Given Usuario esta en pantalla "empresas_habilitadas"
    When Usuario pulsa boton cerrar en pantalla "empresas_habilitadas"
    And Usuario presiona background desde el dispositivo
    Then Usuario esta en pantalla "pago_de_servicios"
    Given Presiona sobre el link ver empresas habilitadas
    And Usuario esta en pantalla "empresas_habilitadas"
    When Pulsar back en "dispositivo" en pantalla "empresas_habilitadas"
    Then Usuario esta en pantalla "pago_de_servicios"
