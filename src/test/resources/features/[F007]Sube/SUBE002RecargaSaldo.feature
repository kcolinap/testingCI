@sube @regression
Feature: Recarga de Saldo de Tarjeta SUBE
  Casos de prueba para la seleccion de un monto de recarga de la tarjeta SUBE.

  @walletint @stage
  Scenario: Validar pantalla de recarga de saldo SUBE (C982)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1723
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    When Pulsa en el boton cargar sube primer uso
    When Ingresa un numero de tarjeta sube "completo" y "valido"
    And Pulsa en el boton continuar
    #Then Usuario esta en pantalla "registro_alias_sube"
    And Ingresa un alias "juanperez" sube "valido"
    And Pulsa en el boton continuar
   # Then Usuario esta en pantalla "recarga_de_saldo"
    Then Se muestran los elementos de recarga de saldo