public class RotateMatrix {

    // n is number of row and column
    static final int N = 3;

    static void rotate(int[][] matrix) {
        System.out.println("After rotate: ");
        for (int i = 0; i < N; i++) {
            for (int j = N - 1; j >= 0; j--) {
                System.out.print(matrix[j][i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int matrix[][] = new int[N][N];
        System.out.println("Before rotate: ");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int num = (int) (Math.random() * 10);
                matrix[i][j] = num;
                System.out.print(num + " ");
            }
            System.out.println();
        }

        rotate(matrix);
    }
}
