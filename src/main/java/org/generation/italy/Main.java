package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

	private final static String URL = "jdbc:mysql://localhost:3306/nations";
	private final static String USER = "root";
	private final static String PASSWORD = "password";
	
	public static void main(String[] args) {
		
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
			
			String sql = "select c.name as country_name, c.country_id, r.name as region_name, c2.name as continent_name\r\n"
					+ "from countries c \r\n"
					+ "left outer join continents c2 on c2.continent_id = c.region_id \r\n"
					+ "left outer join regions r on r.continent_id = c.region_id \r\n"
					+ "order by c.name;";
			
			try(PreparedStatement ps = con.prepareStatement(sql)) {
				
				try(ResultSet rs = ps.executeQuery()) {
					
					while(rs.next()) {
						System.out.print(rs.getString(1) + " - ");
						System.out.print(rs.getString(2) + " - ");
						System.out.print(rs.getString(3) + " - ");
						System.out.println(rs.getString(4));
					}
					
				}
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}

