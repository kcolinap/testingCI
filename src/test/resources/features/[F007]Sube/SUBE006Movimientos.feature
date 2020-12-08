@ignore @sube @regression
Feature: Visualizar los movimientos registrados por recargas de tarjeta SUBE
  Casos de prueba para visualizar los ultimos movimientos por recargas SUBE

  @walletint @stage
  Scenario: Ultimos Movimientos - Ver movimientos de SUBE -desde DASHBOARD (C1208)
  Jira Ticket: https://tunubi.atlassian.net/browse/NWA-1737
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    When Usuario esta en pantalla "cargar_sube"
    Then Pulsa en el boton cargar sube primer uso
    And Usuario esta en pantalla "registro_sube"
    And Ingresa un numero de tarjeta sube "completo" y "valido"
    And Pulsa en el boton continuar
    Then Usuario esta en pantalla "registro_alias_sube"
    And Ingresa un alias "juanperez" sube "valido"
    And Pulsa en el boton continuar
    And Usuario esta en pantalla "recarga_de_saldo"
    And Usuario selecciona para recargar el monto: "$60"
    And Usuario esta en pantalla "revisar_datos_de_recarga"
    And Presiona sobre el boton continuar en pantalla de confirmacion SUBE
    And Usuario esta en pantalla "pin"
    And Usuario ingresa un numero de pin "valido"
    And Usuario esta en pantalla "recarga_exitosa_SUBE"
    And presiona sobre el boton entendido
    When Usuario esta en pantalla "dashboard"
    Then Se valida que exista el ultimo movimiento de tipo "sube" y estado "" que acabamos de realizar
    Given Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    Then Usuario esta en pantalla "lista_de_tarjetas_sube"
    Then Usuario elimina todas las tarjeta del listado
    When Usuario esta en pantalla "cargar_sube"
    Then Pulsar back en "app" en pantalla "cargar_sube"


  @walletint @stage
  Scenario: Ultimos Movimientos - Ver movimientos de SUBE -desde listado de ultimos movimientos (C1237)
  Jira Ticket: https://tunubi.atlassian.net/browse/NWA-1737
    Given Usuario esta en pantalla "dashboard"
    When Presiona sobre ultimos movimientos
    And Usuario esta en pantalla "ultimos_movimientos"
    Then Verifica si el movimiento "recarga_sube" con estado "" se encuentra en la lista

  @walletint @stage
  Scenario: Ultimos Movimientos - validar elementos de la pantalla DETALLE de un movimiento de SUBE
  Jira Ticket: https://tunubi.atlassian.net/browse/NWA-1738
    Given Pulsar back en "app" en pantalla "ultimos_movimientos"
    And Usuario esta en pantalla "dashboard"
    When Se pulsa sobre el ultimo movimiento de tipo "sube" que acabamos de realizar con status ""
    Then Se valida los elementos de la pantalla detalle de movimiento "sube", con impuesto "", con monto "60" y estado ""