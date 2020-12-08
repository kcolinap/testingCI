@prepaid_card @regression

Feature: Tarjeta Prepaga, flujo Merlin

  Contiene los siguientes TCs:
  C19508

  @walletint
  Scenario Outline: <Descripcion>

    Given Resetear app "si"
    And Usuario "<tipousuario>" inicia sesion
    And Pulsa el boton "tarjeta_prepaga" en el menu
    And Usuario esta en pantalla "<tipoTarjeta>"
    #And Validar que usuario tiene dni status <statusDni>
    #And Validar que usuario es mayor de edad <esAdulto>
    When Usuario completa form de datos adicionales <modoCompletitud> y modo "<tipoDatos>" desde "tarjeta_prepaga"
    Then Validar pantalla con <modoCompletitud> y datos "<tipoDatos>" desde "tarjeta_prepaga"
    Then Confirmar datos adicionales
    Then Usuario esta en pantalla "jobs_page"
    Examples:
      | tipousuario          | tipoTarjeta | statusDni | esAdulto | modoCompletitud | tipoDatos   | Descripcion                                |
      | WITHOUT_PREPAID_CARD | pc_nocard   | 1         | 1        | 1               | CORRECTOS   | Flujo merlin. Datos correctos completos    |
      | WITHOUT_PREPAID_CARD | pc_nocard   | 1         | 1        | 0               | CORRECTOS   | Flujo merlin. Datos correctos requeridos   |
      | WITHOUT_PREPAID_CARD | pc_nocard   | 1         | 1        | 1               | DESAMBIGUOS | Flujo merlin. Datos desambiguos completos  |
      | WITHOUT_PREPAID_CARD | pc_nocard   | 1         | 1        | 0               | DESAMBIGUOS | Flujo merlin. Datos desambiguos requeridos |