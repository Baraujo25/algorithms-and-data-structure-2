import java.util.*;
import java.lang.Comparable;
import java.util.function.Consumer;
import java.util.function.Function;

public class Ejercicio3 {
	/*
	 * (1) The idea will be to create a Hash Table with the Closed hasing method (HTC)
	 * Since the HTC has O(1) average and O(n) worst scenario when inserting.
	 * We can add the words to the HTC and have a counter for the duplicated words.
	 * 
	 * EDIT: This idea is not going to work. Since the words that I have to count are 
	 * only the duplicated ones. That means if a word appears thrice, that word is not counted.
	 * It could be possible to store those duplicated words into another structure.. but just an idea.
	 */
	/* (2) This second idea will be to create a hash map which contains a AVL in each bucket.
	 * That will result in O(ln) as average for inserting and obtaining. AVL nodes will only accept 
	 * an structure that will be called Palabras.
	 * Palabras consist two attributes, String palabra and int apareance;
	 * Then in order to provide the count of words that were just duplicated,we are going to loop 
	 * through all the structures. That will cost around O(ln(n) * n). We will be trying to optimice optimice
	 * that run by creating antoher array to keep the index of the buckets that are used.
	 *
	 * TODO: Consider using a simple chained list.
	 * 
	 *
	 */
	private TablaHashAvl<Palabra> tabla;
	private Function<Palabra, Integer> funcionHash;
	private Lista<Integer> indicesUsados;
	private int cantPalabras;

	public Ejercicio3(int cantPalabras) {
		this.tabla = new TablaHashAvl<Palabra>(buildFuncionHash(), cantPalabras*2, (p) -> p.incrementar());
		this.cantPalabras = cantPalabras;
	} 

	public void agregarPalabra(Palabra palabra) {
		int bucket = Math.abs(buildFuncionHash().apply(palabra) % (cantPalabras*2));
		Avl<Palabra> nodo = tabla.obtenerElemento(bucket);
		if (nodo == null) { 
			Lista<Integer> nuevoIndice = new Lista<>(bucket, this.indicesUsados);
			indicesUsados = nuevoIndice;
		}
		tabla.insertar(palabra);
	}

	public int calcularPalabrasDuplicadas() {
		Lista<Integer> indices = this.indicesUsados;
		int contador = 0;
		while(indices != null) {
			Avl<Palabra> nodo = tabla.obtenerElemento(indices.get());
			contador+= contarPalabrasDuplicadasAvl(nodo.getRaiz());
			indices = indices.siguiente();
		}
		return contador;
	}

	private int contarPalabrasDuplicadasAvl(NodoAvl<Palabra> nodo) {
		if (nodo == null) {
			return 0;
		}
		return nodo.get().getOcurr() == 2 ? 
			1 + contarPalabrasDuplicadasAvl(nodo.getIzq()) + 
			contarPalabrasDuplicadasAvl(nodo.getDer()) :
			contarPalabrasDuplicadasAvl(nodo.getIzq()) + 
			contarPalabrasDuplicadasAvl(nodo.getDer()); 
	}
	

	public int getCantPalabras() {
		return this.cantPalabras;
	}	

	public static Function<Palabra, Integer> buildFuncionHash() {
	 	return (p) -> {
			int hash = 0;
			for (int i = 0; i < p.palabra().length(); i++) {
				hash = 31 * hash + p.palabra().charAt(i);
			}
			return hash;
		}; 
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Ejercicio3 e = new Ejercicio3(input.nextInt());
		for (int i = 0; i < e.getCantPalabras(); i++) {
			String palabra = input.next();
			e.agregarPalabra(new Palabra(palabra));
		}
		System.out.println(e.calcularPalabrasDuplicadas());
	}

  public static class Palabra implements Comparable<Palabra>{

    private String palabra;
    private int ocurrencia;

    public Palabra(String palabra) {
      this.palabra = palabra;
      this.ocurrencia = 1;
    }

    public void incrementar() {
      this.ocurrencia++;
    }

    public String palabra() {
	    return this.palabra;
    }

    public int getOcurr() {
      return this.ocurrencia;
    }

    @Override
    public int compareTo(Palabra otra) {
      if(this.palabra.compareTo(otra.palabra) < 0) {
        return -1;
      } else if (this.palabra.compareTo(otra.palabra) > 0) {
        return 1;
      } else {
        return 0;
      }
    }

    @Override
    public String toString() {
	    return "{" + palabra + ", " + ocurrencia + "}";
    } 
  }
  
  public static class TablaHashAvl<T extends Comparable<T>> {

    private Avl<T>[] datos;
    private Function<T, Integer> funcionHash;
    private Consumer<T> aumentarOcurrencia;
    private int largo;

    public TablaHashAvl(Function<T, Integer> funcionHash, int largo,
        Consumer<T> aumentarOcurrencia) {
      this.datos = new Avl[largo];
      this.largo = largo; //This can be improved by making sure that the length is prime number.
      this.funcionHash = funcionHash;
      this.aumentarOcurrencia = aumentarOcurrencia;
    }

    public void insertar(T elemento) {
      int pos = Math.abs(funcionHash.apply(elemento) % largo);
      if (datos[pos] == null) {
        Avl<T> avl = new Avl<T>(this.aumentarOcurrencia);
        avl.setRaiz(avl.agregar(avl.getRaiz(), elemento));
        datos[pos] = avl;
      } else {
        Avl<T> nodo = datos[pos];
        nodo.setRaiz(nodo.agregar(nodo.getRaiz(), elemento));
        datos[pos] = nodo;
      }
    }

    public Avl<T> obtenerElemento(int indice) {
      return datos[indice];
    }

  }

  public static class Nodo<T extends Comparable<T>> {
    private T elemento;

    public Nodo(T elemento) {
      this.elemento = elemento;
    }

    public T get() {
      return elemento;
    }
  }

  public static class Lista<T extends Comparable<T>> extends Nodo<T> {

    private Lista<T> sig;

    public Lista(T elemento) {
      super(elemento);
      this.sig = null;
    }

    public Lista(T elemento, Lista<T> sig) {
      super(elemento);
      this.sig = sig;
    }

    public Lista<T> siguiente() {
      return this.sig;
    }


  }

  public static class NodoAvl<T extends Comparable<T>> extends Nodo<T>{

    private NodoAvl<T> izquierdo;
    private NodoAvl<T> derecho;
    private int altura;

    public NodoAvl(T elemento) {
      super(elemento);
      this.izquierdo = null;
      this.derecho = null;
      altura = 1;
    }

    public NodoAvl(T elemento, NodoAvl<T> izq, NodoAvl<T> der) {
      super(elemento);
      this.izquierdo = izquierdo;
      this.derecho = derecho;
    }

    public NodoAvl<T> getIzq() {
      return this.izquierdo;
    }

    public NodoAvl<T> getDer() {
      return this.derecho;
    }

    public int getAltura() {
      return this.altura;
    }

    public void setDer(NodoAvl<T> der) {
      this.derecho = der;
    }

    public void setIzq(NodoAvl<T> izq) {
      this.izquierdo = izq;
    }

    public void setAltura(int altura) {
      this.altura = altura;
    }
  }

  public static class Avl<T extends Comparable<T>> {

    private NodoAvl<T> raiz;
    private Consumer<T> casoRepetido;

    public Avl(Consumer<T> casoRepetido) {
      this.casoRepetido = casoRepetido;
    }

    public void setRaiz(NodoAvl<T> raiz) {
      this.raiz = raiz;
    }

    public NodoAvl<T> agregar(NodoAvl<T> nodo, T elemento) {
      if (nodo == null) {
        return new NodoAvl<T>(elemento);
      }
      switch(elemento.compareTo(nodo.get())) {
        case -1:
		nodo.setIzq(agregar(nodo.getIzq(), elemento));
		break;
        case 1:
		nodo.setDer(agregar(nodo.getDer(), elemento));
		break;
        case 0: 
          casoRepetido.accept(nodo.get());
          return nodo;
      }

      nodo.altura = 1 + max(altura(nodo.getIzq()), altura(nodo.getDer()));
      int balance = getBalance(nodo);

      if (balance > 1 && elemento.compareTo(nodo.getIzq().get()) == -1) {
        return rotarDerecho(nodo);
      }

      if (balance < -1 && elemento.compareTo(nodo.getDer().get()) == 1) {
        return rotarIzquierdo(nodo);
      }

      if (balance > 1 && elemento.compareTo(nodo.getIzq().get()) == 1) {
        nodo.setIzq(rotarIzquierdo(nodo.getIzq()));
        return rotarDerecho(nodo);
      }

      if (balance < -1 && elemento.compareTo(nodo.getDer().get()) == -1) {
        nodo.setDer(rotarDerecho(nodo.getDer()));
        return rotarIzquierdo(nodo);
      }
      return nodo;
    }

    public int altura(NodoAvl<T> nodo) {
      return nodo == null ? 0 : nodo.getAltura();
    }

    public int getBalance(NodoAvl<T> nodo) {
      return nodo.get() == null ? 0 : altura(nodo.getIzq()) - altura(nodo.getDer());
    }

    public NodoAvl<T> rotarDerecho(NodoAvl<T> nodo) {
      NodoAvl<T> aux = nodo.getIzq();
      NodoAvl<T> T2 = aux.getDer();

      aux.setDer(nodo);
      nodo.setIzq(T2);

      nodo.setAltura(max(altura(nodo.getIzq()), altura(nodo.getDer())) + 1);
      aux.setAltura(max(altura(aux.getIzq()), altura(aux.getDer())) + 1);

      return aux;
    }

    public NodoAvl<T> rotarIzquierdo(NodoAvl<T> nodo) {
      NodoAvl<T> aux = nodo.getDer();
      NodoAvl<T> T2 = aux.getIzq();

      aux.setIzq(nodo);
      nodo.setDer(T2);

      nodo.setAltura(max(altura(nodo.getIzq()), altura(nodo.getDer())) + 1);
      aux.setAltura(max(altura(aux.getIzq()), altura(aux.getDer())) + 1);

      return aux;
    }

    public static int  max(int x, int y) {
      return x > y ? x : y;
    }

    public NodoAvl<T> getRaiz() {
      return raiz;
    }

    public void print(NodoAvl<T> node) { 
	    if (node != null) {
		   System.out.println(node.get().toString());
		   print(node.getIzq());
		   print(node.getDer());
	   }
	}
  }
}
