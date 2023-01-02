import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    
    private class Node {
        int id;
        int y;
        int x;
        Node left;
        Node right;

        public Node(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public void insert(Node node) {
            if (node.x < this.x) {
                if (this.left == null) {
                    this.left = node;
                } else this.left.insert(node);
            } else {
                if (this.right == null) {
                    this.right = node;
                } else this.right.insert(node);
            }
        }
    }

    private List<Integer> preorderList = new ArrayList<>();
    private List<Integer> postorderList = new ArrayList<>();

    public int[][] solution(int[][] nodeinfo) {
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> o2.y - o1.y);
        int id = 1;
        for (int[] info : nodeinfo) {
            pq.add(new Node(id++, info[0], info[1]));
        }

        Node root = pq.poll();

        while (!pq.isEmpty()) {
            root.insert(pq.poll());
        }

        preorder(root);
        postorder(root);

        int[] preorderResult = new int[nodeinfo.length];
        int[] postorderResult = new int[nodeinfo.length];
        for (int i = 0; i < nodeinfo.length; i++) {
            preorderResult[i] = preorderList.get(i);
            postorderResult[i] = postorderList.get(i);
        }

        return new int[][]{preorderResult, postorderResult};
    }

    private void preorder(Node node) {
        preorderList.add(node.id);
        if (node.left != null) preorder(node.left);
        if (node.right != null) preorder(node.right);
    }

    private void postorder(Node node) {
        if (node.left != null) postorder(node.left);
        if (node.right != null) postorder(node.right);
        postorderList.add(node.id);
    }
}