package skywolf46.bss.util;

import java.util.ArrayList;

public class AppendableList<K> extends ArrayList<K> {

    public AppendableList<K> append(K k){
        add(k);
        return this;
    }
}
