package vendviajero;

import java.io.FileNotFoundException;
import static vendviajero.AntColony.*;

public class VendViajero {

    public static void main(String[] args) throws FileNotFoundException {
        
        int semilla =8;
        int tamColonia = 15;
        float iteracion = 10000;
        float alfa = (float)0.1;  //Factor de evaporación de feromona
        float q0 = (float)0.9;    //Probabilidad de avanzar por medio de explotación o exploración
        float beta = (float)4.5; //Peso de valor de heurística        
        float cantIteracion = 0;
        int porcentaje = 0;
        int anterior=0;
        String dataSet = "berlin52.txt";
        
        if(args.length == 0);  //En caso de no ingresar parámetros se ingresarán por defecto los establecidos en líneas de arriba
        else if(args.length != 7){
            System.out.println("Faltan argumentos o puso demasiados al momento de ingresar");
            System.exit(0);
        }
        else{
            //Verificación de números decimales
            char c;
            for (int i = 0; i < args[4].length(); i++) {
                c = args[5].charAt(i);
                if(c == ','){
                    System.out.println("Ingrese el valor de la prob de cruza con . no con ,");
                    System.exit(0);
                }
            }
            for (int i = 0; i < args[5].length(); i++) {
                c = args[6].charAt(i);
                if(c == ','){
                    System.out.println("Ingrese el valor de la prob de mutación con . no con ,");
                    System.exit(0);
                }
            }
            for (int i = 0; i < args[6].length(); i++) {
                c = args[7].charAt(i);
                if(c == ','){
                    System.out.println("Ingrese el valor de la prob de mutación con . no con ,");
                    System.exit(0);
                }
            }
            
            dataSet = args[0];
            semilla = Integer.parseInt(args[1]);
            tamColonia = Integer.parseInt(args[2]);
            iteracion = Float.parseFloat(args[3]);
            alfa = Float.parseFloat(args[4]);
            q0 = Float.parseFloat(args[5]);
            beta = Float.parseFloat(args[6]);
            
            if(alfa<0 || alfa>1){
                System.out.println("alfa debe estar entre 0 y 1");
                System.exit(0);
            }
            if(q0<0 || q0>1){
                System.out.println("q0 debe estar entre 0 y 1");
                System.exit(0);
            }
            if(tamColonia<=1){
                System.out.println("Ingrese un tamaño de colonia mayor a 1");
                System.exit(0);
            }
            if(iteracion<=1){
                System.out.println("Ingrese una cantidad de iteraciones mayor a 1");
                System.exit(0);
            }
        }
        
        leerarchivo data = new leerarchivo();
        AntColony colonia = new AntColony(semilla, alfa, q0, beta, data.leerTxt(dataSet), tamColonia); 
        while(cantIteracion != iteracion){
            if(AntColony.fitnessMejorSolucion <= 7545){
                System.out.println("La solución optima se encontró en la iteración: "+(int)cantIteracion);
                break;
            }
            int[] inicio = new int[tamColonia];
            //formula 1 matrizFeromona * matrizN^beta
            for (int i = 0; i < mDeterministica.length; i++) {
                for (int j = 0; j < mDeterministica.length; j++) {
                    if(i == j){
                        mDeterministica[i][j] = 0;
                    }else{
                        mDeterministica[i][j] = feromonas[i][j]*(float)Math.pow(N[i][j],beta);                   
                    } 
                }
            }
            inicio = colonia.desordenarVector(inicio);
            colonia.setInicio(inicio);
            colonia.antStart();
            cantIteracion++;
            porcentaje = (int)((cantIteracion/iteracion)*100);
            if(porcentaje>anterior){
                anterior = porcentaje;
                System.out.println(porcentaje+"%");
            }
        }
        System.out.print("El camino encontrado es: ");
        for (int i = 0; i < AntColony.mejorSolucion.length; i++) {
            System.out.print(AntColony.mejorSolucion[i]+" ");
        }
        System.out.println("");
        System.out.println("El costo del mejor camino encontrado es: "+AntColony.fitnessMejorSolucion);
    }
    
}
