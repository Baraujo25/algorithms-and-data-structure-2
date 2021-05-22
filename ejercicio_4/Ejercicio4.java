import java.util.Scanner;

public class Ejercicio4 {

	public static void main(String[] args) {
		Grafo grafo = initGrafo();
		if (tieneOT(grafo)) {
			System.out.println("0");
			return;
		}
		System.out.println("1");
	}

	public static Grafo initGrafo(Grafo g, Scanner out, int A) {
		Scanner out = new Scanner(System.in);
		int V = out.nextInt();
		int A = out.nextInt();
		Grafo grafo = new Matriz(V, false);

		for( int i = 0; i <A; i++ ) {
			int[] arista = out.next().split();
			g.agregarArista(arista[0], arista[1]);
		}

		return grafo;
	}

	public static boolean tieneOT(Grafo grafo) {
		boolean[] visitados = new boolean[grafo.V +1];
		int[] gradoEntrada = initGradoEntrada(grafo.V);
		
		for(int i = 1; i<=grafo.V; i++) {

			vertice = obtenerVerticeGradoEntradaCeroNoVisitado(grafo, gradoEntrada, visitados);
			if (vertice==-1) {
				return true;
			}
			visitados[i] = true;
			for( int j = 1; j <=grafo.V; j++) {
				if (esAdyacente(j, i, g)) {
					gradoEntrada--;
				}
			}
		}
		return false;
	}
	
	public static int[] initGradoEntrada(int V) {
		int[] gEntradas = new int[V+1];
		for(int i = 1; i<=V; i++) {
			gEntradas[i] = Intger.MAX_VALUE;
		}
		return gEntradas;
	}
		
	public static int obtenerVerticeGradoEntradaCeroNoVisitado(Grafo grafo,
			int[] gradoEntrada, boolean[] visitados) {
		
		for(int i = 1; i<gradoEntrada.length; i++) {
			if (!visitados[i] && gradoEntrada[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	public static boolean esAdyacente(int v1, int v2, Grafo grafo) {
		ListaArista adyacentes = g.adyacentesA(v1);
		while(adyacentes!=null) {
			Arista ar = adyacentes.getDato();
			if (ar.getDestino() == v2) {
				return true;
			}
		}
		return false;
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


	}

	public static class Matriz extends Grafo {

		private int[][] grafo;	

		public Matriz(int V, boolean esDirigido) {
			super(V, esDirigido);
			this.grafo = new int[V][V];
			initGrafo();
		}

		private void initGrafo() {
			for ( int i = 1; i <= super.V; i ++) {
				for ( int j = 1; j <= super.V; j++) {
					grafo[i][j] = Integer.MAX_VALUE;
				}
			}
		}

		@Override
		protected ListaArista adyacentesA(int vertice) {
			ListaArista listaArista = null;

			for( int i = 1; i<=super.V; i++) {
				if (this.grafo[vertice][i] != Integer.MAX_VALUE) {
					Arista arista = new Arista(vertice, i, this.grafo[vertice][i]);
					listaArista = new ListaArista(arista, listaArista);
				}
			}
			return listaArista;
		}	

		@Override
		protected void agregarArista(int v, int w, int peso) {
			this.grafo[v][w] = peso;
			if (!super.esDirigido) {
				this.grafo[w][v] = peso;
			}
		}
	}

}
