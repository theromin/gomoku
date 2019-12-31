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
		this.setTitle("������");//����
		this.setSize(800,650);//��С
		this.setLocationByPlatform(true);//����λ��
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage((new ImageIcon("icon/icon.png")).getImage());//ͼ��
		this.setLayout(new BorderLayout());
		
		//�ֲ�����
		JLayeredPane pane = new JLayeredPane();
		
		//���ñ���
		ImageIcon bgPicture = new ImageIcon("picture/background.jpg");
		bgPicture.setImage(bgPicture.getImage().getScaledInstance
				(this.getWidth(), this.getHeight(), Image.SCALE_AREA_AVERAGING));
		//�������������
		JLabel bgLabel = new JLabel(bgPicture);
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, 0, bgPicture.getIconWidth(), bgPicture.getIconHeight());
		bgpanel.add(bgLabel);
		pane.add(bgpanel,JLayeredPane.DEFAULT_LAYER);
		
		//����
		JLabel title = new JLabel("������");
		title.setBounds(100, 100, 200, 100);
		title.setFont(new Font("����", 0, 60));
		pane.add(title,JLayeredPane.MODAL_LAYER);
		
		//��ܰ��ʾ
		JLabel tip = new JLabel("<html>���Ʋ�����Ϸ���ܾ�������Ϸ��<br/>"
				+ "ע�����ұ�����������ƭ�ϵ� ��<br/>"
				+ "�ʶ���Ϸ���ԣ�������Ϸ����<br/>"
				+ "������ʱ�䣬���ܽ������</html>");
		tip.setBounds(280, 400, 500, 300);
		tip.setFont(new Font("����", 0, 16));
		pane.add(tip,JLayeredPane.MODAL_LAYER);
		
		//��ť
		JButton start = new JButton("��ʼ");
		start.setFont(new Font("����", 0, 30));
		start.setBounds(130, 300, 100, 50);
//		start.setBorderPainted(false);//�߿�  
//		start.setBorder(null);//�߿�  
		start.setFocusPainted(false);//��ȥ����Ŀ�  
		start.setContentAreaFilled(false);//��ȥĬ�ϵı������
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
		
		JButton setting = new JButton("����");
		setting.setFont(new Font("����", 0, 30));
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
		
		JButton about = new JButton("����");
		about.setFont(new Font("����", 0, 30));
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
		
		JButton quit = new JButton("�˳�");
		quit.setFont(new Font("����", 0, 30));
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
