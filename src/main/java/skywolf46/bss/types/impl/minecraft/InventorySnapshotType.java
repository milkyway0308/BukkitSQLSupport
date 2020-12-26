package skywolf46.bss.types.impl.minecraft;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;
import skywolf46.bss.util.InventorySnapshot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventorySnapshotType extends AbstractSQLType<InventorySnapshot> {
    @Override
    public void insert(PreparedStatement con, int index, InventorySnapshot item) throws SQLException {
        con.setBytes(index, item.toByte());
    }

    @Override
    public InventorySnapshot extract(ResultSet rs, int index) throws SQLException {
        return new InventorySnapshot(rs.getBytes(index));
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "BLOB";
    }
}
