@loan @products_list @regression
Feature: Mostrar lista de productos disponibles para prestamo.

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "con_prestamo" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Se visualiza la tarjeta de promocion de prestamos

  @walletint
  Scenario: Listado de Dispositivos - Mostrar capacidad y color del dispositivo (C20707, C21146)
    When Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones
    And Usuario esta en pantalla "lista_de_articulos"
    Then Se valida los elementos de la pantalla lista de productos en promocion

  @walletint
  Scenario: Listado de Dispositivos - Boton Volver de pantalla y dispositivo - Boton minimizar - Boton Alternar (C21148, C21149, C21150, C21151)
    And Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones
    And Usuario esta en pantalla "lista_de_articulos"
    When Pulsar back en "app" en pantalla "lista_de_articulos"
    Then Usuario esta en pantalla "dashboard"
    Given Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones
    And Usuario esta en pantalla "lista_de_articulos"
    When Pulsar back en "dispositivo" en pantalla "lista_de_articulos"
    Then Usuario esta en pantalla "dashboard"
    Given Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones
    And Usuario esta en pantalla "lista_de_articulos"
    When Usuario presiona background desde el dispositivo
    Then Usuario esta en pantalla "lista_de_articulos"
    When Usuario minimiza la app 2 segundos y vuelve abrirla
    Then Usuario esta en pantalla "lista_de_articulos"

  @walletint
  Scenario: Listado de Dispositivos - Alternar color del dispositivo (C21147)
    And Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones
    And Usuario esta en pantalla "lista_de_articulos"
    And Se valida los elementos de la pantalla lista de productos en promocion
    When Cambiar el color del articulo 0
    Then Se valida que haya quedado seleccionado el radiobutton del color y que el nombre del color haya cambiado

  @walletint
  Scenario: Listado de Dispositivos - Boton Compralo Ahora (C20708)
    And Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones
    And Usuario esta en pantalla "lista_de_articulos"
    And Se valida los elementos de la pantalla lista de productos en promocion
    When Usuario presiona el boton comprar en el producto 0
    Then Usuario esta en pantalla "datos_del_prestamo"

