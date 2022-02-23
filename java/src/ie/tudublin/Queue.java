package ie.tudublin;

public class Queue {
    class Node{
        int value;
        Node next;
        public Node(int x, Object z){
            value = x;
            next = (Node)z;
        }
    }

    Node z, head, tail, before_tail;
    public Queue(){
        z = new Node(0, z);
        z.next = z;
        head = z;
        before_tail = z;
        tail = null;
    } 
    public void enQueue(int x){
        Node t = new Node(x, z);
        if(head == z){
            head = t;
        }
        else{
            tail.next = t;
        }
        before_tail = tail;
        tail = t;
    }
    public int deQueue(){
        int value = head.value;
        head = head.next;
        if(head == null){
            tail = null;
        }
        return value;

    }
    public boolean isMember(int x){
        Node q = head;
        while(q.next != q){
            if(q.value == x){
                return true;
            }
            q = q.next;
        }
        return false;
    }
    public boolean isEmpty(){
        return head == head.next;
    }
    public int peak() throws Exception{
        if(isEmpty()){
            throw new Exception("Penis balls");
        }
            return head.value;
    }
}

