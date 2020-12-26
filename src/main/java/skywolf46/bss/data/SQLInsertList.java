package skywolf46.bss.data;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.util.AppendableList;
import skywolf46.bss.util.ItemPair;

public class SQLInsertList extends AppendableList<ItemPair<AbstractSQLType, Object>> {

    @Override
    public SQLInsertList append(ItemPair<AbstractSQLType, Object> item) {
        super.append(item);
        return this;
    }

    public SQLInsertList append(AbstractSQLType sql, Object item) {
        return append(new ItemPair<>(sql, item));
    }
}
