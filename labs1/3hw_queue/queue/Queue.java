package queue;

// Inv:  size >= 0 && a[1..n] != null;
public interface Queue {

    // Pre: none
    // post: ℝ = n' && n = n' && ∀ i = 1..n : a[i] = a[i]'
    int size();

    // Pre: none
    // Post: ℝ = (n' == 0) && n = n' && ∀ i = 1..n : a[i] = a[i]'
    boolean isEmpty();

    // pre: element ≠ null
    // post: n = n' + 1 && ∀ i = 1..n' : a[i] = a[i]' && a[n] = element
    void enqueue(Object element);

    // pre: n > 0
    // post: ℝ = a[first] && first = first' && ∀ i = 1..n : a[i] = a[i]'
    Object element();

    // pre: n > 0
    // post: ℝ = a[first] && n' = n − 1 ∧ ∀ i = 1..n : a[i] = a[i + 1]'
    Object dequeue();

    // Pre: none
    // Post: ℝ = Object[n'] && n = n' && ∀ i = 1..n : a[i] = a[i]' && ℝ[i] = a[i]'
    Object[] toArray();

    // Pre: none
    // Post: ℝ = "[" a[1] + "," + a[2]+ "," + ... + "," + a[n] + "]"
    // && n = n' && ∀ i = 1..n : a[i] = a[i]'
    String toStr();

    // Pre: None
    // Post: n = 0;
    void clear();
}