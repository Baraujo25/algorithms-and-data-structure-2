import java.util.Scanner;

public class Ejercicio11 {

	public static int POSICION_NO_VALIDA = -1;

	public static void main(String[] args) {
		Scanner out = new Scanner(System.in);

		int N = out.nextInt();
		int S = out.nextInt();
		int L = out.nextInt();

		int[] puntajes = new int[N];
		int[] tamanios = new int[N];
		int[] lineas = new int[N];
		for(int n = 0; n < N; n++) {
			tamanios[n] = out.nextInt();
			lineas[n] = out.nextInt();
			puntajes[n] = out.nextInt();
			System.out.println(String.format("archivo %d tamanio %d linea %d puntaje %d", n, tamanios[n], lineas[n], puntajes[n]));
		}

		int result = calcularMejorPuntajeDeArchivos(S, L, lineas, tamanios, puntajes);
		System.out.println(result);
	}

	public static int calcularMejorPuntajeDeArchivos(int S, int L, int[] lineas, int[] tamanios, int[] puntajes) {
		int[][][] mochila = new int[puntajes.length][S][L];

		int archivoHasta = 1;
		int tamanioHasta = 0;
		int cantLineasHasta = 0;


		for (; tamanioHasta < S; tamanioHasta++) {
			for (;cantLineasHasta < L; cantLineasHasta++) {
				if(tamanios[0] < tamanioHasta && lineas[0] < cantLineasHasta) {
					mochila[0][tamanioHasta][cantLineasHasta] = puntajes[0];
				}
			}
		}
		tamanioHasta = 0;
		cantLineasHasta = 0;
		for(; archivoHasta < puntajes.length; archivoHasta++) {
			for (; tamanioHasta < S; tamanioHasta++) {
				for (;cantLineasHasta < L; cantLineasHasta++) {
					if (tamanios[archivoHasta-1] > tamanioHasta || lineas[archivoHasta-1] > cantLineasHasta) {
						mochila[archivoHasta][tamanioHasta][cantLineasHasta] = mochila[archivoHasta-1][tamanioHasta][cantLineasHasta];
					} else {
						int valorMochilaSinUsarArchivo = mochila[archivoHasta-1][tamanioHasta][cantLineasHasta];
						int valorMochilaConArchivo = puntajes[archivoHasta] + mochila[archivoHasta-1][tamanioHasta -tamanios[archivoHasta-1]][cantLineasHasta - lineas[archivoHasta-1]];
						mochila[archivoHasta][tamanioHasta][cantLineasHasta] = valorMochilaConArchivo > valorMochilaSinUsarArchivo ? valorMochilaConArchivo : valorMochilaSinUsarArchivo;
					}
				}
			}
		}


		return mochila[lineas.length-1][S-1][L-1];
	}


	public static void printMochila(int[][][] mochila) {
		for(int n = 0; n < mochila.length; n++) {
			for(int s = 0; s < mochila[n].length; s++) {
				for(int l = 0; l < mochila[n][s].length; l++) {
					if (mochila[n][s][l] != -1) {
						System.out.print("[(n=" + n + "),(s=" + s + "), (l=" + l + "), (p=" +  mochila[n][s][l] + ")]");
					}
				}
			}
		}
	}





}
