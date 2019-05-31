package frame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.json.simple.JSONObject;

import component.MutableTable;
import event.ComboBoxActionListener;
import event.MutableTableListSelectionListener;
import frame.BasicFrame;
import jdbc.oracle.Items;

@SuppressWarnings("serial")
public class Client extends BasicFrame {
	
	// 외부 클래스에서 접근 가능하도록 설정
	public static JComboBox<String> cbTemp, cbSize;
	public static JTextField tfQuantity;
	public static MutableTable mTable;
	
	public static JSONObject json;
	
	// 내부 클래스에서만 사용하도록 설정
	private JScrollPane spTable;
	private JPanel pCenterNorth;
	
	private JButton[] btnCategory;

	/**
	 * Client 생성자
	 * @param _title
	 * @param _width
	 * @param _height
	 */
	public Client(String _title, int _width, int _height) {
		super(_title, _width, _height);
		// TODO Auto-generated constructor stub
		
		setNorthPanel(100);
		setCenterPanel();
		
		setVisible(true);
	}
	
	/**
	 * 카입력받은 버튼 사이즈 크기의 버튼을 테고리 개수만큼 만들어 Client의 North에 pNorth Panel을 등록하는 함수
	 * @param _buttonSize
	 */
	public void setNorthPanel(int _buttonSize) {
		
		// Client Frame에 등록하기 위한 North Panel 선언 및 카테고리 처리를 위한 lMenu 변수 선언
		JPanel pNorth = new JPanel(new GridLayout(2, 4, 10, 10));
		Vector<JSONObject> lMenu;
		
		// 오류처리
		try {
			
			// 카테고리 정보를 받아오고, 개수만큼 버튼 배열 생성
			lMenu = Items.getCategoryList();
			btnCategory = new JButton[lMenu.size()];
			
			// 카테고리별 버튼 생
			for (int i = 0; i < btnCategory.length; i++) {
				
				// 버튼 이름 및 사이즈 설정
				btnCategory[i] = new JButton(lMenu.get(i).get("카테고리").toString());
				btnCategory[i].setPreferredSize(new Dimension(_buttonSize, _buttonSize));
				
				// 버튼 클릭 시 처리할 익명 이벤트 등록
				btnCategory[i].addActionListener((e) -> {
					JButton btn = (JButton) e.getSource();
					
					// 오류처리
					try {
						
						// 동적 테이블 박스 추가 및 이벤트 클래스 등록
						mTable = new MutableTable(Items.getCategoryItemList(btn.getText()));
						mTable.addListSelectionListener(new MutableTableListSelectionListener());
						
						// 동적 테이블의 스크롤 Pane 가져오기 및 사이즈 조절
						spTable = mTable.getScrollTable();
						spTable.setPreferredSize(new Dimension(getWidth() - 60, getHeight() / 4));
						
						// pWest Panel에 기존 컴포넌트를 지운 후, 새로운 컴포넌트로 등록
						pCenterNorth.removeAll();
						pCenterNorth.add(spTable);
						
						// 화면 갱신
						revalidate();
						
					// 예외처리
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						
						// 오류 발생시 확인창을 띄우고, 프로그램 종료
						if (JOptionPane.showConfirmDialog(null, "예기치 않은 오류가 발생하여 프로그램을 종료합니다. (ErrorCode: -2)", "Error!", JOptionPane.ERROR_MESSAGE,JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION) {
							System.exit(-2);
						};
					}
				});
				
				/**
				 * 버튼 관련 디자인 처리 영역
				 */
				
				// 버튼 테두리, 배경색, 글자 색 설정
				btnCategory[i].setBorder(null);
				btnCategory[i].setBackground(Color.WHITE);
				btnCategory[i].setForeground(Color.DARK_GRAY);
				
				// 선택 불가 및 마우스 hOver, leave 설정
				btnCategory[i].setOpaque(true);
				btnCategory[i].setFocusable(false);
				btnCategory[i].addMouseListener(new MouseListener() {

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						((JButton) e.getSource()).setBackground(Color.ORANGE);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						((JButton) e.getSource()).setBackground(Color.WHITE);
					}
					
					// 아래 함수들은 사용하지 않음
					@Override
					public void mouseClicked(MouseEvent e) {}

					@Override
					public void mousePressed(MouseEvent e) {}

					@Override
					public void mouseReleased(MouseEvent e) {}
				});
				
				// 버튼을 pNorth Panel에 추가
				pNorth.add(btnCategory[i]);
			}
		
		// 예외처리
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			// 오류 발생시 확인창을 띄우고, 프로그램 종료
			if (JOptionPane.showConfirmDialog(null, "예기치 않은 오류가 발생하여 프로그램을 종료합니다. (ErrorCode: -1)", "Error!", JOptionPane.ERROR_MESSAGE,JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION) {
				System.exit(-1);
			};
		}
		
		// 테두리 관련 설정
		pNorth.setBorder(
			new TitledBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20), 
				"카테고리 선택", 
				TitledBorder.LEADING, 
				TitledBorder.TOP, 
				new Font("", Font.BOLD, 16), 
				Color.WHITE
			)
		);
		
		pNorth.setOpaque(true);
		pNorth.setBackground(Color.DARK_GRAY);
		
		// pNorth를 Client에 등록
		add(pNorth, BorderLayout.NORTH);
	}
	
	/**
	 * Client 프로그램의 Center에 '동적 메뉴 테이블 및 옵션 선택, 장바구니 등록'과 관련한 pBody Panel을 등록하는 함수
	 */
	public void setCenterPanel() {
		
		// 전체 레이아웃을 위한 JPanel 선언
		JPanel pBody = new JPanel(new BorderLayout());
		
		// 동쪽 및 서쪽 레이아웃을 위한 JPanel 선언
		JPanel pCenterSouth = new JPanel();
		pCenterNorth = new JPanel();
		
		// 오류처리
		try {
			
			// 메뉴별 품목 리스트를 표기할 테이블 박스 선언
			// 테이블에 표기할 기본 리스트의 메뉴 이름은 1번째 메뉴 카테고리 이름으로 한다.
			// 메뉴가 선택되었을 때 처리할 이벤트를 등록
			mTable = new MutableTable(Items.getCategoryItemList(btnCategory[0].getText()));
			mTable.addListSelectionListener(new MutableTableListSelectionListener());
			
			// mTabl의 스크롤 팬을 가져와 사이즈를 조절한다.
			spTable = mTable.getScrollTable();
			spTable.setPreferredSize(new Dimension(getWidth() - 60, getHeight() / 4));
			
			
			
			/**
			 * 이하는 동북쪽 레이아웃 처리를 위한 영역
			 */
			
			// 동북쪽 레이아웃을 위한 JPanel 선언 및 사이즈 조절
			JPanel pEastNorth = new JPanel(null);
			
			
			// 핫 & 아이스 선택을 위한 관련 컴포넌트 선언
			JLabel lTemp = new JLabel("옵션 선택");
			lTemp.setBounds(20, 10, 50, 20);
			
			cbTemp = new JComboBox<String>();
			cbTemp.addActionListener(new ComboBoxActionListener());
			cbTemp.setBounds(90, 10, 100, 20);
			
			
			// 사이즈 선택을 위한 관련 컴포넌트 선언
			JLabel lSize = new JLabel("사이즈 선택");
			lSize.setBounds(20, 50, 60, 20);
			
			cbSize = new JComboBox<String>();
			cbSize.setBounds(80, 50, 110, 20);
			
			
			// 수량 선택을 위한 관련 컴포넌트 선언
			JLabel lQuantity = new JLabel("수량 입력");
			lQuantity.setBounds(20, 90, 50, 20);
			
			tfQuantity = new JTextField(3);
			tfQuantity.setBounds(88, 90, 100, 20);
			tfQuantity.setHorizontalAlignment(JTextField.RIGHT);
			
			
			// 핫 & 아이스 관련 컴포넌트 등록
			pEastNorth.add(lTemp);
			pEastNorth.add(cbTemp);
			
			// 사이즈 관련 컴포넌트 등록
			pEastNorth.add(lSize);
			pEastNorth.add(cbSize);
			
			// 수량 관련 컴포넌트 등록
			pEastNorth.add(lQuantity);
			pEastNorth.add(tfQuantity);
			
			
			
			/**
			 * 이하는 동남쪽 레이아웃 처리를 위한 영역
			 */
			
			// 동남쪽 레이아웃을 위한 JPanel 선언 및 사이즈 조절
			JPanel pEastSouth = new JPanel(null);
			
			
			
			/**
			 * 컴포넌트 등록 전 디자인 관련 설정
			 */
			
			// 테두리 관련 설정
			pCenterNorth.setBorder(
				new TitledBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20), 
					"메뉴 선택", 
					TitledBorder.LEADING, 
					TitledBorder.TOP, 
					new Font("", Font.BOLD, 16), 
					Color.WHITE
				)
			);
			
			// 투명도 및 배경색 설정
			pCenterNorth.setOpaque(true);
			pCenterNorth.setBackground(Color.DARK_GRAY);
			
			
			/**
			 * 이하는 JPanel 등록을 위한 영역
			 */
			
			// JPanel 등록
			pCenterNorth.add(spTable);
			pCenterSouth.add(pEastNorth, BorderLayout.NORTH);
			pCenterSouth.add(pEastSouth, BorderLayout.SOUTH);
			
			// spTable 및 JPanel 등록
			pBody.add(pCenterNorth, BorderLayout.NORTH);
			//pBody.add(pCenterSouth, BorderLayout.SOUTH);
			
		// 예외처리
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			// 오류 발생시 확인창을 띄우고, 프로그램 종료
			if (JOptionPane.showConfirmDialog(null, "예기치 않은 오류가 발생하여 프로그램을 종료합니다. (ErrorCode: -3)", "Error!", JOptionPane.ERROR_MESSAGE,JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION) {
				System.exit(-3);
			};
		}
		
		// 투명도 및 배경색 설정
		pBody.setOpaque(true);
		pBody.setBackground(Color.DARK_GRAY);
		
		// pBody JPanel을 Client의 Center에 등록
		add(pBody, BorderLayout.CENTER);
		
	}
}
