import java.util.Scanner;

public class Ejercicio8 {

//8. PT. DE ART.        Nom. de archivo: ejercicio8.cpp/Ejercicio8.java
//Implementar un algoritmo que, dado un grafo no dirigido, no ponderado, conexo y disperso, encuentre todos los puntos de articulación del mismo.


//Un punto de articulación es un vértice que, al removerlo (junto con todas las aristas que pasan por él), desconecta el grafo (pasa a ser no conexo ignorado dicho vértice).


//Restricciones: O(V*(V+A))
	public static void main(String args[]) {
		Grafo grafo = initGrafo();
		calcularPuntosDeArticulación(grafo);
		

	}

	public static Grafo initGrafo() {
		Scanner out = new Scanner(System.in);
		int V = out.nextInt();
		int A = out.nextInt();
		Grafo grafo = new ListaAdyacencia(V, false);

		for( int i = 0; i <A; i++ ) {
			grafo.agregarArista(Integer.parseInt(out.next()), Integer.parseInt(out.next()));
		}
		out.close();
		return grafo;
	}

	public static void calcularPuntosDeArticulación(Grafo grafo) {
		boolean[] visitados = new boolean[grafo.V+1];
		for(int i = 1; i <= grafo.V; i++) {
			visitados[i] = true;
			int vertice = i+1 < grafo.V ? i+1 : i-1;
			correrDFS(grafo, visitados, vertice);
			if (!esTrueOneIndexed(visitados, grafo.V)) {
				System.out.println(i);
			}
			visitados = new boolean[grafo.V+1];
		}
	}

	public static void correrDFS(Grafo g, boolean[] visitados, int vertice) {
		visitados[vertice] = true;
		ListaArista adyacentes = g.adyacentesA(vertice);
		while(adyacentes != null) {
			Arista arista = adyacentes.getDato();
			if (!visitados[arista.getDestino()]) {
				correrDFS(g, visitados, arista.destino);
			}
			adyacentes = adyacentes.getSiguiente();
		}
	}

	public static boolean esTrueOneIndexed(boolean[] visitados,int V) {
		if (V == 0) {
			return true;
		}
		return visitados[V] && esTrueOneIndexed(visitados, V-1);
	}

	// ## Implementacion Grafo ##
	public static abstract class Grafo {

		public int V;
		protected boolean esDirigido;

		public Grafo(int V, boolean esDirigido) {
			this.V = V;
			this.esDirigido = esDirigido;
		}

		protected abstract ListaArista adyacentesA(int vertice);

		protected abstract void agregarArista(int v, int w, int peso);

		public void agregarArista(int v, int w) {
			this.agregarArista(v, w, 1);
		}

		public abstract Grafo getTraspuesta();

	}
	
	public static class ListaArista {
		private final Arista dato;
		private final ListaArista sig;
		
		public ListaArista(Arista dato, ListaArista  sig) {
			this.dato = dato;
			this.sig = sig;
		}

		public Arista getDato() {
			return this.dato;
		}


		public ListaArista getSiguiente() {
			return this.sig;
		}
	}

	public static class Arista {
		private int origen;
		private int destino;
		private int peso;

		public Arista(int origen, int destino, int peso) {
			this.origen = origen;
			this.destino = destino;
			this.peso = peso;
		}

		public int getOrigen() {
			return this.origen;
		}

		public int getDestino() {
			return this.destino;
		}

	}

	public static class ListaAdyacencia extends Grafo {

		public ListaArista[] grafo;

		public ListaAdyacencia(int V, boolean esDirigido) {
			super(V, esDirigido);
			this.grafo = new ListaArista[V+1];
		}

		@Override
		protected void agregarArista(int v, int w, int peso) {
			this.grafo[v] = new ListaArista(new Arista(v, w, peso), grafo[v]);
			if (!super.esDirigido) {
				this.grafo[w] = new ListaArista(new Arista(w, v, peso), grafo[w]);
			}
		}

		@Override
		protected ListaArista adyacentesA(int vertice) {
			return this.grafo[vertice];
		}	

		@Override
		public Grafo getTraspuesta() {
			Grafo traspuesta = new ListaAdyacencia(super.V, super.esDirigido);
			for (int v = 1; v <= super.V; v++) {
				ListaArista adyacentes = this.adyacentesA(v);
				while(adyacentes != null) {
					Arista arista = adyacentes.getDato();
					traspuesta.agregarArista(arista.getDestino(), arista.getOrigen());
					adyacentes = adyacentes.getSiguiente();
				}
			}
			return traspuesta;
		}
	}
}
