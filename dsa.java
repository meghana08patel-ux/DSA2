import java.util.*;

class Node {
    int score;
    int height;
    int size;
    Node left, right;

    Node(int score) {
        this.score = score;
        this.height = 1;
        this.size = 1;
    }
}

public class StreamFlixRankingSystem {

    static int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    static int size(Node n) {
        return (n == null) ? 0 : n.size;
    }

    static void update(Node n) {
        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
            n.size = 1 + size(n.left) + size(n.right);
        }
    }

    static int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    static Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        update(y);
        update(x);

        return x;
    }

    static Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        update(x);
        update(y);

        return y;
    }

    static Node insert(Node node, int score) {

        if (node == null) {
            System.out.println("Inserted Score: " + score);
            return new Node(score);
        }

        if (score < node.score)
            node.left = insert(node.left, score);
        else if (score > node.score)
            node.right = insert(node.right, score);
        else
            return node;

        update(node);

        int balance = getBalance(node);

        // LL
        if (balance > 1 && score < node.left.score)
            return rightRotate(node);

        // RR
        if (balance < -1 && score > node.right.score)
            return leftRotate(node);

        // LR
        if (balance > 1 && score > node.left.score) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && score < node.right.score) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    static Node minValue(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    static Node delete(Node root, int score) {

        if (root == null)
            return null;

        if (score < root.score)
            root.left = delete(root.left, score);
        else if (score > root.score)
            root.right = delete(root.right, score);
        else {

            if (root.left == null)
                return root.right;

            if (root.right == null)
                return root.left;

            Node temp = minValue(root.right);
            root.score = temp.score;
            root.right = delete(root.right, temp.score);
        }

        update(root);

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Rank in descending order
    static int rank(Node root, int score) {

        if (root == null)
            return 0;

        if (score > root.score) {
            return rank(root.right, score);
        }

        if (score < root.score) {
            return size(root.right) + 1 + rank(root.left, score);
        }

        return size(root.right) + 1;
    }

    static void reverseInorder(Node root, List<Integer> list) {
        if (root == null)
            return;

        reverseInorder(root.right, list);
        list.add(root.score);
        reverseInorder(root.left, list);
    }

    public static void main(String[] args) {

        Node root = null;

        int[] scores = {
    101, 205, 150, 320, 180,
    270, 400, 125, 350, 220
};

        System.out.println("=========================================");
        System.out.println(" STREAMFLIX TRENDING CONTENT SYSTEM");
        System.out.println("=========================================\n");

        for (int score : scores)
            root = insert(root, score);

        System.out.println("\nAVL Tree constructed successfully.");

        int target = 220;

        System.out.println("\nRank of content with score "
                + target + " = "
                + rank(root, target));

        System.out.println("\nUpdating Scores:");

       System.out.println("205 -> 210");
root = delete(root, 205);
root = insert(root, 210);

System.out.println("320 -> 325");
root = delete(root, 320);
root = insert(root, 325);

        List<Integer> top = new ArrayList<>();
        reverseInorder(root, top);

        System.out.println("\nTop Trending Content:");

        for (int i = 0; i < Math.min(5, top.size()); i++) {
            System.out.println(top.get(i));
        }

        System.out.println("\nTree Height = " + height(root));

       

        System.out.println("\nSystem Status: Operational");
    }
}
