package skywolf46.bss.types.impl.minecraft;

import org.bukkit.Location;
import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;
import skywolf46.bss.util.bytesupportstream.ByteSupportOutputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationType extends AbstractSQLType<Location> {
    @Override
    public void insert(PreparedStatement con, int index, Location item) throws SQLException {
        ByteSupportOutputStream bsos = new ByteSupportOutputStream();
        bsos.writeUTF(item.getWorld().getName());
        bsos.writeDouble(item.getX());
        bsos.writeDouble(item.getY());
        bsos.writeDouble(item.getZ());
        con.setBytes(index, bsos.closeStream());
    }

    @Override
    public Location extract(ResultSet rs, int index) throws SQLException {
        byte[] bx = rs.getBytes(index);
        return null;
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "BINARY";
    }
}
