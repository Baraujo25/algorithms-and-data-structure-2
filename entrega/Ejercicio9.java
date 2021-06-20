import java.util.*;

public class Ejercicio9 {

    public static void main(String[] args) {
	Scanner out = new Scanner(System.in);
	int cantidadElementos = out.nextInt();

	int[] arr = initArray(out, cantidadElementos);
	int sinPareja = buscarPareja(arr, 0, cantidadElementos - 1);
	System.out.println(sinPareja);
    }

    public static Boolean sonDistintos(int a, int b) {
        return (b != a);
    }

    public static int buscarPareja(int[] arr, int inicio, int fin) {

        if(inicio == fin) {
            return arr[inicio];
        }
        // en cantidad de elementos siempre es impar
        int medio = ((fin + inicio) / 2);
        boolean medioesPar = medio % 2 == 0;
        // caso base, el sinPareja es el del medio, encontré el elemento en el medio
        if (sonDistintos(arr[medio], arr[medio - 1]) && sonDistintos(arr[medio], arr[medio + 1])) {
            return arr[medio];
        }

        if (medioesPar) {
            if (arr[medio] == arr[medio + 1]) {
                return buscarPareja(arr, medio + 1, fin);
            } else {
                return buscarPareja(arr, inicio, medio);
            }
        } else {
            if (arr[medio] == arr[medio - 1]) {
                return buscarPareja(arr, medio + 1, fin);
            } else {
                return buscarPareja(arr, inicio, medio);
            }
        }
    }

    public static int[] initArray(Scanner out, int largo) {
        int[] arrayEnteros = new int[largo];
        for (int i = 0; i < largo; i++) {
            arrayEnteros[i] = out.nextInt();
        }
	out.close();
        return arrayEnteros;
    }
}
