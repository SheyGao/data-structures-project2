package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

// height of left subtree and height of right subtree off by at most 1
// When you insert/delete nodes, if tree is “out of balance” then rotate
public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    public AVLTree(){
        super();
    }

    public class AVLNode extends BSTNode {
        int height;
        public AVLNode(K key, V value){
            super(key, value);
            this.height = 0;
        }
    }

    // insert an AVLNode to the AVLTree
    @Override
    public V insert(K key, V value){
        // BST insert
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V oldValue = find(key);
        this.root = nodeInsert(cast(this.root), key, value);
        return oldValue;
    }

    // helper method
    public AVLNode nodeInsert(AVLNode node, K key, V value){
        // base case
        if (node == null){
            this.size++;
            return new AVLNode(key, value);
        }
        // recursion
        if (key.compareTo(node.key) < 0){
            node.children[0] = nodeInsert(getLeft(node), key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.children[1] = nodeInsert(getRight(node), key, value);
        } else {
            node.value = value;
        }
        //updateHeight(node);
        node.height = Math.max(getHeight(getLeft(node)), getHeight(getRight(node))) + 1;
        return reBalance(node);
    }

    // calculate the height difference between left subtree and right subtree
    private int heightDiff (BSTNode node){
        int diff = getHeight(getLeft(node)) - getHeight(getRight(node));
        return diff;
    }

    // update the height of the node's ancestor
//    private int updateHeight(BSTNode node){
//        AVLNode aNode = cast(node);
//        // base case
//        if (node == null) {
//            return -1;
//        }
//        int leftHeight = updateHeight(getLeft(node));
//        int rightHeight = updateHeight(getRight(node));
//        int newHeight = Math.max(leftHeight, rightHeight) + 1;
//        aNode.height = newHeight;
//        return newHeight;
//    }

    // get the height of a given AVLNode
    private int getHeight(AVLNode node){
        if (node == null){
            return -1;
        }
        return node.height;
    }

    // get the left child of the current node
    private AVLNode getLeft (BSTNode node){
        AVLNode left = cast(node.children[0]);
        return left;
    }

    // get the right child of the current node
    private AVLNode getRight (BSTNode node){
        AVLNode right = cast(node.children[1]);
        return right;
    }

    // check if heightDiff <= 1, if not, make rotations to ensure balance.
    private AVLNode reBalance(AVLNode node){
        if (heightDiff(node) > 1){
            // case LL: rightRotation
            if (heightDiff(getLeft(node)) > 0){
                node = rotateRight(node);
                // case RL: leftRotation + rightRotation
            } else {
                node.children[0] = rotateLeft(getLeft(node));
                node = rotateRight(node);
            }
        } else if (heightDiff(node) < -1) {
            // case RR: leftRotation
            if (heightDiff(getRight(node)) < 0) {
                node = rotateLeft(node);
                // case LR: rightRotation + leftRotation
            } else {
                node.children[1] = rotateRight(getRight(node));
                node = rotateLeft(node);
            }
        }
        //updateHeight(node);
        node.height = Math.max(getHeight(getLeft(node)), getHeight(getRight(node))) + 1;
        return node;
    }

    // right rotation
    private AVLNode rotateRight(BSTNode node){
        AVLNode node1 = cast(node);
        AVLNode left = getLeft(node);
        AVLNode rOfLeft = getRight(left);
        left.children[1] = node;
        node.children[0] = rOfLeft;
        //updateHeight(left);
        node1.height = Math.max(getHeight(getLeft(node)), getHeight(getRight(node))) + 1;
        //updateHeight(node);
        left.height = Math.max(getHeight(getLeft(node)), getHeight(getRight(node))) + 1;
        return left;
    }

    // left rotation
    private AVLNode rotateLeft(BSTNode node){
        AVLNode node1 = cast(node);
        AVLNode right = getRight(node);
        AVLNode lOfRight = getLeft(right);
        right.children[0] = node;
        node.children[1] = lOfRight;
        //updateHeight(right);
        right.height = Math.max(getHeight(getLeft(node)), getHeight(getRight(node))) + 1;
        //updateHeight(node);
        node1.height = Math.max(getHeight(getLeft(node)), getHeight(getRight(node))) + 1;
        return right;
    }

    // cast a BSTNode to AVLNode
    private AVLNode cast(BSTNode node){
        return (AVLNode) node;
    }

}
