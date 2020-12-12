package skywolf46.bss.util;

import java.util.HashMap;

public class PairMap<K, V> extends HashMap<K, V> {


    public PairMap<K, V> insert(K k, V v) {
        put(k, v);
        return this;
    }


}
