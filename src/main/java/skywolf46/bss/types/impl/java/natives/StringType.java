package skywolf46.bss.types.impl.java.natives;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringType extends AbstractSQLType<String> {
    @Override
    public void insert(PreparedStatement con, int index, String item) throws SQLException {
        con.setString(index, item);
    }

    @Override
    public String extract(ResultSet rs, int index) throws SQLException {
        return rs.getString(index);
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {
        column.setSize(100);
    }

    @Override
    public String getType() {
        return "VARCHAR";
    }
}
