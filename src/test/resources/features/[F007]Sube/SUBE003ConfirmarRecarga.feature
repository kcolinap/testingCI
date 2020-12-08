@sube @regression
Feature: Recarga de Saldo de Tarjeta SUBE
  Casos de prueba para la seleccion de un monto de recarga de la tarjeta SUBE.

  @walletint @stage
  Scenario: Selecionar un monto y llegar a la pantalla de confirmacion de recarga (C994)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1733
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    #When Usuario esta en pantalla "cargar_sube"
    When Pulsa en el boton cargar sube primer uso
    #And Usuario esta en pantalla "registro_sube"
    And Ingresa un numero de tarjeta sube "completo" y "valido"
    And Pulsa en el boton continuar
    #Then Usuario esta en pantalla "registro_alias_sube"
    Then Ingresa un alias "juanperez" sube "valido"
    And Pulsa en el boton continuar
    #And Usuario esta en pantalla "recarga_de_saldo"
    When Usuario selecciona para recargar el monto: "$60"
    #Then Usuario esta en pantalla "revisar_datos_de_recarga"
    Then Se muestran los elementos de revision de recarga

  @walletint @stage
  Scenario: Volver a la pantalla de seleccion de montos usando el boton back de la aplicacion (C996)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1733
    Given Usuario esta en pantalla "revisar_datos_de_recarga"
    When Pulsar back en "app" en pantalla "revisar_datos_de_recarga"
    Then Usuario esta en pantalla "recarga de saldo"

  @walletint @stage
  Scenario: Volver a la pantalla de seleccion de montos usando el boton back nativo (C997)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1733
    Given Usuario selecciona para recargar el monto: "$60"
    And Usuario esta en pantalla "revisar_datos_de_recarga"
    When Pulsar back en "dispositivo" en pantalla "revisar_datos_de_recarga"
    Then Usuario esta en pantalla "recarga_de_saldo"

  @walletint @stage
  Scenario: Confirmar la recarga de la tarjeta SUBE (C995)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1733
    Given Usuario selecciona para recargar el monto: "$60"
    And Usuario esta en pantalla "revisar_datos_de_recarga"
    When Presiona sobre el boton continuar en pantalla de confirmacion SUBE
    Then Usuario esta en pantalla "sube_nubi_password"