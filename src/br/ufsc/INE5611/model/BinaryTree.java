package br.ufsc.INE5611.model;

public class BinaryTree{
    private Node root;
    private int nodesNumber;

    public BinaryTree(){
        root = new Node(1);
        nodesNumber = 19;
    }

    public boolean IsEmpty() {
        return(nodesNumber == 0);
    }

    public Node getRoot() {
        return root;
    }

    public int count() {
        return nodesNumber;
    }

    public int size(Node ATree) {
        if (ATree == null)
            return 0;
        else
            return(1 + size(ATree.getLeftNode()) + size(ATree.getRightNode()));
    }

    public int height(Node ATree) {
        if (ATree == null)
            return 0;
        else
            return (1 + Math.max(height(ATree.getLeftNode()), height(ATree.getRightNode())));
    }

    public void preOrder(Node ATree) {
        if (ATree != null)
        {
            System.out.println(ATree.getNumber());
            preOrder(ATree.getLeftNode());
            preOrder(ATree.getRightNode());
        }
    }

    public void inOrder(Node ATree) {
        if (ATree != null)
        {
            inOrder(ATree.getLeftNode());
            System.out.println(ATree.getNumber());
            inOrder(ATree.getRightNode());
        }
    }

    public void postOrder(Node ATree) {
        if (ATree != null)
        {
            postOrder(ATree.getLeftNode());
            postOrder(ATree.getRightNode());
            System.out.println(ATree.getNumber());
        }
    }

    public void insert(int nodeNumber) {
        Node Temp, Current, Parent;
        if (root == null) { //tree is empty
            Temp = new Node(nodeNumber);
            root = Temp;
            nodesNumber++;
        } else { //tree is not empty
            Temp = new Node(nodeNumber);
            Current = root;
            while (true) { //never ending while loop
                Parent = Current;
                if (nodeNumber < Current.getNumber()) { //go left
                    Current = Current.getLeftNode();
                    if (Current == null) {
                        Parent.setLeftNode(Temp);
                        nodesNumber++;
                        return;//jump out of loop
                    }
                } else { //go right
                    Current = Current.getRightNode();
                    if (Current == null) {
                        Parent.setRightNode(Temp);
                        nodesNumber++;
                        return;
                    }
                }
            }
        }
    }

    public Node find(int nodeNumber) {
        Node Current = null;
        if(!IsEmpty()) {
            Current = root; //start search at top of tree
            while(Current.getNumber() != nodeNumber) {
                if(nodeNumber < Current.getNumber())
                    Current = Current.getLeftNode();
                else
                    Current = Current.getRightNode();
                if(Current == null)
                    return null;
            }
        }
        return Current;
    }

    public Node getSuccessor(Node ANode) {
        Node Current,Successor,SuccessorParent;
        Successor = ANode;
        SuccessorParent = ANode;
        Current = ANode.getRightNode();
        while(Current !=null) {
            SuccessorParent = Successor;
            Successor = Current;
            Current = Current.getLeftNode();
        }
        if(Successor != ANode.getRightNode()) {
            SuccessorParent.setLeftNode(Successor.getRightNode());
            Successor.setRightNode(ANode.getRightNode());
        }
        return Successor;
    }

}
