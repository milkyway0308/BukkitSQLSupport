package skywolf46.bss.types.impl.java.natives;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ByteType extends AbstractSQLType<Byte> {
    @Override
    public void insert(PreparedStatement con, int index, Byte item) throws SQLException {
        con.setByte(index, item);
    }

    @Override
    public Byte extract(ResultSet rs, int index) throws SQLException {
        return rs.getByte(index);
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "TINYINT";
    }
}
