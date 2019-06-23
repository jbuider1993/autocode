package com.brain.ca.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.brain.ca.template.GenUtils;

public class JDBCUtil {

	private static DatabaseMetaData metadata = null;
	private static Map<Integer, String> fieldTypeMap = new HashMap();
	static {
		fieldTypeMap.put(java.sql.Types.BIGINT, "Long");
		fieldTypeMap.put(java.sql.Types.FLOAT, "double");
		fieldTypeMap.put(java.sql.Types.REAL, "double");
		fieldTypeMap.put(java.sql.Types.DOUBLE, "double");
		fieldTypeMap.put(java.sql.Types.NUMERIC, "java.math.BigDecimal");
		fieldTypeMap.put(java.sql.Types.INTEGER, "Integer");		
		fieldTypeMap.put(java.sql.Types.BOOLEAN, "boolean");
		fieldTypeMap.put(java.sql.Types.BIT, "boolean");
		fieldTypeMap.put(java.sql.Types.BINARY, "byte[]");
		fieldTypeMap.put(java.sql.Types.VARBINARY, "byte[]");
		fieldTypeMap.put(java.sql.Types.TINYINT, "byte");
		fieldTypeMap.put(java.sql.Types.SMALLINT, "Integer");
		fieldTypeMap.put(java.sql.Types.DECIMAL, "java.math.BigDecimal");
		fieldTypeMap.put(java.sql.Types.CHAR, "String");
		fieldTypeMap.put(java.sql.Types.VARCHAR, "String");
		fieldTypeMap.put(java.sql.Types.LONGVARCHAR, "String");
		fieldTypeMap.put(java.sql.Types.DATE, "java.sql.Date");
		fieldTypeMap.put(java.sql.Types.TIME, "	java.sql.Time");
		fieldTypeMap.put(java.sql.Types.TIMESTAMP, "java.sql.Timestamp");
		fieldTypeMap.put(java.sql.Types.JAVA_OBJECT, "Object");
	}

	private static Connection getConnection(String db_url, String db_user, String db_password) throws SQLException {
		Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
		return connection;
	}
	
	public static DatabaseMetaData getMetaData(String db_url, String db_user, String db_password, String dbDriverClass) throws SQLException, ClassNotFoundException {
		Class.forName(dbDriverClass);
		metadata = getConnection(db_url, db_user, db_password).getMetaData();
		return metadata;
	}
	
	public static ArrayList<String> getTables() throws SQLException {
		String table[] = { "TABLE", "VIEW" };
		ResultSet rs = null;
		ArrayList<String> tables = new ArrayList<>();
		rs = metadata.getTables(null, null, null, table);
		while (rs.next()) {
			tables.add(rs.getString("TABLE_NAME"));
		}
		return tables;
	}
	
	public static String getCatalog(String tableName) throws SQLException {
		String type[] = { "TABLE", "VIEW" };
		ArrayList<String> tables = new ArrayList<>();
		ResultSet rs = metadata.getTables(null, null, tableName, type);
		while (rs.next()) {
			return rs.getString("TABLE_CAT");
		}
		return null;	
	}
	
	public String getFieldType(int typeCode) {
        return fieldTypeMap.get(typeCode);
	}
	public static String getFieldType(String typeCode) {
		return fieldTypeMap.get(Integer.parseInt(typeCode));
	}
	
	public static ArrayList<Map<String, Object>> getColumns(String tableName) throws SQLException {	
		 String catalog = getCatalog(tableName);
		 ResultSet columns = metadata.getColumns(catalog, null, tableName, null);            
		 ArrayList<Map<String, Object>> columnsMap = new ArrayList<>();
		 ArrayList<String> primaryKeys = getPrimeryKeys(catalog, tableName);				 				 
		 ArrayList<Map<String, String>> foreignKeys = getImportedKeys(catalog, tableName);
		 while(columns.next())
         {
			 Map<String, Object> column = new HashMap<>();
			 column.put("COLUMN_NAME", columns.getString("COLUMN_NAME"));
			 column.put("DATA_TYPE",  getFieldType(columns.getString("DATA_TYPE"))); 
			 column.put("COLUMN_SIZE",  columns.getString("COLUMN_SIZE"));
			 column.put("DECIMAL_DIGITS",  columns.getString("DECIMAL_DIGITS"));
			 column.put("IS_NULLABLE",  columns.getString("IS_NULLABLE"));
			 column.put("IS_AUTOINCREMENT",  columns.getString("IS_AUTOINCREMENT"));			
			 if ( primaryKeys.contains(columns.getString("COLUMN_NAME"))) {
				 column.put("IS_PRIMARYKEY", true); 
			 } else {
				 column.put("IS_PRIMARYKEY", false); 				 
			 }
			 Map<String, String> foreignKey = getForeignKey(foreignKeys, columns.getString("COLUMN_NAME"));
			 if (foreignKey != null) {
				 column.put("FOREIGNkEY", foreignKey);
			 }
			 columnsMap.add(column);
         }
		 return columnsMap;
	}

	public static ArrayList<String> getPrimeryKeys(String tableName) throws SQLException {
		 String catalog = getCatalog(tableName);
	     return getPrimeryKeys(catalog, tableName);
	}
	
	public static Map<String, Object> getPrmKeyInfo(String tableName) throws SQLException {
		ArrayList<Map<String, Object>> columns = new ArrayList<>();
		columns = getColumns(tableName);
		ArrayList<Map<String, Object>> keys = new ArrayList<>();
		for (Map<String, Object> column : columns) {
	        if ((boolean)column.get("IS_PRIMARYKEY")) {
	        	Map<String, Object> key = new HashMap<>();
	        	keys.add(column);
	        }			
		}
		Map<String, Object> retKeyInfo = new HashMap<>();
		if (keys.size() == 0) {
			return null;
		}
		if (keys.size() > 1) {
			String keyClassName = "";
			for (Map<String, Object> map : keys) {
				keyClassName = keyClassName + StringUtils.capitalize(GenUtils.getCombinName((String)map.get("COLUMN_NAME")));
			}
			keyClassName = keyClassName + "Identity";
			retKeyInfo.put("EMBADABLE", true);
			retKeyInfo.put("TYPE", keyClassName);			
		}  else {
			retKeyInfo.put("TYPE", keys.get(0).get("DATA_TYPE"));						
		}
		retKeyInfo.put("KEYS", keys);
		return retKeyInfo;
	}
	
	public static ArrayList<String> getPrimeryKeys(String catalog, String tableName) throws SQLException {
		ResultSet primaryKeys = metadata.getPrimaryKeys(catalog, null, tableName);
		ArrayList<String> keys = new ArrayList<>();
		while(primaryKeys.next())
        {
			keys.add(primaryKeys.getString("COLUMN_NAME"));
        }
		return keys;
	}
	
	public static ArrayList<Map<String, String>> getImportedKeys(String tableName) throws SQLException {
    	 String catalog = getCatalog(tableName);
    	 return getImportedKeys(catalog, tableName);
	}
	
	public static ArrayList<Map<String, String>> getImportedKeys(String catalog, String tableName) throws SQLException {
		 ResultSet importKeys = metadata.getImportedKeys(catalog, null, tableName);
		 ArrayList<Map<String, String>> keys = new ArrayList<>();
         while(importKeys.next())
         {
        	 Map<String, String> importKey = new HashMap<>();
        	 importKey.put("PKTABLE_NAME", importKeys.getString("PKTABLE_NAME"));
        	 importKey.put("PKCOLUMN_NAME", importKeys.getString("PKCOLUMN_NAME"));
        	 importKey.put("FKTABLE_NAME", importKeys.getString("FKTABLE_NAME"));
        	 importKey.put("FKCOLUMN_NAME", importKeys.getString("FKCOLUMN_NAME"));
        	 keys.add(importKey);
         }
         return keys;
	}
	
	private static Map<String, String> getForeignKey(ArrayList<Map<String, String>> foreignKeys, String columnName) {
		for (Map<String, String> foreignKey : foreignKeys) {
			if (foreignKey.get("FKCOLUMN_NAME").equals(columnName)) {
				return foreignKey;
			}
		}
		return null;
	}
}
