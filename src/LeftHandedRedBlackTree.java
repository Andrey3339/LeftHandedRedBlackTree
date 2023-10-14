/**
 * Домашнее задание:
 * Необходимо преобразовать собранное на семинаре дерево поиска в полноценное левостороннее красно-чёрное дерево.
 * Реализовать метод добавления новых элементов с балансировкой.
 * - Красно-чёрное дерево имеет следующие критерии:
 * - Каждая нода имеет цвет (красный или черный);
 * - Корень дерева всегда черный;
 * - Новая нода всегда красная;
 * - Красные ноды могут быть только левым дочерним элементом;
 * - У красной ноды все дочерние элементы черного цвета.
 * <p>
 * Соответственно, чтобы данные условия выполнялись, после добавления элемента в дерево необходимо произвести балансировку,
 * благодаря которой все критерии выше станут валидными.
 * Для балансировки существует 3 операции:
 * - левый малый поворот
 * - правый малый поворот
 * - смена цвета.
 * <p>
 * Критерии применения этих операций следующие:
 * - Если правый дочерний элемент красный, а левый черный, то применяем малый правый поворот
 * - Если левый дочерний элемент красный и его левый дочерний элемент тоже красный, то применяем малый левый поворот
 * - Если оба дочерних элемента красные, то делаем смену цвета
 * - Если корень стал красным, то перекрашиваем его в черный
 * <p>
 * Данная промежуточная аттестация оценивается по системе "зачет" / "незачет».
 * "Зачет" ставится, если слушатель успешно выполнил задание.
 * "Незачет" ставится, если слушатель не выполнил задание.
 * <p>
 * Критерии оценивания задания: Слушатель преобразовал собранное на семинаре дерево поиска в полноценное левостороннее
 * красно-черное дерево и реализовал в нём метод добавления новых элементов с балансировкой.
 */

public class LeftHandedRedBlackTree {
    private Node root;

    public boolean add(int value) {
        if (root != null) {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.BLACK;
            return result;
        } else {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }
    private boolean addNode(Node node, int value) {
        if (node.value == value) {
            return false;
        } else {
            if (node.value > value) {
                if (node.leftChild != null) {
                    boolean result = addNode(node.leftChild, value);
                    node.leftChild = rebalance(node.leftChild);
                    return result;
                } else {
                    node.leftChild = new Node();
                    node.leftChild.color = Color.RED;
                    node.leftChild.value = value;
                    return true;
                }
            } else {
                if (node.rightChild != null) {
                    boolean result = addNode(node.rightChild, value);
                    node.rightChild = rebalance(node.rightChild);
                    return result;
                } else {
                    node.rightChild = new Node();
                    node.rightChild.color = Color.RED;
                    node.rightChild.value = value;
                    return true;
                }
            }
        }
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.rightChild != null && result.rightChild.color == Color.RED &&
                    (result.leftChild == null || result.leftChild.color == Color.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.rightChild != null && result.rightChild.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
        }  while (needRebalance);
        return result;
    }

    private void colorSwap(Node node) {
        node.rightChild.color = Color.BLACK;
        node.leftChild.color = Color.BLACK;
        node.color = Color.RED;
    }

    private Node rightSwap(Node node) {
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    private Node leftSwap(Node node) {
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    private class Node implements Comparable {
        public int value;
        private Color color;
        public Node leftChild;
        public Node rightChild;

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
            return contain(value, node.leftChild);
        return contain(value, node.rightChild);
    }

    private enum Color {
        RED, BLACK
    }
}
