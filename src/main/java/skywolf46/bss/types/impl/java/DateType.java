package skywolf46.bss.types.impl.java;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DateType extends AbstractSQLType<Date> {
    @Override
    public void insert(PreparedStatement con, int index, Date item) throws SQLException {
        con.setDate(index, new java.sql.Date(item.getTime()));
    }

    @Override
    public Date extract(ResultSet rs, int index) throws SQLException {
        return new Date(rs.getDate(index).getTime());
    }

    @Override
    public void create(SQLTable.SQLTableBuilder.SQLTableColumn column) {

    }

    @Override
    public String getType() {
        return "DATE";
    }
}
