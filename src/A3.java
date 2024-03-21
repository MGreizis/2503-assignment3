import java.util.Scanner;
import java.util.Iterator;

/**
 * COMP 2503 Winter 2024 Assignment 3
 * 
 * This program reads a text file and compiles a list of the
 * words together with the frequency of the words.
 * Use a BST for storing the words.
 * Words from a standard list of stop words are then deleted.
 * 
 * BSTs with alternative orderings are constructed to show the
 * required output.
 */

public class A3 {
   /*
    * The lists (trees) of words. Alphabetic, by Frequency
    * and by length.
    */
   private BST<Token> wordsByNaturalOrder = new BST<Token>();
   private BST<Token> wordsByFreqDesc = new BST<Token>(Token.CompFreqDesc);
   private BST<Token> wordsByLength = new BST<Token>(Token.CompLengthDesc);

   // there are 103 stopwords in this list
   private String[] stopwords = {
         "a", "about", "all", "am", "an", "and", "any", "are", "as", "at",
         "be", "been", "but", "by", "can", "cannot", "could", "did", "do", "does",
         "else", "for", "from", "get", "got", "had", "has", "have", "he", "her",
         "hers", "him", "his", "how", "i", "if", "in", "into", "is", "it",
         "its", "like", "more", "me", "my", "no", "now", "not", "of", "on",
         "one", "or", "our", "out", "said", "say", "says", "she", "so", "some",
         "than", "that", "thats", "the", "their", "them", "then", "there", "these", "they", "this",
         "to", "too", "us", "upon", "was", "we", "were", "what", "with", "when",
         "where", "which", "while", "who", "whom", "why", "will", "you", "your", "up", "down", "left", "right",
         "man", "woman", "would", "should", "dont", "after", "before", "im", "men"
   };

   private int totalwordcount = 0;
   private int stopwordcount = 0;

   private Scanner inp = new Scanner(System.in);

   public static void main(String[] args) {
      A3 a3 = new A3();
      a3.run();
   }

   private void printResults() {
      System.out.println("Total Words: " + totalwordcount);
      System.out.println("Unique Words: " + wordsByNaturalOrder.size());
      System.out.println("Stop Words: " + stopwordcount);
      System.out.println();
      System.out.println("10 Most Frequent");

      /*
       * TODO:
       * Use an iterator to traverse the wordsByFreqDesc in-order, and print the first
       * 10
       */

      System.out.println();
      System.out.println("10 Longest");

      /*
       * TODO:
       * Use an iterator to traverse the wordsByLength in-order, and print the first
       * 10
       */

      System.out.println();
      System.out.println("The longest word is " + wordsByLength.min());
      System.out.println("The average word length is " + avgLength());
      System.out.println();
      System.out.println("All");

      /*
       * TODO:
       * Use an iterator to traverse the wordsByNaturalOrder in-order, and print all
       * elements in the tree
       */

      System.out.println();

      System.out.println("Alphabetic Tree: (Optimum Height: " +
            optHeight(wordsByNaturalOrder.size()) + ") (Actual Height: "
            + wordsByNaturalOrder.height() + ")");
      System.out.println("Frequency Tree: (Optimum Height: " +
            optHeight(wordsByFreqDesc.size()) + ") (Actual Height: "
            + wordsByFreqDesc.height() + ")");
      System.out.println("Length Tree: (Optimum Height: " +
            optHeight(wordsByLength.size()) + ") (Actual Height: "
            + wordsByLength.height() + ")");
   }

   /*
    * Read the file and add words to the list/tree.
    */
   private void readFile() {
      // some starter code for this method is provided:

      while (inp.hasNext()) {
         // get the next word, convert to lower case, strip out blanks and non
         // alphabetic characters.
         String word = inp.next().toLowerCase().trim().replaceAll("[^a-z]", "");

         if (word.length() > 0) {
            // TODO:
            // Create a new token object, if not already in the wordsByNaturalOrder,
            // add the token to the BST, otherwise, increase the frequency count of the
            // object already in the tree.
            Token token = new Token(word);
            wordsByNaturalOrder.insert(token);
            totalwordcount++;
         }
      }
   }

   /* Create the frequency and length lists. */
   private void createFreqLists() {
      // TODO:
      // Use your implementation of the iterator interface
      // for the BST class.

      // Make sure you only only add words that have occurred more than twice
      // to the tree ordered by word frequency.

      // All words in the original tree must be added to tree ordered by word length
   }

   /* Calculate the average length of words stored the wordsByNaturalOrder tree */
   private int avgLength() {
      // TODO:
      return 0;
   }

   /* Remove stop words from the tree. */
   private void removeStop() {
      // TODO:
   }

   /*
    * Calculate the optimal height for a tree of size n.
    * Round to an int.
    */
   private int optHeight(int n) {
      double h = Math.log(n + 1) / Math.log(2) - 1;
      if (Math.round(h) < h)
         return (int) Math.round(h) + 1;
      else
         return (int) Math.round(h);
   }

   /* Run the program. */
   private void run() {
      readFile();
      removeStop();
      createFreqLists();
      printResults();
   }
}
