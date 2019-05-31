package event;

import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.JSONObject;

import frame.Client;
import jdbc.oracle.Items;

/**
 * MutableTable의 튜플 선택 이벤트 처리를 위한 리스너
 */
public class MutableTableListSelectionListener implements ListSelectionListener {
	
	// 오류 무시 및 오버라이드 선언
	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
		// 튜플이 클릭되었을 시에만 동작하도록 처리
		if(!e.getValueIsAdjusting()) {
			
			// 리스트 셀렉션 이벤트로부터 기본 목록 선택 모델을 가져온다
			DefaultListSelectionModel dlsm = (DefaultListSelectionModel) e.getSource();
			
			// 오류 처리
			try {
				
				// 기본 목록 선택 모델에서 선택된 튜플 번호를 가져온 후, 해당 튜플의 메뉴이름에 해당하는 옵션 정보를 Vector<JSONObject>로 가져온다.
				// 전역 처리를 위해 Client 클래스의 static타입 Client.json 변수에 JSONObject 객체를 할당한다.
				Vector<JSONObject> iDetail = Items.getItemDetailList(Client.mTable.getContents()[dlsm.getAnchorSelectionIndex()][0]);
				Client.json = new JSONObject();
				
				// 옵션 개수만큼 반복하며 JSON 정보를 가져온다
				for (JSONObject _json : iDetail) {
					
					// '옵션'이라는 키에 해당하는 값을 가져와 " & "로 파싱한다.
					String[] split = _json.get("옵션").toString().split(" & ");
					
					// 파싱한 문자열의 좌측에 해당하는 키값이 Client.json에 없을 경우, 해당 키값에 Vector<String> 객체를 값으로 할당한다.
					if (Client.json.get(split[0]) == null) {
						Client.json.put(split[0], new Vector<String>()); 
					}
					
					/*
					 * 파싱한 문자열의 0번째 값을 '키'로 하고, 해당 '키'에 해당하는 값인 Vector<String> 객체에 파싱한 문자열의 1번째 값을 넣어준다.
					 * 처리 완료된 Client.json은 아래 구조를 가진다.
					 * {
					 * 		"ICE" : ["TALL", "GRANDE", "VENTI"],
					 * 		"HOT" : ["TALL", "GRANDE", "VENTI"]
					 * }
					 */
					((Vector<String>) Client.json.get(split[0])).add(split[1]);
				}

				// 핫 & 아이스 관련 콤보 박스의 목록을 지우고 위에서 얻은 새로운 정보로 채워준다.
				Client.cbTemp.removeAllItems();
				for (Object _temp : Client.json.keySet().toArray()) {
					Client.cbTemp.addItem(_temp.toString());
				}
			
			// 예외처리
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				// 오류 발생시 확인창을 띄우고, 프로그램 종료
				if (JOptionPane.showConfirmDialog(null, "예기치 않은 오류가 발생하여 프로그램을 종료합니다. (ErrorCode: -4)", "Error!", JOptionPane.ERROR_MESSAGE,JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION) {
					System.exit(-4);
				};
			}
		}
		
	}
}
