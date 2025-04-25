biblioteca = {}

print("agregar libro al diccionario")
id =0

while True:

    nombre = str(input("ingrese el nombre del libro "))
    autor = str(input("ingrese el  nombre del autor "))
    año = int(input("ingrese el año de creacion "))

    info = {'titulo': nombre, 'autor': autor, 'año': año}
    id += 1  

    biblioteca[str(id)] = info

    titulo = biblioteca[str(id)]['titulo']
    autor = biblioteca[str(id)]['autor']
    año = biblioteca[str(id)]['año']

    print(f"id : {id} , titulo: {titulo}, autor: {autor}, año: {año}")

    op = int(input("otro = 1, salir = 2 ") )

    if op == 2:
        break


for i in biblioteca:
    titulo = biblioteca[str(i)]['titulo']
    autor = biblioteca[str(i)]['autor']
    año = biblioteca[str(i)]['año']

    print(f"id : {i} , titulo: {titulo}, autor: {autor}, año: {año}")


