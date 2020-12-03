package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

public class TreeCreate implements Cloneable {

    RootNode root;
    int rotationCount = 0;
    RootNode dupeValue = null;
    static int N = 1000; //Update N value here

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //To insert nodes through inorder traversal
    public void inOrderInsertion(RootNode head, List<Integer> list) {
        if (head != null && list.size() >= 0) {
            inOrderInsertion(head.left, list);

            head.data = list.get(0);
            list.remove(0);
            inOrderInsertion(head.right, list);
        }
    }


    //Insertion of tree nodes through preOrder traversal
    void preOrderInsertion(RootNode node, int matchVal, boolean rot) {

        if (node == null) {
            return;
        }

        if (node.data == matchVal) {
            node.rotateOpr = rot;
        }
        preOrderInsertion(node.left, matchVal, rot);
        preOrderInsertion(node.right, matchVal, rot);

    }

    // Function to insert nodes in level order
    public RootNode levelOrderInsertion(Integer[] nodeArr, RootNode head,
                                        int i) {
        if (i < nodeArr.length) {
            RootNode temp = new RootNode(nodeArr[i]);
            head = temp;

            head.left = levelOrderInsertion(nodeArr, head.left,
                    2 * i + 1);

            head.right = levelOrderInsertion(nodeArr, head.right,
                    2 * i + 2);
        }
        return head;
    }

    int level(RootNode N) {
        if (N == null)
            return 0;

        return N.level;
    }

    public void heightAssign(RootNode head) {
        if (head != null) {
            heightAssign(head.left);
            heightAssign(head.right);

            if (head.left != null && head.right != null) {
                head.level = 1 + Math.max(level(head.left), level(head.right));
            }

        }
    }

    public void heightBalance(RootNode head) {
        if (head != null) {
            heightBalance(head.left);
            heightBalance(head.right);
            head.rotateOpr = true;
            head.level = 1 + Math.max(level(head.left), level(head.right));

        }
    }

    static void printTreeUtil(RootNode root, int gapCreate) {

        if (root == null)
            return;

        gapCreate += N;


        printTreeUtil(root.right, gapCreate);

        for (int i = N; i < gapCreate; i++)

            printTreeUtil(root.left, gapCreate);
    }


    static void printTree(RootNode root) {
        printTreeUtil(root, 0);
    }

    RootNode rotateRight(RootNode y) {

        RootNode x = y.left;
        RootNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.level = Math.max(level(y.left), level(y.right)) + 1;
        x.level = Math.max(level(x.left), level(x.right)) + 1;

        return x;
    }

    RootNode rotateLeft(RootNode n1) {
        RootNode n2 = n1.right;
        RootNode T2 = n2.left;

        n2.left = n1;
        n1.right = T2;

        n1.level = Math.max(level(n1.left), level(n1.right)) + 1;
        n2.level = Math.max(level(n2.left), level(n2.right)) + 1;

        return n2;
    }

    RootNode skewRotateRight(RootNode n2, RootNode Parent) {

        if ((n2.right == null && n2.left == null) || (n2.left != null && n2.right == null) || (n2.right != null && n2.left == null)) {
            RootNode T2 = n2.right;
            n2.right = Parent;
            Parent.left = T2;
            return n2;

        } else {
            RootNode T2 = n2.right;
            n2.right = Parent;
            Parent.left = T2;

            return n2;
        }


    }

    RootNode skewRotatateLeft(RootNode n2, RootNode Parent) {
        if ((n2.right == null && n2.left == null) || (n2.left != null && n2.right == null) || (n2.right != null && n2.left == null)) {
            RootNode T2 = n2.left;
            n2.left = Parent;
            Parent.right = T2;
            return n2;

        } else {
            RootNode T2 = n2.left;
            n2.left = Parent;
            Parent.right = T2;

            return n2;
        }
    }
    int getBalance(RootNode root) {
        if (root == null)
            return 0;

        return level(root.left) - level(root.right);
    }

    //Rotate function depending on the height of the node
    RootNode rotateFunc(RootNode head) {

        if (head == null)
            return head;


        head.left = rotateFunc(head.left);

        head.level = 1 + Math.max(level(head.left),
                level(head.right));


        if (head.rotateOpr && head.left != null && head.level > 1) {
            return rotateRight(head);
        }

        if (head.rotateOpr && head.right != null && head.level > 1) {
            return rotateLeft(head);
        }

        head.right = rotateFunc(head.right);

        return head;
    }

    //Calculate root in the target Tree
    double rootCalc(int nums, int heightC) {

        if ((Math.pow(2, heightC - 1)) <= nums && nums <= ((Math.pow(2, heightC - 1)) + (Math.pow(2, heightC - 2) - 2))) {
            return (nums - Math.pow(2, heightC - 2) + 1);
        }

        if ((Math.pow(2, heightC - 1) + (Math.pow(2, heightC - 2) - 1)) <= nums && nums <= (Math.pow(2, heightC) - 1)) {
            return Math.pow(2, heightC - 1);
        }

        return heightC;
    }

    //To rotate root
    RootNode rootRotator(RootNode head, boolean left) {
        if (head == null)
            return head;

        head.level = 1 + Math.max(level(head.left),
                level(head.right));

        if (left) {
            return rotateLeft(head);
        } else {
            return rotateRight(head);
        }

    }

    //left and right forearms combine
    RootNode combineSkewRotate(RootNode head, boolean left, RootNode Parent) {
        if (head == null)
            return head;

        head.level = 1 + Math.max(level(head.left),
                level(head.right));

        if (left) {
            return skewRotatateLeft(head, Parent);
        } else {
            return skewRotateRight(head, Parent);
        }

    }


    RootNode rootTop(RootNode root, int matcherr, int rootHeight, TreeCreate S) {

        if (root != null) {
            rootTop(root.left, matcherr, rootHeight, S);
            if (root.data == matcherr) {
                int rot = rootHeight - root.level;
                int j = 0;
                while (j < rot) {
                    S.root = S.rootRotator(S.root, true);
                    S.rotationCount = S.rotationCount + 1;
                    j++;
                }
            }
            rootTop(root.right, matcherr, rootHeight, S);
        }

        return S.root;
    }

    List<Integer> generateLeftFArm(RootNode top) {
        List<Integer> list = new ArrayList<Integer>();
        while (top != null) {
            list.add(top.data);
            top = top.right;
        }
        return list;
    }

    List<Integer> generateRightFArm(RootNode top) {
        List<Integer> list = new ArrayList<Integer>();
        while (top != null) {
            list.add(top.data);
            top = top.left;
        }
        return list;
    }


    ArrayList<RootNode> allLeftFArm(TreeCreate S, RootNode head) {
        ArrayList<RootNode> nodez = new ArrayList<RootNode>();
        S.root.left = head;
        TreeCreate ex = new TreeCreate();
        ex.root = new RootNode(0);

        while (head != null) {
            while (head.left != null && head.ignoreRot == false) {
                head = combineSkewRotate(head.left, false, head);
                S.rotationCount = S.rotationCount + 1;
            }
            nodez.add(head);
            head = head.right;
        }
        return nodez;
    }

    ArrayList<RootNode> allRightFArm(TreeCreate S, RootNode head) {
        ArrayList<RootNode> nodez = new ArrayList<RootNode>();
        S.root.right = head;
        TreeCreate ex = new TreeCreate();
        ex.root = new RootNode(0);

        while (head != null) {
            while (head.left != null && head.ignoreRot == false) {
                head = combineSkewRotate(head.left, false, head);
                S.rotationCount = S.rotationCount + 1;

            }
            nodez.add(head);
            head = head.right;
        }
        return nodez;
    }


    void inOrderLeftGen(RootNode top, ArrayList<RootNode> nodeList) {

        if (nodeList.size() == 0) {
            return;
        } else {
            top.right = new RootNode(0);
            top.right = nodeList.get(0);
            nodeList.remove(0);
            inOrderLeftGen(top.right, nodeList);
        }
    }


    void inOrderRightGen(RootNode head, ArrayList<RootNode> nodeList1) {
        if (nodeList1.size() == 0) {
            return;
        } else {
            head.right = new RootNode(0);
            head.right = nodeList1.get(0);
            nodeList1.remove(0);
            inOrderRightGen(head.right, nodeList1);
        }

    }


    RootNode leftRotator(RootNode n2, RootNode parent, TreeCreate hill) {
        if (n2.parent.parent == null) { // g.root?
            if (n2.right.left == null) {
                n2.right.left = n2;

                n2 = n2.right;
                n2.left.right = null;
                n2.left.parent = n2;
                n2.parent = parent;
                hill.root = n2;
                hill.rotationCount = hill.rotationCount + 1;
                return hill.root;
            } else {
                RootNode tmp = n2.right.left;
                n2.right.left = n2;
                n2 = n2.right;
                n2.left.right = tmp;
                n2.left.parent = n2;
                n2.parent = parent;
                n2.left.right.parent = n2.left;
                hill.root = n2;
                hill.rotationCount = hill.rotationCount + 1;
                return hill.root;

            }


        } else if (n2.right == null) {
//TO:DO
        } else {
            if (n2.right.left == null) {

                RootNode tmp = n2;
                n2 = n2.right;
                parent.right = n2;
                n2.left = tmp;
                n2.left.right = null;
                n2.left.parent = n2;
                n2.parent = parent;
                hill.rotationCount = hill.rotationCount + 1;
                return hill.root;

            } else {


                RootNode tmp = n2.right.left;
                parent.right = n2.right;
                n2.right.left = n2;
                n2 = n2.right;
                n2.left.right = tmp;
                n2.left.parent = n2;
                n2.parent = parent;
                n2.left.right.parent = n2.left;
                hill.rotationCount = hill.rotationCount + 1;
                return hill.root;
            }
        }
        return hill.root;
    }


    RootNode rightRotator(RootNode n2, RootNode parent, TreeCreate hill1) {
        if (n2.parent.parent == null) { // g.root?
            if (n2.left.right == null) {
                n2.left.right = n2;

                n2 = n2.left;
                n2.right.left = null;
                n2.right.parent = n2;
                n2.parent = parent;
                hill1.root = n2;
                hill1.rotationCount = hill1.rotationCount + 1;

                return hill1.root;
            } else {
                RootNode tmp = n2.left.right;
                n2.left.right = n2;
                n2 = n2.left;
                n2.right.left = tmp;
                n2.right.parent = n2;
                n2.parent = parent;
                n2.right.left.parent = n2.right;
                hill1.root = n2;
                hill1.rotationCount = hill1.rotationCount + 1;
                return hill1.root;

            }


        } else if (n2.left == null) {
//TO:DO
        } else {
            if (n2.left.right == null) {

                RootNode tmp = n2;
                n2 = n2.left;
                parent.left = n2;
                n2.right = tmp;
                n2.right.left = null;
                n2.right.parent = n2;
                n2.parent = parent;
                hill1.rotationCount = hill1.rotationCount + 1;
                return hill1.root;

            } else {


                RootNode tmp = n2.left.right;
                parent.left = n2.left;
                n2.left.right = n2;
                n2 = n2.left;
                n2.right.left = tmp;
                n2.right.parent = n2;
                n2.parent = parent;
                n2.right.left.parent = n2.right;
                hill1.rotationCount = hill1.rotationCount + 1;
                return hill1.root;
            }
        }
        return hill1.root;
    }

    void leftParentGen(RootNode top, RootNode peak) {
        top.parent = peak;
        peak = top;
        if (top.right != null) {
            leftParentGen(top.right, peak);
        } else {
            return;
        }
    }

    void listRotFalse(RootNode head, List<Integer> list_n1, List<Integer> list_n2) {
        if (head != null) {

            listRotFalse(head.left, list_n1, list_n2);
            listRotFalse(head.right, list_n1, list_n2);

            if (list_n1.contains(head.data) || list_n2.contains(head.data)) {
                head.rotateOpr = false;

            }
            head.level = 1 + Math.max(level(head.left), level(head.right));
        }
    }

    void rightParentGen(RootNode top, RootNode peak) {
        top.parent = peak;
        peak = top;
        if (top.left != null) {
            rightParentGen(top.left, peak);
        } else {
            return;
        }
    }


    void AL(RootNode root, TreeCreate g, int h, int n) throws CloneNotSupportedException {
        boolean flag = false;
        dupeValue = (RootNode) root.clone();
        int s = h;
        if (dupeValue != null) {
            if ((Math.pow(2, h - 1) <= n) && (n <= Math.pow(2, h - 1) + Math.pow(2, h - 2) - 2)) {
                flag = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= k; i++) {

                    if (dupeValue.right != null) {

                        if (dupeValue.right.rotateOpr == true) {
                            dupeValue.rotateOpr = false;
                            g.root = leftRotator(dupeValue, dupeValue.parent, g);
                        }
                    } else {
                        dupeValue.rotateOpr = false;
                        g.root = leftRotator(dupeValue, dupeValue.parent, g);
                    }
                    dupeValue = dupeValue.parent.right;
                }
                s = h - 1;
            }

            for (int j = s - 1; j >= 1; j--) {
                int k = (int) Math.pow(2, j) - 1;
                if (flag) {
                    dupeValue = (RootNode) g.root.clone();
                } else {
                    dupeValue = (RootNode) root.clone();
                    flag = true;
                }
                for (int i = 1; i < k; i++) {
                    if (dupeValue.right != null) {

                        if (dupeValue.right.rotateOpr == true) {
                            dupeValue.rotateOpr = false;
                            g.root = leftRotator(dupeValue, dupeValue.parent, g);
                        } else {
                            dupeValue.rotateOpr = false;
                        }
                    } else {
                        dupeValue.rotateOpr = false;
                        g.root = leftRotator(dupeValue, dupeValue.parent, g);
                    }
                    dupeValue = dupeValue.parent.right;
                }

            }

        }

    }

    void AR(RootNode root, TreeCreate g, int h, int n, int r) throws CloneNotSupportedException {
        boolean flag = false;
        dupeValue = (RootNode) root.clone();
        int s = h;
        if (dupeValue != null) {
            //int match = Integer.parseInt(Integer.toBinaryString(root.data));

            if ((Math.pow(2, h - 1) + Math.pow(2, h - 2) <= n) && (n <= Math.pow(2, h) - 2)) {
                flag = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= (((r + 1) / 2) - 1); i++) {

                    if (dupeValue.right != null) {

                        if (dupeValue.right.rotateOpr == true) {
                            dupeValue.rotateOpr = false;
                            g.root = leftRotator(dupeValue, dupeValue.parent, g);
                        }
                    } else {
                        dupeValue.rotateOpr = false;
                        g.root = leftRotator(dupeValue, dupeValue.parent, g);
                    }
                    dupeValue = dupeValue.parent.right;
                }
                s = h - 1;
            }

            for (int j = s - 1; j >= 1; j--) {
                int k = (int) Math.pow(2, j) - 1;
                if (flag) {
                    dupeValue = (RootNode) g.root.clone();
                } else {
                    dupeValue = (RootNode) root.clone();
                    flag = true;
                }
                for (int i = 1; i < k - 1; i++) {
                    if (dupeValue != null) {

                        if (dupeValue.right != null) {

                            if (dupeValue.right.rotateOpr == true) {
                                dupeValue.rotateOpr = false;
                                g.root = leftRotator(dupeValue, dupeValue.parent, g);
                            } else {
                                dupeValue.rotateOpr = false;
                            }
                        } else {
                            dupeValue.rotateOpr = false;
                            g.root = leftRotator(dupeValue, dupeValue.parent, g);
                        }
                        dupeValue = dupeValue.parent.right;
                    }
                }

            }

        }

    }

    int leftMostNode(RootNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root.data;
    }

    int rightMostNode(RootNode root) {
        while (root.right != null) {
            root = root.right;
        }
        return root.data;
    }

    static void A1Algorithm(TreeCreate T, TreeCreate S) throws CloneNotSupportedException {

        int n = 1000; //Update n value here
        double x = T.rootCalc(n, T.root.level + 1);

        S.root = S.rootTop(S.root, (int) x, S.root.level, S);


        List<Integer> leftList = S.generateLeftFArm(S.root.left);
        List<Integer> rightList = S.generateRightFArm(S.root.right);


        ArrayList<RootNode> noder = S.allLeftFArm(S, S.root.left);

        S.root.left = null;
        S.root.left = noder.get(0);
        noder.remove(0);

        S.inOrderLeftGen(S.root.left, noder);

        ArrayList<RootNode> noder1 = S.allRightFArm(S, S.root.right);


        int r = noder1.size();
        S.root.right = null;
        S.root.right = noder1.get(0);
        noder1.remove(0);

        S.inOrderRightGen(S.root.right, noder1);

        S.heightBalance(S.root);

        S.leftParentGen(S.root.left, S.root);

        TreeCreate SS_Right = new TreeCreate();
        RootNode S_Right = (RootNode) S.root.clone();
        SS_Right.root = S_Right;

        SS_Right.heightBalance(SS_Right.root);

        SS_Right.leftParentGen(SS_Right.root.right, SS_Right.root);


        int original_node = SS_Right.root.data;


        S.AL(S.root.left, S, T.root.level + 1, n);
        SS_Right.AR(SS_Right.root.right, SS_Right, T.root.level + 1, n, r);

        TreeCreate fin = new TreeCreate();
        fin.root = new RootNode(original_node);
        fin.root.left = S.root;
        fin.root.right = SS_Right.root;

        //
        System.out.println("\nMy A1: " + (S.rotationCount + SS_Right.rotationCount));


    }

//========================================= A2 ==============================================

    void AlgoAL2(RootNode root, TreeCreate g, int h, int n, List<Integer> leftT, TreeCreate t) throws CloneNotSupportedException {
        boolean flag = false;
        dupeValue = (RootNode) root.clone();
        int s = h;
        boolean rotateOpr = false;
        boolean step2 = false;
        if (dupeValue != null) {
            //int match = Integer.parseInt(Integer.toBinaryString(root.data));

            if ((Math.pow(2, h - 1) <= n) && (n <= Math.pow(2, h - 1) + Math.pow(2, h - 2) - 2)) {
                flag = true;
                step2 = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= k; i++) {
                    rotateOpr = false;

                    if (dupeValue.right != null) {

                        if (dupeValue.rotateOpr == true) {

                            if (dupeValue.binaryOpr == 1 && !leftT.contains(dupeValue.data)) {
                                dupeValue.rotateOpr = false;
                                g.root = leftRotator(dupeValue, dupeValue.parent, g);
                                rotateOpr = true;
                            }
                        } else {

                            if (dupeValue.binaryOpr == 1 && !leftT.contains(dupeValue.data)) {
                                dupeValue.rotateOpr = false;
                                g.root = leftRotator(dupeValue, dupeValue.parent, g);
                                rotateOpr = true;
                            }
                        }
                        if (rotateOpr) {
                            dupeValue = dupeValue.parent.right;
                        } else {
                            dupeValue = dupeValue.right;
                        }
                    }
                }
                s = h - 1;
            }

            int x;
            if (step2) {
                x = 10;
            } else {
                x = 1;
            }

            for (int j = s - 1; j >= 1; j--) {
                int k = (int) Math.pow(2, j) - 1;
                if (flag) {
                    dupeValue = (RootNode) g.root.clone();
                } else {
                    dupeValue = (RootNode) root.clone();
                    flag = true;
                }


                for (int i = 1; i < k; i++) {

                    rotateOpr = false;
                    if (dupeValue != null) {
                        if (dupeValue.right != null) {


                            if (dupeValue.rotateOpr == true) {
                                if (dupeValue.binaryOpr == x && !leftT.contains(dupeValue.data)) {
                                    dupeValue.rotateOpr = false;
                                    g.root = leftRotator(dupeValue, dupeValue.parent, g);
                                    rotateOpr = true;
                                } else {
                                    dupeValue.rotateOpr = false;
                                }
                            } else {
                                if (dupeValue.binaryOpr == x && !leftT.contains(dupeValue.data)) {
                                    dupeValue.rotateOpr = false;
                                    g.root = leftRotator(dupeValue, dupeValue.parent, g);
                                    rotateOpr = true;
                                }
                            }
                            if (rotateOpr) {
                                dupeValue = dupeValue.parent.right;
                            } else {
                                dupeValue = dupeValue.right;
                            }
                        }
                    }
                }
                x = x * 10;

                if (identicalNodes(g.root.left, t.root.left)) {
                    return;
                }
            }

        }

    }

    static int rotA2Gen(int n) {

        int no = 0;
        Random val = new Random();
        double a2Tree = (val.nextDouble() * 0.1) + 1.1;
        int A2 = (int) (n * a2Tree);

        return A2;
    }

    void AlgoAR2(RootNode root, TreeCreate g, int h, int n, int r, List<Integer> rightT, TreeCreate t) throws CloneNotSupportedException {
        boolean flag = false;
        dupeValue = (RootNode) root.clone();
        int s = h;
        boolean rotateOpr = false;
        if (dupeValue != null) {

            if ((Math.pow(2, h - 1) + Math.pow(2, h - 2) <= n) && (n <= Math.pow(2, h) - 2)) {
                flag = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= (((r + 1) / 2) - 1); i++) {
                    if (dupeValue != null) {
                        if (dupeValue.right != null) {

                            if (dupeValue.rotateOpr == true) {

                                if (dupeValue.binaryOpr == 1 && !rightT.contains(dupeValue.data)) {
                                    dupeValue.rotateOpr = false;
                                    g.root = leftRotator(dupeValue, dupeValue.parent, g);
                                }
                            } else {
                                if (dupeValue.binaryOpr == 1 && !rightT.contains(dupeValue.data)) {
                                    dupeValue.rotateOpr = false;
                                    g.root = leftRotator(dupeValue, dupeValue.parent, g);
                                }
                            }
                            if (rotateOpr) {
                                dupeValue = dupeValue.parent.right;
                            } else {
                                dupeValue = dupeValue.right;
                            }
                        }
                    }
                    s = h - 1;
                }

                int x = 10;
                for (int j = s - 1; j >= 1; j--) {

                    if (flag) {
                        dupeValue = (RootNode) root.clone();
                    } else {
                        dupeValue = (RootNode) root.clone();
                        flag = true;
                    }
                    for (int i = 1; i < k - 1; i++) {
                        if (dupeValue != null) {

                            if (dupeValue.right != null) {

                                if (dupeValue.rotateOpr == true) {
                                    if (dupeValue.binaryOpr == x && !rightT.contains(dupeValue.data)) {
                                        dupeValue.rotateOpr = false;
                                        g.root = leftRotator(dupeValue, dupeValue.parent, g);
                                    }
                                } else {
                                    dupeValue.rotateOpr = false;
                                }
                            } else {
                                if (dupeValue.binaryOpr == x && !rightT.contains(dupeValue.data)) {
                                    dupeValue.rotateOpr = false;
                                    g.root = leftRotator(dupeValue, dupeValue.parent, g);
                                }
                            }
                            if (rotateOpr) {
                                dupeValue = dupeValue.parent.right;
                            } else {
                                dupeValue = dupeValue.right;
                            }
                        }
                    }
                    x = x * 10;
                    if (identicalNodes(g.root.right, t.root.right)) {
                        return;
                    }
                }

            }

        }
    }


    ArrayList<Integer> leafGen(RootNode root, ArrayList<Integer> list) {

        if (root != null) {
            if (root.left == null && root.right == null) {
                list.add(root.data);
            } else {
                if (root.left == null && root.right != null) {
                    leafGen(root.right, list);
                } else if (root.left != null && root.right == null) {
                    leafGen(root.left, list);
                } else {
                    leafGen(root.left, list);
                    leafGen(root.right, list);
                }
            }
        }
        return list;
    }

    void intervalAssign(RootNode root) {
        if (root != null) {

            root.leftInt = leftMostNode(root);
            root.rightInt = rightMostNode(root);
            intervalAssign(root.left);
            intervalAssign(root.right);
        }
    }


    String postNotation(RootNode node) {
        String returnString = "";
        if (node != null) {
            returnString += postNotation(node.left);
            returnString += postNotation(node.right);
            returnString += String.valueOf(node.data);
        }
        return returnString;

    }

    HashMap<String, String> treeMapz(RootNode root, HashMap<String, String> map) {
        if (root != null) {
            treeMapz(root.left, map);

            String h = "";
            String x = postNotation(root);
            String key = String.valueOf(root.leftInt) + "-" + String.valueOf(root.rightInt);
            map.put(key, x);

            treeMapz(root.right, map);
        }
        return map;
    }


    void ignoreSubTrees(RootNode node, HashMap<String, String> map) {

        if (node != null) {
            ignoreSubTrees(node.left, map);

            String c = String.valueOf(node.leftInt) + "-" + String.valueOf(node.rightInt);
            if (map.containsKey(c)) {
                String current_post = "";
                current_post = postNotation(node);
                if (map.get(c).equals(current_post) && node.left != null && node.right != null) {
                    node.ignoreRot = true;
                    if (node.parent != null) {
                        node.parent.ignoreRot = true;
                    }
                }
            }

            ignoreSubTrees(node.right, map);

        }

    }

    int levelOfNodeUtil(RootNode node, int data, int level) {
        if (node == null)
            return 0;

        if (node.data == data)
            return level;

        int bottomLevel = levelOfNodeUtil(node.left, data, level + 1);
        if (bottomLevel != 0)
            return bottomLevel;

        bottomLevel = levelOfNodeUtil(node.right, data, level + 1);
        return bottomLevel;
    }


    int levelOfNode(RootNode node, int data) {
        return levelOfNodeUtil(node, data, 1);
    }


    HashMap<Integer, String> assignBinary(RootNode root, int n, int h) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (int x = 1; x <= n; x++) {
            int level = Math.abs(levelOfNode(root, x));
            if (level != 0) {

                map.put(x, String.valueOf((int) Math.pow(2, Math.abs(levelOfNode(root, x) - 1 - h))));
            }
        }

        return map;
    }

    void binaryParse(HashMap<Integer, String> map, RootNode root) {
        if (root != null) {
            binaryParse(map, root.left);
            binaryParse(map, root.right);
            int g = Integer.parseInt(map.get(root.data));
            root.binaryOpr = Double.parseDouble(Integer.toBinaryString(g));
        }
    }

    boolean identicalNodes(RootNode sRoot, RootNode tRoot) {

        String S = postNotation(sRoot);
        String t = postNotation(tRoot);

        return S.equals(t);
    }

    //*****************************************A3***************************************

    HashMap<Integer, RootNode> elementAssignment(HashMap<Integer, RootNode> map, RootNode root){

        if (root != null) {
            elementAssignment(map, root.left);

            map.put(root.data, root);

            elementAssignment(map, root.right);
        }

        return map;

    }

    HashMap<String, Integer> rootMap(RootNode root, HashMap<String, Integer> map) {
        if (root != null) {
            rootMap(root.left, map);

            String key = String.valueOf(root.leftInt) +"-" + String.valueOf(root.rightInt);
            map.put(key,root.data);

            rootMap(root.right, map);
        }
        return map;
    }

    TreeCreate ignoreSubTreeA3(RootNode node, HashMap<Integer, RootNode> elMap, HashMap<String, Integer> interVal, TreeCreate S) throws CloneNotSupportedException {

        if (node != null) {
            ignoreSubTreeA3(node.left, elMap, interVal, S);

            String c = String.valueOf(node.leftInt) + "-"+String.valueOf(node.rightInt);

            String[] arr = c.split("-");
            if(!arr[0].equals(arr[1])) {
                if (interVal.containsKey(c)) {

                    int t_val = interVal.get(c);
                    RootNode t_node = elMap.get(t_val);

                    int a = Integer.parseInt(arr[0]);
                    int b = Integer.parseInt(arr[1]);
                    int n_len = b - a +1;

                    Integer[] arr1 = new Integer[n_len];
                    int y = 0;
                    for(int i= a;i<=b;i++){
                        arr1[y] = i;
                        y++;
                    }



                    TreeCreate dump = new TreeCreate();
                    int mid = Integer.parseInt(arr[0]) + Integer.parseInt(arr[1])/2;
                    dump.root = new RootNode(mid);
                    dump.root = dump.levelOrderInsertion(arr1, dump.root, 0);

                    List<Integer> deep_list = new ArrayList<Integer>();

                    for(int i=0;i<n_len;i++){
                        deep_list.add(arr1[i]);
                    }

                    dump.inOrderInsertion(dump.root, deep_list);

                    if(dump.root.data > node.parent.data){
                        node.parent.right =dump.root;
                    } else {
                        node.parent.left = dump.root;
                    }

                }
            } else {

            }

            ignoreSubTreeA3(node.right, elMap, interVal, S);

        }
        return S;
    }


    void ALgorithmA2(TreeCreate t2, TreeCreate S, int n,List<Integer> leftT , List<Integer> rightT , HashMap<String, String> TMap1 ) throws CloneNotSupportedException {

        S.ignoreSubTrees(S.root, TMap1);

        double x = t2.rootCalc(n, t2.root.level+1);

       // System.out.println("TRoot = "+ x);

        S.root  = S.rootTop(S.root, (int) x, S.root.level, S);


        int rootTop = S.rotationCount;

        List<Integer> leftList = S.generateLeftFArm(S.root.left);
        List<Integer> rightList = S.generateRightFArm(S.root.right);

        ArrayList<RootNode> noder = S.allLeftFArm(S, S.root.left);

        int lArmm = S.rotationCount - rootTop;
        S.root.left = null;
        S.root.left = noder.get(0);
        noder.remove(0);

        S.inOrderLeftGen(S.root.left,noder);

        ArrayList<RootNode> noder1 = S.allRightFArm(S, S.root.right);

        int intoRightForearm = S.rotationCount;


        int r = noder1.size();
        S.root.right = null;
        S.root.right = noder1.get(0);
        noder1.remove(0);

        S.inOrderRightGen(S.root.right,noder1);

        S.heightBalance(S.root);

        S.leftParentGen(S.root.left , S.root);



        S.listRotFalse(S.root, leftT, rightT);
       // System.out.println("S after generating left and right forearms :");
        //S.print2D(S.root);






//        //Duplicate Tree
        TreeCreate treeMore = new TreeCreate();
        RootNode S_Right = (RootNode) S.root.clone();
        treeMore.root = S_Right;

        treeMore.heightBalance(treeMore.root);

        treeMore.leftParentGen(treeMore.root.right, treeMore.root);


        int original_node = treeMore.root.data;


        S.AlgoAL2(S.root.left,  S, t2.root.level+1, n, leftT, t2);

        S.leftParentGen(S.root.left , S.root);

        treeMore.root.left = S.root;
        int lRott  = S.rotationCount - lArmm;


        treeMore.AlgoAR2(treeMore.root.right,  treeMore, t2.root.level+1, n, r, rightT, t2);

        TreeCreate fin = new TreeCreate();
        fin.root = new RootNode(original_node);
        fin.root.left = S.root;
        fin.root.right = treeMore.root.right;


        //System.out.println("Total Rotations for A : "+ (S.rotationCount + treeMore.rotationCount));
        System.out.println("My A3: "+ (rootTop + 2*(lRott+ lArmm)-1));

    }

    }



