/*****************************************************
 *	Title: SpellCheck Benchmark Suite (CA1 Edition)
 *	Author: Horstmann / Edited for CA1
 *  Editor: Jan Arno COetzer
 *	Site owner/sponsor: Wiley
 *	Code version: Edited November 2026
 *	Availability: Instructor Companion Site to Big Java: Late Objects
 *	Modified:
 *       - Restructured to sequentially run LinkedList, ArrayList (.contains),
 *         ArrayList (binarySearch), HashSet, and TreeSet.
 *       - Added individual execution targets to separate profiler tracks.
 *****************************************************/

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class SpellCheck {

   public static void main(String[] args) throws FileNotFoundException {
      String dictFile = "words"; // FIXED: Removed .txt extension to match your actual file
      String textFile = "Project-Gutenberg.txt";

      // Load document words once to keep the verification uniform across implementations
      Set<String> documentWords = readWords(textFile);
      System.out.println("Document loaded. Total unique words to check: " + documentWords.size());
      System.out.println("=================================================================");

      // Run 1: LinkedList
      runLinkedListTest(dictFile, documentWords);

      // Run 2: ArrayList
      //runArrayListContainsTest(dictFile, documentWords);

      // Run 3: ArrayList (Collections.binarySearch) - Required by Question (3) & (4)
      //runArrayListBinarySearchTest(dictFile, documentWords);

      // Run 4: HashSet
      //runHashSetTest(dictFile, documentWords);

      // Run 5: TreeSet
      //runTreeSetTest(dictFile, documentWords);
   }


   // 1. LINKED LIST TEST (.contains)

   private static void runLinkedListTest(String filename, Set<String> documentWords) throws FileNotFoundException {
      System.out.println("Starting LinkedList evaluation...");
      Collection<String> dict = new LinkedList<>();
      loadDictionary(filename, dict);

      int misspelt = 0;
      for (String word : documentWords) {
         if (!dict.contains(word)) {
            misspelt++;
         }
      }
      System.out.println("LinkedList Finished. Misspelt: " + misspelt + "\n");
   }


   // 2. ARRAYLIST TEST (.contains baseline)

   private static void runArrayListContainsTest(String filename, Set<String> documentWords) throws FileNotFoundException {
      System.out.println("Starting ArrayList (.contains) evaluation...");
      Collection<String> dict = new ArrayList<>();
      loadDictionary(filename, dict);

      int misspelt = 0;
      for (String word : documentWords) {
         if (!dict.contains(word)) {
            misspelt++;
         }
      }
      System.out.println("ArrayList (.contains) Finished. Misspelt: " + misspelt + "\n");
   }


   // 3. ARRAYLIST TEST (Collections.binarySearch)

   private static void runArrayListBinarySearchTest(String filename, Set<String> documentWords) throws FileNotFoundException {
      System.out.println("Starting ArrayList (Binary Search) evaluation...");
      List<String> dict = new ArrayList<>();
      loadDictionary(filename, dict);

      // Binary search absolutely requires a sorted structure beforehand
      Collections.sort(dict);

      int misspelt = 0;
      for (String word : documentWords) {
         // Performance optimization using O(log n) binary step method
         if (Collections.binarySearch(dict, word) < 0) {
            misspelt++;
         }
      }
      System.out.println("ArrayList Binary Search Finished. Misspelt: " + misspelt + "\n");
   }


   // 4. HASHSET TEST (.contains)

   private static void runHashSetTest(String filename, Set<String> documentWords) throws FileNotFoundException {
      System.out.println("Starting HashSet evaluation...");
      Collection<String> dict = new HashSet<>();
      loadDictionary(filename, dict);

      int misspelt = 0;
      for (String word : documentWords) {
         if (!dict.contains(word)) {
            misspelt++;
         }
      }
      System.out.println("HashSet Finished. Misspelt: " + misspelt + "\n");
   }


   // 5. TREESET TEST (.contains)

   private static void runTreeSetTest(String filename, Set<String> documentWords) throws FileNotFoundException {
      System.out.println("Starting TreeSet evaluation...");
      Collection<String> dict = new TreeSet<>();
      loadDictionary(filename, dict);

      int misspelt = 0;
      for (String word : documentWords) {
         if (!dict.contains(word)) {
            misspelt++;
         }
      }
      System.out.println("TreeSet Finished. Misspelt: " + misspelt + "\n");
   }


   // FILE DISK UTILITIES

   private static void loadDictionary(String filename, Collection<String> storageContainer) throws FileNotFoundException {
      Scanner in = new Scanner(new File(filename));
      in.useDelimiter("[^a-zA-Z]+");
      while (in.hasNext()) {
         storageContainer.add(in.next().toLowerCase());
      }
      in.close();
   }

   public static Set<String> readWords(String filename) throws FileNotFoundException {
      Set<String> words = new HashSet<>();
      Scanner in = new Scanner(new File(filename));
      in.useDelimiter("[^a-zA-Z]+");
      while (in.hasNext()) {
         words.add(in.next().toLowerCase());
      }
      in.close();
      return words;
   }
}
