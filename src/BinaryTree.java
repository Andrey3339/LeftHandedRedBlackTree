/**
 * Задание 7
 * 1. Реализуем структуру бинарного дерева.
 * 2. Для бинарного дерева характерно наличии двух потомков, где левый меньше родителя, а правый – больше.
 * 3. Для реализации можно использовать как и простое числовое дерево, так и обобщенный тип.
 * Учитывая, что мы строим именно бинарное дерево, то при использовании обобщенных типов убедитесь,
 * что значение поддерживает сравнение (интерфейс Comparable)

 * Задание 8
 * 1. Реализуем алгоритм поиска элементов по дереву (поиск в глубину).
 * 2. Для работы с бинарным деревом необходимо как минимум организовать метод поиска.
*/


public class BinaryTree {
    Node root;

    private class Node implements Comparable {
        public int value;
        public Node left;
        public Node right;

        public int compareTo(Object o) {
            Node node = (Node) o;
            return this.value - node.value;
        }
    }

    public boolean contain(int value) {
        if (root == null)
            return false;
        return contain(value, root);
    }

    private boolean contain(int value, Node node) {
        if (node.value == value)
            return true;
        if (value < node.value)
            return contain(value, node.left);
        return contain(value, node.right);
    }
}
