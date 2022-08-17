package com.anhnbt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.anhnbt.DBConnection;

public class StatisticsCounterImpl implements StatisticsCounter {

	private static final String GET_COUNTER = "SELECT last_update FROM STATISTICS_COUTER WHERE c_type = ? AND c_value = ? AND site_name = ?";

	@Override
	public void update(String siteName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtYearExists = null;
		PreparedStatement pstmtInsertYear = null;
		PreparedStatement pstmtUpdate = null;
		PreparedStatement pstmtUpdateLast = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rsYearExists = null;
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			pstmt = conn.prepareStatement(GET_COUNTER);
			pstmt.setString(1, "c_time");
			pstmt.setString(2, "last");
			pstmt.setString(3, siteName);

			rs = pstmt.executeQuery();
			Long lastUpdate = null;
			if (rs.next()) {
				lastUpdate = rs.getLong(1);
			}

			Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
			Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
			calendar.setTimeInMillis(lastUpdate);
			String name = cal.getTimeZone().getDisplayName();
			System.out.println("Current Time Zone:" + name);
			

			int lastYear = calendar.get(Calendar.YEAR);
			int lastMonth = calendar.get(Calendar.MONTH) + 1;
			int lastDay = calendar.get(Calendar.DATE);
			
			System.out.println("Last year: " + lastYear);
			System.out.println("Last month: " + lastMonth);
			System.out.println("Last day: " + lastDay);

			int currentYear = cal.get(Calendar.YEAR);
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentDay = cal.get(Calendar.DATE);
			int currentHour = cal.get(Calendar.HOUR);
			int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			System.out.println("Current Time: " + cal.getTimeInMillis());
			System.out.println("Current Year: " + currentYear);
			System.out.println("Current Month: " + currentMonth);
			System.out.println("Current Day: " + currentDay);
			System.out.println("Current Hour: " + currentHour);
			System.out.println("Current Week: " + currentDayOfWeek);
			
			if (lastYear != currentYear) {
				pstmtYearExists = conn.prepareStatement("SELECT COUNT(*) FROM STATISTICS_COUTER WHERE c_type='year' AND c_value=? AND site_name = ?");
				pstmtYearExists.setString(1, String.valueOf(currentYear));
				pstmtYearExists.setString(2, siteName);
				rsYearExists = pstmtYearExists.executeQuery();
				if (rsYearExists.next()) {
					int yearExists = rsYearExists.getInt(0);
					if (yearExists != 0) {
						pstmtInsertYear = conn.prepareStatement("INSERT INTO STATISTICS_COUTER (c_type, c_value) VALUES ('year', ?, ?)");
						pstmtInsertYear.setString(1, String.valueOf(currentYear));
						pstmtInsertYear.setString(2, siteName);
						pstmtInsertYear.executeUpdate();
					}
				}
				stmt.executeUpdate("UPDATE STATISTICS_COUTER SET c_count = 0 WHERE (c_type='month' OR c_type='day')");
		    } else if (lastMonth != currentMonth) {
		    	stmt.executeUpdate("UPDATE STATISTICS_COUTER SET c_count = 0 WHERE (c_type='day')");
		    }
			
			StringBuilder query = new StringBuilder("UPDATE STATISTICS_COUTER SET last_update = ?, c_count = (select max(c_count)+1 from STATISTICS_COUTER");
			query.append(" WHERE site_name = ? AND (c_type='total' AND c_value='hits')");
			query.append(" OR (c_type='year' AND c_value=?)");
			query.append(" OR (c_type='month' AND c_value=?)");
			query.append(" OR (c_type='day' AND c_value=?))");
			query.append(" WHERE site_name = ? AND (c_type='total' AND c_value='hits')");
			query.append(" OR (c_type='year' AND c_value=?)");
			query.append(" OR (c_type='month' AND c_value=?)");
			query.append(" OR (c_type='day' AND c_value=?)");
//			query.append(" OR (c_type='dayofweek' AND c_value=?)");
			pstmtUpdate = conn.prepareStatement(query.toString());
			pstmtUpdate.setLong(1, calendar.getTimeInMillis());
			pstmtUpdate.setString(2, siteName);
			pstmtUpdate.setString(3, String.valueOf(currentYear));
			pstmtUpdate.setString(4, String.valueOf(currentMonth));
			pstmtUpdate.setString(5, String.valueOf(currentDay));
			pstmtUpdate.setString(6, siteName);
			pstmtUpdate.setString(7, String.valueOf(currentYear));
			pstmtUpdate.setString(8, String.valueOf(currentMonth));
			pstmtUpdate.setString(9, String.valueOf(currentDay));
			pstmtUpdate.executeUpdate();
			
			pstmtUpdateLast = conn.prepareStatement("UPDATE STATISTICS_COUTER SET last_update = ? WHERE c_type='c_time' AND c_value= 'last'");
			pstmtUpdateLast.setLong(1, calendar.getTimeInMillis());
			pstmtUpdateLast.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
				if (rs != null)
					rs.close();
				if (rsYearExists != null) {
					rsYearExists.close();
				}
				if (stmt != null)
					stmt.close();
				if (pstmtYearExists != null)
					pstmtYearExists.close();
				if (pstmtUpdate != null)
					pstmtUpdate.close();
				if (pstmtUpdateLast != null)
					pstmtUpdateLast.close();
				if (pstmtInsertYear != null)
					pstmtInsertYear.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
