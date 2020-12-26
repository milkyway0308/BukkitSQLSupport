package skywolf46.bss.types.impl.java.natives;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerType extends AbstractSQLType<Integer> {
    @Override
    public void insert(PreparedStatement con, int index, Integer item) throws SQLException {
        con.setInt(index, item);
    }

    @Override
    public Integer extract(ResultSet rs, int index) throws SQLException {
        return rs.getInt(index);
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "INTEGER";
    }
}
