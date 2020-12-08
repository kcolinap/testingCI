@ignore
@e2e
Feature: Tarjeta Prepaga


  Scenario: Flujo Pedir tarjeta virtual(Usuario sin dni validado)

    Given Usuario esta en pantalla "login"
    When Usuario "PHYSICAL_PREPAID_CARD" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "tarjeta_prepaga" en el menu
    And Validar elementos de tarjeta prepaga "landing"
    And Usuario es tipo 1
