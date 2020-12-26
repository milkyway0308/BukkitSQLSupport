package skywolf46.bss.types.impl.java.array;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ByteArrayType extends AbstractSQLType<byte[]> {
    @Override
    public void insert(PreparedStatement con, int index, byte[] item) throws SQLException {
        con.setBytes(index, item);
    }

    @Override
    public byte[] extract(ResultSet rs, int index) throws SQLException {
        return rs.getBytes(index);
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "BLOB";
    }
}
