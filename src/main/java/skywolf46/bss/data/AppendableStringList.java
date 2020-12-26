package skywolf46.bss.data;

import skywolf46.bss.util.AppendableList;

public class AppendableStringList extends AppendableList<String> {

    @Override
    public AppendableStringList append(String s) {
        return (AppendableStringList) super.append(s);
    }
}
