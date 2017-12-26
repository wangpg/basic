package basic.hash;

public interface Map<K,V> {
    V put(K key,V value);
    V get(K key);
    int size();
    
    interface Entry<K,V>{
      K getKey();
      V getValue();
      V setValue(V value);
    }
}
