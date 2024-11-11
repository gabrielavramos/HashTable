import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hash1 hashTable = new Hash1();
        Hash2 hashTable2 = new Hash2();
        Scanner scanner = new Scanner(System.in);

        String filePath = "female_names.txt";
        int opcao;

        do {
            System.out.println("********************************");
            System.out.println("Escolha uma opção!!");
            System.out.println(" 1 - Inserir na tabela usando a Hash 1");
            System.out.println(" 2 - Inserir na tabela usando a Hash 2");
            System.out.println(" 0 - Sair");
            System.out.print("Escolha uma opção: ");
            System.out.println("---------------------------------------------------------");

            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                long startTime = System.nanoTime();
                hashTable.readFileAndInsert(filePath);
                long endTime = System.nanoTime();
                int elapsedMicroseconds = (int) Math.round((endTime - startTime) / 1_000.0);
                hashTable.printTable();
                System.out.println("");
                System.out.println("---------------------------------------------------------");
                System.out.println("*************** RESUMO ***************");
                System.out.printf("Tempo de inserção na Hash1: %d microsegundos%n", elapsedMicroseconds);
                System.out.println("Total de colisões na Hash1: " + hashTable.getCollisions());
                hashTable.analyzePerformance(filePath);
                System.out.println("---------------------------------------------------------");
                System.out.println("");

            } else if (opcao == 2) {
                long startTime = System.nanoTime();
                hashTable2.readFileAndInsert(filePath);
                long endTime = System.nanoTime();
                int elapsedMicroseconds = (int) Math.round((endTime - startTime) / 1_000.0);
                hashTable2.printTable();
                System.out.println("");
                System.out.println("---------------------------------------------------------");
                System.out.println("*************** RESUMO ***************");
                System.out.printf("Tempo de inserção na Hash2: %d microsegundos%n", elapsedMicroseconds);
                System.out.println("Total de colisões na Hash2: " + hashTable2.getCollisions());
                hashTable2.analyzePerformance(filePath);
                System.out.println("---------------------------------------------------------");
                System.out.println("");

            } else if (opcao != 0) {
                System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
        } while (opcao != 0);

        System.out.println("Programa encerrado.");
        scanner.close();
    }
}
