package week03;

/**
 * @author : sh Lee
 * @date : 22. 12. 13.
 */

import java.util.*;

/**
 * 아이디어
 * 모든 컬럼의 조합을 만든다.인
 * 클래스를 만들어서 합쳐진 컬럼과, 몇개의 컬럼을 사용했는지 기록한다(나중에 최소성 확인하기 위해.)
 * 만들어진 조합으로 유일성을 확인한다.
 * 유일성의 경우, Set으로 체크해가면서 , 중복된 값이 없는 지 확인 하는 식으로 한다.
 * 유일성이 확인되면, 리스트에 담고, 최소성을 확인한다
 * 사용한 컬럼의 수로 내림차순 정렬을 하고, 2중 for문을 이용해서 포함관계가 존재하는지 확인해준다.
 * 컬럼의 경우, 알파벳 소문자와 숫자로만 이루어져있다고 되어 있으므로, &기호를 이용해서 컬럼을 연결해준다.( ex. 이름&전공)
 */
public class Programmers_후보키 {

    static List<Node> candidateKey;
    static Set<String> columnSet;

    //유일성을 확인하는 메서드
    static void uniqueCheck(String[][] relation, List<Integer> selectColumn){

        //중복확인.
        String tempColumn = "";
        for(int i = 0; i < selectColumn.size(); i++){
            tempColumn += selectColumn.get(i);

            //마지막 문자가 아니면 &를 붙여서 구분
            if(i != (selectColumn.size() - 1)){
                tempColumn += "&";
            }
        }
        if(columnSet.contains(tempColumn)) return;
        else columnSet.add(tempColumn);

        Set<String> rowCheck = new HashSet<>();

        for(String[] row : relation){

            String temp = "";
            for(int i = 0; i < selectColumn.size(); i++){
                temp += row[selectColumn.get(i)];

                //마지막 문자가 아니면 &를 붙여서 구분
                if(i != (selectColumn.size() - 1)){
                    temp += "&";
                }
            }

            //만들어진 문자가 set에 없으면 저장, 있으면 종료.
            if(rowCheck.contains(temp)) return;
            else{
                rowCheck.add(temp);
            }
        }

        //중간에 리턴되지 않았다면, 유일성을 만족한다는 뜻
        candidateKey.add(new Node(selectColumn.size(), tempColumn));
        return;
    }

    //최소성을 확인하는 메서드 - 주어진 후보키들의 최소성을 확인해서 만족하는 후보키 개수만 세어서 리턴.
    static int miniCheck(){
        candidateKey.sort(Comparator.naturalOrder());

        int count = 0; //가능한 키의 개수 세기.

        for(int i = 0; i < candidateKey.size(); i++){

            String stdKey = candidateKey.get(i).key;
            boolean check = false;
            for(int j = candidateKey.size()-1; j >= 0 ; j--){
                if(i != j){
                    String[] compareKey = candidateKey.get(j).key.split("&");

                    boolean check2 = false;


                    //기준키를 하나 뽑고, 나머지 키들과 포함관계를 확인해서 포함되는 것이 하나라도 존재하면 최소성을 만족하지 않음.

                    for(int k = 0; k < compareKey.length; k++){
                        //해당 컬럼이 기준이 되는 후보키에 포함이 안된다면, 종료
                        if(!stdKey.contains(compareKey[k])){
                            check2 = true;
                            break;
                        }
                    }
                    //탐색이 끝났는데 false라면 완전한 포함관계, 즉 최소성을 만족하지 못했다는 의미.
                    if(!check2) {
                        check = true;
                        break;
                    }
                }
            }

            if(!check) count++;
        }

        return count;
    }
    //컬럼을 조합하는 메서드
    static void recursive(String[][] relation, int idx, List<Integer> selectColumn){

        //1개 이상의 컬럼을 뽑았을 때만 유일성 확인.
        if(selectColumn.size() >= 1){
            uniqueCheck(relation,selectColumn);
        }


        if(idx >= relation[0].length){
            return;
        }

        //해당컬럼을 선택안하는 경우.
        recursive(relation, idx + 1, selectColumn);

        //해당 컬럼을 선택하는 경우.
        selectColumn.add(idx);
        recursive(relation, idx + 1, selectColumn);
        selectColumn.remove(selectColumn.size() - 1);

    }


   public int solution(String[][] relation) {

       candidateKey = new ArrayList<>(); //유일성을 통과한 후보키들을 저장.
       columnSet = new HashSet<>(); //중복 컬럼 제거를 위한 집합.
       recursive(relation, 0, new ArrayList<>());

        return miniCheck();
    }

    static class Node implements Comparable<Node>{
        int num; //몇개의 컬럼을 합쳤는지
        String key;// &기호를 이용해서 컬럼을 합쳐 둠.

        Node(int num, String key){
            this.num = num;
            this.key = key;
        }

        @Override
        public int compareTo(Node o) {
            return o.num - this.num;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "num=" + num +
                    ", key='" + key + '\'' +
                    '}';
        }
    }

}
