@profile @regression
Feature: Clave nubi actual

  Contienes los TC incluidos en la seccion:

          Perfil -> ... -> Pin actual


############################################################

  @walletint @stage
  Scenario: Validar la pantalla ¨Cambio de Clave Nubi¨(C784)
    Contempla el tc:
  C784 https://tunubi.atlassian.net/browse/NWA-583

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    When Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    Then Validar elementos de pantalla "current_nubi_password"
#
#
  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)
  Contempla los tc:
  C785 https://tunubi.atlassian.net/browse/NWA-583
  C786 https://tunubi.atlassian.net/browse/NWA-583
  C792 https://tunubi.atlassian.net/browse/NWA-583

    Given Usuario esta en pantalla "current_nubi_password"
    When Ingresa clave nubi "<type>"
    Then Validar pantalla cuando clave nubi es "<type>"
    Then Ir al dashboard
    And Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    
    Examples:
    |     type    |     descripcion       | testID |
    |  correcto  |  Clave nubi correcto |  C786  |
    | incorrecto |Clave nubi incorrecto | C785   |
    | incompleto |Clave nubi incompleto |  C792  |
#
#
  @walletint @stage
  Scenario: Salir de la pantalla(C788)
    Contempla el tc
    C788 https://tunubi.atlassian.net/browse/NWA-583

    Given Usuario esta en pantalla "current_nubi_password"
    When Usuario pulsa boton cerrar en pantalla "change_nubipass"
    Then Usuario esta en pantalla "security"
#
#
  @walletint @stage
  Scenario: Pin actual -  Back desde el device(C789)
  Contempla el tc
  C789 https://tunubi.atlassian.net/browse/NWA-583

    Given Ir al dashboard
    Given Pulsa el boton "perfil" en el menu
    And Usuario esta en pantalla "perfil"
    When Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    And Pulsar back en "dispositivo" en pantalla "change_nubipass"
    Then Usuario esta en pantalla "security"
#
#
  @walletint @stage
  Scenario: Matar la app desde ¨Cambiar Pin¨(C791)
  Contempla el tc
  C791 https://tunubi.atlassian.net/browse/NWA-583

    Given Ir al dashboard
    Given Pulsa el boton "perfil" en el menu
    And Usuario esta en pantalla "perfil"
    And Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    When Usuario cierra la app
    And Inicia la app
    Then Usuario esta en pantalla "login"