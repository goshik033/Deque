/*
— ПРИНЦИП РАБОТЫ —
Решение реализованно с помощью кольцевого буфера
head - индекс первого элемента
tail - индекс последнего элемента
size - текущий размер

При выполнении операции pushBack индекс головы уменьшается на едеиницу, но так как буфер закольцован,
то после нуля последует индекс равный m (длине списка) -1, туда и записывается число.
Если size=0, то head не изменяется

При выполнении операции pushFront индекс хвоста увеличивается и туда записывается новое значение,
если индекс равняется длине списка, то он становится равным 0
Если size=0, то tail не изменяется

При выполнении операции popFront удаляется элемент под индексом tail,
после этого tail уменьшается

При выполнении операции popBack удаляется элемент под индексом head,
после этого head увеличивается

— ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ —
Двусторонняя очередь, элементы можно добавлять и удалять с обоих концов.
Благодаря тому, что буфер закольцован, хвост и голова при добавлении элементов движутся как бы навстречу
друг другу, а при удалении наоборот, также может использоваться как обычная очередь,
если не добавлять и не убирать элементы из головы.

Если дек заполнен при pushBack или pushFront (realSize == size), программа должна вернуть "error",
что и происходит.

Если дек пустой при popFront и popBack (realSize == 0), программа должна вернуть "error",
что и происходит.


— ВРЕМЕННАЯ СЛОЖНОСТЬ —
Вставка элемента в начало или конец дека (pushFront, pushBack): O(1)
Удаление элемента из начала или конца дека (popFront, popBack): O(1)
Обработка команд в количестве n займет O(n);

— ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ —
Пространственная сложность составляет O(n), где n - это размер массива, который выделяется при инициализации дека.

ID решения 89464617
*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.OptionalInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(bf.readLine());
        int m = Integer.parseInt(bf.readLine());
        Queue queue = new Queue(m);
        for (int i = 0; i < n; i++) {
            String str = bf.readLine();
            if (str.contains("push_back")) {
                OptionalInt result = queue.pushBack(Integer.parseInt(str.split(" ")[1]));
                if (result.isEmpty()) {
                    sb.append("error" + "\n");
                }
            } else if (str.contains("push_front")) {
                OptionalInt result = queue.pushFront(Integer.parseInt(str.split(" ")[1]));
                if (result.isEmpty()) {
                    sb.append("error" + "\n");
                }
            } else if (str.contains("pop_front")) {
                OptionalInt result = queue.popFront();
                if (result.isPresent()) {
                    sb.append(result.getAsInt() + "\n");

                } else {
                    sb.append("error" + "\n");

                }
            } else {
                OptionalInt popresult = queue.popBack();
                if (popresult.isPresent()) {
                    sb.append(popresult.getAsInt() + "\n");
                } else {
                    sb.append("error" + "\n");
                }
            }
        }
        System.out.println(sb);
    }

    public static class Queue {
        private int head = 0;
        private int tail = 0;
        private int realSize = 0;
        private int[] arr;
        private int size;

        public Queue(int size) {
            this.size = size;
            this.arr = new int[size];
        }

        public OptionalInt pushBack(int number) {
            if (realSize == size) {
                return OptionalInt.empty();
            } else {
                if (realSize == 0) {
                    tail = 0;
                    head = 0;
                } else {
                    head = (head - 1 + size) % size;
                }
                arr[head] = number;
                realSize += 1;
                return OptionalInt.of(number);
            }
        }

        public OptionalInt pushFront(int number) {
            if (realSize == size) {
                return OptionalInt.empty();
            } else {
                if (realSize == 0) {
                    tail = 0;
                    head = 0;
                } else {
                    tail = (tail + 1) % size;
                }
                arr[tail] = number;
                realSize += 1;
                return OptionalInt.of(number);
            }
        }

        public OptionalInt popFront() {
            if (realSize == 0) {
                return OptionalInt.empty();
            } else {
                int oldTail = tail;
                if (head == tail) {
                    tail = -1;
                    head = -1;
                } else {
                    tail = (tail - 1 + size) % size;
                }
                realSize -= 1;
                return OptionalInt.of(arr[oldTail]);
            }
        }

        public OptionalInt popBack() {
            if (realSize == 0) {
                return OptionalInt.empty();
            } else {
                int oldHead = head;
                if (head == tail) {
                    tail = -1;
                    head = -1;
                } else {
                    head = (head + 1) % size;
                }
                realSize -= 1;
                return OptionalInt.of(arr[oldHead]);
            }
        }
    }
}