package bearsCream;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BearsCream extends JFrame {
	JScrollPane scrollPane;
	ArrayList<JLabel> scoreList=new ArrayList<JLabel>(10);

	// 이미지
	private Image bgimg = new ImageIcon(this.getClass().getResource("/intro_Background.jpg")).getImage();
	private ImageIcon startButtonEnteredImg = new ImageIcon(this.getClass().getResource("/startButtonEntered.png"));
	private ImageIcon startButtonBasicImg = new ImageIcon(this.getClass().getResource("/startButtonBasic.png"));
	private ImageIcon explanationButtonEnteredImg = new ImageIcon(this.getClass().getResource("/explanationButtonEntered.png"));
	private ImageIcon explanationButtonBasicImg = new ImageIcon(this.getClass().getResource("/explanationButtonBasic.png"));
	private ImageIcon rankButtonEnteredImg = new ImageIcon(this.getClass().getResource("/rankButtonEntered.png"));
	private ImageIcon rankButtonBasicImg = new ImageIcon(this.getClass().getResource("/rankButtonBasic.png"));
	private ImageIcon backButtonImg = new ImageIcon(this.getClass().getResource("/back.png"));

	// 버튼
    private JButton startButton = new JButton(startButtonBasicImg);
	private JButton explanationButton = new JButton(explanationButtonBasicImg);
	private JButton rankButton = new JButton(rankButtonBasicImg);
	private JButton backButton=new JButton(backButtonImg);
	
	//음악
	Music music=new Music("introBackgroundMusic.mp3",true);
	
	int nowScreen=0; // 0은 메인 1은 설명 2는 게임 시작 3은 랭킹
	
	public static Game game;
	
	public BearsCream() {
		setTitle("Bear's Cream"); //제목
		setLayout(null);
		setVisible(true);
		setResizable(false); // 창크기 바뀌지 않게
		
		JPanel background = new JPanel() {
	           public void paintComponent(Graphics g) {
	               g.drawImage(bgimg, 0, 0, null);
	               if(nowScreen==2){
	            	   game.screenDraw(g);
	               }
	               setOpaque(false); //그림을 표시하게 설정,투명하게 조절
	               super.paintComponent(g);
	               this.repaint();
	           }
	     };
	     background.setLayout(null);
	     
	     addKeyListener(new KeyListener());
	     
	     music.start();
	     
	     explanationButton.setBounds(120,460,300,120);
	     explanationButton.setBorderPainted(false); // 버튼 테두리 없애기
	     explanationButton.setContentAreaFilled(false);// 버튼 배경없애기
	     explanationButton.setFocusPainted(false); // 버튼 안에 이미지 테두리 없애기
	     background.add(explanationButton);
	     scrollPane = new JScrollPane(background);
	     setContentPane(scrollPane);
	     explanationButton.addMouseListener(new MouseAdapter() {
			@Override
			// 버튼에 마우스를 올렸을 때
			public void mouseEntered(MouseEvent e) {
				explanationButton.setIcon(explanationButtonEnteredImg);
			}

			@Override
			// 버튼에 마우스를 뗐을 때
			public void mouseExited(MouseEvent e) {
				explanationButton.setIcon(explanationButtonBasicImg);
			}

			@Override
			// 버튼을 눌렀을 때
			public void mousePressed(MouseEvent e) {
				startButton.setVisible(false);
				explanationButton.setVisible(false);
				rankButton.setVisible(false);
				bgimg = new ImageIcon(this.getClass().getResource("/explain_Background.jpg")).getImage();
				backButton.setVisible(true);
			}
		});

	     startButton.setBounds(340,460,300,120);
	     startButton.setBorderPainted(false); // 버튼 테두리 없애기
	     startButton.setContentAreaFilled(false);// 버튼 배경없애기
	     startButton.setFocusPainted(false); // 버튼 안에 이미지 테두리 없애기
	     background.add(startButton);
	     scrollPane = new JScrollPane(background);
	     setContentPane(scrollPane);
	     startButton.addMouseListener(new MouseAdapter() {
			@Override
			// 버튼에 마우스를 올렸을 때
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImg);
			}

			@Override
			// 버튼에 마우스를 뗐을 때
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImg);
			}

			@Override
			// 버튼을 눌렀을 때
			public void mousePressed(MouseEvent e) {
				startButton.setVisible(false);
				explanationButton.setVisible(false);
				rankButton.setVisible(false);
				bgimg = new ImageIcon(this.getClass().getResource("/game_Background.jpg")).getImage();
				game=new Game();
				music.close();
				music=new Music("gameBackgroundMusic.mp3",true);
				music.start();
				backButton.setVisible(true);
				nowScreen=2;
				game.start();
			}
		});
	     
	     rankButton.setBounds(560,460,300,120);
	     rankButton.setBorderPainted(false); // 버튼 테두리 없애기
	     rankButton.setContentAreaFilled(false);// 버튼 배경없애기
	     rankButton.setFocusPainted(false); // 버튼 안에 이미지 테두리 없애기
	     background.add(rankButton);
	     scrollPane = new JScrollPane(background);
	     setContentPane(scrollPane);
	     rankButton.addMouseListener(new MouseAdapter() {
			@Override
			// 버튼에 마우스를 올렸을 때
			public void mouseEntered(MouseEvent e) {
				rankButton.setIcon(rankButtonEnteredImg);
			}

			@Override
			// 버튼에 마우스를 뗐을 때
			public void mouseExited(MouseEvent e) {
				rankButton.setIcon(rankButtonBasicImg);
			}

			@Override
			// 버튼을 눌렀을 때
			public void mousePressed(MouseEvent e) {
				startButton.setVisible(false);
				explanationButton.setVisible(false);
				rankButton.setVisible(false);
				bgimg = new ImageIcon(this.getClass().getResource("/rank_Background.jpg")).getImage();
				backButton.setVisible(true);

				rankRead();
				for(int i=0;i<10;i++){
					scoreList.get(i).setFont(new Font("돋움", Font.PLAIN, 20));
					scoreList.get(i).setBounds(480, 165+(i*48), 250, 25);
					background.add(scoreList.get(i));
					scoreList.get(i).setVisible(true);
				}
				nowScreen=3;
			}
		}); 
	     
	     backButton.setBounds(20,20,72,72);
	     backButton.setBorderPainted(false); // 버튼 테두리 없애기
	     backButton.setContentAreaFilled(false);// 버튼 배경없애기
	     backButton.setFocusPainted(false); // 버튼 안에 이미지 테두리 없애기
	     background.add(backButton);
	     backButton.setVisible(false);
	     scrollPane = new JScrollPane(background);
	     setContentPane(scrollPane);
	     backButton.addMouseListener(new MouseAdapter() {
			// 버튼을 눌렀을 때
			public void mousePressed(MouseEvent e) {
				backMain();
			}
		});
	     
	}
	
	public void backMain(){
		startButton.setVisible(true);
		explanationButton.setVisible(true);
		rankButton.setVisible(true);
		bgimg = new ImageIcon(this.getClass().getResource("/intro_Background.jpg")).getImage();
		backButton.setVisible(false);
		if(nowScreen==2){
			nowScreen=0;
			music.close();
			music=new Music("introBackgroundMusic.mp3",true);
			music.start();
			game.stop();
		}
		if(nowScreen==3){
			for(int i=0;i<10;i++){
				scoreList.get(i).setVisible(false);
			}
		}
	}
	
	public void rankRead(){
		File file=new File("score.txt");
		Scanner scan;
		try {
			scan = new Scanner(file);
			String line="";
			
			while(scan.hasNextLine()){
				line =scan.nextLine()+"개";
				scoreList.add(new JLabel(line));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
