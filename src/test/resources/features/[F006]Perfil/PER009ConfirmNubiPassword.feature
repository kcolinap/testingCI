@profile @regression
Feature: Confirmar Clave nubi

  Contiene los TC incluidos en la seccion:

          Perfil -> ... -> Confirmar PIN


  @walletint @stage
  Scenario:  Confirmar Clave nubi - Validacion de pantalla(C825)
    Contempla el tc:
  C825 https://tunubi.atlassian.net/browse/NWA-2264

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario esta en pantalla "perfil"
    And Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    And Ingresa clave nubi "correcto"
    When Usuario ingresa una nueva clave Nubi "correcto"
    Then Validar elementos de pantalla "confirm_nubi_password"
#
#
  @walletint @stage
  Scenario: Corfirmar clave Nubi - incompleto(C826)
  Contempla los tc:
  C826 https://tunubi.atlassian.net/browse/NWA-2264

    Given Usuario esta en pantalla "confirm_nubi_password"
    When Usuario confirma clave Nubi "incompleto"
    Then Validar pantalla cuando confirmacion de nueva clave nubi es "incompleto"

#
#
  @walletint @stage
  Scenario: Corfirmar PIN - No ingresar el PIN actual (C827)
  Contempla los tc:
  C827 https://tunubi.atlassian.net/browse/NWA-2264

    Given Usuario esta en pantalla "confirm_nubi_password"
    When Usuario confirma clave Nubi "incompleto"
    Then Validar pantalla cuando confirmacion de nueva clave nubi es "incorrecto"

##
##
#  @regression
#  Scenario: Confirmar PIN - Back desde la pantalla ¨PIN nuevo¨(C828)
#  Contempla los tc:
#  C828 https://tunubi.atlassian.net/browse/NWA-2264
#
#    Given Pulsa el boton "perfil" en el menu
#    And Usuario esta en pantalla "perfil"
#    And Usuario presiona el link "security"
#    And Usuario presiona el link "change_nubipass"
#    And Ingresa clave nubi "correcto"
#    And Validar pantalla cuando el pin es "correcto"
#    And Usuario ingresa una nueva clave Nubi "correcto"
#    And Validar pantalla cuando el nuevo pin es "correcto"
#    And Usuario confirma un pin "incompleto"
#    When Pulsar back en "app" en pantalla "confirmarpinnuevo"
#    Then Validar elementos de pantalla "pin_nuevo"
##
##
#  @regression
#  Scenario: Confirmar PIN - Matar la app desde ¨Confirmar PIN¨(C829)
#  Contempla los tc:
#  C829 https://tunubi.atlassian.net/browse/NWA-2264
#
#    Given Pulsa el boton "perfil" en el menu
#    And Usuario esta en pantalla "perfil"
#    And Usuario presiona el link "security"
#    And Usuario presiona el link "change_nubipass"
#    And Ingresa clave nubi "correcto"
#    And Validar pantalla cuando el pin es "correcto"
#    And Usuario ingresa una nueva clave Nubi "correcto"
#    And Validar pantalla cuando el nuevo pin es "correcto"
#    And Usuario confirma un pin "incompleto"
#    When Usuario cierra la app
#    And Usuario abre la app
#    Then Usuario esta en pantalla "login"
##
##
#  @regression
#  Scenario: Corfirmar PIN- Back desde el device(C830)
#  Contempla los tc:
#  C830 https://tunubi.atlassian.net/browse/NWA-2264
#
#    Given Pulsa el boton "perfil" en el menu
#    And Usuario esta en pantalla "perfil"
#    And Usuario presiona el link "security"
#    And Usuario presiona el link "change_nubipass"
#    And Ingresa clave nubi "correcto"
#    And Validar pantalla cuando el pin es "correcto"
#    And Usuario ingresa una nueva clave Nubi "correcto"
#    And Validar pantalla cuando el nuevo pin es "correcto"
#    And Usuario confirma un pin "incompleto"
#    When Pulsar back en "dispositivo" en pantalla "cambiopin"
#    Then Validar elementos de pantalla "pin_nuevo"
##
##
#  @regression
#  Scenario: Corfirmar PIN- Backgroud desde el device(C831)
#  Contempla los tc:
#  C831 https://tunubi.atlassian.net/browse/NWA-2264
#
#    Given Pulsa el boton "perfil" en el menu
#    And Usuario esta en pantalla "perfil"
#    And Usuario presiona el link "security"
#    And Usuario presiona el link "change_nubipass"
#    And Ingresa clave nubi "correcto"
#    And Validar pantalla cuando el pin es "correcto"
#    And Usuario ingresa una nueva clave Nubi "correcto"
#    And Validar pantalla cuando el nuevo pin es "correcto"
#    And Usuario confirma un pin "incompleto"
#    When Presiona boton de aplicaciones recientes
#    Then Validar elementos de pantalla "confirmarpinnuevo"

#  Scenario: Salir desde la pantalla ¨PIN nuevo¨(C797)
#    Contempla el tc
#    C797 https://tunubi.atlassian.net/browse/NWA-2263
#
#    Given Pulsa el boton "perfil" en el menu
#    And Usuario esta en pantalla "perfil"
#    And Usuario presiona el link "security"
#    And Usuario presiona el link "cambiopin"
#    And Ingresa clave nubi "correcto"
#    And Validar pantalla cuando el pin es "correcto"
#    When Usuario pulsa boton cerrar en pantalla "cambiopin"
#    Then Usuario esta en pantalla "security"
##
##
#  Scenario: Nuevo PIN- Back desde el device(C799)
#  Contempla el tc
#  C799 https://tunubi.atlassian.net/browse/NWA-2263
#
#    Given Pulsa el boton "perfil" en el menu
#    And Usuario esta en pantalla "perfil"
#    When Usuario presiona el link "security"
#    And Usuario presiona el link "cambiopin"
#    And Ingresa clave nubi "correcto"
#    And Validar pantalla cuando el pin es "correcto"
#    And Pulsar back en "dispositivo" en pantalla "cambiopin"
#    Then Usuario esta en pantalla "security"
##
##
#  Scenario: Matar la app desde ¨Nuevo PIN¨(C798)
#  Contempla el tc
#  C798 https://tunubi.atlassian.net/browse/NWA-2263
#
#    Given Pulsa el boton "perfil" en el menu
#    And Usuario esta en pantalla "perfil"
#    And Usuario presiona el link "security"
#    And Usuario presiona el link "cambiopin"
#    And Ingresa clave nubi "correcto"
#    And Validar pantalla cuando el pin es "correcto"
#    When Ingresa clave nubi "incompleto"
#    And user kill-close the app
#    Then open the app
#    Then Usuario esta en pantalla "login"
##
##
#  Scenario:  Nuevo PIN - Backgroud desde el device(C800)
#  Contempla el tc
#  C800 https://tunubi.atlassian.net/browse/NWA-2263
#
#    Given Pulsa el boton "perfil" en el menu
#    And Usuario esta en pantalla "perfil"
#    And Usuario presiona el link "security"
#    And Usuario presiona el link "cambiopin"
#    And Ingresa clave nubi "correcto"
#    And Validar pantalla cuando el pin es "correcto"
#    When Ingresa clave nubi "incompleto"
#    And Presiona boton de aplicaciones recientes
#    Then Validar elementos de pantalla "pinnuevo"
#    And Validar que los campos esten vacios en la pantalla "pinnuevo"