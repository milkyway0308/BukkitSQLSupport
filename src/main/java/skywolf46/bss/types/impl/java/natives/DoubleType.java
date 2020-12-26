package skywolf46.bss.types.impl.java.natives;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleType extends AbstractSQLType<Double> {
    @Override
    public void insert(PreparedStatement con, int index, Double item) throws SQLException {
        con.setDouble(index, item);
    }

    @Override
    public Double extract(ResultSet rs, int index) throws SQLException {
        return rs.getDouble(index);
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "FLOAT";
    }
}
