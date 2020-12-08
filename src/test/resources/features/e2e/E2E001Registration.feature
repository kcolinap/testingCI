@ignore
@e2e
Feature: Registro



  Background:
    Given Usuario esta en pantalla "login"
    #And Home screen has been showed


  Scenario Outline: <descripcion> - <testID>

      Ejecuta el escenario de registration de cuenta completo

    Given tap registrarme
    When Completa la pantalla de email
    And Completa pantalla de confirmacion de email
    And Completa pantalla de informacion personal
    And Completa pantalla de CUIL
    And Completa pantalla de informacion de usuario
    And Completa pantalla de informacion de telefono
    And Completa pantalla de codigo sms
    And Completa pantalla de PIN
    And Completa pantalla de terminos y condiciones con "<sujetoPE>"
    Then Crear cuenta de usuario "<user>"
    #Then Save user on device
    Then Resetear app "si"
  Examples:
    |   user  | sujetoPE |        descripcion       | testID |
    |    1    |   off    | Creacion de usuario No PE|   C269 |
    |    2    |   on     | Creacion de usuario PE   |   C269 |
#    |    3    |      off        |