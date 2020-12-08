@service_payment @invoice_code_input @regression
Feature: Ingreso manual de codigo para pago de factura
  Casos de prueba para el ingreso manual de codigo para el pago de facturas.

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa sobre la tarjeta paga tus facturas
    And Usuario esta en pantalla "pago_de_servicios"
    And Presiona sobre el boton escanear codigo de barras
    And Permitir el acceso a la camara "si"
    And Usuario esta en pantalla "escanear_codigo_de_barras"
    When Presiona sobre el boton ingresar codigo manualmente en la pantalla "escaner_factura"

  @walletint
  Scenario: Validar elementos en pantalla Ingresar codigo manualmente CON y SIN teclado activo y redirecionamiento del back (C1466, C1457 y C1475)
    And Usuario esta en pantalla "ingresar_codigo_manual"
    Then Valida que el teclado este "activo"
    And Valida los elementos de la pantalla ingresar codigo manual "sin_codigo_ingresado"
    When Pulsar back en "app" en pantalla "ingresar_codigo_manual"
    Then Usuario esta en pantalla "escanear_codigo_de_barras"
    Given Presiona sobre el boton ingresar codigo manualmente en la pantalla "escaner_factura"
    And Usuario esta en pantalla "ingresar_codigo_manual"
    When Pulsar back en "dispositivo" en pantalla "ingresar_codigo_manual"
    Then Usuario esta en pantalla "escanear_codigo_de_barras"

  @walletint
  Scenario: Validar elementos en pantalla Ingresar codigo manualmente completo (C1468)
    And Usuario esta en pantalla "ingresar_codigo_manual"
    And Ingresa manualmente el codigo de la factura de tipo "random" a pagar
    Then Valida los elementos de la pantalla ingresar codigo manual "con_codigo_ingresado"

  @walletint
  Scenario: Validar redireccionamiento a CONFIRMACION factura con monto fijo (C1470)
    And Usuario esta en pantalla "ingresar_codigo_manual"
    When Ingresa manualmente el codigo de la factura de tipo "monto_fijo" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    Then Usuario esta en pantalla "confirmacion_pago_servicio"

    #No hay disponible actualmente un codigo valido que redirija a la pantalla monto abierto, por eso se coloca @ignore (12/06/2020)
  @walletint @ignore
  Scenario: Validar redireccionamiento a factura de monto abierto (C1471)
    And Usuario esta en pantalla "ingresar_codigo_manual"
    When Ingresa manualmente el codigo de la factura de tipo "monto_abierto" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    Then Usuario esta en pantalla "factura_monto_abierto"

  @walletint
  Scenario: Validar redireccionamiento a pantalla multiples facturas (C1472)
    And Usuario esta en pantalla "ingresar_codigo_manual"
    When Ingresa manualmente el codigo de la factura de tipo "multiples_facturas" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    Then Usuario esta en pantalla "multifacturas"

  @walletint
  Scenario: Validar redireccionamiento a Pantalla de error generico (C1503)
    And Usuario esta en pantalla "ingresar_codigo_manual"
    When Ingresa manualmente el codigo de la factura de tipo "random" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    Then Usuario esta en pantalla "ps_error_generico"



