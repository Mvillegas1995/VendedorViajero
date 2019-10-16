
package vendviajero;

import java.util.ArrayList;

public class Ant extends Thread{
    private int[] camino; //guarda camino por dónde pasa
    private ArrayList<Integer> memoria; //recuerda si paso por ese vector
    
    public Ant(int tamMemoria){
        camino = new int[tamMemoria];
        memoria = new ArrayList<>();
        reestablecerMemoria();       
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
        memoria.remove(camino[0]);
    }
    public float costoCamino(AntColony aC){
        float costo = aC.costo(camino);
        return costo;
    }
    
    public void Run(AntColony colonia, float[][] mDeterministica){
        float rand = colonia.random.nextFloat();
        for (int i = 1; i < mDeterministica.length; i++) {   
            
            if(rand < colonia.q0){
                
            }
            else{
            }
        }
    }
}
