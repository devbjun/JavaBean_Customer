package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import org.json.simple.JSONObject;

import frame.panel.CategoryPanel;
import jdbc.oracle.Items;

@SuppressWarnings("serial")
public class Client_v2 extends BasicFrame {

	public Client_v2(String _title, int _width, int _height) {
		super(_title, _width, _height);
		// TODO Auto-generated constructor stub
		
		setNorthPanel();
		setCenterPanel();
		setSouthPanel();
		
		// 창 표기
		setVisible(true);
	}
	
	/**
	 * 카페 로고 및 이름을 표기
	 */
	private void setNorthPanel() {
		
		JPanel pNorth = new JPanel();
		pNorth.setPreferredSize(new Dimension(getWidth(), 75));
		
		// 로고 이미지 등록
		JLabel lLogo = new JLabel();
		lLogo.setIcon(new ImageIcon("img/logo/java_bean.png"));
		
		// 등록 전, 디자인 수정
		pNorth.setOpaque(true);
		pNorth.setBackground(Color.DARK_GRAY);
		
		// 판넬 등록
		add(pNorth, BorderLayout.NORTH);
	}
	
	/**
	 * 카테고리 탭 및 탭별 아이템 목록 패널 등록
	 */
	private void setCenterPanel() {
		
		// Client Frame에 등록하기 위한 탭 패널 선언 및 카테고리 처리를 위한 lMenu 변수 선언
		JTabbedPane tpCategory = new JTabbedPane();
		Vector<JSONObject> lMenu;
		
		// 오류처리
		try {
					
			// 카테고리 정보를 받아오고, 개수만큼 버튼 배열 생성
			lMenu = Items.getCategoryList();
			
			for (JSONObject _json : lMenu) {
				tpCategory.add(_json.get("카테고리").toString(), new CategoryPanel(_json.get("카테고리").toString()));
				tpCategory.setBackgroundAt(tpCategory.getTabCount() - 1, Color.DARK_GRAY);
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
		
		// tpCategory 디자인 수정
		tpCategory.setOpaque(true);
		tpCategory.setBackground(Color.DARK_GRAY);
		tpCategory.setUI(new BasicTabbedPaneUI() {
					
			@Override 
			protected void paintTabBorder(
					Graphics g, int tabPlacement, int tabIndex,
					int x, int y, int w, int h, boolean isSelected) 
			{
				// Do not paint anything
					    	  
				g.setColor(Color.white);
				g.drawRoundRect(x, y, w, h, 0, 0);
				tpCategory.setForegroundAt(tabIndex, Color.WHITE);
					    	  	
				if (isSelected)
				{
					g.setColor(Color.white);
					g.fillRect(x, y, w, h);				
					tpCategory.setForegroundAt(tabIndex, Color.DARK_GRAY);
				}
			}
		});
		
		// tpCategory 포커스 해제
		tpCategory.setFocusable(false);
		
		// 카테고리 탭 등록
		add(tpCategory, BorderLayout.CENTER);
	}
	
	private void setSouthPanel() {
		
		JPanel pSouth = new JPanel();
		pSouth.setPreferredSize(new Dimension(getWidth(), getHeight() / 3));
		
		add(pSouth, BorderLayout.SOUTH);
	}
	
}
