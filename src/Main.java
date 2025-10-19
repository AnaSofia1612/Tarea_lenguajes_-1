import java.util.*;
import java.io.*;

public class Main {

    // Leer siguiente línea no vacía
    private static String nextNonEmptyLine(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) return line;
        }
        return null;
    }

    public static boolean solve(BufferedReader reader) throws IOException {
        // Número de estados
        String numStatesStr = nextNonEmptyLine(reader);
        if (numStatesStr == null) return false;
        int numStates = Integer.parseInt(numStatesStr.trim());

        // Alfabeto
        String[] alphabet = nextNonEmptyLine(reader).trim().split("\\s+");

        // Estados finales
        String finalStatesStr = nextNonEmptyLine(reader).trim();
        Set<Integer> finalStates = new HashSet<>();
        if (!finalStatesStr.isEmpty()) {
            String[] parts = finalStatesStr.split("\\s+");
            for (String part : parts) {
                finalStates.add(Integer.parseInt(part));
            }
        }

        // Transiciones
        Map<Integer, Map<String, Integer>> transitions = new HashMap<>();
        for (int i = 0; i < numStates; i++) {
            String[] line = nextNonEmptyLine(reader).trim().split("\\s+");
            Map<String, Integer> stateTransitions = new HashMap<>();
            for (int j = 0; j < alphabet.length; j++) {
                stateTransitions.put(alphabet[j], Integer.parseInt(line[j]));
            }
            transitions.put(i, stateTransitions);
        }

        // --- Algoritmo de minimización (table-filling con dependencias) ---

        // Tabla de pares marcados
        boolean[][] marked = new boolean[numStates][numStates];

        // Dependencias: para cada par (p, q), lista de pares que dependen de él
        Map<String, List<String>> dependencies = new HashMap<>();

        Queue<String> queue = new LinkedList<>();

        // 1. Inicial: marcar pares (final, no final)
        for (int i = 0; i < numStates; i++) {
            for (int j = i + 1; j < numStates; j++) {
                boolean oneFinal = finalStates.contains(i) ^ finalStates.contains(j);
                if (oneFinal) {
                    marked[i][j] = true;
                    queue.add(i + "," + j);
                }
            }
        }

        // 2. Construir dependencias
        for (int i = 0; i < numStates; i++) {
            for (int j = i + 1; j < numStates; j++) {
                for (String symbol : alphabet) {
                    int ni = transitions.get(i).get(symbol);
                    int nj = transitions.get(j).get(symbol);
                    int a = Math.min(ni, nj);
                    int b = Math.max(ni, nj);
                    String key = a + "," + b;
                    dependencies.computeIfAbsent(key, k -> new ArrayList<>())
                            .add(i + "," + j);
                }
            }
        }

        // 3. Propagar marcas con BFS
        while (!queue.isEmpty()) {
            String pair = queue.poll();
            List<String> dependents = dependencies.get(pair);
            if (dependents != null) {
                for (String dep : dependents) {
                    String[] parts = dep.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    if (!marked[x][y]) {
                        marked[x][y] = true;
                        queue.add(dep);
                    }
                }
            }
        }

        // 4. Los pares NO marcados son equivalentes
        List<String> equivalentPairs = new ArrayList<>();
        for (int i = 0; i < numStates; i++) {
            for (int j = i + 1; j < numStates; j++) {
                if (!marked[i][j]) {
                    equivalentPairs.add("(" + i + ", " + j + ")");
                }
            }
        }

        // Ordenar e imprimir
        Collections.sort(equivalentPairs);
        System.out.println(String.join(" ", equivalentPairs));

        return true;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String numCasesStr = nextNonEmptyLine(reader);
            if (numCasesStr == null) return;

            int numCases = Integer.parseInt(numCasesStr.trim());

            for (int i = 0; i < numCases; i++) {
                if (!solve(reader)) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo input.txt: " + e.getMessage());
        }
    }
}

