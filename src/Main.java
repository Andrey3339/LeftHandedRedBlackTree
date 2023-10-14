import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
//        Employee employee = new Employee("Andrey", 40, 120000.0);
//        Employee employee1 = new Employee("Ivan", 35, 100000.0);
//        HashTable<Integer, Employee> employeeIntegerHashTable = new HashTable<>();
//        employeeIntegerHashTable.add(0, employee);
//        employeeIntegerHashTable.add(1, employee1);
//        System.out.println("employeeIntegerHashTable.get(1) = " + employeeIntegerHashTable.get(1));
//        employeeIntegerHashTable.remove(1);
//        System.out.println("employeeIntegerHashTable.get(1) = " + employeeIntegerHashTable.get(1));
        final LeftHandedRedBlackTree tree = new LeftHandedRedBlackTree();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    int value = Integer.parseInt(reader.readLine());
                    tree.add(value);
                    System.out.println("finish");
                } catch (Exception ignored) {

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}