@sube @regression
Feature: Ingreso de Clave nubi para confirmar la recarga de tarjeta SUBE
  Casos de prueba para el ingreso de la clave nubi y asi confirmar la recarga de una tarjeta SUBE.

  @walletint @stage
  Scenario: Validar pantalla de "Ingresar Clave Nubi" (C1149)
  Jira Ticket: https://tunubi.atlassian.net/browse/NWA-1734
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    And Pulsa en el boton cargar sube primer uso
    And Ingresa un numero de tarjeta sube "completo" y "valido"
    And Pulsa en el boton continuar
    And Ingresa un alias "juanperez" sube "valido"
    And Pulsa en el boton continuar
    And Usuario esta en pantalla "recarga_de_saldo"
    And Usuario selecciona para recargar el monto: "$60"
    When Presiona sobre el boton continuar en pantalla de confirmacion SUBE
    Then Usuario esta en pantalla "sube_nubi_password"
#    And Se validan los elementos de la pantalla pin despues de "cargar la pantalla"

  @walletint @stage
  Scenario: Ingresar clave nubi incorrecta (C1150)
  Jira Ticket: https://tunubi.atlassian.net/browse/NWA-1734
    Given Usuario esta en pantalla "sube_nubi_password"
    When Usuario ingresa clave nubi "INCORRECTO" en recarga SUBE
    Then Validar pantalla al ingresar Clave Nubi "INCORRECTO" en recarga SUBE




  @ignore @walletint @stage
  Scenario: Ingresar PIN - Cerrar pantalla con X (C1151)
  Jira Ticket: https://tunubi.atlassian.net/browse/NWA-1734
    Given Usuario esta en pantalla "pin"
    When Usuario pulsa boton cerrar en pantalla "ingresar_pin"
    Then Usuario esta en pantalla "revisar_datos_de_recarga"