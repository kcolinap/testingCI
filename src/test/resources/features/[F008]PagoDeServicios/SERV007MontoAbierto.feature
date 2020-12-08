@service_payment @regression
Feature: Ingresar un monto para abonar a la factura
  Casos de prueba para el ingreso de montos abiertos en pago de servicios.

  @walletint
  Scenario: Validar elementos pantalla servicio con monto abierto (C1543)
    Given Resetear app "si"
    #And Permiso para camara "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "pagar_factura" en el menu
    And Usuario esta en pantalla "pago_de_servicios"
    And Presiona sobre el boton escanear codigo de barras
    #And Usuario presiona background desde el dispositivo
    And Usuario esta en pantalla "escanear_codigo_de_barras"
    And Presiona sobre el boton ingresar codigo manualmente en la pantalla "escaner_factura"
    #And Usuario esta en pantalla "ingresar_codigo_manual"
    When Ingresa manualmente el codigo de la factura de tipo "monto_abierto" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    #Then Usuario esta en pantalla "factura_monto_abierto"
    Then Valida los elementos de la pantalla factura con monto abierto

  @walletint
  Scenario Outline: Validar mensaje de error al ingresar montos no permitidos y redireccionamiento del back (C1565, C1566, C1604, C1568)
    Given Usuario esta en pantalla "factura_monto_abierto"
    When Ingresa un monto "<tipoDeMonto>" al permitido
    Then Se muestra advertencia de monto "<tipoDeMonto>" y se deshabilita el boton siguiente
    And Pulsar back en "<backType>" en pantalla "factura_monto_abierto"
    And Usuario esta en pantalla "ingresar_codigo_manual"
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    Examples:
      | tipoDeMonto | backType |
      | mayor       | app      |
      | menor       | device   |
      | menor(cero) | app      |

  @walletint
  Scenario: Validar redireccionamiento boton Siguiente (C1567)
    Given Usuario esta en pantalla "factura_monto_abierto"
    When Presiona el boton siguiente en la pantalla monto abierto
    Then Usuario esta en pantalla "confirmacion_pago_servicio"