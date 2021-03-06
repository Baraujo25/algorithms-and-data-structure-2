import java.util.Scanner;

public class Ejercicio12{

	public static int POSICION_NO_VALIDA = -1;

	public static void main(String[] args) {
	    Scanner out = new Scanner(System.in);
	    	// Manejamos un array para poder modificar por referencia.
		// Integer.class no tuvo efecto.
		int[]  mejorPuntaje = {0};
		int[] puntajeCandidato = {0};

		int N = out.nextInt();
		int S = out.nextInt();
		int L = out.nextInt();

		int[] puntajes = new int[N+1];
		int[] tamanios = new int[N+1];
		int[] lineas = new int[N+1];
		for(int n = 0; n < N; n++) {
			tamanios[n] = out.nextInt();
			lineas[n] = out.nextInt();
			puntajes[n] = out.nextInt();
		}
		int[] tamanioActual = {S};
		int[] lineaActual = {L};

        mochilaBT(0, tamanioActual, lineaActual, puntajeCandidato,mejorPuntaje,tamanios,lineas,puntajes);
	System.out.println(mejorPuntaje[0]);
    }

    public static boolean esUnObjetoValido(int archivoHasta, int [] puntajes) {
        return archivoHasta < puntajes.length;
    }

    public static boolean puedoPonerElArchivo(int archivoHasta, int[]
		    tamanioActual, int[] lineasActual, int[] tamanios,int[]
		    lineas) {
        
        return ((tamanioActual[0] >= tamanios[archivoHasta]) && (lineasActual[0] >= lineas[archivoHasta]));
        
    }
    public static void colocarTentativamenteElArchivo(int archivoHasta, int[]
		    tamanioActual, int[] lineasActual, int[]
		    puntajeCandidato,int[] tamanios,int[] lineas, int []
		    puntajes) {
        tamanioActual[0] -= tamanios[archivoHasta];
        lineasActual[0] -= lineas[archivoHasta];
        puntajeCandidato[0] += puntajes[archivoHasta];
    }
    public static void retirarElArchivo(int archivoHasta, int[] tamanioActual,
		    int[] lineasActual, int[] puntajeCandidato,int[]
		    tamanios,int[] lineas,int [] puntajes) {
        tamanioActual[0] += tamanios[archivoHasta];
        lineasActual[0] += lineas[archivoHasta];
        puntajeCandidato[0] -= puntajes[archivoHasta];
    }

    public static boolean esSolucion() {
        return true; // todas son soluciones
    }

    public static boolean esMejorSolucion(int[] puntajeCandidato, int[] mejorPuntaje) {
        return (puntajeCandidato[0] > mejorPuntaje[0]);
    }

    public static void mochilaBT(int archivoHasta, int[] tamanioActual, int[]
		    lineasActual, int[] puntajeCandidato, int[] mejorPuntaje,
		    int [] tamanios, int [] lineas, int [] puntajes ){ 

	if (esUnObjetoValido(archivoHasta, puntajes)) {
		if (puedoPonerElArchivo(archivoHasta, tamanioActual, lineasActual,tamanios,lineas)) {

			colocarTentativamenteElArchivo(archivoHasta, tamanioActual,
				lineasActual,
				puntajeCandidato,tamanios,lineas,
				puntajes);

			if (esSolucion() && esMejorSolucion(puntajeCandidato, mejorPuntaje)) {
				mejorPuntaje[0] = puntajeCandidato[0]; 
			}
			// exploro el siguiente objeto
			mochilaBT(archivoHasta + 1, tamanioActual, lineasActual,
				puntajeCandidato, mejorPuntaje, tamanios,
				lineas, puntajes);
			// deshacemos el movimiento
			retirarElArchivo(archivoHasta, tamanioActual, lineasActual,
				puntajeCandidato,tamanios, lineas,
				puntajes); 

			mochilaBT(archivoHasta + 1,tamanioActual, lineasActual,
				puntajeCandidato, mejorPuntaje,
				tamanios, lineas, puntajes);

		} else {
			mochilaBT(archivoHasta + 1, tamanioActual, lineasActual,
				puntajeCandidato, mejorPuntaje,tamanios,
				lineas, puntajes);       
		}
	}
    }
}    
    

