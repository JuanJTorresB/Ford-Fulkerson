import java.util.LinkedList;
import java.util.Queue;

public class FordFulkerson {

    //Breadth-First Search

    static boolean BFSearch(int[][] grafo, int source, int sink, int[] parent) {
        // El grafo es un array de arrays con este formato [from_vértice, to_vértice, capacidad]
        //      8
        // A ------ B        ----->  [A, B, 8]

        int vertices = grafo.length;

        // Una Array de booleanos en el que cada uno representa un vértice
        // Inicia todos en falso
        boolean[] visitados = new boolean[vertices];

        // Se crea una cola vacia
        Queue<Integer> cola = new LinkedList<>();

        // Agregamos el nodo de inicio a la cola
        cola.offer(source);
        // Su booleano pasa a verdadero, pues ya está siendo evaluado
        visitados[source] = true;
        // y al ser el nodo inicial no tiene un nodo padre, pues es el inicio del camino
        parent[source] = -1;

        while (!cola.isEmpty()) {
            int verticeActual = cola.poll();
            // Usamos poll no peak porque no puede ser null
            // Exploramos todos los vertices
            for (int posibleVecino = 1; posibleVecino < vertices; posibleVecino++) {
                // Visitar los vertices NO visitados y con capacidad de flujo
                if (!visitados[posibleVecino] && grafo[verticeActual][posibleVecino] > 0) {
                    // Lo marcamos como visitado
                    visitados[posibleVecino] = true;
                    parent[posibleVecino] = verticeActual;

                    // Se llegó al vertice de destino
                    if (posibleVecino == sink)
                        return true;

                    // En caso de no ser visitado el vertice destino
                    cola.offer(posibleVecino);
                }
            }
        }

        return false;
    }


    // aristas = [from, to, capacity]
    static int FordFulkersonAlgorithm(int NumeroVertices, int[][] aristas) {

        // Grafo de capacidad, se crea una matriz en la que la fila y columna de cero no se utiliza por eso + 1
        int[][] grafo = new int[NumeroVertices + 1][NumeroVertices + 1];

        for (int[] edge : aristas) {

            int fromVertice = edge[0];
            int toVertice = edge[1];
            int capacidad = edge[2];

            //Llenamos la matriz con las capacidades
            grafo[fromVertice][toVertice] = capacidad;
        }

        // El grafo residual empieza igual que la matriz de las capacidades
        int[][] grafoResidual = new int[NumeroVertices + 1][NumeroVertices + 1];

        for (int i = 0; i <= NumeroVertices; i++)
            grafoResidual[i] = grafo[i].clone();

        int src = 1;
        int sink = NumeroVertices;

        // Creamos la matriz con los padres
        int[] parent = new int[NumeroVertices + 1];
        int maxFlow = 0;

        // Se repite el bucle mientras encontremos camino que permitan aumentar el flujo (usando BFS)
        while (BFSearch(grafoResidual, src, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;

            // Encontrar la minima capacidad del camino actual recorriendo el camino de para atrás
            for (int vertice = sink; vertice != src; vertice = parent[vertice]) {

                int verticeActual = parent[vertice];
                // Se queda con la min capacidad del camino
                pathFlow = Math.min(pathFlow, grafoResidual[verticeActual][vertice]);
            }

            // Actualiza las capacidades de las aristas sumando capacidad a la reversa restando al camino normal
            for (int v = sink; v != src; v = parent[v]) {
                int u = parent[v];

                grafoResidual[u][v] -= pathFlow;
                grafoResidual[v][u] += pathFlow;
            }

            // Vamos acumulando el flujo
            maxFlow += pathFlow;
        }

        return maxFlow;
    }
}
