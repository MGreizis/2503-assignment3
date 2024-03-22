import java.util.Scanner;
import java.util.Comparator;
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
   private BST<Token> wordsByLength = new BST<Token>(new TokenLengthComparator());

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
      printTopWords(wordsByFreqDesc, 10);
      System.out.println();

      System.out.println("10 Longest");
      printTopWords(wordsByLength, 10);
      System.out.println();

      Token longestWord = findLongestWord();
      System.out.println("The longest word is " + longestWord + " : " + longestWord.getCount());
      System.out.println("The average word length is " + avgLength());
      System.out.println();

      System.out.println("All");
      Iterator<Token> iterator = wordsByNaturalOrder.iterator();
      while (iterator.hasNext()) {
         Token token = iterator.next();
         System.out.println(token.getWord() + " : " + token.getLength() + " : " + token.getCount());
      }
      System.out.println();

      printTreeHeightStatistics("Alphabetic Tree", wordsByNaturalOrder);
      printTreeHeightStatistics("Frequency Tree", wordsByFreqDesc);
      printTreeHeightStatistics("Length Tree", wordsByLength);
   }

   /**
    * Finds the longest word in the collection of words by length.
    *
    * @return the longest word in the collection, or null if the collection is
    *         empty
    */
   private Token findLongestWord() {
      Token longestWord = null;
      int maxLength = 0;
      Iterator<Token> iterator = wordsByLength.iterator();
      while (iterator.hasNext()) {
         Token token = iterator.next();
         if (token.getLength() > maxLength) {
            longestWord = token;
            maxLength = token.getLength();
         }
      }
      return longestWord;
   }

   /**
    * Reads a file and processes each word in it.
    *
    * @param None
    * @return None
    */
   private void readFile() {
      while (inp.hasNext()) {
         String word = inp.next().toLowerCase().trim().replaceAll("[^a-z]", "");
         if (word.length() > 0) {
            totalwordcount++;
            if (!isStopWord(word)) {
               Token newToken = new Token(word);
               BSTNode<Token> existingNode = wordsByNaturalOrder.find(newToken);
               if (existingNode != null) {
                  existingNode.getData().incrCount();
               } else {
                  wordsByNaturalOrder.insert(newToken);
                  wordsByFreqDesc.insert(newToken);
                  wordsByLength.insert(newToken);
               }
            }
         }
      }
   }

   /**
    * Checks if the given word is a stop word.
    *
    * @param word the word to be checked
    * @return true if the word is a stop word, false otherwise
    */
   private boolean isStopWord(String word) {
      for (String stopWord : stopwords) {
         if (stopWord.equals(word)) {
            stopwordcount++; // Increment stop words count
            return true;
         }
      }
      return false;
   }

   /**
    * A description of the entire Java function.
    *
    * @param bst   description of parameter
    * @param count description of parameter
    * @return description of return value
    */
   private void printTopWords(BST<Token> bst, int count) {
      Iterator<Token> iterator = bst.iterator();
      int printed = 0;
      while (iterator.hasNext() && printed < count) {
         Token token = iterator.next();
         System.out.println(token.getWord() + " : " + token.getLength() + " : " + token.getCount());
         printed++;
      }
   }

   private void printTreeHeightStatistics(String treeName, BST<Token> bst) {
      System.out.println(treeName + ": (Optimum Height: " +
            optHeight(bst.size()) + ") (Actual Height: " + bst.height() + ")");
   }

   /* Create the frequency and length lists. */
   private void createFreqLists() {
      Iterator<Token> iterator = wordsByNaturalOrder.iterator();
      while (iterator.hasNext()) {
         Token token = iterator.next();
         if (token.getCount() >= 3) {
            wordsByFreqDesc.insert(token);
         }
         wordsByLength.insert(token);
      }
   }

   /* Calculate the average length of words stored the wordsByNaturalOrder tree */
   private int avgLength() {
      int totalLength = 0;
      int wordCount = 0;
      Iterator<Token> iterator = wordsByNaturalOrder.iterator();
      while (iterator.hasNext()) {
         Token token = iterator.next();
         totalLength += token.getLength() * token.getCount();
         wordCount += token.getCount();
      }

      return wordCount > 0 ? totalLength / wordCount : 0;
   }

   /* Remove stop words from the tree. */
   private void removeStop() {
      for (String stopWord : stopwords) {
         Token stopToken = new Token(stopWord);
         BSTNode<Token> existingNode = wordsByNaturalOrder.find(stopToken);
         if (existingNode != null) {
            wordsByNaturalOrder.delete(existingNode.getData());
         }
      }
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

   private class TokenLengthComparator implements Comparator<Token> {
      /**
       * Compares two tokens based on their length and word.
       *
       * @param token1 the first token to compare
       * @param token2 the second token to compare
       * @return a negative integer, zero, or a positive integer as the first token is
       *         less than, equal to, or greater than the second
       */
      @Override
      public int compare(Token token1, Token token2) {
         int lengthCompare = Integer.compare(token2.getLength(), token1.getLength());

         if (lengthCompare != 0) {
            return lengthCompare;
         } else {
            return token1.getWord().compareTo(token2.getWord());
         }
      }
   }

   /* Run the program. */
   private void run() {
      readFile();
      createFreqLists();
      removeStop();
      printResults();
   }
}
