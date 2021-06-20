import java.util.Scanner;

public class Ejercicio10 {

	public static void main(String[] args) {
		Scanner out = new Scanner(System.in);
		int cantVuelos = out.nextInt();
		int[] llegadas = new int[cantVuelos];
		int[] salidas = new int[cantVuelos];
		for(int i = 0; i < cantVuelos; i++) {
			llegadas[i] = out.nextInt();
			salidas[i] = out.nextInt();
		}
		ordenar(llegadas);
		ordenar(salidas);
		System.out.println(calcularPlataformasNecesarias(llegadas, salidas));
	}

	public static void ordenar(int[] array) {
		MinHeap heap = new MinHeap(array.length);	
		for(int i = 0; i < array.length; i++) {
			heap.encolar(array[i]);
		}

		for(int i = 0; i < array.length; i++) {
			array[i] = heap.obtenerMinimo();
			heap.borrarMinimo();
		}
	}

	public static int calcularPlataformasNecesarias(int[] llegadas, int[] salidas) {
		int cantVuelos = llegadas.length;	
		int llegadasI = 0;
		int salidasI = 0;
		int cantPlataformasMax = 0;
		int cantPlataformasAlMomento = 0;
		while(llegadasI < cantVuelos || salidasI < cantVuelos) {

			//salidasI nunca mayor igual a cantVuelos 
			//no se pueden ir aviones antes de llegar
			if (llegadasI >= cantVuelos){
				salidasI++;
				cantPlataformasAlMomento--;
			} else if (llegadas[llegadasI] < salidas[salidasI]) {
				llegadasI++;
				cantPlataformasAlMomento++;
			} else if (llegadas[llegadasI] > salidas[salidasI]) {
				salidasI++;
				cantPlataformasAlMomento--;
			} else {
				llegadasI++;
				salidasI++;
			}
			cantPlataformasMax = cantPlataformasAlMomento > cantPlataformasMax ? cantPlataformasAlMomento : cantPlataformasMax;
		}
		return cantPlataformasMax;
	}


public static class MinHeap {
	    private int[] elementos;
	    private int ultimoLibre;

	    public MinHeap(int tamanio) {
		elementos = new int[tamanio + 1];
		largo = tamanio;
		ultimoLibre = 1;
	    }

	    private int izq(int nodo) {
		return nodo * 2;
	    }

	    private int der(int nodo) {
		return nodo * 2 + 1;
	    }

	    private int padre(int nodo) {
		return nodo/2;
	    }

	    private void intercambiar(int x, int y) {
		int aux = elementos[x];
		elementos[x] = elementos[y];
		elementos[y] = aux;
	    }

	    public void encolar(int nuevoElemento) {
		if(!estaLleno()) {
		    elementos[ultimoLibre] = nuevoElemento;
		    flotar(ultimoLibre);
		    ultimoLibre++;
		}
	    }

	    private void flotar(int nodo) {
		if(nodo != 1) {
		    int nodoPadre = padre(nodo);
		    if(elementos[nodo] < elementos[nodoPadre]) {
			intercambiar(nodo, nodoPadre);
			flotar(nodoPadre);
		    }
		}
	    }

	    public int obtenerMinimo() {
		if(!esVacio()) {
		    return elementos[1];
		}
		return -1;
	    }

	    public void borrarMinimo() {
		if(!esVacio()) {
		    elementos[1] = elementos[ultimoLibre - 1];
		    ultimoLibre--;
		    hundir(1);
		}
	    }

	    private void hundir(int nodo) {
		// si tiene hijos (al menos 1)
		if(izq(nodo) < ultimoLibre) {
		    int izq = izq(nodo);
		    int der = der(nodo);
		    int hijoMenor = izq;

		    if(der < ultimoLibre && elementos[der] < elementos[izq]) {
			hijoMenor = der;
		    }

		    if(elementos[hijoMenor] < elementos[nodo]) {
			intercambiar(hijoMenor, nodo);
			hundir(hijoMenor);
		    }
		}
	    }

	}

}
