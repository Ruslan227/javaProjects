### Домашнее задание 2. Очередь на массиве

1. Определите модель и найдите инвариант структуры данных «очередь». Определите функции, которые необходимы для реализации очереди. Найдите их пред- и постусловия, при условии что очередь не содержит null.
2.Реализуйте классы, представляющие циклическую очередь с применением массива.
    - Класс ArrayQueueModule должен реализовывать один экземпляр очереди с использованием переменных класса.
    - Класс ArrayQueueADT должен реализовывать очередь в виде абстрактного типа данных (с явной передачей ссылки на экземпляр очереди).
    - Класс ArrayQueue должен реализовывать очередь в виде класса (с неявной передачей ссылки на экземпляр очереди).
    - Должны быть реализованы следующие функции (процедуры) / методы:
        - enqueue – добавить элемент в очередь;
        - element – первый элемент в очереди;
        - dequeue – удалить и вернуть первый элемент в очереди;
        - size – текущий размер очереди;
        - isEmpty – является ли очередь пустой;
        - clear – удалить все элементы из очереди.
    - Инвариант, пред- и постусловия записываются в исходном коде в виде комментариев.
    - Обратите внимание на инкапсуляцию данных и кода во всех трех реализациях.
3. Напишите тесты к реализованным классам.