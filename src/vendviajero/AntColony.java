
package vendviajero;

import java.util.Random;

public final class AntColony {
    private final Random random;    
    private final float alfa; //disolución feromona
    private final float q0;  //probabilidad de explotación o exploración
    private final float beta; //potenciación de matriz N
    private final float[][] N; //matriz heurística 1/distancia
    private final Ant[] antVector;
    private int[] mejorSolucion;
    private float fitnessMejorSolucion;
    private float[][] feromonas;
    
    public AntColony(int semilla, float alfa, float q0, float beta,int[][] coordenadas, int tamColonia){
        random = new Random(semilla);
        this.alfa = alfa;
        this.q0 = q0;
        this.beta = beta;
        N = distanciaHeuristica(coordenadas);
        antVector = new Ant[tamColonia];
        for (int i = 0; i < tamColonia; i++) {
            antVector[i] = new Ant(N.length);
        }
        mejorSolucion = new int[N.length];
        for (int i = 0; i < mejorSolucion.length; i++) { //inicializo mejor Solución
            mejorSolucion[i] = i;
        }
        mejorSolucion = desordenarVector(mejorSolucion); //desordeno mejor solucion
        fitnessMejorSolucion = costo(mejorSolucion);    //obtengo el fitness de esa solucion
        feromonas = new float[N.length][N.length];
        for (int i = 0; i < N.length; i++) {            //inicializo matriz de feromonas
            for (int j = 0; j < N.length; j++) {
                if(i == j){
                    feromonas[i][j] = 0;
                }
                feromonas[i][j] = 1/fitnessMejorSolucion;
            }
        }
    }
    private int[] desordenarVector(int[] vector){
        int rand,temp;
        for (int i = 0; i < vector.length; i++) {
            temp = vector[i];
            rand = random.nextInt(vector.length);
            vector[i] = vector[rand];
            vector[rand] = temp;
        }
        return vector;
    }
    private float[][] distanciaHeuristica(int[][] coordenadas){
        int cantPuntos = coordenadas.length;
        //MATRIZ DE COSTOS 
        float[][]distancia = new float[cantPuntos][cantPuntos];
        int diferenciaX, diferenciaY;
        for (int i = 0; i < cantPuntos; i++) {
            for (int j = 0; j < cantPuntos; j++) {
                if (i==j) {
                    distancia[i][j] = 0;
                }
                else{
                    diferenciaX = coordenadas[i][0] - coordenadas[j][0];
                    diferenciaY = coordenadas[i][1] - coordenadas[j][1];
                    distancia[i][j] =  (float) (1/(Math.sqrt(Math.pow(diferenciaX, 2) + Math.pow(diferenciaY, 2))));
                }
            }
        }
        return distancia;
    }
    public float costo(int[] solucion){
        float costo = 0;
        for (int i = 0; i < (N.length-1); i++) {
            costo += N[solucion[i]][solucion[i+1]];
        }        
        return costo;
    }
}
