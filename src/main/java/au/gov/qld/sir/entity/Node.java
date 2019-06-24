package au.gov.qld.sir.entity;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class Node<T> {

    private T data = null;

    private List<Node<T>> children  = new ArrayList<>();

    public Node(T data) {
        this.data = data;
    }

    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    private Node<T> parent;

}
