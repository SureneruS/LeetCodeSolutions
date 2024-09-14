import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Boolean isWord;
    Map<Character, TrieNode> children;

    TrieNode() {
        this.isWord = false;
        this.children = new HashMap<>();
    }

}

class Trie {
    private TrieNode root;
    public Trie() {
        this.root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode cursor = this.root;
        for (var ch : word.toCharArray()) {
            cursor.children.putIfAbsent(ch, new TrieNode());
            cursor = cursor.children.get(ch);
        }
        cursor.isWord = true;
    }
    
    public boolean search(String word) {
        var matchingNode = searchNode(word);
        return matchingNode != null && matchingNode.isWord;
    }
    
    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    private TrieNode searchNode(String word) {
        TrieNode cursor = this.root;
        for (var ch : word.toCharArray()) {
            if (!cursor.children.containsKey(ch)) {
                return null;
            }
            cursor = cursor.children.get(ch);
        }

        return cursor;        
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */