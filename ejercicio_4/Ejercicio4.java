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

	public static boolean tieneOT(Grafo grafo) {
		boolean[] visitados = new boolean[grafo.V +1];
		int[] gradoEntrada = initGradoEntrada(grafo);
		
		for(int i = 1; i<=grafo.V; i++) {

			int vertice = obtenerVerticeGradoEntradaCeroNoVisitado(grafo, gradoEntrada, visitados);
			if (vertice==-1) {
				return false;
			}
			visitados[vertice] = true;
			ListaArista adyacentes = grafo.adyacentesA(vertice);
			while (adyacentes != null) {
				Arista a = adyacentes.getDato();
				gradoEntrada[a.getDestino()]--;
				adyacentes = adyacentes.getSiguiente();
			}
		}
		return true;
	}
	
	public static int[] initGradoEntrada(Grafo grafo) {
		int[] gEntradas = new int[grafo.V+1];
		
		for(int i = 1; i <= grafo.V; i++) {

			ListaArista adyacentes = grafo.adyacentesA(i);
			while (adyacentes != null) {
				Arista a = adyacentes.getDato();
				gEntradas[a.getDestino()]++;
				adyacentes = adyacentes.getSiguiente();
			}
		}
		return gEntradas;
	}

	public static int obtenerVerticeGradoEntradaCeroNoVisitado(Grafo grafo,
			int[] gradoEntrada, boolean[] visitados) {
		
		for(int i = 1; i<gradoEntrada.length; i++) {
			if (gradoEntrada[i] == 0 && !visitados[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean esAdyacente(int v1, int v2, Grafo grafo) {
		ListaArista adyacentes = grafo.adyacentesA(v1);
		while(adyacentes!=null) {
			Arista ar = adyacentes.getDato();
			if (ar.getDestino() == v2) {
				return true;
			}
			adyacentes = adyacentes.getSiguiente();
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

		public ListaArista getSiguiente() {
			return sig;
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

}
