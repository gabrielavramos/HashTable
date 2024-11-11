import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public abstract class HashTable {
    LinkedList<String>[] tabela;
    protected int collisions;
    protected int[] collisionsPerIndex;

    public HashTable(int tamanho) {
        this.tabela = new LinkedList[tamanho];
        this.collisions = 0;
        this.collisionsPerIndex = new int[tamanho];
        initTable();
    }

    public void initTable() {
        for (int i = 0; i < tabela.length; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    protected abstract int hashFunction(String key);

    public void insert(String str) {
        int value = hashFunction(str) % tabela.length;
        if (tabela[value].size() > 0) {
            collisions++;
            collisionsPerIndex[value]++;
        }
        tabela[value].add(str);
    }

    public int getCollisions() {
        return collisions;
    }

    public void readFileAndInsert(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    insert(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void printTable() {
        for (int i = 0; i < tabela.length; i++) {
            System.out.print("Índice " + i + ": ");
            if (tabela[i].isEmpty()) {
                System.out.println("vazio");
            } else {
                for (String str : tabela[i]) {
                    System.out.print(str + " -> ");
                }
                System.out.println("null");
            }
        }
    }

    public void analyzePerformance(String filePath) {
        collisions = 0;
        for (int i = 0; i < collisionsPerIndex.length; i++) {
            collisionsPerIndex[i] = 0;
        }

        long insertStartTime = System.nanoTime();
        readFileAndInsert(filePath);
        long insertEndTime = System.nanoTime();
        int insertTimeMicroseconds = (int) Math.round((insertEndTime - insertStartTime) / 1_000.0);

        long searchStartTime = System.nanoTime();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    search(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        long searchEndTime = System.nanoTime();
        int searchTimeMicroseconds = (int) Math.round((searchEndTime - searchStartTime) / 1_000.0);

        int totalKeys = 0;
        int usedIndices = 0;
        for (LinkedList<String> bucket : tabela) {
            int bucketSize = bucket.size();
            totalKeys += bucketSize;
            if (bucketSize > 0) {
                usedIndices++;
            }
        }
        double averageKeysPerIndex = (double) totalKeys / tabela.length;
        double usedIndexPercentage = (usedIndices * 100.0) / tabela.length;

        double averageCollisionsPerIndex = 0.0;
        int indicesWithCollisions = 0;
        for (int i = 0; i < collisionsPerIndex.length; i++) {
            if (collisionsPerIndex[i] > 0) {
                indicesWithCollisions++;
                averageCollisionsPerIndex += collisionsPerIndex[i];
            }
        }
        averageCollisionsPerIndex /= indicesWithCollisions;

        System.out.println("Desempenho para " + getClass().getSimpleName() + ":");
        System.out.printf("Tempo de busca total: %d microsegundos%n", searchTimeMicroseconds);
        System.out.printf("Total de chaves por índice (média): %.2f%n", averageKeysPerIndex);
        System.out.printf("Percentual de índices usados: %.2f%%%n", usedIndexPercentage);
        System.out.printf("Média de colisões por índice: %.2f%n", averageCollisionsPerIndex);
    }

    public boolean search(String str) {
        int value = hashFunction(str) % tabela.length;
        return tabela[value].contains(str);
    }

}
