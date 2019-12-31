package gomoku;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// ���ﱾ������ֱ���ü������ӿڣ����½�һ���࣬���������ѣ����ò�����
public class Gframe extends JFrame implements Config,MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int [][]piece = new int[row][col];
	private static ArrayList<Point>list=new ArrayList<Point>();//����ÿһ�����������
	private static int operable = 1;//0���ɲ��� 1�� 2��
//	private static boolean ban;
	
	private static JTextArea feedback=null;//Ϊ��������¼���ʹ�ã��ŵ�������
//	private static JCheckBox banbox=null;
	
	private static Clip bgmClip = null;//���ö��bgm,�����һ��
	private static Clip setClip = null;
	private static Clip winClip = null;
	static {
		try {
			File setMusic = new File("music/set.wav");
			AudioInputStream setAudioStream = 
					AudioSystem.getAudioInputStream(setMusic);
			setClip = AudioSystem.getClip();
			setClip.open(setAudioStream);
			
			File bgmMusic = null;
			switch((int)(2*Math.random())) {
			case 0: bgmMusic = new File("music/bgm0.wav");break;
			case 1: bgmMusic = new File("music/bgm1.wav");break;
			//�кõ��ټ�,�ٺ�
			}
			
			AudioInputStream bgmAudioStream = 
					AudioSystem.getAudioInputStream(bgmMusic);
			bgmClip = AudioSystem.getClip();
			bgmClip.open(bgmAudioStream);
			
			File winMusic = new File("music/win.wav");
			AudioInputStream winAudioStream = 
					AudioSystem.getAudioInputStream(winMusic);
			winClip = AudioSystem.getClip();
			winClip.open(winAudioStream);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public Gframe() {
		this.setTitle("������");//����
		this.setSize(820,650);//��С
		this.setLocationByPlatform(true);//����λ��
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage((new ImageIcon("icon/icon.png")).getImage());//ͼ��
		this.setLayout(new BorderLayout());

		//�����ı���
		feedback = new JTextArea();
//		feedback.setBounds(x+(col-1)*size+20, y+200,160,300);
		feedback.setFont(new Font("����",0,18));
		feedback.setLineWrap(true);//�Զ�����
		feedback.setCaretPosition(feedback.getText().length());
		//feedback.enable(false);
		this.add(feedback);
		
		JScrollPane scroll = new JScrollPane(feedback);
		scroll.setBounds(x+(col-1)*size+40, y+240,160,320);
		this.add(scroll);
		
		Font buttonf = new Font("����", 0, 24);
		
//		//���ֿ���
//		banbox = new JCheckBox("����");
//		banbox.setFont(new Font("����", 0, 16));
//		banbox.setBounds(x+(col-1)*size+20, y+180,70,40);
//		this.add(banbox);
		
		//��Ӱ�ť
		JButton end = new JButton("����");
		end.setFont(buttonf);
		end.setBounds(x+(col-1)*size+40, y+180,160,40);
		end.setFocusPainted(false);//��ȥ����Ŀ�  
		end.setContentAreaFilled(false);//��ȥĬ�ϵı������
		this.add(end);
		ActionListener endAction = new ActionListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				bgmClip.stop();
//				StartFrame.setVisible(true);
				StartFrame sframe = new StartFrame();
//				System.exit(0);
//				list.clear();
//				banbox.setEnabled(true);
//				feedback.append("�ѽ�����\n");
//				feedback.setCaretPosition(feedback.getText().length());
//				bgmClip.stop();
//				operable = 0;
			}
		};
		end.addActionListener(endAction);
		
		JButton start = new JButton("���¿�ʼ");
		start.setFont(buttonf);
		start.setBounds(x+(col-1)*size+40, y,160,40);
		start.setFocusPainted(false);//��ȥ����Ŀ�  
		start.setContentAreaFilled(false);//��ȥĬ�ϵı������
		this.add(start);
		ActionListener startAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<row;i++) {
					for(int j=0;j<col;j++) {
						piece[i][j]=0;
					}
				}
				list.clear();
//				banbox.setEnabled(false);
				feedback.append("���¿�ʼ��\n");
				feedback.setCaretPosition(feedback.getText().length());
				
//				bgmClip.setFramePosition(0);
//				bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
				operable = 1;
				repaint();
			}
		};
		start.addActionListener(startAction);
		
		JButton remove = new JButton("����");//������Ūһ������,����ÿ�����л����������
		remove.setFont(buttonf);
		remove.setFocusPainted(false);//��ȥ����Ŀ�  
		remove.setContentAreaFilled(false);//��ȥĬ�ϵı������
		remove.setBounds(x+(col-1)*size+40, y+60,160,40);
		this.add(remove);
		ActionListener removeAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.isEmpty()) {//��ջΪ��
					feedback.append("���ɻ��壡\n");		
					feedback.setCaretPosition(feedback.getText().length());
				}
				else {
					//ɾ������
					piece[list.get(list.size()-1).lx][list.get(list.size()-1).ly]=0;
					list.remove(list.size()-1);//��ջ����
					if(operable==1)operable++;
					else if(operable==2)operable--;
					feedback.append("�ɹ����壡\n");
					feedback.setCaretPosition(feedback.getText().length());
					repaint();
				}
			}
		};
		remove.addActionListener(removeAction);
		
		JButton save = new JButton("�洢");
		save.setFocusPainted(false);//��ȥ����Ŀ�  
		save.setContentAreaFilled(false);//��ȥĬ�ϵı������
		save.setFont(new Font("����", 0, 16));
		save.setBounds(x+(col-1)*size+40, y+120,70,40);
		this.add(save);
		ActionListener saveAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save(1);
				feedback.append("����ɹ���\n");
				feedback.setCaretPosition(feedback.getText().length());
			}
		};
		save.addActionListener(saveAction);
		
		
		JButton read = new JButton("��ȡ");
		read.setFocusPainted(false);//��ȥ����Ŀ�  
		read.setContentAreaFilled(false);//��ȥĬ�ϵı������
		read.setFont(new Font("����", 0, 16));
		read.setBounds(x+(col-1)*size+40+90, y+120,70,40);
		this.add(read);
		ActionListener readAction = new ActionListener() {//��ȡ����
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=read(1);
				list.clear();
				feedback.append("��ȡ�ɹ���\n");//��ʾ
				feedback.setCaretPosition(feedback.getText().length());
				actAfterRead(flag);
				repaint();
			}
		};
		read.addActionListener(readAction);
		
		if(SettingFrame.bgm.isSelected()) {
			bgmClip.setFramePosition(0);
			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		this.add(new Checkerboard());//����
		this.addMouseListener(this);
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(operable==0) {
			feedback.append("��Ϸ�ѽ����������¿�ʼ��\n");
			feedback.setCaretPosition(feedback.getText().length());
			return;
		}
		int x=e.getX();
		int y=e.getY();//������
//		System.out.println(x+" "+y);
		int pi = y/40-1;//��֪��Ϊ�Σ�����y����ƫ��40
		int pj = x/40;//��������
		if(pi>14||pj>14)return;
		
		if(piece[pi][pj]!=0) {
			feedback.append("�˴��������ӣ�\n");
			feedback.setCaretPosition(feedback.getText().length());
			return;
		}
		
		//TODO ����д�⣺���ӽ����������������ʾ������
		if(SettingFrame.ban.isSelected()&&operable==1&&banOrNot(pi,pj)) {
			feedback.append("�˴�Ϊ���ӽ��֣�\n");
			feedback.setCaretPosition(feedback.getText().length());
			return;
		}
		
		if(SettingFrame.sound.isSelected()) {
			setClip.setFramePosition(0);
			setClip.loop(0);
		}
		
		if(operable == 1) {
			piece[pi][pj]=1;
			list.add(new Point(pi, pj));
			repaint();
			operable++;
			if(winOrNot(pi, pj, 1)) {//Ӯ�˻����ʾ�����̱�Ϊ���ɲ���״̬
				feedback.append("���ӻ�ʤ��\n");
//				banbox.setEnabled(true);
//				bgmClip.stop();
				if(SettingFrame.sound.isSelected()) {
					winClip.setFramePosition(0);
					winClip.loop(0);
				}
				feedback.setCaretPosition(feedback.getText().length());
				operable = 0;
			}
		}
		else if(operable == 2) {
			piece[pi][pj]=2;
			list.add(new Point(pi, pj));
			repaint();
			operable--;
			if(winOrNot(pi, pj, 2)) {//Ӯ�˻����ʾ�����̱�Ϊ���ɲ���״̬
				feedback.append("���ӻ�ʤ��\n");
//				banbox.setEnabled(true);
//				bgmClip.stop();
				if(SettingFrame.sound.isSelected()) {
					winClip.setFramePosition(0);
					winClip.loop(0);
				}
				feedback.setCaretPosition(feedback.getText().length());
				operable = 0;
			}
		}
		
	}
	public boolean banOrNot(int x,int y) {
		int count=0;
		//��������
		
		//����
		count=0;
		for(int i=1;i<5;i++) {
			if(x-i<0)break;
			if(piece[x-i][y]==1)count++;
			else break;
		}
		for(int i=1;i<5;i++) {
			if(x+i>14)break;
			if(piece[x+i][y]==1)count++;
			else break;
		}
		if(count>4)return true;
		
		//����
		count=0;
		for(int i=1;i<5;i++) {
			if(y-i<0)break;
			if(piece[x][y-i]==1)count++;
			else break;
		}
		for(int i=1;i<5;i++) {
			if(y+i>14)break;
			if(piece[x][y+i]==1)count++;
			else break;
		}
		if(count>4)return true;
		
		//45�ȷ���
		count=0;
		for(int i=1;i<5;i++) {
			if(x+i>14||y-i<0)break;
			if(piece[x+i][y-i]==1)count++;
			else break;
		}
		for(int i=1;i<5;i++) {
			if(y+i>14||x-i<0)break;
			if(piece[x-i][y+i]==1)count++;
			else break;
		}
		if(count>4)return true;
		
		//135��
		count=0;
		for(int i=1;i<5;i++) {
			if(x+i>14||y+i>14)break;
			if(piece[x+i][y+i]==1)count++;
			else break;
		}
		for(int i=1;i<5;i++) {
			if(y-i<0||x-i<0)break;
			if(piece[x-i][y-i]==1)count++;
			else break;
		}
		if(count>4)return true;
		
		int four=0/*,three=0*/;
		
		//��4
		//180
		for(int i=1;i<5;i++) {
			if(y-i<0)break;
			if(piece[x][y-i]!=1)break;
			if(i==4&&y-5>=0&&piece[x][y-5]==0)four++;
		}
		//0
		for(int i=1;i<5;i++) {
			if(y+i>14)break;
			if(piece[x][y+i]!=1)break;
			if(i==4&&y+5<15&&piece[x][y+5]==0)four++;
		}
		//90
		for(int i=1;i<5;i++) {
			if(x-i<0)break;
			if(piece[x-i][y]!=1)break;
			if(i==4&&x-5>=0&&piece[x-5][y]==0)four++;
		}
		//270
		for(int i=1;i<5;i++) {
			if(x+i>14)break;
			if(piece[x+i][y]!=1)break;
			if(i==4&&x+5<15&&piece[x+5][y]==0)four++;
		}
		//315
		for(int i=1;i<5;i++) {
			if(x+i>14||y+i>14)break;
			if(piece[x+i][y+i]!=1)break;
			if(i==4&&x+5<15&&y+i<15&&piece[x+5][y+5]==0)four++;
		}
		//45
		for(int i=1;i<5;i++) {
			if(x-i<0||y+i>14)break;
			if(piece[x-i][y+i]!=1)break;
			if(i==4&&x-5>=0&&y+i<15&&piece[x-5][y+5]==0)four++;
		}
		//135
		for(int i=1;i<5;i++) {
			if(x-i<0||y-i<0)break;
			if(piece[x-i][y-i]!=1)break;
			if(i==4&&x-5>=0&&y-i>=0&&piece[x-5][y-5]==0)four++;
		}
		//215
		for(int i=1;i<5;i++) {
			if(x+i>14||y-i<0)break;
			if(piece[x+i][y-i]!=1)break;
			if(i==4&&x+5<15&&y-i>=0&&piece[x+5][y-5]==0)four++;
		}
		
		//��4
		//����
		count=0;
//		int available = 0;
		for(int i=1;i<5;i++) {
			if(x-i<0)break;
			if(piece[x-i][y]==1)count++;
			else break;
		}
		for(int i=1;i<5;i++) {
			if(x+i>14)break;
			if(piece[x+i][y]==1)count++;
			else break;
		}
		if(count==4)four++;
		
		//����
		count=0;
		for(int i=1;i<5;i++) {
			if(y-i<0)break;
			if(piece[x][y-i]==1)count++;
			else break;
		}
		for(int i=1;i<5;i++) {
			if(y+i>14)break;
			if(piece[x][y+i]==1)count++;
			else break;
		}
		if(count==4)four++;
		
		//45�ȷ���
		count=0;
		for(int i=1;i<5;i++) {
			if(x+i>14||y-i<0)break;
			if(piece[x+i][y-i]==1)count++;
			else break;
		}
		for(int i=1;i<5;i++) {
			if(y+i>14||x-i<0)break;
			if(piece[x-i][y+i]==1)count++;
			else break;
		}
		if(count==4)four++;
		
		//135��
		count=0;
		for(int i=1;i<5;i++) {
			if(x+i>14||y+i>14)break;
			if(piece[x+i][y+i]==1)count++;
			else break;
		}
		for(int i=1;i<5;i++) {
			if(y-i<0||x-i<0)break;
			if(piece[x-i][y-i]==1)count++;
			else break;
		}
		if(count==4)four++;
		
		//��������,����
		
		if(four>1/*||three>1*/)return true;
		return false;
	}
	
	public boolean winOrNot(int x,int y,int key) {
		int count = 0;
		//���ж�
		int jmin=y-5,jmax=y+5;
		if(jmin<0) jmin=-1;
		if(jmax>14) jmax=15;
		for(int j=y;j>jmin;j--) {//����������������ӣ�������û������ʱ���ͽ���
			if(piece[x][j]==key)
				count++;
			else break;
		}
		for(int j=y;j<jmax;j++) {
			if(piece[x][j]==key) 
				count++;
			else break;
		}
		if(count>5)return true;//����Ҫ6,���������Լ�
		
		//���ж�
		int imin=x-5,imax=x+5;
		if(imin<0) imin=-1;
		if(imax>14) imax=15;
		count = 0;//�ж�������������
		for(int i=x;i>imin;i--) {
			if(piece[i][y]==key)count++;
			else break;
		}
		for(int i=x;i<imax;i++) {
			if(piece[i][y]==key)count++;
			else break;
		}
		if(count>5)return true;
		
		//45�㷽��
		count=0;
		for(int k=0;k<5;k++) {
			if(x+k>14||y-k<0)break;
			if(piece[x+k][y-k]==key)count++;
			else break;
		}
		for(int k=0;k<5;k++) {
			if(x-k<0||y+k>14)break;
			if(piece[x-k][y+k]==key)count++;
			else break;
		}
		if(count>5)return true;
		
		//135�㷽��
		count=0;
		for(int k=0;k<5;k++) {
			if(x-k<0||y-k<0)break;
			if(piece[x-k][y-k]==key)count++;
			else break;
		}
		for(int k=0;k<5;k++) {
			if(x+k>14||y+k>14)break;
			if(piece[x+k][y+k]==key)count++;
			else break;
		}
		if(count>5)return true;
		
		
		return false;
	}

	public void save(int op) {
		PrintWriter out = null;
		try {
			if(op==0)
				out = new PrintWriter("document/data.txt");
			else if(op == 1)
				out = new PrintWriter("document/save.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				out.print(piece[i][j]+" ");
			}
			out.println();
		}
		out.close();
	}
	
	public int read(int op) {
		Scanner in = null;
		try {
			if(op==0)
				in = new Scanner(new File("document/data.txt"));
			else if(op==1)
				in = new Scanner(new File("document/save.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		int win=0;
		int black=0,white=0;
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				piece[i][j]=in.nextInt();
				if(piece[i][j]==1) {
					black++;
					if(win==0&&winOrNot(i, j, 1))win = 1;
				}
				else if(piece[i][j]==2) {
					white++;
					if(win==0&&winOrNot(i, j, 2))win = 2;
				}
//				else {
//					
//				}
//				������дһ���浵�𻵵ķ�֧
			}
		}
		in.close();
		int flag;
		if(white!=black&&white+1!=black)flag=0;
		else if(win==1)flag=1;
		else if(win==2)flag=2;
		else if(black==white)flag=3;
		else flag=4;
		return flag;
	}
	
	public void actAfterRead(int flag) {
		if(flag==0) {
			operable = 0;
//			banbox.setEnabled(true);
			feedback.append("��ȡ�����������֣�\n");
			feedback.setCaretPosition(feedback.getText().length());
		}
		else if(flag==1) {//�����ѻ�ʤ
			operable = 0;
//			bgmClip.stop();
//			banbox.setEnabled(true);
			feedback.append("�����ѻ�ʤ��\n");
			feedback.setCaretPosition(feedback.getText().length());
		}
		else if(flag == 2) {//�����ѻ�ʤ
			operable = 0;
//			bgmClip.stop();
//			banbox.setEnabled(true);
			feedback.append("�����ѻ�ʤ��\n");
			feedback.setCaretPosition(feedback.getText().length());
		}
		else if(flag==3) {//��������
//			banbox.setEnabled(false);
//			bgmClip.setFramePosition(0);
//			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
			operable = 1;
		}
		else if(flag==4) {//��������
//			banbox.setEnabled(false);
//			bgmClip.setFramePosition(0);
//			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
			operable = 2;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
