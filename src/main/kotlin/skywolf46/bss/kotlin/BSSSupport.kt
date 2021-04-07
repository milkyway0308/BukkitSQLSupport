package skywolf46.bss.kotlin

import skywolf46.bss.api.SQLTable
import java.sql.Connection


fun Connection.tableOf(table: String) = SQLTable.of(this, table)
