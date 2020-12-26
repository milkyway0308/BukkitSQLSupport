package skywolf46.bss.util;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PairMap<K, V> extends LinkedHashMap<K, V> {


    public PairMap<K, V> insert(K k, V v) {
        put(k, v);
        return this;
    }


}
