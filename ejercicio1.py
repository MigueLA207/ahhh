def mostrar_usuarios():
    for i in biblioteca:
        titulo = biblioteca[str(i)]['titulo']
        autor = biblioteca[str(i)]['autor']
        año = biblioteca[str(i)]['año']

        print(f"id : {i} , titulo: {titulo}, autor: {autor}, año: {año}")



biblioteca = {}

print("Opciones del programa")
print("1) Agregar libro")
print("2) Mostrar libros")
print("3) Actualizar libro")
print("4) Eliminar id")
opcion_menu = int(input("Escoge una opcion: "))

match opcion_menu:
    case 1: 
        



print("agregar libro al diccionario")
id =0


while True:
    #Agregar libro a la biblioteca
    nombre = str(input("ingrese el nombre del libro: "))
    autor = str(input("ingrese el  nombre del autor: "))
    año = int(input("ingrese el año de creacion: "))

    info = {'titulo': nombre, 'autor': autor, 'año': año}
    id += 1  

    biblioteca[str(id)] = info

    titulo = biblioteca[str(id)]['titulo']
    autor = biblioteca[str(id)]['autor']
    año = biblioteca[str(id)]['año']

    print("\nLibro Ingresado:")
    print(f"id : {id} , titulo: {titulo}, autor: {autor}, año: {año}")

    op = int(input("\n¿Quieres salir del programa? digite 1 para agregar mas libros, digite 2 para salir: \n") )

    if op == 2:
        break


mostrar_usuarios()



