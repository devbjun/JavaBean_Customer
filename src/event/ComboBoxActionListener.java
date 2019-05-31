package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import frame.Client;

/**
 * ComboBox에서 발생하는 모든 종류의 이벤트를 처리하기 위한 리스너
 */
public class ComboBoxActionListener implements ActionListener {

	// 오류 무시 및 오버라이드 선언
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		// 사이즈 관련 콤보 박스의 목록을 지운다.
		Client.cbSize.removeAllItems();
		
		// 핫 & 아이스 관련 콤보 박스에서 선택된 아이템이 아무것도 없다면 이벤트를 종료한다.
		if (Client.cbTemp.getSelectedItem() == null) { return; }
		
		// 핫 & 아이스 관련 콤보 박스의 정보를 기준으로, 선택 가능한 사이즈 종류를 사이즈 관련 콤보 박스에 등록한다.
		for (String _size : (Vector<String>) Client.json.get(Client.cbTemp.getSelectedItem().toString())) {
			Client.cbSize.addItem(_size);
		}
	}
}
