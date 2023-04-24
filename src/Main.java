import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static int niknameAmount = 100_000;
    public static String letters = "abc";
    public static int length = 3;
    public static AtomicInteger amount3 = new AtomicInteger();
    public static AtomicInteger amount4 = new AtomicInteger();
    public static AtomicInteger amount5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[niknameAmount];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText(letters, length + random.nextInt(3));
//            System.out.println(texts[i]);
        }
        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    countWordsWithTextLength(text);
                }
            }
//           System.out.println(amount3);
        });
        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isWordContainOneLetter(text)) {
                    countWordsWithTextLength(text);
                }
            }
//            System.out.println(amount4);
        });
        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isWordConsistsOfSortedLetters(text)) {
                    countWordsWithTextLength(text);
                }
            }
//            System.out.println(amount5);
        });
        List<Thread> threads = new ArrayList<>();
        threads.add(thread1);
        threads.add(thread2);
        threads.add(thread3);
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Красивых слов с длиной 3: " + amount3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + amount4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + amount5 + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String word) {
        int i = 0, j = word.length() - 1;
        while (i < j) {
            if (word.charAt(i) != word.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static boolean isWordContainOneLetter(String word) {
        char firstLetter = word.charAt(0);
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) != firstLetter) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWordConsistsOfSortedLetters(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) > word.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static void countWordsWithTextLength(String word) {
        if (word.length() == 3) {
            amount3.incrementAndGet();
        } else if (word.length() == 4) {
            amount4.incrementAndGet();
        } else if (word.length() == 5) {
            amount5.incrementAndGet();
        }
    }

}
