@create_user_from_file
Feature: Creacion de Usuarios Android desde un archivo CSV

  # El archivo de usuarios debe estar en el directorio main/resources/
  # Como parametro va SOLO el NOMBRE del archivo, si el archivo es archivo_de_usuarios.csv
  # el nombre es archivo_de_usuarios y eso va de parametro
  # Los campos REQUERIDOS son:
  #     Column 0 FIRST NAME
  #     Column 1 LAST NAME
  #     Column 2 DNI
  #     Column 3 GENDER should be F or M
  #     Column 5 PHONE NUMBER like: 011415415415
  # IMPORTANTE: SI ALGUNO DE LOS DATOS FALTAN SE GENERAN RANDOM.
  
  @stage
  Scenario: Se crean usuario a partir de un CSV
    Given Se crean "faltantes" usuarios desde el backend separados por ";" desde un archivo csv

