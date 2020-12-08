@login @regression
Feature: Login

  Contempla los TCs que se encuentran en la seccion Login --> Splash

  @walletint @stage
  Scenario: Splash - (C271)
    When Resetear app "si"
    And Resetear app "si"
    Then Usuario esta en pantalla "splash"
#
#
  @walletint @stage
  Scenario: Retornar a la app - (C275/C276)
    Given Usuario "any" inicia sesion
    When Usuario esta en pantalla "dashboard"
    And Usuario cierra la app
    When Inicia la app
    #Then Usuario esta en pantalla "login" aahora se mantiene la sesion al matar la app
    Then Usuario esta en pantalla "dashboard"
#
#
  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)
    Given Resetear app "si"
    When Validar texto de link "<link>"
    Then Validar pantalla al pulsar link "<link>"
    Examples:
      | descripcion                     | testID | link            |
      | Validar link registrarme        | C335   | crear_cuenta    |
      | Validar link olvide mi password | c      | forgot_password |
