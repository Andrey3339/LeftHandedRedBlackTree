import java.lang.reflect.Array;

/*
   Задание 1
1. Начинаем реализацию хэш-таблицы с подготовки структуры и необходимых классов.
2. Давайте напишем реализацию односвязного списка, в котором мы и будем хранить пары ключ-значение.
3. Стоит обратить внимание, что можно использовать как дженерики, для обобщения
   возможных типов ключей и значений, так и заранее определить для себя конкретные типы, которые будут использоваться
   в качестве ключа и значения. Оба подхода допустимы для реализации.

   Задание 2
1. Добавляем массив связных списков с фиксированным размером (массив бакетов), либо передаваемым в конструкторе.
2. Хэш-таблица оперирует индексами, потому массив будет идеальным вариантов для представления бакетов.
3. Также реализуем метод вычисления индекса на основании хэш-кода ключа.

   Задание 3
1. Реализуем метод поиска данных по ключу в хэш-таблице.
2. Теперь, когда у нас есть базовая структура нашей хэш-таблицы, можно написать алгоритм поиска элементов, включающий
   в себя поиск нужного бакета и поиск по бакету

   Задание 4
1. Необходимо реализовать методы добавления элементов в связный список, если там еще нет пары с аналогичным ключом и
   удаления элемента с аналогичным ключом из списка.
2. Все значения ключей в хэш-таблице уникальны, а значит и в каждом из связных список это правило будет также выполняться.

   Задание 5
1. Реализуем алгоритм добавления и удаления элементов из хэш-таблицы по ключу.

   Задание 6
1. Добавляем информацию о размере хэш-таблицы, а также алгоритм увеличения количества бакетов при достижении количества
   элементов до определенного размера относительно количества бакетов (load factor).
2. Чтобы хэш-таблица сохраняла сложность поиска близкой к O(1), нам необходимо контролировать количество бакетов,
   чтобы в них не скапливалось слишком много элементов, которые способны увеличить длительность операции поиска и добавления.
3. В Java load factor для хэш-таблицы – 0.75, что значит, что при достижении количества значений 75% от общего количества
   бакетов – это количество необходимо увеличить. Это позволяет минимизировать шансы, что в бакетах будет больше 1-2 значений,
   а значит сохранит скорость поиска на уровне сложности O(1).



*/
public class HashTable<K, V> {
    private static final int INT_BASKET_COUNT = 16;
    public static final double LOAD_FACTOR = 0.75;
    private Basket[] baskets;
    private int size = 0;

    public HashTable(int initSize) {
        this.baskets = (Basket[]) Array.newInstance(Basket.class, initSize);
    }

    public HashTable() {
        this(INT_BASKET_COUNT);
    }

    private void recalculate() {
        Basket[] old = baskets;
        baskets = (Basket[]) new Object[old.length * 2];
        for (int i = 0; i < old.length; i++) {
            Basket basket = old[i];
            Basket.Node node = basket.head;
            while (node != null) {
                add(node.value.key, node.value.value);
                node = node.next;
            }
            old[i] = null;
        }
    }

    // метод вычисления индекса на основании хэш-кода ключа
    private int calculateBasketIndex(K key) {
        return key.hashCode() % baskets.length;
    }

    public V get(K key) {
        int index = calculateBasketIndex(key);
        Basket basket = baskets[index];
        if (basket != null) {
            return basket.get(key);
        }
        return null;
    }

    public boolean add(K key, V value) {
        if (baskets.length * LOAD_FACTOR < size) {
            recalculate();
        }
        int index = calculateBasketIndex(key);
        Entity entity = new Entity(key, value);
        Basket basket = baskets[index];
        if (basket == null) {
            basket = new Basket();
            baskets[index] = basket;
        }
        boolean add = basket.add(entity);
        if (add) {
            size++;
        }
        return add;
    }

    public boolean remove(K key) {
        int index = calculateBasketIndex(key);
        Basket basket = baskets[index];
        if (basket == null) {
            return false;
        }
        boolean remove = basket.remove(key);
        if (remove) {
            size--;
        }
        return remove;
    }

    private class Entity {
        private K key;
        private V value;

        public Entity(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class Basket {
        private Node head;

        public V get(K key) {
            Node current = head;
            while (current != null) {
                if (current.value.key.equals(key)) {
                    return current.value.value;
                }
                current = current.next;
            }
            return null;
        }

        public boolean remove(K key) {
            if (head != null) {
                Entity entity = head.value;
                if (entity.key.equals(key))
                    head = head.next;
                else {
                    Node node = head;
                    while (node.next != null) {
                        Entity entityNext = node.next.value;
                        if (entityNext.key.equals(key)) {
                            node.next = node.next.next;
                            return true;
                        }
                        node = node.next;
                    }
                }
            }
            return false;
        }

        public boolean add(Entity entity) {
            Node node = new Node();
            node.value = entity;
            if (head != null) {
                Node current = head;
                while (current != null) {
                    if (current.value.key.equals(entity.key)) {
                        return false;
                    }
                    if (current.next == null) {
                        current.next = node;
                        return true;
                    } else {
                        current = current.next;
                    }
                }
            }
            head = node;
            return true;
        }

        private class Node {
            private Node next;
            private Entity value;
        }
    }
}
