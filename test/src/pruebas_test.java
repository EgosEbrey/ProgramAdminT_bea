import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class pruebas_test {
    public static void main(String[] args) {
        // Crear una lista de cadenas
        List<String> lista = new ArrayList<>();

        // Añadir elementos a la lista
        lista.add("Elemento 1");
        lista.add("Elemento 2");
        lista.add("Elemento 3");

        // Acceder y mostrar un elemento específico
        String primerElemento = lista.get(0);
        System.out.println("Primer elemento: " + primerElemento);

        // Iterar sobre la lista usando un bucle for mejorado
        System.out.println("Elementos de la lista:");
        for (String elemento : lista) {
            System.out.println(elemento);
        }

        // Iterar sobre la lista usando un iterador
        System.out.println("Elementos de la lista usando un iterador:");
        Iterator<String> iterador = lista.iterator();
        while (iterador.hasNext()) {
            String elemento = iterador.next();
            System.out.println(elemento);
        }

        // Mostrar el tamaño de la lista
        System.out.println("Tamaño de la lista: " + lista.size());

        // Eliminar un elemento de la lista
        lista.remove(1); // Eliminar el segundo elemento

        // Mostrar el tamaño de la lista después de la eliminación
        System.out.println("Tamaño de la lista después de la eliminación: " + lista.size());

        // Verificar si la lista está vacía
        System.out.println("¿La lista está vacía? " + lista.isEmpty());

        // Limpiar la lista
        lista.clear();

        // Verificar si la lista está vacía después de limpiar
        System.out.println("¿La lista está vacía después de limpiar? " + lista.isEmpty());
    }
}

