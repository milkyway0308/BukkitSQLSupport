package skywolf46.bss.types.impl.minecraft;

import org.bukkit.inventory.ItemStack;
import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;
import skywolf46.bss.util.BukkitUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemStackType extends AbstractSQLType<ItemStack> {
    @Override
    public void insert(PreparedStatement con, int index, ItemStack item) throws SQLException {
        con.setBytes(index, BukkitUtil.toByte(item));
    }

    @Override
    public ItemStack extract(ResultSet rs, int index) throws SQLException {
        return BukkitUtil.fromByte(rs.getBytes(index));
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "BLOB";
    }
}
