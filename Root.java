import java.util.Arrays;
import java.util.ArrayList;

public class Root {
    static int[] solvedArr =   {0 ,1 ,2 ,3,
                                4 ,5 ,6 ,7,
                                8 ,9 ,10,11,
                                12,13,14,15};

    int[] unsolvedArr ={1 ,0 ,2 ,3,
                        4 ,5 ,6 ,7,
                        8 ,9 ,10,11,
                        12,13,14,15};

    static int size = 4;
    static ArrayList<int[]> movesArr = new ArrayList<int[]>();

    public void allMoves() {
        if(!Root.solvedArr.equals(unsolvedArr)) {
            int zeroIndex = 0;
            for(int j = 0; j < (Root.size * Root.size); j++) {
                if(unsolvedArr[j] == 0) {
                    zeroIndex = j;
                    break;
                }
            }
            System.out.println(Functions.IsCloserToSolved(unsolvedArr));
            int[][] unsolvedArrMoved = new int[Root.size][];
            unsolvedArrMoved[0] = Functions.MoveLeft(zeroIndex, unsolvedArr);
            unsolvedArrMoved[1] = Functions.MoveRight(zeroIndex, unsolvedArr);
            unsolvedArrMoved[2] = Functions.MoveUp(zeroIndex, unsolvedArr);
            unsolvedArrMoved[3] = Functions.MoveDown(zeroIndex, unsolvedArr);

            String out = "";
            for(int i = 0; i < unsolvedArrMoved.length; i++) {
                if(unsolvedArrMoved[i] != null) {
                    for(int k = 0; k < Root.size*Root.size; k+=4) {
                        for(int l = 0; l < Root.size; l++) {
                            out = out + String.valueOf(unsolvedArrMoved[i][l+k]) + ", ";
                        }
                        out += "\n";
                    }
                    System.out.println(Functions.IsCloserToSolved(unsolvedArrMoved[i]));
                }
                else {out += "null\n";}
                out += "\n";
            }
            System.out.println(out);
        }
    }

    @Override
    public String toString() {
        return "" + Arrays.toString(unsolvedArr);
    }
}
