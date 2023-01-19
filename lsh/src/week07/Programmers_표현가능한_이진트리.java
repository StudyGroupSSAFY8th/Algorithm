package week07;

/**
 * 아이디어
 * 주어진 숫자를 이진수 문자열로 변환후, 반복문을 돌면서 완전 이진트리로 만든다.
 * 완전이진트리의 노드의 수는 2^(높이) - 1이다
 * 반복문으로 높이를 하나씩 높이면서, 노드의 수가 주어진 이진 문자열의 수와 같거나 커질때까지 돌린다. => 완전 이진트리의 노드수를 구하게 됨.
 * 구한 노드 개수에 맞추기 위해서, 주어진 문자열의 왼쪽에 0을 채워넣는다. 오른쪽에 채워넣게 되면, 값이 달라지기 때문에 왼쪽에 넣는다.
 * 만든후, 트리를 루트에서 부터 탐색했을때, 모든 노드를 전부 탐색할 수 있다면 제대로 된것
 */
public class Programmers_표현가능한_이진트리 {

    //long 타입의 숫자를 이진수 문자열로 변환.
    static String longToBinary(long number){

        return Long.toBinaryString(number);
    }

    //주어진 이진수를 완전이진트리로 만듦
    static String fullBinaryTree(String binaryNum){

        StringBuilder sb = new StringBuilder();

        int height = 1;

        //완전이진트리를 만들기 위해서 , 몇개의 노드가 필요한지 구하기
        while((int)Math.pow(2,height) - 1 < binaryNum.length()){
            height++;
        }

        //기존 문자열 앞에 0붙이기
        for(int i = 0; i < (int)Math.pow(2,height) - 1 - binaryNum.length(); i++){
            sb.append("0");
        }

        return sb.toString() + binaryNum;
    }

    //dfs로 탐색
    static boolean dfs(String binaryNum){

        int minIndex = (binaryNum.length() - 1)/2;

        char root = binaryNum.charAt(minIndex); //루트는 가운데 값
        String leftSubNum = binaryNum.substring(0,minIndex); // 왼쪽 문자열
        String rightSubNum = binaryNum.substring(minIndex+1, binaryNum.length()); //오른쪽 문자열

        //루트가 0인데 다음 루트가 1이면 트리 못만듦
        if(root == '0' && (leftSubNum.charAt((leftSubNum.length()-1)/2) == '1' || rightSubNum.charAt((rightSubNum.length()-1)/2) == '1')){
            return false;
        }

        boolean check = true;
        //서브 문자열의 길이가 3이상이면 재귀호출
        if(leftSubNum.length() >= 3){
            check = dfs(leftSubNum);

            //왼쪽 탐색을 하면서 문제가 없었다면 오른쪽도 탐색
            if(check) check = dfs(rightSubNum);
        }

        return check;
    }

    public int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];

        for(int i = 0; i < numbers.length; i++){

            String binaryNum = longToBinary(numbers[i]); //주어진 수를 이진수로 변환함.

            binaryNum = fullBinaryTree(binaryNum);//완전 이진트리로 바꿈

            if(dfs(binaryNum)) answer[i] = 1;
        }


        return answer;
    }
}