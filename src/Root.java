import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Root {
    public static int sizeOfMat = 3;
    public static int row[] = { 1, 0, -1, 0 };
    public static int col[] = { 0, -1, 0, 1 };
    ArrayList<Node> nodes = new ArrayList<Node>();

    public void printMatrix(int mat[][]) {
        for(int i = 0; i < sizeOfMat; i++) {
            for(int j = 0; j < sizeOfMat; j++) {
                System.out.print(mat[i][j]+" ");
            }
            System.out.println("");
        }
    }

    public Node newNode(int mat[][], int zero_x, int zero_y, int new_zero_x, int new_zero_y, int layer, Node parent) {
        Node node = new Node();
        node.parent = parent;

        node.mat = new int[sizeOfMat][sizeOfMat];
        for(int i = 0; i < sizeOfMat; i++) {
            for(int j = 0; j < sizeOfMat; j++) {
                node.mat[i][j] = mat[i][j];
            }
        }

        int temp = node.mat[zero_x][zero_y];
        node.mat[zero_x][zero_y] = node.mat[new_zero_x][new_zero_y];
        node.mat[new_zero_x][new_zero_y]=temp;

        node.value = Integer.MAX_VALUE;
        node.layer = layer;

        node.zero_x = new_zero_x;
        node.zero_y = new_zero_y;

        return node;
    }

    public int getValue(int unsolvedMat[][], int solvedMat[][]) {
        int count = 0;
        for (int i = 0; i < sizeOfMat; i++) {
            for (int j = 0; j < sizeOfMat; j++) {
                if (unsolvedMat[i][j]!=0 && unsolvedMat[i][j] != solvedMat[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isInMat(int zero_x, int zero_y) {
        if(zero_x >= 0 && zero_x < sizeOfMat && zero_y >= 0 && zero_y < sizeOfMat) {return true;}
        return false;
    }

    public void printPath(Node solved) {
        if(solved == null) {
            return;
        }
        printPath(solved.parent);
        nodes.add(solved);
    }
    
    public class comparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return (node1.value + node1.layer) > (node2.value + node2.layer)?1:-1;
        }
    }

    public ArrayList<Node> solve(int unsolvedMat[][], int zero_x, int zero_y, int solvedMat[][])
    {
        int numOfNodes = 0;
        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(new comparator());

        Node root = newNode(unsolvedMat, zero_x, zero_y, zero_x, zero_y, 0, null);
        root.value = getValue(unsolvedMat,solvedMat);
        
        priorityQueue.add(root);

        while(!priorityQueue.isEmpty()) {
            Node min = priorityQueue.peek();
            priorityQueue.poll();

            if(min.value == 0) {
                printPath(min);
                System.out.println("Number of nodes generated: " + numOfNodes);
                return nodes;
            }

            System.out.println("On layer: " + min.layer);
            if(min.layer > 20) {
                return null;
            }
            if(numOfNodes > 3000000) {
                return null;
            }

            for (int i = 0; i < 4; i++) {
                if (isInMat(min.zero_x + row[i], min.zero_y + col[i])) {
                    numOfNodes++;
                    Node child = newNode(min.mat, min.zero_x, min.zero_y, min.zero_x + row[i],min.zero_y + col[i], min.layer + 1, min);
                    child.value = getValue(child.mat, solvedMat);

                    priorityQueue.add(child);
                }
            }
        }
        return null;
    }
}