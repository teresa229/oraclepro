package com.javaex.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {

	public static void main(String[] args) {
	
		//필드
		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "phonedb"; //연결이 안되고 있음(_ _)
		private String pw = "phonedb";
		
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		
		private int count = 0;
		
		
		//생성자
		
		//메소드 g/s
		
		private void getConnection() {
			
			try {
					Class.forName(driver);
					conn = DriverManager.getConnection(url, id, pw);
				
			catch (ClassNotFoundException e) {
			    System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
			    System.out.println("error:" + e);
			}
			}
		}
		
		private void resourceClose() {
			try {
		        if (rs != null) {
		            rs.close();
		        }                
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }
		}

		/* list */
		public list<PhoneVo> getPersonList(){
			
			List<PhoneVo> PhoneVoList = new ArrayList<PhoneVo>();
			
			getConnection();
			
			try {		
				String query = "";
				query += " select person_id, ";
				query += "        name, ";
				query += "        hp, ";
				query += "        company ";
				query += " from person ";
			}
			
			while(rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getNString("name");
				String hp = rs.getNString("hp");
				String company = rs.getNString("company");
				
				PhoneVo phoneVo = new PhoneVo(personId, name, hp, company);
				PhoneVoList.add(phoneVo);
				
			} catch (SQLException e) {
			    System.out.println("error:" + e);
			} 
	
			resourceClose();
			
			return PhoneVoList;

		}
		
		
		/* insert */   
		public int phoneInsert(String name, String hp, String company) {
		    	
		    getConnection();
	
			String query = "";
			query += "insert into person";
			query += "values(seq_author_id.nextval, ?, ? )";
			
			pstmt = conn.prepareStatement(query); 
			
			pstmt.setString(1, name);
			pstmt.setString(2, hp);
			pstmt.setString(3, company);
			
			count = pstmt.executeUpdate();    
			
			
		    //결과처리
			System.out.println("[" +count+ "건 등록되었습니다.]");

			} catch (SQLException e) {
			    System.out.println("error:" + e);
			} 
	
			resourceClose();
			
			return count;
	
			

		/* update */
		public int phoneUpdate(int personID, String name, String hp, String company) {
			
			getConnection();
			
			try {		
					String query = "";
					query += " update person ";
					query += " set name = ?, ";
					query += "     hp = ?, ";
					query += "     company = ? ";
					query += " where person_id = ? ";
					
					pstmt = conn.prepareStatement(query);
					
					pstmt.setSting(1, name);
					pstmt.setSting(2, hp);
					pstmt.setString(3, company);
					pstmt.setString(4, personId);
					
					count = pstmt.executeUpdate();
					
					//결과처리
					System.out.println("[" +count+ "건 수정되었습니다.]");
					
				} catch (SQLException e) {
				    System.out.println("error:" + e);
				} 
		
				resourceClose();
				
				return count;
		
		}
				
		/* delete */
		public int phoneDelete(int personId) {
			
			getConnection();
			
			try {		
					String query = "";
					query += " delete person ";
					query += " where person_id = ? ";
					
					pstmt = conn.prepareStatement(query);
					
					pstmt.setInt(1, personId);
					
					count = pstmt.executeUpdate();
				
					
					//결과처리
					System.out.println("[" +count+ "건 삭제되었습니다.]");
				
				} catch (SQLException e) {
				    System.out.println("error:" + e);
				} 
		
				resourceClose();
				
				return count;
		
			}
			
		
				
		}
			
}
		
		
		
	
	

