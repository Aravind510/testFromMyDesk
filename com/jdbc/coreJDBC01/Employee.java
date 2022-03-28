package com.jdbc.coreJDBC01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Employee {

	public static void main(String[] args) {

		/*
		 * boolean mainFlag = createEmployee(); System.out.println(mainFlag); if
		 * (mainFlag) { System.out.println("Employee table created Sucuessfully."); }
		 * else { System.out.println("We are sorry."); }
		 */

		boolean mainFlag = createDynamicEmp();
		System.out.println(mainFlag);
		if (mainFlag) {
			System.out.println("Employee table created Sucuessfully.");
		} else {
			System.out.println("We are sorry.");
		}

	}

	public static boolean createEmployee() {

		Connection con = null;
		String query = null;
		Statement st = null;
		boolean flag = false;

		try {

			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "toor");
			st = con.createStatement();
			query = "create table Employee(ENO number primary key,ENAME varchar2(10),ESAL float,EADDR varchar2(10))";

			flag = st.execute(query);

			if (!flag) {
				System.out.println("Table created Sucuessfully.");
				flag = true;
			} else {
				System.out.println("nooo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				con.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return flag;

	}

	public static boolean createDynamicEmp() {

		boolean flag = false;
		Connection con = null;
		Statement st = null;
		BufferedReader br = null;
		String query = "create table ";
		String primaryKey_columns = "primary key (";
		try {
			
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter table name : ");
			String table_name = br.readLine();
			query = query+table_name+"(";
			int primaryKey_count = 0;
			while(true) {
				System.out.println("Enter Column Name : ");
				String column_name = br.readLine();
				
				System.out.println("Enter datatype with size : ");
				String column_typeNdSize = br.readLine();
				
				System.out.println("Is it primary key [Yes/No] : ");
				if(br.readLine().equalsIgnoreCase("yes")) {
					if(primaryKey_count >= 1) {
						primaryKey_columns = primaryKey_columns+","+column_name;
					}
					else{
						primaryKey_columns = primaryKey_columns+column_name;
					}
					primaryKey_count = primaryKey_count+1;
				}
				
				System.out.println("Enter one more Column ?? [Yes/No] :");
				String lastOrNot = br.readLine();
				if(lastOrNot.equalsIgnoreCase("Yes")) {
					query = query+column_name+" "+column_typeNdSize+",";
				}
				else if(lastOrNot.equalsIgnoreCase("No")) 
				{
					if (primaryKey_count < 1) {
						query = query+column_name+" "+column_typeNdSize+")";
					}
					else {
						query = query+column_name+" "+column_typeNdSize+", "+primaryKey_columns+"))";
					}
					break;
				}
				
				
			}
			
			System.out.println("Query : "+query);
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "toor");
			st = con.createStatement();
			flag = st.execute(query);
			if (!flag) {
				flag = true;
			} else {
				System.out.println("some error");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				br.close();
			} catch (Exception e2) {
				e2.getMessage();
			}
		}

		return flag;
	}

}
