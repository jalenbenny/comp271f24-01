import java.util.ArrayList;
//
public class HuffmanEncodingWithHeap {

    private static final int ASCII8 = 256;
    private static final char LEFT = '0';
    private static final char RIGHT = '1';
    private static final String EMPTY = "";
    private static final int BITS_PER_BYTE = 8;

    /**
     * parses a string and produce a frequency count for each of its symbols.
     * 
     * @param message String to parse, one character at a time.
     * @return array with frequency count for each possible ASCII value.
     */
    static public int[] countFrequency(String message) {
        int[] frequencies = new int[ASCII8];
        // no parsing if message is null
        if (message != null) {
            for (int i = 0; i < message.length(); i++) {
                frequencies[(int) message.charAt(i)]++;
            }
        }
        return frequencies;
    }

    /**
     * builds a MinHeap of huffman nodes, each a minimal tree containing a symbol and
     * its frequency as it appears in a string we are looking to compress
     * 
     * @param frequencies int[] with the frequency counts for each ASCII symbol
     * @return a MinHeap of HuffmanNodes, essentially a forest of minimal trees
     */
    public static MinHeap<HuffmanNode> buildForest(int[] frequencies) {
        MinHeap<HuffmanNode> forest = new MinHeap<>();
        for (int asciiCode = 0; asciiCode < frequencies.length; asciiCode++) {
            // only processes characters in the string that we want to compress
            if (frequencies[asciiCode] > 0) {
                forest.insert(new HuffmanNode((char) asciiCode, frequencies[asciiCode]));
            }
        }
        return forest;
    }

    /**
     * apply Huffman's algorithm to organize a MinHeap of Huffman nodes into a
     * Huffman tree. remove the two nodes of least frequency from the forest,
     * make them left and right children for a new node with no symbol and a
     * combined frequency of its children, and put it back to the forest.
     * 
     * @param forest MinHeap of HuffmanNodes with symbol and frequency data
     * @return a HuffmanNode which is the root of the Huffman tree
     */
    public static HuffmanNode buildTree(MinHeap<HuffmanNode> forest) {
        while (forest.size() > 1) {
            // Remove the two nodes with the least frequencies from the forest
            HuffmanNode t1 = forest.removeMin();
            HuffmanNode t2 = forest.removeMin();
            
            // create a new node with combined frequency
            HuffmanNode combined = new HuffmanNode(t1.getFrequency() + t2.getFrequency());
            
            // make the removed nodes the left and right children of the new node
            combined.setLeft(t1);
            combined.setRight(t2);
            
            // Place the new node back into the forest
            forest.insert(combined);
        }
        
        // the only node remaining in the forest is the root of the Huffman tree
        return forest.getMin();
    }

    /**
     * recursive traversal of a Huffman tree to produce an encoding table like in HuffmanEncoding
     * 
     * @param node  Huffman tree node to process
     * @param code  encoding value up to the current point in traversal
     * @param codes String[] with Huffman codes produced so far.
     */
    public static void createEncodingTable(HuffmanNode node, String code, String[] codes) {
        // only process non-null nodes
        if (node != null) {
            // base case when node has a symbol -- we've reached a leaf node in the tree
            if ((int) node.getSymbol() != 0) {
                codes[(int) node.getSymbol()] = code;
            } else {
                // recursive case: intermediate nodes; explore their children 
                // updating the corresponding code with left and right information
                createEncodingTable(node.getLeft(), code + LEFT, codes);
                createEncodingTable(node.getRight(), code + RIGHT, codes);
            }
        }
    }

    /**
     * helper method to launch the recursive traversal of the Huffman tree
     * 
     * @param node The root of the Huffman tree
     * @return String[] indexed by ASCII values (0-255) containing the Huffman code
     *         for the corresponding character
     */
    public static String[] createEncodingTable(HuffmanNode node) {
        String[] codes = new String[ASCII8];
        createEncodingTable(node, EMPTY, codes);
        return codes;
    }

    /**
     * prints out the Huffman codes for ASCII symbols in a given message
     * 
     * @param codes String[] the Huffman codes indexed by ASCII value
     */
    public static void displayCodes(String[] codes) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] != null) {
                System.out.printf("\n '%s' --> %-10s", (char) i, codes[i]);
            }
        }
    }

    /**
     * Computes the number of bits required for the compressed message
     * 
     * @param message String to compress
     * @param codes   Huffman codes for compression
     * @return int with length of compressed message
     */
    public static int computeCompressionLength(String message, String[] codes) {
        int compressionLength = 0;
        for (int i = 0; i < message.length(); i++) {
            compressionLength += codes[(int) message.charAt(i)].length();
        }
        return compressionLength;
    }

    /**
     * Prints a brief report about the length of the compressed message v. the
     * length of the uncompressed message.
     * 
     * @param message string to compress
     * @param codes   Huffman codes for compression
     */
    public static void reportEfficiency(String message, String[] codes) {
        System.out.printf("\nCompressed message requires %d bits versus %d bits for ASCII encoding.\n",
                computeCompressionLength(message, codes), message.length() * BITS_PER_BYTE);
    }

    /**
     * orchestrate the Huffman encoding of a string
     * 
     * @param message String to encode/compress
     */
    static void encode(String message) {
        int[] frequencies = countFrequency(message);
        MinHeap<HuffmanNode> forest = buildForest(frequencies);
        HuffmanNode huffmanTreeRoot = buildTree(forest);
        String[] codes = createEncodingTable(huffmanTreeRoot);
        displayCodes(codes);
        reportEfficiency(message, codes);
    }

    
    public static void main(String[] args) {
        String message = "to whom much is given much is tested get arrested guess until he get the message i feel the pressure under more scrutiny and what i do act more stupidly";
        encode(message);
    }
}



