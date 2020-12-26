package skywolf46.bss.types.impl.java;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UUIDType extends AbstractSQLType<UUID> {
    @Override
    public void insert(PreparedStatement con, int index, UUID item) throws SQLException {
        con.setString(index, item.toString());
    }

    @Override
    public UUID extract(ResultSet rs, int index) throws SQLException {
        return UUID.fromString(rs.getString(index));
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {
        column.setSize(36);
    }

    @Override
    public String getType() {
        return "VARCHAR";
    }
}
