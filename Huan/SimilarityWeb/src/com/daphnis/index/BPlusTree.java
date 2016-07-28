//package com.daphnis.index; 
// 
//import java.util.ArrayList;
//import java.util.Random;
//
//import com.daphnis.index.BTree;
//import com.daphnis.index.Node; 
// 
//@SuppressWarnings("rawtypes")
//public class BPlusTree implements BTree { 
//     
//    /** 根节点 */ 
//    protected Node root; 
//     
//    /** 阶数，M值 */ 
//    protected int order; 
//     
//    /** 叶子节点的链表头*/ 
//    protected Node head; 
//     
//    public Node getHead() { 
//        return head; 
//    } 
// 
//    public void setHead(Node head) { 
//        this.head = head; 
//    } 
// 
//    public Node getRoot() { 
//        return root; 
//    } 
// 
//    public void setRoot(Node root) { 
//        this.root = root; 
//    } 
// 
//    public int getOrder() { 
//        return order; 
//    } 
// 
//    public void setOrder(int order) { 
//        this.order = order; 
//    } 
// 
//    @Override 
//    public Object findClosestNode(double key) { 
//        return root.findClosestNode(key); 
//    } 
// 
//    @Override 
//    public void removeNode(double key) { 
//        root.remove(key, this); 
// 
//    } 
// 
//    @Override 
//    public void insertOrUpdate(double key, Object obj) { 
//        root.insertOrUpdate(key, obj, this); 
// 
//    } 
//     
//    public BPlusTree(int order){ 
//        if (order < 3) { 
//            System.out.print("order must be greater than 2"); 
//            System.exit(0); 
//        } 
//        this.order = order; 
//        root = new Node(true, true); 
//        head = root; 
//    } 
//     
//    //测试 
//    public static void main(String[] args) {
//    	ArrayList<Integer> ai=new ArrayList<Integer>();
//        BPlusTree tree = new BPlusTree(6); 
//        Random random = new Random(); 
//        for (int j = 0; j < 100000; j++) { 
//            for (int i = 0; i < 100; i++) { 
//                int randomNumber = random.nextInt(1000);
//                if(j==99000){
//                	randomNumber=1001;
//                }
//                ai.add(randomNumber);
//            }  
////            for (int i = 0; i < 100; i++) { 
////                int randomNumber = random.nextInt(1000); 
////                tree.remove(randomNumber); 
////            } 
//        } 
//        int search = 10011; 
//        long current = System.currentTimeMillis(); 
//        int ts=0;
//        for(int n:ai){
//        	++ts;
//        	if(n==search){
//        		System.out.println(n+","+ts);
//        		break;
//        	}
//        }
//        long duration = System.currentTimeMillis() - current; 
//        System.out.println("foeach 耗时: " + duration); 
//        
//        current = System.currentTimeMillis();
//        for(int n:ai){
//            tree.insertOrUpdate(n, n); 
//        }
////        System.out.print(tree.get(search)); 
//        duration = System.currentTimeMillis() - current;
//        System.out.println(" b+ 耗时: " + duration); 
//    } 
// 
//} 