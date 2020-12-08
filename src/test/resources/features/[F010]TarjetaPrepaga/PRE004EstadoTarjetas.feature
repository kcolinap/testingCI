@prepaid_card @regression
Feature: Tarjeta Prepaga, estado de tarjetas


  @walletint
  Scenario Outline: <description> - <testId>

    Given Resetear app "si"
    And Usuario "<tipousuario>" inicia sesion
    And Actualizar estado de tarjeta <status>
    And Pulsa el boton "tarjeta_prepaga" en el menu
    And Usuario esta en pantalla "<tipoTarjeta>"
    When Pulsar boton para "<action>" la tarjeta "<tipoTarjeta>"
    Then Validar pantalla al "<action>" la tarjeta "<tipoTarjeta>"
    Then Rollback status card
    Examples:
      |      tipousuario      |  tipoTarjeta |   action   | status |          description        | testId |
      |  VIRTUAL_PREPAID_CARD |  pc_virtual  |  TO_BLOCK  |   0    |   Congelar tarjeta virtual  | C21791 |
      |  VIRTUAL_PREPAID_CARD |  pc_virtual  | TO_UNBLOCK |   1    | Descongelar tarjeta virtual | C21829 |
      | PHYSICAL_PREPAID_CARD |  pc_physical |  TO_BLOCK  |   0    |   Congelar tarjeta fisica   | C21813 |
      | PHYSICAL_PREPAID_CARD |  pc_physical | TO_UNBLOCK |   1    | Descongelar tarjeta fisica  | C21828 |