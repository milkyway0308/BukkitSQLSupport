package skywolf46.bss.data;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.util.AppendableList;
import skywolf46.bss.util.ItemTriple;

public class SQLSelectList extends AppendableList<ItemTriple<AbstractSQLType, String, Object>> {

    @Override
    public SQLSelectList append(ItemTriple<AbstractSQLType, String, Object> item) {
        super.append(item);
        return this;
    }

    public SQLSelectList append(AbstractSQLType type, String str, Object object) {
        return append(new ItemTriple<>(type, str, object));
    }
}
