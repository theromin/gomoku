package gomoku;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SettingFrame extends JFrame {
	public static JCheckBox bgm;
	public static JCheckBox sound;
	public static JCheckBox ban;
	public SettingFrame() {
		this.setTitle("设置");//标题
		this.setSize(300,200);//大小
		this.setLocationByPlatform(true);//窗口位置
		
		JPanel panel = new JPanel();
		
		//音乐音效
		bgm = new JCheckBox("音乐");
		bgm.setFont(new Font("楷体", 0, 30));
//		bgm.setBounds(50,50,70,40);
		bgm.setSelected(true);
		panel.add(bgm);
		
		sound = new JCheckBox("音效");
		sound.setFont(new Font("楷体", 0, 30));
//		sound.setBounds(50,50,70,40);
		sound.setSelected(true);
		panel.add(sound);
		
		
		//禁手开关
		ban = new JCheckBox("禁手");
		ban.setFont(new Font("楷体", 0, 30));
//		ban.setBounds(50,50,70,40);
		panel.add(ban);
		
		JButton close = new JButton("关闭");
		close.setFont(new Font("楷体", 0, 24));
//		close.setBounds(120, 300, 60, 30);
		panel.add(close);
		
		ActionListener closeAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		};
		close.addActionListener(closeAction);
		
		this.add(panel);
	}
}
