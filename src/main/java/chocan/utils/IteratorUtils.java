package chocan.utils;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 *
 */
public class IteratorUtils {

    /**
     * Gets the value from the iterator at the given index.
     * @param it The iterator to iterate over.
     * @param index The index of the target element.
     * @param <E>
     * @return The element (from the iterator) at the given index.
     */
    @Nullable
    public static <E> E get(final Iterator<E> it, final int index) {
        if (index < 0) {
            return null;
        }
        E current;
        int i = 0;
        do {
            if (!it.hasNext()) {
                return null;
            }
            current = it.next();
            i++;
        } while (i < index);
        return current;
    }

}
