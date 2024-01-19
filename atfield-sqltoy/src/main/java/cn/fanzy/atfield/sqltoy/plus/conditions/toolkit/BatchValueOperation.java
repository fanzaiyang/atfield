package cn.fanzy.atfield.sqltoy.plus.conditions.toolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BatchValueOperation<T> {

    private final List<T> values;

    public BatchValueOperation(List<T> values) {
        this.values = values;
    }

    public static <T> BatchValueOperation<T> from(List<T> values) {
        return new BatchValueOperation<>(values);
    }

    @SafeVarargs
    public final List<Object[]> toListArray(Function<T, ?>... fieldFunctions) {
        if (fieldFunctions == null || fieldFunctions.length == 0 || values == null || values.size() <= 0) {
            return null;
        }
        int fieldSize = fieldFunctions.length;
        List<Object[]> list = new ArrayList<>();
        Object[] objects;
        for (T t : values) {
            objects = new Object[fieldSize];
            for (int i = 0; i < fieldSize; i++) {
                objects[i] = fieldFunctions[i].apply(t);
            }
            list.add(objects);
        }
        return list;
    }
}
