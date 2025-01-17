package level.tools;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** Specifies how large a level should be. Exact definition is interpreted by the generator. */
public enum LevelSize {
    SMALL,
    MEDIUM,
    LARGE;

    private static final List<LevelSize> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static LevelSize randomSize() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
