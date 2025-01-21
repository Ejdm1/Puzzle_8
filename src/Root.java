import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Root {
    public static int sizeOfMat = 3;
    // bottom, left, top, right
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
        
        node.value = Integer.MAX_VALUE;// set number of misplaced tiles
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

    public void printPath(Node root) {
        if(root == null) {
            return;
        }
        printPath(root.parent);
        nodes.add(root);
    }
    
    // Comparison object to be used to order the heap
    public class comparator implements Comparator<Node> {
        @Override
        public int compare(Node lhs, Node rhs) {
            return (lhs.value + lhs.layer) > (rhs.value+rhs.layer)?1:-1;
        }
    }

    public ArrayList<Node> solve(int unsolvedMat[][], int zero_x, int zero_y, int solvedMat[][])
    {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(new comparator());

        Node root = newNode(unsolvedMat, zero_x, zero_y, zero_x, zero_y, 0, null);
        root.value = getValue(unsolvedMat,solvedMat);
        
        priorityQueue.add(root);

        while(!priorityQueue.isEmpty()) {
            Node min = priorityQueue.peek();
            priorityQueue.poll();

            if(min.value == 0) {
                printPath(min);
                return nodes;
            }

            for (int i = 0; i < 4; i++) {
                if(min.layer >= 18) {
                    return null;
                }

                if (isInMat(min.zero_x + row[i], min.zero_y + col[i])) {
                    Node child = newNode(min.mat, min.zero_x, min.zero_y, min.zero_x + row[i],min.zero_y + col[i], min.layer + 1, min);
                    child.value = getValue(child.mat, solvedMat);

                    priorityQueue.add(child);
                }
            }
        }
        return null;
    }
}