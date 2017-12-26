package basic.hash;

public class MyHashMap<K,V> implements Map<K, V> {
  static int defaultLength = 16;
  static double defaultLoader = 0.75d;
  Entry<K,V>[] table;
  int size;
  
  @Override
  public V put(K key, V value) {
    if(table==null){
      table = new Entry[defaultLength];
    }
    
    int index = getIndex(key);
    
    Node<K,V> node = (Node<K, V>) table[index];
    if(node==null){
      table[index] = new Node<K,V>(key,value,null);
    }else{
      table[index] = new Node<K,V>(key,value,node);
    }
    return null;
  }

  private int getIndex(K key) {
    int hashcode = key.hashCode();
    return hashcode%(table.length-1);
  }

  @Override
  public V get(K key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  }

  
  static class Node<K,V> implements Entry<K,V>{
    K key;
    V value;
    Node<K,V> next;
    
    public Node(K key, V value, Node<K, V> next) {
      super();
      this.key = key;
      this.value = value;
      this.next = next;
    }

    @Override
    public K getKey() {
      return this.key;
    }

    @Override
    public V getValue() {
      return this.value;
    }

    @Override
    public V setValue(V value) {
      V oldValue = this.value;
      this.value = value;
      return oldValue;
    }
    
  }
}
