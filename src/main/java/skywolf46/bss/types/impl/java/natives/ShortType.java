package skywolf46.bss.types.impl.java.natives;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShortType extends AbstractSQLType<Short> {
    @Override
    public void insert(PreparedStatement con, int index, Short item) throws SQLException {
        con.setShort(index, item);
    }

    @Override
    public Short extract(ResultSet rs, int index) throws SQLException {
        return rs.getShort(index);
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "SMALLINT";
    }
}
