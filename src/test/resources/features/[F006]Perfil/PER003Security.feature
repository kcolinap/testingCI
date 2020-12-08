@profile @regression
Feature: Security

Contienes los TC incluidos en la seccion:

Perfil -> ... -> Seguridad


    ############################################################
@walletint @stage
Scenario: Validar pantalla ¨Seguridad¨ (C619)
Contempla el tc:
C619 https://tunubi.atlassian.net/browse/NWA-580

Given Resetear app "si"
And Usuario "any" inicia sesion
And Usuario esta en pantalla "dashboard"
And Pulsa el boton "perfil" en el menu
When Usuario presiona el link "security"
Then Validar elementos de pantalla "security"

  ##############################################
@walletint @stage
Scenario: Volver a Perfil desde el back de ¨Seguridad¨ (C620)
Contempla el tc:
C620 https://tunubi.atlassian.net/browse/NWA-580

Given Usuario esta en pantalla "security"
When Pulsar back en "app" en pantalla "security"
Then Usuario esta en pantalla "PROFILE"

@walletint @stage
Scenario: Volver a ¨Perfil¨ desde el back del device, desde ¨Seguridad" (C621)
Contempla el tc:
C621 https://tunubi.atlassian.net/browse/NWA-580

Given Ir al dashboard
Given Usuario esta en pantalla "dashboard"
Given Pulsa el boton "perfil" en el menu
And Usuario esta en pantalla "perfil"
When Usuario presiona el link "security"
And Pulsar back en "dispositivo" en pantalla "security"
Then Usuario esta en pantalla "PROFILE"

@walletint @stage
Scenario: Matar la app desde la pantalla ¨Seguridad¨ (C622)
Contempla el tc:
C622 https://tunubi.atlassian.net/browse/NWA-580

Given Ir al dashboard
Given Usuario esta en pantalla "dashboard"
Given Pulsa el boton "perfil" en el menu
And Usuario presiona el link "security"
When Usuario cierra la app
And Inicia la app
Then Usuario esta en pantalla "login"