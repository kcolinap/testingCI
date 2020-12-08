@profile @regression
Feature: Contrasenia actual

  Contienes los TC incluidos en la seccion:

          Perfil -> ... -> Contrasenia actual

############################################################


  @walletint @stage
  Scenario: Validar la pantalla cambio de contrasenia(C623)
    Contempla el tc:
      C623 https://tunubi.atlassian.net/browse/NWA-590

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "security"
    And Usuario presiona el link "cambio_password"
    Then Validar elementos de pantalla "cambio_password"
    #And Ir al dashboard

  ##############################################
  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)
  Contempla los tc:
  C749 https://tunubi.atlassian.net/browse/NWA-590
  C746 https://tunubi.atlassian.net/browse/NWA-590

    Given Usuario ingresa un password "<password>"
    Then Validar cuando el password es "<password>"
    And Pulsar back en "app" en pantalla "change_password"
    And Usuario presiona el link "cambio_password"
    Examples:
    |     password    |      descripcion     | testID |
    |   CORRECTO      | Contrasenia correcta |  C749  |
    |   INCORRECTO    |Contrasenia incorrecta|  C746  |


  @walletint @stage
  Scenario: C750  Cambiar Contraseña - Salir de la pantalla
    Contempla el tc C750

    Given Usuario esta en pantalla "CHANGE_PASSWORD"
    Then Pulsar back en "app" en pantalla "CHANGE_PASSWORD"
    And Usuario esta en pantalla "security"


  @walletint @stage
  Scenario: C751 Cambiar Contraseña -  Back desde el device
  Contempla el tc C751

    Given Usuario presiona el link "cambio_password"
    Then Pulsar back en "dispositivo" en pantalla "CHANGE_PASSWORD"
    And Usuario esta en pantalla "security"


  @walletint @stage
  Scenario: C754 Matar la app desde ¨Cambiar Contraseña¨
  Contempla el tc C755

    Given Usuario presiona el link "cambio_password"
    When Usuario cierra la app
    And Inicia la app
    And Usuario esta en pantalla "login"