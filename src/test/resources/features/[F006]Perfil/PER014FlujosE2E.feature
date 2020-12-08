@profile @regression
Feature: Flujos E2E de perfil

  Contiene los flujos para:
  - Cambio de clave nubi
  - Olvide de clave nubi
  - Cambio de password
  - Bloqueo de pin
  - Cerrar session
  - Validacion de datos

  @walletint @stage @smoke
  Scenario: Olvido de clave Nubi

    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario esta en pantalla "perfil"
    And Usuario presiona el link "security"
    When Usuario presiona el link "forgot_nubipass"
    And Usuario presiona boton "enviar_sms"
    And Usuario ingresa el codigo sms "correcto"
    And Usuario ingresa nueva clave nubi para flujo "FORGOT_NUBI_PASS"
    And Usuario confirma nueva clave Nubi para flujo "FORGOT_NUBI_PASS"
    Then Validar elementos de pantalla "nubi_pass_success"
    And Usuario esta en pantalla "security"


  @walletint @stage @smoke
  Scenario: Cambiar clave nubi

    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    And Usuario ingresa clave nubi para flujo "CHANGE_CURRENT_NUBIPASS"
    And Usuario ingresa nueva clave nubi para flujo "CHANGE_NUBIPASS"
    And Usuario confirma nueva clave Nubi para flujo "CHANGE_NUBI_PASS"
    Then Validar elementos de pantalla "nubi_pass_success"
    And Usuario esta en pantalla "security"


  @walletint @stage @smoke
  Scenario: Cambio de password

    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "security"
    When Usuario presiona el link "cambio_password"
    And Usuario ingresa un password "CORRECTO"
    And Validar cuando el password es "CORRECTO"
    And Usuario ingresa un nuevo password "CORRECTO"
    Then Validar cuando el cambio de password es "EXITOSO"
    And Usuario esta en pantalla "security"


  @walletint @stage @smoke
  Scenario: Flujo bloqueo de clave Nubi

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    When Ingresa clave nubi incorrecto 5 veces
    Then Validar que la clave Nubi esta "BLOCKED"
    And Realizar flujo de olvido de clave Nubi
    And Validar que la clave Nubi esta "UNBLOCKED"

  @walletint @stage @smoke
  Scenario: Cerrar sesion

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "close_session"
    When Usuario presiona boton "close_session"
    Then Usuario esta en pantalla "login"


  @walletint @stage @smoke
  Scenario: Validacion de datos personales

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    When Usuario presiona el link "personal_data"
    Then Validar elementos de pantalla "personal_data"

  @walletint @stage @smoke
  Scenario: Validacion de Preguntas frecuentes

    Given Resetear app "si"
    And Find user type "any" and set preReq ""
    And Iniciar sesion
    When Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "faq"
    Then Validar elementos de pantalla "faq"