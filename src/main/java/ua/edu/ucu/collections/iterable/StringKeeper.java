package ua.edu.ucu.collections.iterable;

import java.util.Iterator;

public class StringKeeper implements Iterable<String>{
    private final String[] keeper;
    private final int length;

    public StringKeeper(int length) {
        this.length = length;
        this.keeper = new String[length];
    }

    public void add(String word, int index) {
        if (index >= 0 && index < keeper.length) {
            this.keeper[index] = word;
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int elementIndex;

            @Override
            public boolean hasNext() {
                return elementIndex < length;
            }

            @Override
            public String next() {
                if (this.hasNext()) {
                    elementIndex++;
                    return keeper[elementIndex];
                }
                return null;
            }
        };
    }

    public String[] toArray() {
        return this.keeper.clone();
    }
}
