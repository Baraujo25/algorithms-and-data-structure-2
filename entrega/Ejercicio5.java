import java.util.Scanner;

public class Ejercicio5 {

	public static void main(String[] args) {
		Grafo grafo = initGrafo();
		Floyd(grafo);
		
	}

	public static Grafo initGrafo() {
		Scanner out = new Scanner(System.in);
		int V = out.nextInt();
		int A = out.nextInt();
		Grafo grafo = new Matriz(V, true);//dirigido o no
		
		for( int i = 1; i <= A; i++ ) {
			int vorigen=out.nextInt();
			int vdestino=out.nextInt();
			int apeso=out.nextInt();
			grafo.agregarArista(vorigen,vdestino,apeso);
		}

		return grafo;

	}
	
	public static void Floyd(Grafo grafo) {
		int [][] caminosMasCortos = new int[grafo.V+1][grafo.V+1];
		int [][] vengodeVertice = new int[grafo.V+1][grafo.V+1];	

		//inicializo la matriz de caminos más cortos con MAXValue si no hay arista, si hay arista con el valor
		for (int i=1; i<=grafo.V; i++) {
			for(int j=1; j<=grafo.V; j++) {
				if (i==j) {
					caminosMasCortos[i][j]=-1;
				} else
					{caminosMasCortos[i][j]= Integer.MAX_VALUE;
				}		
			}
		}		

		for (int i=1; i<=grafo.V; i++) {
			for(int j=1; j<=grafo.V; j++){
				vengodeVertice[i][j]=Integer.MAX_VALUE;	
			}
		}	

		for (int i=1; i<=grafo.V; i++) {
			ListaArista adyacentes = grafo.adyacentesA(i);
			while(adyacentes!=null) {
				Arista ar = adyacentes.getDato();				
				caminosMasCortos[i][ar.destino]=ar.peso;	
				adyacentes = adyacentes.sig;
			}
		}						
			
		for(int k=1; k <= grafo.V; k++) {
			for (int i=1; i<=grafo.V; i++) {
				for (int j=1; j<=grafo.V; j++) {
					if (caminosMasCortos[i][k]!=-1 
						&& caminosMasCortos[k][j]!=-1 
						&& caminosMasCortos[i][k]!=Integer.MAX_VALUE 
						&& caminosMasCortos[k][j]!= Integer.MAX_VALUE 
						&& caminosMasCortos[i][j] > caminosMasCortos[i][k] + caminosMasCortos[k][j]) {
						caminosMasCortos[i][j] = caminosMasCortos[i][k] + caminosMasCortos[k][j];
						vengodeVertice[i][j] = k;
					}	
				}
			}
		}

		for (int i=1; i<=grafo.V; i++) {
			for(int j=1; j<=grafo.V; j++){
				System.out.println(caminosMasCortos[i][j]);	
			}
		}	
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

	public static class Matriz extends Grafo {

		private int[][] grafo;	

		public Matriz(int V, boolean esDirigido) {
			super(V, esDirigido);
			this.grafo = new int[V+1][V+1];
			initGrafo();
		}

		private void initGrafo() {
			
			for ( int i = 1; i <= super.V; i ++) {
				for ( int j = 1; j <= super.V; j++) {
					grafo[i][j] = Integer.MAX_VALUE;
					//int valor = grafo[i][j];					
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
