package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	private final static String URL = "jdbc:mysql://localhost:3306/nations";
	private final static String USER = "root";
	private final static String PASSWORD = "password";
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
			
			System.out.print("Cerca : ");
			String ricerca ="%" + scan.nextLine() + "%";
			
			
			String sql = "select c.name as country_name, c.country_id, r.name as region_name, c2.name as continent_name\r\n"
					+ "from countries c \r\n"
					+ "left outer join regions r on r.region_id = c.region_id \r\n"
					+ "left outer join continents c2 on r.continent_id = c2.continent_id \r\n"
					+ "where c.name like ? \r\n" 
					+ "order by c.name asc;";
			
			try(PreparedStatement ps = con.prepareStatement(sql)) {
				
				ps.setString(1, ricerca);
				
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
		scan.close();
	}

}

