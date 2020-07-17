/**
 * SimpleMap.java
 * Version 1
 * @author Andy Li
 * April 2020
 * Simple Map implementation with linked list
 */
class SimpleMap <K extends Comparable<K>,V extends Comparable<V>>{
    private Node<K,V> head;
    private Node<K,V> tail;
    private int size;
    
    SimpleMap(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
//------------------------------------------------------------------------------  
     private Node<K,V> getNode(int index){
        Node<K,V> node = this.head; 
        for (int i=0; i<index; i++){  
            node = node.getNext();
        }
        return node;
     }
    public boolean containsKey(K key) {
        Node<K,V> node = this.head;
        if(node == null) return false;
        if(node.getKey().equals(key)) return true;
        while(node.getNext() != null) {
            node = node.getNext();
            if(node.getKey().equals(key)) return true;
        }
        return false;
    }
    public void put(K key,V value){
        if(containsKey(key)) {
            Node<K,V> node = this.head; 
            while(node != null) {
                if(node.getKey().equals(key)) {
                    node.setValue(value);
                }
                node = node.getNext();
            }
        } else {
            Node<K,V> node = new Node(key,value,null);
            if(head == null) {
            this.head = node;
            this.tail = node;
            } else {
                this.tail.setNext(node);
                this.tail =  node;
            }
            size++;
        } 
    }
    
    public V get(K key){
       Node<K,V> node = this.head;
        if(node == null) return null;
        if(node.getKey().equals(key)) return node.getValue();
        while(node.getNext() != null) {
            node = node.getNext();
            if(node.getKey().equals(key)) return node.getValue();
        }
        return null;
    }
    public K getKey(int index) {
        //Not a feature in java map
        return getNode(index).getKey();
    }
    public int size(){ 
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }
   
//------------------------------------------------------------------------------  
    @Override
    public String toString(){
        if (this.head == null){return "";}
        Node<K,V> currentNode = this.head;
        String s = currentNode.getKey().toString()+" "+currentNode.getValue();
        while(currentNode.getNext()!=null){ 
            currentNode = currentNode.getNext();
            s = s +", "+ currentNode.getKey().toString()+" "+currentNode.getValue().toString();
        }
        return "["+ s +"]";
    }
//------------------------------------------------------------------------------    
    private class Node<K,V>{ 
        private K key;
        private V value;
        private Node<K,V> next;

        public Node(K key,V value, Node<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    
        public Node<K,V> getNext(){
            return this.next;
        }
        public void setNext(Node<K,V> next){
            this.next = next;
        }
        public K getKey(){
            return this.key;
        }
        public V getValue() {
            return this.value;
        }
        public void setValue(V value){
            this.value = value;
        }
    }    
}