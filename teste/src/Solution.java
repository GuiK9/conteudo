import java.util.ArrayList;
import java.util.Arrays;

public class Solution{

    public int findLongestChain(int[][] pairs) {

        ArrayList<Integer> arrayTemp = new ArrayList(3);

        Arrays.stream(pairs).forEach((pair) -> {
            int numtemp = pair[0] + pair[1];
            arrayTemp.add(numtemp);
        });

        for (int i = 0; i < arrayTemp.size(); i++) {
            if (arrayTemp.get(i) > arrayTemp.get(i + 1)){

            }
        }



        int ctrl = 0;

        for (int i = 0; i < pairs.length; i++) {
            int[] pair1 = pairs[i];
            if(i != pairs.length - 1){
                int[] pair2 = pairs[i + 1];
                if(pair1[1] <= pair2[0]) {
                    ctrl++;
                }
                System.out.println(ctrl);
            }
        }


        Arrays.stream(pairs)
                .flatMapToInt(row -> Arrays.stream(row))
                .forEach(element -> System.out.print(element + " "));
        return 3;
    }
}