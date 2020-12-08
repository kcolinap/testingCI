@create_user
Feature: Creacion de Usuarios Android

  Scenario Outline: Crear usuarios desde el BackEnd para usarlos desde la UI con Contactos Nubi y Bancarios

    Given Se crean <cantUsers> usuarios desde el backend de genero "<gender>" con contactos "<contactType>"

    Examples:
      | cantUsers | gender    | contactType |
      #| 15        | femenino  | ninguno     |
      | 10        | masculino | ninguno     |
     # | 3         | femenino  | bancario        |
     # | 5         | masculino | ninguno         |