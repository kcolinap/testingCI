@registration @regression
Feature: Flujos E2E de registro

  *Registro completo
  *Pantalla de error en token vencido.


  @walletint @stage @smoke @e2e_registration @ignore
  Scenario: Creacion de usuario (C269)
    Given Resetear app "si"
    When Pulsar registrar "true"
    And Completa la pantalla de email
    And Completa pantalla de confirmacion de email
    And Completa pantalla de informacion personal
    And Completa pantalla de CUIL
    And Completa pantalla de informacion de usuario
    And Completa pantalla de informacion de telefono
    And Completa pantalla de codigo sms
    And Completa pantalla de PIN
    And Completa pantalla de terminos y condiciones
    Then Crear cuenta de usuario
    And Verificar email de confirmacion

  @walletint @stage
  Scenario: Validacion de pantalla token vencido (C22493)
    Given Resetear app "si"
    And Pulsar registrar "true"
    And Completa la pantalla de email
    And Completa pantalla de confirmacion de email
    And Elimina todos los datos de la aplicacion
    When Iniciar registro con la data anterior
    Then Validar pantalla "caducated_token"