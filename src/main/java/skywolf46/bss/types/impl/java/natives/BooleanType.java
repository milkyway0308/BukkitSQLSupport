package skywolf46.bss.types.impl.java.natives;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanType extends AbstractSQLType<Boolean> {
    @Override
    public void insert(PreparedStatement con, int index, Boolean item) throws SQLException {
        con.setBoolean(index, item);
    }

    @Override
    public Boolean extract(ResultSet rs, int index) throws SQLException {
        return rs.getBoolean(index);
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "BIT";
    }
}
