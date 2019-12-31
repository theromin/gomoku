package gomoku;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StartFrame extends JFrame {
	public StartFrame() {
		this.setTitle("五子棋");//标题
		this.setSize(800,650);//大小
		this.setLocationByPlatform(true);//窗口位置
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage((new ImageIcon("icon/icon.png")).getImage());//图标
		this.setLayout(new BorderLayout());
		
		//分层网格
		JLayeredPane pane = new JLayeredPane();
		
		//设置背景
		ImageIcon bgPicture = new ImageIcon("picture/background.jpg");
		bgPicture.setImage(bgPicture.getImage().getScaledInstance
				(this.getWidth(), this.getHeight(), Image.SCALE_AREA_AVERAGING));
		//背景随面板缩放
		JLabel bgLabel = new JLabel(bgPicture);
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, 0, bgPicture.getIconWidth(), bgPicture.getIconHeight());
		bgpanel.add(bgLabel);
		pane.add(bgpanel,JLayeredPane.DEFAULT_LAYER);
		
		//标题
		JLabel title = new JLabel("五子棋");
		title.setBounds(100, 100, 200, 100);
		title.setFont(new Font("楷体", 0, 60));
		pane.add(title,JLayeredPane.MODAL_LAYER);
		
		//温馨提示
		JLabel tip = new JLabel("<html>抵制不良游戏，拒绝盗版游戏。<br/>"
				+ "注意自我保护，谨防受骗上当 。<br/>"
				+ "适度游戏益脑，沉迷游戏伤身。<br/>"
				+ "合理安排时间，享受健康生活。</html>");
		tip.setBounds(280, 400, 500, 300);
		tip.setFont(new Font("宋体", 0, 16));
		pane.add(tip,JLayeredPane.MODAL_LAYER);
		
		//按钮
		JButton start = new JButton("开始");
		start.setFont(new Font("楷体", 0, 30));
		start.setBounds(130, 300, 100, 50);
//		start.setBorderPainted(false);//边框  
//		start.setBorder(null);//边框  
		start.setFocusPainted(false);//除去焦点的框  
		start.setContentAreaFilled(false);//除去默认的背景填充
		pane.add(start,JLayeredPane.MODAL_LAYER);
		ActionListener startAction = new ActionListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Gframe frame = new Gframe();
			}
		};
		start.addActionListener(startAction);
		
		JButton setting = new JButton("设置");
		setting.setFont(new Font("楷体", 0, 30));
		setting.setBounds(130, 360, 100, 50);
		setting.setFocusPainted(false);
		setting.setContentAreaFilled(false);
		pane.add(setting,JLayeredPane.MODAL_LAYER);
		SettingFrame stframe = new SettingFrame();
		ActionListener settingAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stframe.setVisible(true);
			}
		};
		
		setting.addActionListener(settingAction);
		
		JButton about = new JButton("关于");
		about.setFont(new Font("楷体", 0, 30));
		about.setBounds(130, 420, 100, 50);
		about.setFocusPainted(false);
		about.setContentAreaFilled(false);
		pane.add(about,JLayeredPane.MODAL_LAYER);
		ActionListener aboutAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(StartFrame.this,
						"author:wang&&geng 2019/12/30","ver3.0 about...",
						JOptionPane.PLAIN_MESSAGE);
			}
		};
		about.addActionListener(aboutAction);
		
		JButton quit = new JButton("退出");
		quit.setFont(new Font("楷体", 0, 30));
		quit.setBounds(130, 480, 100, 50);
		quit.setFocusPainted(false);
		quit.setContentAreaFilled(false);
		pane.add(quit,JLayeredPane.MODAL_LAYER);
		ActionListener quitAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		};
		quit.addActionListener(quitAction);
		
		this.setLayeredPane(pane);
		this.setVisible(true);
	}
}
