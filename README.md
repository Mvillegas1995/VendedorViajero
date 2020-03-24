# Vendedor Viajero

Dado un grupo de ciudades encontrar la ruta más corta para recorrerlas todas sin repetir, finalmente volver a la ciudad de origen. A través del algoritmo colonia de hormigas.

# Informacion

Proyecto realizado en Netbeans IDE 8.2; 

# Descargar 

> git clone https://github.com/Mvillegas1995/VendedorViajero.git

# Ejecucion 

> El programa está previamente compilado, solo falta abrirlo como proyecto en la IDE Netbeans y dar click a RUN.
Para modificar los parámetros de entrada debemos ir a la clase principal VendViajero y modificar los atributos ubicados entre las líneas 10 y 16, los cuales son los siguientes:

- semilla: Valor de semilla para generación de números randomicos.  
- tamColonia: Tamaño de la colonia de hormigas. 
- iteracion: Cantidad máxima de iteraciones.  
- alfa: Factor de evaporación de feromona.  
- q0: Probabilidad de avanzar por medio de explotación o exploración.
- beta: Peso de valor de heurística.
- daraSet: Ruta de acceso a los datos de las ciudades y sus distancias.

- semilla: Entero positivo.  
- tamColonia: Entero positivo.  
- iteracion: Entero positivo.  
- alfa: Flotante entre 0 y 1 **(usar . entremedio , ej: 0.9)**  
- q0: Flotante entre 0 y 1 **(usar . entremedio , ej: 0.9)**
- beta: Flotante **(usar . entremedio , ej: 4.5)**  
- daraSet: Cadena de carácteres.

# Ejemplo y valores por defecto

- int semilla = 8;
- int tamColonia = 15;
- float iteracion = 10000;
- float alfa = (float)0.1;
- float q0 = (float)0.9;
- float beta = (float)4.5;  
- String dataSet = "berlin52.txt"; 

# Integrantes

- Marcos Villegas
- Julio Cáceres
