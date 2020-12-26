package skywolf46.bss.types.impl.java.natives;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FloatType extends AbstractSQLType<Float> {
    @Override
    public void insert(PreparedStatement con, int index, Float item) throws SQLException {
        con.setFloat(index, item);
    }

    @Override
    public Float extract(ResultSet rs, int index) throws SQLException {
        return rs.getFloat(index);
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "REAL";
    }
}
