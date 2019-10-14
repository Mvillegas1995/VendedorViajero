
package vendviajero;

public class Ant {
    private int[] camino; //guarda camino por dónde pasa
    private int[] memoria; //recuerda si paso por ese vector
    
    public Ant(int tamMemoria){
        camino = new int[tamMemoria];
        memoria = new int[tamMemoria];
        for (int i = 0; i < memoria.length; i++) {
            memoria[i] = 0;
        }
    }
    public void setCamino(int pos, int visitar){
        camino[pos] = visitar;
    }
    public int getCamino(int pos){
        int ciudad = camino[pos];      
        return ciudad;
    }
    public void borrarMemoria(){
        for (int i = 0; i < memoria.length; i++) {
            memoria[i] = 0;
        }
    }
    public float costoCamino(AntColony aC){
        float costo = aC.costo(camino);
        return costo;
    }
}
