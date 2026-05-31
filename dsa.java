class AVLNode {
    int key, height;
    AVLNode left, right;

    AVLNode(int key) {
        this.key = key;
        this.height = 1;
    }
}

public class PackageAVL {

    AVLNode root;

    int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(AVLNode node) {
        return (node == null) ? 0 :
                height(node.left) - height(node.right);
    }

    AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left),
                height(y.right)) + 1;
        x.height = Math.max(height(x.left),
                height(x.right)) + 1;

        return x;
    }

    AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left),
                height(x.right)) + 1;
        y.height = Math.max(height(y.left),
                height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, int key) {

        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(
                height(node.left),
                height(node.right));

        int balance = getBalance(node);

        // LL
        if (balance > 1 && key < node.left.key)
            return rotateRight(node);

        // RR
        if (balance < -1 && key > node.right.key)
            return rotateLeft(node);

        // LR
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    void inorder(AVLNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.println(node.key);
            inorder(node.right);
        }
    }

    void printTree(AVLNode root, int space) {

        if (root == null)
            return;

        space += 10;

        printTree(root.right, space);

        System.out.println();

        for (int i = 10; i < space; i++)
            System.out.print(" ");

        System.out.println(root.key);

        printTree(root.left, space);
    }

    public static void main(String[] args) {

        PackageAVL tree = new PackageAVL();

        int[] packageIds = {
            15, 25, 35, 45, 55, 65,
            75, 85, 95, 105, 115,
            125, 135
        };

        System.out.println(
            "AVL INSERTION (Package Indexing System)\n");

        System.out.println("Insertion Order:");

        for (int id : packageIds) {
            System.out.print(id + " ");
            tree.root = tree.insert(tree.root, id);
        }

        System.out.println("\n\nFINAL AVL TREE\n");

        tree.printTree(tree.root, 0);

        System.out.println(
            "\n\nSORTED PACKAGE IDS\n");

        tree.inorder(tree.root);

        System.out.println(
            "\nTime Complexity:");

        System.out.println(
            "AVL Insert/Search/Delete -> O(log n)");

        System.out.println(
            "Traversal -> O(n)");
    }
}