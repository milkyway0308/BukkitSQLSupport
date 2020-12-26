package skywolf46.bss.types.impl.java.natives;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LongType extends AbstractSQLType<Long> {
    @Override
    public void insert(PreparedStatement con, int index, Long item) throws SQLException {
        con.setLong(index, item);
    }

    @Override
    public Long extract(ResultSet rs, int index) throws SQLException {
        return rs.getLong(index);
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "BIGINT";
    }
}
