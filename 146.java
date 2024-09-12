import java.util.HashMap;

class DoubleLinkedList {
    int key;
    int val;
    DoubleLinkedList left;
    DoubleLinkedList right;

    DoubleLinkedList() {}

    DoubleLinkedList(int key, int val) {
        this.key = key;
        this.val = val;
    }

    DoubleLinkedList(int key, int val, DoubleLinkedList left, DoubleLinkedList right) {
        this.key = key;
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class LRUCache {
    HashMap<Integer, DoubleLinkedList> data;
    DoubleLinkedList usageListHead;
    Integer capacity;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.data = new HashMap<>();
        DoubleLinkedList tailNode = new DoubleLinkedList(-1, -1);
        this.usageListHead = new DoubleLinkedList(-1, -1, tailNode, tailNode);
        tailNode.left = this.usageListHead;
        tailNode.right = this.usageListHead;
    }
    
    public int get(int key) {
        DoubleLinkedList node = getNode(key);
        return node != null ? node.val : -1;
    }
    
    /*
     * <- HEAD <-> TAIL ->
     * 
     * 
     * <- HEAD <-> 1 <-> 2 <-> 3 <-> TAIL ->
     * <- HEAD <-> 2 <-> 1 <-> 3 <-> TAIL ->
     * <- HEAD <-> 3 <-> 2 <-> 1 <-> TAIL ->
     * <- HEAD <-> 4 <-> 3 <-> 2 <-> 1 <-> TAIL ->
     * <- HEAD <-> 4 <-> 3 <-> 2 <-> TAIL ->
     */
    public void put(int key, int value) {
        DoubleLinkedList existingNode = getNode(key);
        if (existingNode != null) {
            existingNode.val = value;
            return;
        }

        DoubleLinkedList newNode = new DoubleLinkedList(key, value);
        this.data.put(key, newNode);  
        addNodeToHead(newNode);

        if (this.data.size() > capacity) {
            DoubleLinkedList toEvict = usageListHead.left.left;
            data.remove(toEvict.key);
            removeNode(toEvict);
        }
    }

    private DoubleLinkedList getNode(int key) {
        if (!data.containsKey(key)) {
            return null;
        }

        // move node to head
        DoubleLinkedList node = data.get(key);
        removeNode(node);
        addNodeToHead(node);
        return node;
    }

    private void addNodeToHead(DoubleLinkedList node) {
        node.right = usageListHead.right;
        node.left = usageListHead;

        usageListHead.right.left = node;
        usageListHead.right = node;
    }

    private void removeNode(DoubleLinkedList node) {
        node.right.left = node.left;
        node.left.right = node.right;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */