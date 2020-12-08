@sube @regression
Feature: Recarga de tarjeta SUBE


  @walletint @stage @smoke
  Scenario Outline: Recarga de tarjeta SUBE

    Given Resetear app "si"
    #And Usuario "SIN_SUBE" inicia sesion
    And Find user type "SIN_SUBE" and set preReq ""
    And Iniciar sesion
    And Pulsa el boton "principal" en el menu
    When Pulsa el boton "recarga_sube" en el menu
    And Pulsa en el boton cargar sube primer uso
    And Ingresa un numero de tarjeta sube "completo" y "valido"
    And Pulsa en el boton continuar
    And Ingresa un alias "juanperez" sube "valido"
    And Pulsa en el boton continuar
    And Usuario selecciona para recargar el monto: "<monto>"
    And Presiona sobre el boton continuar en pantalla de confirmacion SUBE
    And Usuario ingresa clave nubi para flujo "SUBE_RECHARGE"
    And presiona sobre el boton entendido
    Then Validar movimiento "SUBE_RECHARGE", "" en "detalle_movimiento" y "" con status ""
    Examples:
    |monto|
    |$60  |



  @walletint @stage @smoke
  Scenario Outline: Editar Alias y Eliminar tarjeta SUBE

    Given Resetear app "si"
    And Find user type "CON_SUBE" and set preReq ""
    And Iniciar sesion
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    And Usuario esta en pantalla "list_sube_card"
    And Pulsar action button para "EDIT_ALIAS"
    When Editar alias de tarjeta sube por "<alias_text>"
    Then Validar cambio en alias
    And Pulsar action button para "DELETE_CARD"
    And Usuario esta en pantalla "cargar_sube"
    Examples:
      |monto| alias_text |
      |$60  | perezjuan |