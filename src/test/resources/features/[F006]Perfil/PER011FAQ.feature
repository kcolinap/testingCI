@profile @regression
Feature: Preguntas frecuentes

  Contienes los TC incluidos en la seccion:

  Perfil -> ... -> Ayuda


  @walletint @stage
  Scenario: Completitud de pantalla ¨Preguntas Frecuentes¨(C778)
  Contempla el tc:
  C778 https://tunubi.atlassian.net/browse/NWA-584

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    When Usuario presiona el link "faq"
    Then Validar elementos de pantalla "faq"

  @walletint @stage
  Scenario: Volver a la pantalla de ¨Perfil¨ desde la pantalla ¨Preguntas frecuentes¨(C779)
  Contempla el tc:
  C779 https://tunubi.atlassian.net/browse/NWA-584

    Given Usuario esta en pantalla "faq"
    When Pulsar back en "app" en pantalla "faq"
    Then Usuario esta en pantalla "profile"

  @walletint @stage
  Scenario:  Background desde la pantalla ¨Preguntas frecuentes¨(C780)
  Contempla el tc:
  C780 https://tunubi.atlassian.net/browse/NWA-584

    Given Usuario presiona el link "faq"
    And Usuario esta en pantalla "faq"
    When Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "faq"

  @walletint @stage
  Scenario:  Matar app desde la pantalla ¨Ayuda¨(C781)
  Contempla el tc:
  C781 https://tunubi.atlassian.net/browse/NWA-584

    Given Usuario esta en pantalla "faq"
    When Usuario cierra la app
    And Inicia la app
    Then Usuario esta en pantalla "login"