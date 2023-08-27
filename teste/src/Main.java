public class Main {
    public static void main(String[] args) {
        int[][] matriz = new int[3][2];

        matriz[0][0] = 1;
        matriz[0][1] = 2;
        matriz[1][0] = 2;
        matriz[1][1] = 3;
        matriz[2][0] = 3;
        matriz[2][1] = 4;

        Solution solution = new Solution();
        solution.findLongestChain(matriz);
    }

}

