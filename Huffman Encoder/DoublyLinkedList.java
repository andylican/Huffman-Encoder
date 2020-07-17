/**
 * SimpleMap.java
 * Version 1
 * @author Andy Li
 * April 2020
 * Doubly linked list implementation 
 */
public class DoublyLinkedList<E extends Comparable<E>>{ 
    private Node<E> head;
    private Node<E> tail;
    private int size;
    
    //Constructor
    public DoublyLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
//------------------------------------------------------------------------------  
    //This method is for internal use only! This is why it is private.
    //Returns the node at the specified position in this list. 
    private Node<E> getNode(int index){
        Node<E> node = this.head;
        if(size-index < index) {
            node = this.tail;
            for(int i=size-1; i>index; i--) {
                node = node.getPrev();
            }
        } else {
            for (int i=0; i<index; i++){    //how can you get a node that is close to the tail?
                node = node.getNext();
            }
        }
        return node;
    }
//------------------------------------------------------------------------------      
    //Inserts the specified element at the beginning of this list.
    public void addFirst(E item){
        Node<E> first = this.head;
        Node<E> newNode = new Node<E>(null,item,first);
        this.head = newNode;
        if (first == null){             //if the first node was null the list was empty
            this.tail = newNode;
        }else {
            first.setPrev(newNode);
        }
        this.size = this.size + 1;
    }
    
    //Appends the specified element to the end of this list.
    public void addLast(E item){
        Node<E> last = this.tail;
        Node<E> newNode = new Node<E>(last,item,null);
        this.tail = newNode;
        if (last == null){              //if the last node was null the list was empty
            this.head = newNode;
        }else {
            last.setNext(newNode);
        }
        this.size = this.size + 1;
    }

    //Inserts the specified element at the specified position in this list.
    public void add(int index, E item){
        if (index == 0){this.addFirst(item);}
        else if (index == this.size){this.addLast(item);}
        else if (index > 0 && index < this.size){
            Node<E> nextNode = this.getNode(index);
            //how can you get the previous node without calling the getNode() method again?
            Node<E> prevNode = nextNode.getPrev();
            Node<E> newNode = new Node<E>(prevNode,item,nextNode);
            prevNode.setNext(newNode);
            nextNode.setPrev(newNode);
            this.size = this.size + 1;
        }
    }
//------------------------------------------------------------------------------      
    //Removes and returns the first element from this list.
    public E removeFirst(){
        if (this.head == null){return null;} //if the first node was null the list was empty
        else {
            Node<E> first = this.head;
            Node<E> second = first.getNext();
            if (second == null){             //if the second node was null the list had one element
                this.head = this.tail = null;
                this.size = 0;
                return first.getItem();
            }else {
                second.setPrev(null);
                this.head = second;
                this.size = this.size - 1;
                return first.getItem();
            }
        }
    }
    
    //Removes and returns the last element from this list.
    public E removeLast(){
        if (this.tail == null){return null;} //if the last node was null the list was empty
        else {
            Node<E> last = this.tail;
            Node<E> secondLast = last.getPrev();
            if (secondLast == null){         //if the second last node was null the list had one element
                this.head = this.tail = null;
                this.size = 0;
                return last.getItem();
            }else {
                secondLast.setNext(null);
                this.tail = secondLast;
                this.size = this.size - 1;
                return last.getItem();
            }
        }
    }    
//------------------------------------------------------------------------------                   
    //Returns the element at the specified position in this list.
    public E get(int index){
        return this.getNode(index).getItem();
    }    

    //Returns the index of the first occurrence of the specified element in this list,
    //or -1 if this list does not contain the element.    
    public int indexOf(E item){ 
        Node<E> front = head;
        Node<E> back = tail;
        for(int i=0; i<=size/2; i++) {
            if(front.getItem().equals(item)) {
                return i;
            } else if(back.getItem().equals(item)) {
                return size-i-1;
            }
            front = front.getNext();
            back = back.getPrev();
        }
        return -1;
    }    
    
    //Removes and returns the element at the specified position in this list.
    public E remove(int index){
        Node<E> node = getNode(index);
        if(node == null) return null;
        if(size==1) {
            clear();
            size++;
        } else if(index == 0) {
            Node<E> next = node.getNext();
            head =  next;
            next.setPrev(null);
            
        } else if (index == size-1) {
            Node<E> prev = node.getPrev();
            tail = prev;
            prev.setNext(null);
        } else {
            Node<E> next = node.getNext();
            Node<E> prev = node.getPrev();
            prev.setNext(next);
            next.setPrev(prev);
        } 
        size--;
        return node.getItem();
    }    
    
    //Removes the first occurrence of the specified element from this list, if it is present.    
    public boolean remove(E item){
        Node<E>node = head;
        for(int i=0; i<size; i++) {
            
            if(node.getItem().equals(item)) {
                if(i == 0) {
                    Node<E> next = node.getNext();
                    head =  next;
                    next.setPrev(null);
                    
                } else if (i == size-1) {
                    Node<E> prev = node.getPrev();
                    tail = prev;
                    prev.setNext(null);
                } else {
                    Node<E> next = node.getNext();
                    Node<E> prev = node.getPrev();
                    prev.setNext(next);
                    next.setPrev(prev);
                } 
                size--;
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    //Removes all of the elements from this list.
    public void clear(){ 
        this.head = null;
        this.tail = null;
        this.size = 0;               
    }

    //Returns the number of elements in this list.
    public int size(){ 
        return this.size;
    }
//------------------------------------------------------------------------------  
    //Returns String representation of the list.
    @Override
    public String toString(){
        if (this.head == null){return "";}
        Node<E> currentNode = this.head;
        String s = currentNode.getItem().toString();
        while(currentNode.getNext()!= null){ 
            currentNode = currentNode.getNext();
            s = s +", "+ currentNode.getItem().toString();
        }
        return "["+ s +"]";
    }
//------------------------------------------------------------------------------     
//  inner class Node  
//------------------------------------------------------------------------------         
    private class Node<T>{ 
        private Node<T> prev;        
        private T item;
        private Node<T> next;
        
        //Node constructor
        public Node(Node<T> prev, T item, Node<T> next){
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
        //getters and setters
        public Node<T> getNext(){
            return this.next;
        }
        public void setNext(Node<T> next){
            this.next = next;
        }
        public Node<T> getPrev(){
            return this.prev;
        }
        public void setPrev(Node<T> prev){
            this.prev = prev;
        }
        public T getItem(){
            return this.item;
        }
        public void setItem(T item){
            this.item = item;
        }
    } //end of Node class    
} //end of DoublyLinkedList class



