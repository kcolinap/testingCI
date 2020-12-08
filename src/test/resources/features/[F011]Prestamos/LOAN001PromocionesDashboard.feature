@loan @dashboard_promo @regression
Feature: Mostrar tarjeta de promoción en el dashboard

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    When Usuario "con_prestamo" inicia sesion
    And Usuario esta en pantalla "dashboard"

  @walletint
  Scenario: Visualizacion de Promocion en el Dashboard - Formato, Boton Minimizar y Boton Alternar (C20703, C20705, C20706)
    Then Se visualiza la tarjeta de promocion de prestamos
    When Usuario minimiza la app 2 segundos y vuelve abrirla
    Then Se visualiza la tarjeta de promocion de prestamos
    When Usuario presiona background desde el dispositivo
    Then Se visualiza la tarjeta de promocion de prestamos

  @walletint
  Scenario: Visualizacion de Promocion en el Dashboard - Boton Pedi el tuyo (C20704)
    And Se visualiza la tarjeta de promocion de prestamos
    When Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones
    Then Usuario esta en pantalla "lista_de_articulos"


