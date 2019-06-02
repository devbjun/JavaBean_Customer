import java.sql.SQLException;

import frame.Customer;
import jdbc.JDBCManager;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 이곳에 메인 프레임을 선언해주세요.
		// 나머지 프레임 및 기타 작업들은 패키지에 맞게 클래스 선언해서 사용해주세요.
		// 이곳은 프로그램 실행을 위한 메인함수만 작성하도록 합니다.
		
		// 커스토머용 프로그램 시작
		new Customer(" JavaBean 1.0.3v - 카페에 오신걸 환영합니다.", "img/icon/java_bean.png", 500, 700);
		
		// 프로그램 종료 이벤트를 후킹한다.
		Runtime.getRuntime().addShutdownHook(
			new Thread() {
	            public void run() {
	      			try {
	      				
	      				// 프로그램 종료시 JDBC 연결 해제
	      				JDBCManager jdbc = JDBCManager.getJDBCManager();
						jdbc.setClose();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
			}
		);
	}
}
