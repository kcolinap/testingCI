@ignore @sube @regression
Feature: Elimar tarjeta sube

  Casos de prueba para eliminar tarjeta sube

  @walletint @stage
  Scenario: Eliminar una tarjeta

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    Given Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Validar que tiene tarjeta sube agregada
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    Then Usuario esta en pantalla "lista_de_tarjetas_sube"
    Then Usuario elimina todas las tarjeta del listado
    When Usuario esta en pantalla "cargar_sube"
    Then Pulsar back en "app" en pantalla "cargar_sube"