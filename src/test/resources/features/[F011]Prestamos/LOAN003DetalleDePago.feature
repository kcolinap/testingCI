@loan @downpayment_details @regression
Feature: Detalle del Plan de Pago de un Prestamo.

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "con_prestamo" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Se visualiza la tarjeta de promocion de prestamos
    And Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones
    And Usuario esta en pantalla "lista_de_articulos"
    And Se valida los elementos de la pantalla lista de productos en promocion
    And Usuario presiona el boton comprar en el producto 0
    When Usuario esta en pantalla "datos_del_prestamo"

  @walletint
  Scenario: Detalle de Pago - Formato, Fecha de Primer Cuota, Tasas de Campania  (C20713, C20820, C20821, C21995)
    Then Se validan los elementos de la pantalla detalle de plan de pago
    And Se corrobora la fecha de la primera cuota

  @walletint
  Scenario: Detalle de Pago - Boton Volver de la App y del Dispositivo, Minimizar y Alternar la App (C20715, C20716, C20717, C20718)
    And Pulsar back en "app" en pantalla "datos_del_prestamo"
    Then Usuario esta en pantalla "lista_de_articulos"
    When Usuario presiona el boton comprar en el producto 0
    And Usuario esta en pantalla "datos_del_prestamo"
    And Pulsar back en "dispositivo" en pantalla "datos_del_prestamo"
    Then Usuario esta en pantalla "lista_de_articulos"
    When Usuario presiona el boton comprar en el producto 0
    And Usuario esta en pantalla "datos_del_prestamo"
    And Usuario minimiza la app 2 segundos y vuelve abrirla
    Then Usuario esta en pantalla "datos_del_prestamo"
    When Usuario presiona background desde el dispositivo
    Then Usuario esta en pantalla "datos_del_prestamo"

  @walletint
  Scenario: Detalle de Pago - Boton Continuar (C20714)
    And Presiona el boton continuar en pantalla downpayment
    Then Usuario esta en pantalla "direccion_de_envio"