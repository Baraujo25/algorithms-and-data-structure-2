import java.util.Scanner;

public class Ejercicio11 {

	public static void main(String[] args) {
		Scanner out = new Scanner(System.in);

		int N = out.nextInt();
		int S = out.nextInt();
		int L = out.nextInt();

		int[] puntajes = new int[N];
		int[] tamanios = new int[N];
		int[] lineas = new int[N];
		for (int n = 0; n < N; n++) {
			tamanios[n] = out.nextInt();
			lineas[n] = out.nextInt();
			puntajes[n] = out.nextInt();
		}
		int result = calcularMejorPuntajeDeArchivos(S, L, lineas, tamanios, puntajes);
		System.out.println(result);
		out.close();
	}

	public static int calcularMejorPuntajeDeArchivos(int S, int L, int[] lineas, int[] tamanios, int[] puntajes) {
		int[][][] mochila = new int[puntajes.length][S + 1][L + 1];

		for (int tamanioHasta = 0; tamanioHasta <= S; tamanioHasta++) {
			for (int cantLineasHasta = 0; cantLineasHasta <= L; cantLineasHasta++) {
				if (tamanios[0] <= tamanioHasta && lineas[0] <= cantLineasHasta) {
					mochila[0][tamanioHasta][cantLineasHasta] = puntajes[0];
				}
			}
		}

		for (int archivoHasta = 1; archivoHasta < puntajes.length; archivoHasta++) {
			for (int tamanioHasta = 0; tamanioHasta <= S; tamanioHasta++) {
				for (int cantLineasHasta = 0; cantLineasHasta <= L; cantLineasHasta++) {
					if (tamanios[archivoHasta] > tamanioHasta || lineas[archivoHasta] > cantLineasHasta) {
						mochila[archivoHasta][tamanioHasta][cantLineasHasta] = mochila[archivoHasta
								- 1][tamanioHasta][cantLineasHasta];
					} else {
						int valorMochilaSinUsarArchivo = mochila[archivoHasta - 1][tamanioHasta][cantLineasHasta];
						int valorMochilaConArchivo = puntajes[archivoHasta]
								+ mochila[archivoHasta - 1][tamanioHasta - tamanios[archivoHasta]][cantLineasHasta
										- lineas[archivoHasta]];
						mochila[archivoHasta][tamanioHasta][cantLineasHasta] = valorMochilaConArchivo > valorMochilaSinUsarArchivo
								? valorMochilaConArchivo
								: valorMochilaSinUsarArchivo;
					}
				}
			}
		}

		return mochila[lineas.length - 1][S][L];
	}
}
