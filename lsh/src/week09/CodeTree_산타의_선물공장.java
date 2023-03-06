package week09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.*;

/**
 * 아이디어
 * 1. 먼저 공장안에 컨베이어 벨트를 직접만든 LinkedList로 둔다.
 * 2. 물건의 위치를 저장할 map을 만든다, 데이터가 많으면 물건을 찾을때 순차탐색하기보단 물건위치(노드)를 해쉬맵으로 저장해두면 탐색에 빠르다.
 * 3. 물건을 하차할때는 맨 앞에서 하나를 빼주면 된다.
 * 4. 제거시에는 맵으로 특정위치를 참고해서 해당 위치를 제거해주면 된다.
 * 5. 고장시에는 고장나지 않은 최초의 벨트를 찾아서 뒤에 이어붙여주면 된다.
 */

/**
 * 후기
 * 데이터 수가 워낙 많기 때문에 무조건 링크드리스트를 커스텀해서 구현해야 한다.
 * 특히 중요한게 NPE가 안나도록 확인하는 것이 중요하다
 * 또한 문제를 정말 잘읽어야 하는게, 벨트 고장을 보면, 1번쨰 벨트부터 탐색하면서 고장안난 최초의 벨트를 찾는게 아닌,
 * 고장난 벨트의 다음번째 벨트부터 확인을 해야 한다.
 * 시간이 좀 더 걸리더라도 문제를 잘 읽고 모든 조건을 빼먹지말고 설계 부터 잘해야 한다.
 * 설계를 제대로 하고 들어가지 않으면 코드 짜다 바꾸고, 짜다 바꾸고 하는 경우가 많아진다.
 * 필자의 경우에도, 리스트 커스텀이니 무지성으로 중간 값 추가같은거를 만들다가 문제를 보니 필요 없어서 삭제하기도 했고,
 * 노드를 이동시키거나 추가할때, 해당 노드뒤로 연결된 노드가 있을거라는 것을 간과해서 그부분을 다시 짜기도 했다
 * 먼저 설계를 확실하게 가져가자.
 */

public class CodeTree_산타의_선물공장 {

    static class Node{

        int weight; //무게
        String id; //식별자

        int beltNum;//벨트 번호 - 물건확인 시에 필요.

        Node left,right;

        Node(int weight, String id, int beltNum){
            this.weight = weight;
            this.id = id;
            this.beltNum = beltNum;
        }
    }

    static class CustomLinkedList{
        Node head, tail; // 시작노드와 끝 노드 저장.
        int beltNum;

        CustomLinkedList(int beltNum){
            this.head = null;
            this.tail = null;
            this.beltNum = beltNum;
        }

        //노드 추가 - 맨뒤에
        void addNodeLast(Node node){

            if(this.head == null){
                this.head = node;

                while(node.right != null){
                    node.beltNum = this.beltNum;
                    node = node.right;
                }

                node.beltNum =  this.beltNum;
                this.tail = node;
            }
            else{
                this.tail.right = node;
                node.left = this.tail;

                while(node.right != null){
//                    System.out.println(node.id);
                    node.beltNum = this.beltNum;
                    node = node.right;
                }

                node.beltNum =  this.beltNum;
                this.tail = node;

//                System.out.println("test12314");
            }
        }

        //노드 이동 - 맨앞으로
        void moveNode(Node node){

            //해당 노드가 맨 앞이 아니면 이동.
            if(node.left != null){

                //이전의 tail
                Node preTail = this.tail;

                //이전의 head
                Node preHead = this.head;

                //새로운 tail
                this.tail = node.left;
                this.tail.right = null;

                //새로운 head
                this.head = node;
                this.head.left = null;

                //이전의 tail은 이전의 head 앞에 와야 된다.
                preTail.right = preHead;
                preHead.left = preTail;

            }
        }

        //노드 삭제
        void removeNode(Node currentNode){

            //현재 노드의 앞에 있는 노드와 현재노드의 뒤에 있는 노드를 연결.

            //왼쪽 노드가 존재하지 않는다면  - 해당노드는 맨 앞노드,
            if(currentNode.left == null){

                this.head = currentNode.right;
                if(this.head != null) this.head.left = null;
            }
            //오른쪽 노드가 존재하지 않다면 - 해당 노드는 맨 뒤 노드
            else if(currentNode.right == null){
                this.tail = currentNode.left;
                if(this.tail != null) this.tail.right = null;
            }
            //둘다 아니라면 중간에 있는 노드
            else{
                currentNode.left.right = currentNode.right;
                currentNode.right.left = currentNode.left;
            }
        }


        //노드 삭제 - 맨 앞노드
        Node firstPopNode(){
            Node returnNode = head;

            if(returnNode == null) return null;

            head = returnNode.right;
            if(head != null){
                head.left = null;
            }

            returnNode.left = null;
            returnNode.right = null;

            return returnNode;
        }

    }


    static int n; //총 물건 수
    static int m; //벨트 수
    static CustomLinkedList[] factory; // 공장
    static Map<String,Node> factoryMap; //각 컨베이어 벨트 안의 정보 저장.
    static StringBuilder result;//출력할 빌더

    /*공장 설립 - 데이터 넣기.*/
    static void createFactory(String[] commandInfo){

        n = Integer.parseInt(commandInfo[1]);
        m = Integer.parseInt(commandInfo[2]);

        int maxBoxCount = n/m;//벨트당 최대 박스 수

        //공장 초기화
        factory = new CustomLinkedList[m];
        factoryMap = new HashMap<>();

        for(int i = 0; i < m ; i++){
            factory[i] = new CustomLinkedList(i+1);
        }
        // ID : 3 ~ (3 + n)-1 , weight : (3 + n) ~ 2n+2
        int beltIndex = 0;
        int count = 1; // n/m가 넘어가면 다음 벨트로 넘김.
        for(int i = 3; i < 3+n; i++){
            String boxId = commandInfo[i];
            int boxWeight = Integer.parseInt(commandInfo[i+n]);

            //노드 만들기
            Node node = new Node(boxWeight,boxId, beltIndex+1);

            //해당 컨베이어 벨트에 박스 추가
            factory[beltIndex].addNodeLast(node);

            // 고유번호로 박스 저장
            factoryMap.put(boxId, node);

            count++;
            // 최대 박스 수를 넘었으면 다음 컨베이어 벨트로 옮김
            if(count > maxBoxCount){
                beltIndex++;
                count = 1;
            }
        }
    }

    /*물건 하차 - 하차한 무게 합계 출력*/
    static long downGift(int maxWeight){

        long totalWeight = 0;

        //컨베이어 벨트를 하나씩 돌면서 맨앞 무게 확인
        for(int i = 0; i < m; i++){


            //고장난 벨트면 패스
            if(factory[i] == null) continue;

            Node currentNode = factory[i].firstPopNode();

            if(currentNode == null) continue;

            if(currentNode.weight <= maxWeight){

                totalWeight += currentNode.weight;

                //맵 업데이트 - 삭제하면 비용이 많이 들기 때문에 null로 바꿈.
                factoryMap.put(currentNode.id, null);
            }
            //요구 무게보다 크면 뒤로 보냄.
            else{

                factory[i].addNodeLast(currentNode);

            }
        }

        return totalWeight;

    }

    /*물건 제거 - id값 또는 -1을 리턴.*/
    static int removeGift(String removeId){

        Node node = factoryMap.get(removeId);

        if(node == null) return -1;

        //중간에 노드를 뻈으니, 나머지 연결.
        factory[node.beltNum-1].removeNode(node);

        //노드 매핑 정보 업데이트
        factoryMap.put(removeId,null);

        return Integer.parseInt(removeId);
    }

    /*물건 확인*/
    static int checkGift(String findId){

        Node node = factoryMap.get(findId);

        //물건이 없다면 -1리턴.
        if(node == null) return -1;

        //해당 박스가 놓인 벨트에서 노드를 움직임.
        factory[node.beltNum-1].moveNode(node);

        return node.beltNum;
    }

    /*벨트 고장*/
    static int breakdownBelt(int beltNum){

        //이미 해당 벨트가 고장나있다면 -1 반환.
        if(factory[beltNum - 1] == null) return -1;

        //해당 벨트에 값이 없으면 패스
        if(factory[beltNum - 1].head == null) {
            factory[beltNum - 1] = null; //벨트 고장 처리.
            return beltNum;

        }

        CustomLinkedList conveyorBelt = factory[beltNum - 1];

        //반복문 돌면서 이동할 벨트 찾기.
        int moveBeltIndex = 0;

        //고장난 벨트의 오른쪽부터 봄.
        for(int i = beltNum - 1; i < beltNum - 1 + m; i++){
            if((i % m) != (beltNum - 1) && factory[i % m] != null){
                moveBeltIndex = i % m;
                break;
            }
        }

//        System.out.println("moveBeltIndex : " + moveBeltIndex);
//        System.out.println(conveyorBelt.tail.right);

        //이동할 벨트로 고장난 벨트에 있는 값들을 이동.
        factory[moveBeltIndex].addNodeLast(conveyorBelt.head);

        //고장난 벨트는 null처리
        factory[beltNum - 1] = null;

        return beltNum;
    }


    //입력값을 받아서 처리하는 로직.
    static void logic(String[] commandInfo){

        //100 - 공장 설립, ,
        if(commandInfo[0].equals("100")){
            createFactory(commandInfo);
        }
        //200 - 물건하차,
        else if(commandInfo[0].equals("200")){
            long totalWeight = downGift(Integer.parseInt(commandInfo[1]));
            result.append(totalWeight).append("\n");

        }
        // 300 - 물건 제거
        else if(commandInfo[0].equals("300")){
            int removeId = removeGift(commandInfo[1]);
            result.append(removeId).append("\n");
        }
        //400 - 물건확인.
        else if(commandInfo[0].equals("400")){
            int findId = checkGift(commandInfo[1]);
            result.append(findId).append("\n");
        }
        //벨트고장.
        else {
            int beltNum = breakdownBelt(Integer.parseInt(commandInfo[1]));
            result.append(beltNum).append("\n");
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(br.readLine()); //명령 수

        result = new StringBuilder();

        for(int i = 0; i < q; i++){
            String[] commandInfo = br.readLine().split(" ");
            logic(commandInfo);

//            if(i == 7){
//                System.out.println("start");
////                System.out.println(factoryMap.get("12").id);
//
//                Node tempNode = factory[0].head;
//                while(tempNode.right != null){
//                    System.out.println(tempNode.id);
//                    tempNode = tempNode.right;
//                }
//                System.out.println(tempNode.id);
//                System.out.println(tempNode.right);
//                System.out.println("end");
//            }
        }

        System.out.println(result);
    }
}
