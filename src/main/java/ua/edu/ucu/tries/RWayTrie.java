package ua.edu.ucu.tries;


import ua.edu.ucu.collections.queue.Queue;
import ua.edu.ucu.collections.iterable.StringKeeper;


public class RWayTrie implements Trie {
    private static final int R = 26;
    private static final char FIRST_ALPH_CH = 'a';
    private static final char LAST_ALPH_CH = 'z';

    private Node root = new Node();

    private static class Node {
        private Object val;
        private final Node[] next = new Node[R];
    }

    /*
    * Throw IllegalArgumentException if character is not a letter.
    */
    private void validateCharacter(char c) {
        if (c < FIRST_ALPH_CH || c > LAST_ALPH_CH) {
            throw new IllegalArgumentException("Not valid character! ");
        }
    }

    @Override
    public void add(Tuple t) {
        this.root = add(this.root, t.term, t.weight, 0);
    }

    /*
    * Add a new node to the trie if there is no such a node.
    * Change value associated with key if in subtrie rooted at node.
    *
    * @source: «Robert Sedgewick, Kevin Wayne Algorithms, 4th Edition Addison»
    *
    * @param: (Node) node - node to insert
    * @param: (String) key - a word to insert
    * @param: (int) value - weight of a word
    * @param: (int) dest - index of a node of where to insert
    *
    * @return: Node
    */
    private Node add(Node node, String key, int value, int dest) {
        Node newNode = node;
        if (node == null)
            newNode = new Node();

        if (dest == key.length()) {
            newNode.val = value;
            return newNode;
        }

        // Use dth key char to identify subtrie.
        char c = key.charAt(dest);
        validateCharacter(c);

        // We subtract FIRST_ALPH_CH from c as our array has bounds [0, 25], while
        // ASCII characters start with 65
        newNode.next[c - FIRST_ALPH_CH] = add(newNode.next[c - FIRST_ALPH_CH], key, value, dest + 1);
        return newNode;
    }

    @Override
    public boolean contains(String word) {
        Node node = get(root, word, 0);
        return node != null;
    }

    /*
     * Return True if such word exists in a trie.
     *
     * @source: «Robert Sedgewick, Kevin Wayne Algorithms, 4th Edition Addison»
     *
     * @param: (String) key - a word to delete
     * @return: boolean
     */
    private Node get(Node node, String key, int dest) {
        if (node == null) {
            return null;
        }

        if (dest == key.length()) {
            return node;
        }

        // Use dth key char to identify subtrie.
        char c = key.charAt(dest);
        return get(node.next[c - FIRST_ALPH_CH], key, dest + 1);
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)) {
            root = delete(root, word, 0);
            return true;
        }
        return false;
    }

    /*
     * Return a node of the trie if such exists and delete its connection.
     *
     * @source: «Robert Sedgewick, Kevin Wayne Algorithms, 4th Edition Addison»
     *
     * @param: (Node) node - node to start with
     * @param: (String) key - a word to delete
     * @param: (int) dest - index of a node of where to start
     *
     * @return: Node
     */
    private Node delete(Node node, String key, int dest) {
        if (node == null) {
            return null;
        }

        if (dest == key.length()) {
            node.val = null;
        } else {
            char c = key.charAt(dest);
            validateCharacter(c);

            node.next[c - FIRST_ALPH_CH] = delete(node.next[c - FIRST_ALPH_CH], key, dest + 1);
        }

        if (node.val != null) {
            return node;
        }

        for (char c = 0; c < R; c++) {
            if (node.next[c] != null) {
                return node;
            }
        }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, s, 0), s, q);

        int queueSize = q.size();
        StringKeeper sk = new StringKeeper(queueSize);

        for (int i = 0; i < queueSize; i++) {
            sk.add((String) q.dequeue(), i);
        }
        return sk;
    }

    /*
     * Add a word to a queue.
     *
     * @source: «Robert Sedgewick, Kevin Wayne Algorithms, 4th Edition Addison»
     *
     * @param: (Node) node - node to start with
     * @param: (String) pre - a word to collect
     * @param: (Queue) q - collection to store elements
     *
     * @return: Null
     */
    private void collect(Node node, String pre, Queue q) {
        if (node == null) {
            return;
        }

        if (node.val != null) {
            q.enqueue(pre);
        }

        for (char c = FIRST_ALPH_CH; c < LAST_ALPH_CH; c++) {
            collect(node.next[c - FIRST_ALPH_CH], pre + c, q);
        }
    }

    @Override
    public int size() {
        return size(this.root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }

        int cnt = 0;
        if (node.val != null)  {
            cnt++;
        }

        for (char c = 0; c < R; c++) {
            cnt += size(node.next[c]);
        }

        return cnt;
    }

}
