@profile @regression
Feature: Tcs para validar el bloqueo de clave nubi cuando se superan los intentos

  US NWA-6097

  @walletint @stage
  Scenario: Validar bloqueo de clave nubi después de 5 intentos - (C22088)

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    When Ingresa clave nubi incorrecto 5 veces
    Then Validar que la clave Nubi esta "BLOCKED"

  @walletint @stage
  Scenario: Validar contenido de mensaje de bloqueo y redireccion del mismo - (C22090 - C22089)

    When Validar redirecionamiento del mensaje de bloqueo de pin
    Then Usuario esta en pantalla "forgot_nubipass"

  @walletint @stage
  Scenario: Validar desbloqueo al realizar el flujo de olvido de clave nubi - (C22092)

    Given Usuario esta en pantalla "forgot_nubipass"
    When Completa flujo olvide pin
    Then Usuario ingresa una nueva clave Nubi "correcto"
    And Validar pantalla cuando nueva clave nubi es "correcto"
    And Usuario confirma clave Nubi "correcto"
    And Validar elementos de pantalla "nubi_pass_success"
    And Validar que la clave Nubi esta "UNBLOCKED"