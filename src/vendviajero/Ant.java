
package vendviajero;

import java.util.ArrayList;

public class Ant extends Thread{
    private int[] camino; //guarda camino por dónde pasa
    private ArrayList<Integer> memoria; //recuerda si paso por ese vector
    
    public Ant(int tamMemoria){
        camino = new int[tamMemoria];
        memoria = new ArrayList<>();
               
    }
    public void setCamino(int pos, int visitar){
        camino[pos] = visitar;
    }
    public int getCamino(int pos){
        int ciudad = camino[pos];      
        return ciudad;
    }
    public void borrarMemoria(){
        memoria.removeAll(memoria);
    }
    public final void reestablecerMemoria(){
        for (int i = 0; i < camino.length; i++) {
            memoria.add(i);
        }
        memoria.remove(memoria.indexOf(camino[0]));
    }
    public float costoCamino(AntColony aC){
        float costo = aC.costo(camino);
        return costo;
    }
    
    public void Run(AntColony colonia, float[][] mDeterministica){
        reestablecerMemoria();
        float valorMayor;
        int indiceMayor = -1;
        float rand = colonia.random.nextFloat();
        for (int i = 1; i < mDeterministica.length; i++) {  
            valorMayor = Integer.MIN_VALUE;
            for (int j = 0; j < memoria.size(); j++) {              
                if(mDeterministica[camino[i-1]][memoria.get(j)] > valorMayor){
                    valorMayor = mDeterministica[camino[i-1]][memoria.get(j)];
                    indiceMayor = memoria.get(j);
                }
            }
            if(rand < colonia.q0){                      //Explotación
                camino[i] = indiceMayor;
                memoria.remove(memoria.indexOf(indiceMayor));
                System.out.println(indiceMayor);
                for (int a:memoria) {
                    System.out.print(a+" ");
                }
                System.out.println("");
            }
            else{                                       //Exploración
                
            }
            
        }
        for (int i = 0; i < camino.length; i++) {
            System.out.print(camino[i]+" ");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("costo: "+colonia.costo(camino));
    }
}
