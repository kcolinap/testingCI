@login @regression
Feature: Login. validar que se bloquee el intento de logueo al superar los intentos


  @walletint @stage
  Scenario: Validar bloqueo de login - C22054
    Given Resetear app "si"
    When Usuario intenta loguearse 5 veces
    Then Validar que el intento de login esta "BLOCKED"


  @walletint @stage
  Scenario: Validar contenido de mensaje de bloqueo y redireccion del mismo - (C22055 - C22056)

    Given Usuario esta en pantalla "login"
    Then Validar redirecionamiento del mensaje de bloqueo de login
    And Usuario esta en pantalla "cambiopass_landing"


  @walletint @stage
  Scenario: Validar desbloqueo al cambiar contraseña - (C22059)
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    And Usuario intenta loguearse 5 veces
    And Validar que el intento de login esta "BLOCKED"
    And Validar redirecionamiento del mensaje de bloqueo de login
    When Usuario realiza proceso de recupero de contrasenia
    Then Validar que el intento de login esta "UNBLOCKED"
    And Usuario puede loguearse
