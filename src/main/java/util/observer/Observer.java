package util.observer;

@FunctionalInterface
public interface Observer<T> {
    void update(T data);
}