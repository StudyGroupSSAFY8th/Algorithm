import java.util.*;
class Solution {
    class Node{
        int x;
        int y;
        int idx;
        Node(int x, int y, int idx){
            this.x = x;
            this.y = y;
            this.idx = idx+1;
        }
    }
    ArrayList<Integer> pre = new ArrayList<>();
    ArrayList<Integer> post = new ArrayList<>();
    public int[][] solution(int[][] nodeinfo) {
        
        Node[] nodeList = new Node[nodeinfo.length];

        for(int i=0;i<nodeinfo.length;i++){
            nodeList[i] = new Node(nodeinfo[i][0],nodeinfo[i][1],i);
        }
        
        Arrays.sort(nodeList,(a,b)->{
            return a.x-b.x;
        });
        
        
        preorder(nodeList,0,nodeList.length-1);
        postorder(nodeList,0,nodeList.length-1);
        
        int[][] answer = new int[2][nodeList.length];
        
        for(int i=0;i<nodeList.length;i++){
            answer[0][i] = pre.get(i);
        }
        
        for(int i=0;i<nodeList.length;i++){
            answer[1][i] = post.get(i);
        }
        
        return answer;
    }
    public int getRoot(Node[] nodeList,int left, int right){
        int root = -1;
        int rootHeight = -1;
        for(int i=left;i<=right;i++){
            if(rootHeight<nodeList[i].y){
                rootHeight=nodeList[i].y;
                root = i;
            }
        }
        
        
        return root;
    }
    public void preorder(Node[] nodeList, int left, int right){
        //루트
        int root = getRoot(nodeList, left,  right);
                
        pre.add(nodeList[root].idx);
        //왼쪽
        if(root!=left){
            preorder(nodeList,left,root-1);
        }
        
        //오른쪽
        if(root!=right){
            preorder(nodeList,root+1,right);
        }
        
    }
    
    public void postorder(Node[] nodeList, int left, int right){
        //루트
        int root = getRoot(nodeList, left,  right);
                
        
        //왼쪽
        if(root!=left){
            postorder(nodeList,left,root-1);
        }
        
        //오른쪽
        if(root!=right){
            postorder(nodeList,root+1,right);
        }
        
        post.add(nodeList[root].idx);
    }
}