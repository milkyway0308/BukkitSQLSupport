package skywolf46.bss.abstraction;

import skywolf46.bss.api.SQLTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractSQLType<T> {
    public abstract void insert(PreparedStatement con, int index, T item) throws SQLException;

    public abstract T extract(ResultSet rs, int index) throws SQLException;

    public abstract void create(SQLTable.SQLTableBuilder.SQLTableColumn column);

    public abstract String getType();
}
