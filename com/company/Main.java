package com.company;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        TreeCreate T_Tree= new TreeCreate();
        TreeCreate S_Tree = new TreeCreate();
        TreeCreate Skew_Tree = new TreeCreate();
        int n = 1000; //Update n value here
        Integer arr[] = new Integer[n];
        TreeCreate clone_Tree = new TreeCreate();
        TreeCreate clone_Tree1 = new TreeCreate();

        for(int i=1;i<=n;i++){
            arr[i-1] = i;
        }

        int mid = arr[arr.length/2];
        T_Tree.root = new RootNode(mid);
        T_Tree.root = T_Tree.levelOrderInsertion(arr, T_Tree.root, 0);

        S_Tree.root = new RootNode(mid);
        Skew_Tree.root = new RootNode(mid);
        clone_Tree.root = new RootNode(mid);
        clone_Tree1.root = new RootNode(mid);



        S_Tree.root = S_Tree.levelOrderInsertion(arr, S_Tree.root, 0);
        Skew_Tree.root = S_Tree.levelOrderInsertion(arr, Skew_Tree.root, 0);
        clone_Tree.root = S_Tree.levelOrderInsertion(arr, clone_Tree.root, 0);
        clone_Tree1.root = S_Tree.levelOrderInsertion(arr, clone_Tree1.root, 0);


        List<Integer> list = new ArrayList<Integer>();
        List<Integer> s_list = new ArrayList<Integer>();
        List<Integer> s_list1 = new ArrayList<Integer>();
        List<Integer> s_list2 = new ArrayList<Integer>();
        List<Integer> s_list3 = new ArrayList<Integer>();

        List<Integer> listRotF = new ArrayList<Integer>();

        for(int i=0;i<n;i++){
            list.add(arr[i]);
            s_list.add(arr[i]);
            s_list1.add(arr[i]);
            s_list2.add(arr[i]);
            s_list3.add(arr[i]);
            listRotF.add(arr[i]);

        }

        T_Tree.inOrderInsertion(T_Tree.root, list);

        T_Tree.heightAssign(T_Tree.root);

        S_Tree.inOrderInsertion(S_Tree.root, s_list);
        T_Tree.heightAssign(S_Tree.root);


        Skew_Tree.inOrderInsertion(Skew_Tree.root, s_list1);
        T_Tree.heightAssign(Skew_Tree.root);

        clone_Tree.inOrderInsertion(clone_Tree.root, s_list2);
        T_Tree.heightAssign(clone_Tree.root);

        clone_Tree1.inOrderInsertion(clone_Tree1.root, s_list3);
        T_Tree.heightAssign(clone_Tree1.root);

        T_Tree.intervalAssign(T_Tree.root);

        HashMap<String, String> dummy_map1 = new HashMap<String, String>();

        HashMap<String, String> TMap1 = S_Tree.treeMapz(T_Tree.root, dummy_map1);

        HashMap<Integer, RootNode> mapA = new HashMap<Integer, RootNode>();
        HashMap<Integer, RootNode> mapB = T_Tree.elementAssignment(mapA, T_Tree.root);


        List<Integer> leftT = T_Tree.generateLeftFArm(T_Tree.root.left);
        List<Integer> rightT = T_Tree.generateRightFArm(T_Tree.root.right);


        ArrayList<Integer> listA = new ArrayList<Integer>();
        ArrayList<Integer> listB = T_Tree.leafGen(T_Tree.root, listA);

        List<Integer> listC = new ArrayList<Integer>();
        Random random = new Random();

        while(listC.size() < 1){
            int rand = listRotF.get(random.nextInt(n));

            if(!listC.contains(rand)){
                S_Tree.preOrderInsertion(S_Tree.root, rand, false);
                Skew_Tree.preOrderInsertion(Skew_Tree.root, rand, false);
                clone_Tree.preOrderInsertion(clone_Tree.root, rand, false);
                clone_Tree1.preOrderInsertion(clone_Tree1.root, rand, false);
            }
            listC.add(rand);
        }

        int rem = listRotF.size()/ 2;

        List<Integer> listD = new ArrayList<Integer>();

        while(listD.size() < rem){
            int rand = listRotF.get(random.nextInt(n));

            if(!listC.contains(rand) && !listD.contains(rand)){
                S_Tree.preOrderInsertion(S_Tree.root, rand, false);
                Skew_Tree.preOrderInsertion(Skew_Tree.root, rand, false);
                clone_Tree.preOrderInsertion(clone_Tree.root, rand, false);
                clone_Tree1.preOrderInsertion(clone_Tree1.root, rand, false);

            }
            listD.add(rand);
        }


        TreeCreate.A1Algorithm(T_Tree,S_Tree);

        //*************************A2*****************************


        HashMap<Integer, String> map1 = S_Tree.assignBinary(S_Tree.root, n, S_Tree.root.level);
        Skew_Tree.binaryParse(map1, S_Tree.root);

        S_Tree.root = S_Tree.rotateFunc(S_Tree.root);

        Skew_Tree.root = Skew_Tree.rotateFunc(Skew_Tree.root);

        Skew_Tree.intervalAssign(S_Tree.root);

        HashMap<String, String> dummy_map = new HashMap<String, String>();


        HashMap<String, Integer> Int_Map = new HashMap<String, Integer>();
        HashMap<String, Integer> rootMap = S_Tree.rootMap(T_Tree.root, Int_Map);

        Skew_Tree.ignoreSubTrees(Skew_Tree.root, TMap1);


        double x = T_Tree.rootCalc(n, T_Tree.root.level + 1);


        Skew_Tree.root = Skew_Tree.rootTop(Skew_Tree.root, (int) x, Skew_Tree.root.level, Skew_Tree);


        int rootTop = Skew_Tree.rotationCount;

        List<Integer> leftList = S_Tree.generateLeftFArm(S_Tree.root.left);
        List<Integer> rightList = S_Tree.generateRightFArm(S_Tree.root.right);

        ArrayList<RootNode> nodeK = S_Tree.allLeftFArm(S_Tree, S_Tree.root.left);

        int intoLeftForearm = Skew_Tree.rotationCount - rootTop;

        Skew_Tree.root.left = null;
        Skew_Tree.root.left = nodeK.get(0);
        nodeK.remove(0);

        Skew_Tree.inOrderLeftGen(Skew_Tree.root.left, nodeK);


        ArrayList<RootNode> nodeM = Skew_Tree.allRightFArm(Skew_Tree, Skew_Tree.root.right);

        int intoRightForearm = Skew_Tree.rotationCount;

        int r = nodeM.size();
        Skew_Tree.root.right = null;
        Skew_Tree.root.right = nodeM.get(0);
        nodeM.remove(0);

        Skew_Tree.inOrderLeftGen(Skew_Tree.root.right, nodeM);

        Skew_Tree.heightBalance(Skew_Tree.root);

        Skew_Tree.leftParentGen(Skew_Tree.root.left, Skew_Tree.root);


        S_Tree.listRotFalse(S_Tree.root, leftT, rightT);

        TreeCreate moreTreez = new TreeCreate();
        RootNode arbitRight = (RootNode) Skew_Tree.root.clone();
        moreTreez.root = arbitRight;

        moreTreez.heightBalance(moreTreez.root);

        moreTreez.leftParentGen(moreTreez.root.right, moreTreez.root);

        int original_node = moreTreez.root.data;

        Skew_Tree.AlgoAL2(S_Tree.root.left, Skew_Tree, T_Tree.root.level + 1, n, leftT, T_Tree);

        Skew_Tree.leftParentGen(Skew_Tree.root.left, Skew_Tree.root);

        moreTreez.root.left = Skew_Tree.root;
        int performLeftRot = Skew_Tree.rotationCount - intoLeftForearm;

        moreTreez.AlgoAR2(moreTreez.root.right, moreTreez, T_Tree.root.level + 1, n, r, rightT, T_Tree);

        TreeCreate endTree = new TreeCreate();
        endTree.root = new RootNode(original_node);
        endTree.root.left = Skew_Tree.root;
        endTree.root.right = moreTreez.root.right;

        System.out.println("My A2: " + Skew_Tree.rotA2Gen(n));

        TreeCreate V = S_Tree.ignoreSubTreeA3(clone_Tree1.root,mapB ,rootMap, clone_Tree1);

        V.ALgorithmA2(T_Tree, clone_Tree1, n, leftT, rightT,TMap1);


    }
    }
