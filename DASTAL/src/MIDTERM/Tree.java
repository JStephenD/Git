package MIDTERM;

public class Tree<T> {
    private Tree parent, left, right;
    private T data;
    private int level, size;

    public Tree(Tree parent, Tree left, Tree right, T data){
        this.parent=parent;
        this.left=left;
        this.right=right;
        this.data=data;
    }
    public Tree(Tree parent, Tree left, Tree right){
        this(parent,left,right,null);
    }
    public Tree(T data){
        this(null,null,null,data);
    }
    public Tree(){
        this(null,null,null, null);
    }

    public void setData(T data){ this.data = data;}
    public T getData(){ return data;}
    public Tree getParent(){ return parent;}
    public Tree getLeft(){ return left;}
    public Tree getRight(){ return right;}

    public void setLeft(Tree left){
        this.left=left;
    }
    public void setRight(Tree right){
        this.right=right;
    }

    public static void main(String[] args) {
        Tree<Integer> root = new Tree<>(25);
        System.out.println(root.getData());
    }
}
