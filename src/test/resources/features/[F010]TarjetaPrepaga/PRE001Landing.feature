@prepaid_card @regression
Feature: Tarjeta Prepaga, landing

  @walletint
  Scenario Outline: Landing Page - <descripcion>

    Given Resetear app "si"
    And Usuario "<tipousuario>" inicia sesion
    When Pulsa el boton "tarjeta_prepaga" en el menu
    Then Validar elementos de tarjeta prepaga "<tipoTarjeta>"
    Examples:
      | tipousuario           | tipoTarjeta | decripcion                 |
      | WITHOUT_PREPAID_CARD  | NO_PC       | Sin tarjeta prepaga(C1793) |
      | VIRTUAL_PREPAID_CARD  | VIRTUAL_PC  | Con tarjeta virtual(C1792) |
      | PHYSICAL_PREPAID_CARD | PHYSICAL_PC | Con tarjeta fisica         |