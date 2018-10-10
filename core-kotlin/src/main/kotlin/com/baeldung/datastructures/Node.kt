package com.baeldung.datastructures

class Node(
        var key: Int,
        var left: Node? = null,
        var right: Node? = null) {

    /**
     * Return a node with given value. If no such node exists, return null.
     * @param value
     */
    fun findByValue(value: Int): Node? = when {
        this.key > value -> left?.findByValue(value)
        this.key < value -> right?.findByValue(value)
        else -> this

    }

    /**
     * Insert a given value into the tree.
     * After insertion, the tree should contain a node with the given value.
     * If the tree already contains the given value, nothing is performed.
     * @param value
     */
    fun insert(value: Int) {
        if (value > this.key) {
            if (this.right == null) {
                this.right = Node(value)
            } else {
                this.right?.insert(value)
            }
        } else if (value < this.key) {
            if (this.left == null) {
                this.left = Node(value)
            } else {
                this.left?.insert(value)
            }
        }
    }

    /**
     * Remove the given value from the tree.
     *
     * After this operation the tree should contain no node with the value.
     *
     * If that value his not present, no action is performed.
     *
     * @param value
     */
    fun delete(value: Int) {
        if (key == value) {
            val leftPresent = left != null
            val rightPresent = right != null

            if (!leftPresent && !rightPresent) {
                throw IllegalStateException("Can not remove the root node without children")
            }
            if (leftPresent && rightPresent) {

            } else if (leftPresent) {
                key = left!!.key
                right = left!!.right
                left = left!!.left
            } else if (rightPresent) {
                key = right!!.key
                left = right!!.left
                right = right!!.right
            }
        } else if (key > value) {
            if (left?.key == value) {
                removeLeftChild()
            } else {
                left?.delete(value)
            }


        } else if (key < value) {
            if (right?.key == value) {
                removeRightChild()
            } else {
                right?.delete(value)
            }
        }
    }

    /**
     * Return a parent of a node with a maximal key. If no such node exists, null is returned.
     * @param n tree in which the search should be performed
     * @return node with a non-null "right" child or null
     */
    private fun findParentOfMaxNode(n: Node): Node? {
        return n.right?.let { if (it.right != null) findParentOfMaxNode(it) else it }
    }

    private fun removeRightChild() {


    }

    private fun removeLeftChild() {
        val maxNode = findParentOfMaxNode(left!!)!!
        if (maxNode.key == left!!.key) {
            if (left!!.left != null) {
                left!!.key = left!!.left!!.key
            } else {
                left = null
            }
        } else {
            left!!.key = maxNode.key

        }

    }

    private fun deleteWithParent(value: Int, node: Node, parent: Node?) {
        if (node.key > value) {
            node.left?.let { deleteWithParent(value, it, this) }
        } else if (node.key < value) {
            node.right?.let { deleteWithParent(value, it, this) }
        } else {
            deleteNode(node, parent)
        }
    }

    private fun deleteNode(node: Node, parent: Node?) {
        val l = node.left
        val r = node.right
        if (parent == null) {
            if (l == null && r == null) {
                throw IllegalStateException("Can not remove the root node without children")
            }
            if (l != null && r == null) {
                node.key = l.key
                node.left = null
            } else if (l == null && r != null) {
                node.key = r.key
                node.right = null
            } else {
                val n = nodeWithMaxChild(node)
                node.key = n.key
                n.right = null
            }
        } else {
            if (l == null && r == null) {
                if (parent.left?.key == node.key) {
                    parent.left = null
                } else if (parent.right?.key == node.key) {
                    parent.right = null
                } else {
                    throw IllegalStateException("Parent does not contain the node")
                }
            } else if (l != null && r == null) {
                node.key = l.key
                node.left = null
            } else if (l == null && r != null) {
                node.key = r.key
                node.right = null
            } else {
                val n = nodeWithMaxChild(node)
                node.key = n.key
                n.right = null
            }


        }
    }

    private fun nodeWithMaxChild(node: Node): Node {

        return node.right?.let { r -> r.right?.let { nodeWithMaxChild(r) } ?: node }
                ?: throw IllegalArgumentException("The argument must have a right child")
    }

}
