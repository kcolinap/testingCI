@service_payment @payment_successful @regression
Feature: Mostrar pantalla de Saldo Insuficiente cuando el usuario quiere hacer una compra de monto abierto.

  Background:
    Given Resetear app "si"
    And Permiso para camara "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    When Pulsa el boton "pagar_factura" en el menu
    And Usuario esta en pantalla "pago_de_servicios"
    And Presiona sobre el boton escanear codigo de barras
    And Usuario esta en pantalla "escanear_codigo_de_barras"
    And Presiona sobre el boton ingresar codigo manualmente en la pantalla "escaner_factura"
    And Usuario esta en pantalla "ingresar_codigo_manual"
    When Ingresa manualmente el codigo de la factura de tipo "monto_abierto" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    Given Usuario esta en pantalla "factura_monto_abierto"
    When Presiona el boton siguiente en la pantalla monto abierto
    Then Usuario esta en pantalla "confirmacion_pago_servicio"
    And Presiona el boton pagar en la pantalla confirmacion de pago de servicio
    And Usuario esta en pantalla "pin"
    And Usuario ingresa un numero de pin "valido"

  @walletint
  Scenario: Validar elementos de la pantalla PS Confirmacion de pago y redireccionamiento del boton back del dispositivo  (C1579 y C1606)
    And Usuario esta en pantalla "rc_saldo_insuficiente"
    And Valida los elementos de pantalla de saldo insuficiente