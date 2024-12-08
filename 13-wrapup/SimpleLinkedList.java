/**
 * simple linked list implementation
 * contains nodes and methods to manipulate the linked list structure
 */
public class SimpleLinkedList {

    /**
     * node class represents an element in the linked list
     * each node contains data and a reference to the next node
     */
    class Node {
        String data;
        Node next;

        /**
         * returns the string representation of the node
         */
        public String toString() {
            return this.data;
        }
    }

    /**
     * head represents the start of the linked list
     */
    Node head;

    /**
     * adds a new node with the given data to the end of the list
     * traverses the list to find the last node and links the new node
     */
    public void add(String data) {
        Node newNode = new Node();
        newNode.data = data;
        if (this.head == null) {
            this.head = newNode;
        } else {
            Node current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    /**
     * finds and returns the middle node of the linked list
     * uses two pointers: slow and fast, where fast moves twice as quickly
     */
    public Node findMiddle() {
        if (head == null) {
            return null;
        }

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * inverts the linked list so that the order of nodes is reversed
     * iteratively adjusts the next pointers of the nodes to invert the list
     */
    public SimpleLinkedList invert() {
        SimpleLinkedList invertedList = new SimpleLinkedList();
        Node current = head;
        Node prev = null;

        while (current != null) {
            Node nextNode = current.next;
            current.next = prev;
            prev = current;
            current = nextNode;
        }

        invertedList.head = prev;
        return invertedList;
    }

    /**
     * returns the string representation of the linked list
     * concatenates the data of each node in the list
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.head != null) {
            Node current = this.head;
            while (current != null) {
                sb.append(String.format("%s", current.data));
                current = current.next;
            }
        }
        return sb.toString();
    }

    /**
     * demonstrates the functionality of the linked list
     * prints the middle node and the inverted list for testing
     */
    public static void main(String[] args) {
        SimpleLinkedList demo = new SimpleLinkedList();

        System.out.println("Middle node when list is empty: " + demo.findMiddle());

        demo.add("A");
        System.out.println("Middle node: " + demo.findMiddle());

        demo.add("B");
        System.out.println("Middle node: " + demo.findMiddle());

        demo.add("C");
        System.out.println("Middle node: " + demo.findMiddle());

        demo.add("D");
        demo.add("E");
        System.out.println("Middle node: " + demo.findMiddle());

        System.out.println("Original list: " + demo);
        System.out.println("Inverted list: " + demo.invert());
    }
}


