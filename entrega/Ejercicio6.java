import java.util.Scanner;

public class Ejercicio6 {

	public static void main(String[] args) {
		Grafo grafo = initGrafo();
		
		Boolean [] coloreados= initColoreados(grafo);
		Integer [] coloresUsados = initColoresUsados(grafo);		
		int error= BFS(1,grafo,coloreados,coloresUsados);
		System.out.println(Integer.toString(error));

			
	}

	public static Grafo initGrafo() {
		Scanner out = new Scanner(System.in);
		int V = out.nextInt();
		int A = out.nextInt();
		Grafo grafo = new ListaAdyacencia(V, false);//dirigido o no
		
		for( int i = 1; i <= A; i++ ) {
			int vorigen=out.nextInt();
			int vdestino=out.nextInt();
			grafo.agregarArista(vorigen,vdestino); //no tiene costos
		}

		out.close();
		return grafo;
	}
	
	public  static Boolean[] initColoreados(Grafo grafo) {
		Boolean [] coloreados = new Boolean[grafo.V+1]; //al principio están todos en false, no coloreados
		for (int i=1; i<=grafo.V;i++)
		{
			coloreados[i]=false;			
		}
		return coloreados;
	}

	public  static Integer [] initColoresUsados(Grafo grafo) {
		Integer [] queColorTiene = new Integer[grafo.V+1]; //existen 2 colores 1,2; 0 = ausencia de color;
		for (int i=1; i<=grafo.V;i++)
		{
			queColorTiene[i]=0;
		}
		return queColorTiene;
	}	
	
	
	public static int  BFS(int origen, Grafo grafo, Boolean [] coloreados, Integer [] queColorTiene) {		
		int errorBipartita=1;
		int coloresUso=0;
		for (int i=1;i<=grafo.V;i++) {	//reseteo los 2 colores disponibles		
			coloresUso=1;		
			if (coloreados[i]!=true) {
				coloreados[i]=true;
				queColorTiene[i]=coloresUso;				
			}					
			int colorVengo = queColorTiene[i];
			ListaArista adyacentes = grafo.adyacentesA(i);		
			coloresUso++;		
			//a sus adyacentes le doy el próximo color disponible
			while(adyacentes!=null)	{
				
				Arista ar = adyacentes.getDato();				
				if (coloreados[ar.destino]!=true) {
						coloreados[ar.destino]=true;						
						queColorTiene[ar.destino]=coloresUso;
						adyacentes=adyacentes.sig;
					
				} else {
					if (colorVengo == queColorTiene[ar.destino]) {
						errorBipartita=0;
					}	
					adyacentes=adyacentes.sig;
				}		
			}			
			
		}        
		return errorBipartita;
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

		public Arista(int origen, int destino, int peso) {
			this.origen = origen;
			this.destino = destino;
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
