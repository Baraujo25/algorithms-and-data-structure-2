import java.util.Scanner;
import java.lang.Runnable;

public class Ejercicio7 {

	public static void main(String[] args) {
		Grafo grafo = initGrafo();
		System.out.println(calculateComponentesFuertementeConexas(grafo));
	}

	public static int calculateComponentesFuertementeConexas(Grafo grafo) {
		int cantComponentesConexas = 0;
		boolean[] visitados = new boolean[grafo.V+1];
		Pila pila = new Pila(grafo.V);
		cargarPila(pila, visitados, grafo);
		Grafo traspuesta = grafo.getTraspuesta();
		visitados = new boolean[grafo.V+1];
			
		while(!pila.esVacia()) {
			int vertice = pila.quitar();
			if (!visitados[vertice]) {
				correrDFS(traspuesta, visitados, vertice);
				cantComponentesConexas++;
			}
		}
		return cantComponentesConexas;
	}

	public static void cargarPila(Pila p, boolean[] visitados, Grafo grafo) {
		for(int i = 1; i < visitados.length; i++) {
			if (!visitados[i]) {
				cargarPilaDFS(grafo, p, visitados, i);
			}
		}
	}

	public static void cargarPilaDFS(Grafo g, Pila pila, boolean[] visitados, int vertice) {
		visitados[vertice] = true;
		ListaArista adyacentes = g.adyacentesA(vertice);
		while(adyacentes != null) {
			Arista arista = adyacentes.getDato();
			if (!visitados[arista.getDestino()]) {
				cargarPilaDFS(g, pila, visitados, arista.getDestino());
			}
			adyacentes = adyacentes.getSiguiente();
		}
		pila.agregar(vertice);
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

	public static Grafo initGrafo() {
		Scanner out = new Scanner(System.in);
		int V = out.nextInt();
		int A = out.nextInt();
		Grafo grafo = new ListaAdyacencia(V, true);

		for( int i = 0; i <A; i++ ) {
			grafo.agregarArista(Integer.parseInt(out.next()), Integer.parseInt(out.next()));
		}
		out.close();
		return grafo;
	}

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

	public static class Pila {

		private int[] pila;
		private int indice;
		private int largo;

		public Pila(int largo) {
			this.pila = new int[largo];
			this.indice = 0;
			this.largo = largo;
		}

		public void agregar(int n) {
			if (!estaLlena()) {
				this.pila[this.indice++] = n;
			}
			System.out.println(this.indice);
		}

		public int quitar() {
			if (!esVacia()) {
				this.indice--;
				return this.pila[indice];
			}
			return -1;
		}

		public boolean esVacia() {
			return this.indice == 0;
		}

		public boolean estaLlena() {
			return this.largo == this.indice;
		}
	}


}
