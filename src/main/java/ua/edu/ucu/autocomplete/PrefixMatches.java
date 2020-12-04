package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;
import ua.edu.ucu.collections.iterable.StringKeeper;

import java.util.ArrayList;
import java.util.Locale;


public class PrefixMatches {

    private final Trie trie;
    private final static int LONGEST_WORD_EVER = 189819;
    private final static int MIN_PREF_LEN = 3;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        String[] words;
        for (String str: strings) {
            words = str.split(" ");

            for (String word: words) {
                if (word.length() > 2) {
                    // convert the word to lowercase for more
                    // proper autocomplete work
                    this.trie.add(new Tuple(word.toLowerCase(Locale.ENGLISH),
                            word.length()));
                }
            }
        }
        return size();
    }

    public boolean contains(String word) {
        if (word == null || word.equals("")) {
            throw new IllegalArgumentException(
                    "Empty strings are not maintained.");
        }
        return this.trie.contains(word.toLowerCase(Locale.ENGLISH));
    }

    public boolean delete(String word) {
        if (word == null || word.equals("")) {
            throw new IllegalArgumentException(
                    "Empty strings are not maintained.");
        }
        return this.trie.delete(word.toLowerCase(Locale.ENGLISH));
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref == null || pref.length() < 2) {
            throw new IllegalArgumentException("" +
                    "Prefix should have >= 2 characters");
        }

        return wordsWithPrefix(pref, LONGEST_WORD_EVER);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref == null || pref.length() < 2) {
            throw new IllegalArgumentException("" +
                    "Prefix should have >= 2 characters");
        }
        if (k <= 0) {
            throw new IllegalArgumentException("" +
                    "Indicator k has to be positive valued integer");
        }

        int minPrefLen = pref.length();
        if (pref.length() == 2) {
            minPrefLen = MIN_PREF_LEN;
        }

        StringKeeper allWordsWithPrefix =
                (StringKeeper) this.trie.wordsWithPrefix(pref);
        ArrayList<String> wordsThatMatch = new ArrayList<>();

        int wordLength;
        for (String word: allWordsWithPrefix.toArray()) {
            wordLength = word.length();
            if (minPrefLen <= wordLength && wordLength <= minPrefLen + k - 1) {
                wordsThatMatch.add(word);
            }
        }

        return wordsThatMatch;
    }

    public int size() {
        return this.trie.size();
    }
}
