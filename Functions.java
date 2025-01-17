public class Functions {
    public static int[] MoveLeft(int zeroIndex, int[] unsolvedArrIn) {
        int[] unsolvedArrCopy = new int[Root.size * Root.size];
        System.arraycopy(unsolvedArrIn,0,unsolvedArrCopy,0,unsolvedArrIn.length);
        try {
            int pom1 = unsolvedArrCopy[zeroIndex-1];
            
            unsolvedArrCopy[zeroIndex] = pom1;
            unsolvedArrCopy[zeroIndex-1] = 0;
            return unsolvedArrCopy;
        }
        catch(Exception e) {}
        return null;
    }

    public static int[] MoveRight(int zeroIndex, int[] unsolvedArrIn) {
        int[] unsolvedArrCopy = new int[Root.size * Root.size];
        System.arraycopy(unsolvedArrIn,0,unsolvedArrCopy,0,unsolvedArrIn.length);
        try {
            int pom1 = unsolvedArrCopy[zeroIndex+1];
            
            unsolvedArrCopy[zeroIndex] = pom1;
            unsolvedArrCopy[zeroIndex+1] = 0;
            return unsolvedArrCopy;
        }
        catch(Exception e) {}
        return null;
    }

    public static int[] MoveUp(int zeroIndex, int[] unsolvedArrIn) {
        int[] unsolvedArrCopy = new int[Root.size * Root.size];
        System.arraycopy(unsolvedArrIn,0,unsolvedArrCopy,0,unsolvedArrIn.length);
        try {
            int pom1 = unsolvedArrCopy[zeroIndex-Root.size];
            
            unsolvedArrCopy[zeroIndex] = pom1;
            unsolvedArrCopy[zeroIndex-Root.size] = 0;
            return unsolvedArrCopy;
        }
        catch(Exception e) {}
        return null;
    }

    public static int[] MoveDown(int zeroIndex, int[] unsolvedArrIn) {
        int[] unsolvedArrCopy = new int[Root.size * Root.size];
        System.arraycopy(unsolvedArrIn,0,unsolvedArrCopy,0,unsolvedArrIn.length);
        try {
            int pom1 = unsolvedArrCopy[zeroIndex+Root.size];
            
            unsolvedArrCopy[zeroIndex] = pom1;
            unsolvedArrCopy[zeroIndex+Root.size] = 0;
            return unsolvedArrCopy;
        }
        catch(Exception e) {}
        return null;
    }

    public static int IsCloserToSolved(int[] unsolvedArr) {
        int value = 0;
        for(int i = 0; i < Root.size * Root.size; i++) {
            if(unsolvedArr[i] == Root.solvedArr[i]) {value++;}
        }
        return value;
    }
}
