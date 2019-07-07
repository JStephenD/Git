package PRELIM;

public class Node {
    private Object data;
    private Node next, prev;

    public Node(Node prev, Object data, Node next){
        this.prev = prev;
        this.data = data;
        this.next = next;
    }
    public Node(Object data, Node next){
        this(null, data, next);
    }
    public Node(Node prev, Object data){
        this(prev, data, null);
    }
    public Node(Object data){
        this(null, data, null);
    }

    public void setData(Object data){
        this.data = data;
    }
    public void setNext(Node next){
        this.next = next;
    }
    public void setPrev(Node prev){
        this.prev = prev;
    }

    public Object getData(){
        return this.data;
    }
    public Node getPrev(){
        return this.prev;
    }
    public Node getNext(){
        return this.next;
    }

    public void insertAfter(Object data){
        this.setNext(new Node(data, this.getNext()));
    }
    public boolean hasNext(){
        return this.next != null;
    }

    public static void main(String[] args) {
        Node node = new Node(1, new Node(2, new Node(3, new Node(4, new Node(5)))));

        Object data;
        int cnt = 0;
        while(node.hasNext()){
            node = node.getNext();
            cnt++;

            data = node.getData();
            if(data.equals(3))
                node.insertAfter(7);
            System.out.println(cnt+" : "+data);
        }
        System.out.println("hello");
    }
}
