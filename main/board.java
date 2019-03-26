
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class board extends JFrame {
	static int x=6, y=7, point=4; 		//棋盘  y:长 7   x:宽 6  point:获胜连续个数
	static boolean first=true, piecedown=true, win=false; //PVE落子先后     棋子下落 
	int m=0, n=0;		//落子点
	int mode=2;		//模式 0:pvp 1:pveeasy 2:pvphard
	String player1="-YELLOW-",player2="-RED-";
    Color playerc1 = Color.YELLOW, playerc2=Color.RED;//player 颜色
    Color c0 = new Color(240, 255, 240),buttonc; //背景色   使用色
    Color swing;  //temp 颜色
    //ImageIcon playerIMG1 = new ImageIcon(board.class.getResource("image/yellow.png")), 
    //		playerIMG2 = new ImageIcon(board.class.getResource("image/red1.png"));
    JButton[][] buts = new JButton[x][y];
    public board() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);		
		JMenu mnNewMenu = new JMenu("游戏");
		menuBar.add(mnNewMenu);		
		JMenuItem restart = new JMenuItem("重新开始"); //刷新棋盘
		mnNewMenu.add(restart);
		JMenuItem sitting = new JMenuItem("设置  ");  //设置
		mnNewMenu.add(sitting);
		JMenuItem group = new JMenuItem("Our Group!");  //groupinfo
		mnNewMenu.add(group);
		JMenuItem exit = new JMenuItem("Exit");  //EXIT
		mnNewMenu.add(exit);
		JMenu modemenu = new JMenu(" 模式 ");
		menuBar.add(modemenu);
		JMenuItem pvp = new JMenuItem("PVP");
		modemenu.add(pvp);		
		JMenuItem pve = new JMenu(" PVE ");
		modemenu.add(pve);
		JMenuItem pveeasy = new JMenuItem("薛定谔bot ");
		pve.add(pveeasy);
		JMenuItem pvehard = new JMenuItem("bot");
		pve.add(pvehard);
		
		setResizable(false);  //禁止窗口放缩,拉伸
		
		exit.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {  
	            super.mouseClicked(e);  
	            System.exit(0);  
			}
		});
		sitting.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
	            sitting();  
			}
		});
		group.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
	            info();  
			}
		});
		restart.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
	            //super.mouseClicked(e);
				restart();
				modemenu.setVisible(true);
			}
		});
		pvp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mode=0;//pvp();buts
				restart();//modemenu.setVisible(false);
			}
		});
		pveeasy.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				mode=1;//pveeasy();
				restart();//modemenu.setVisible(false);
			}
		});
		pvehard.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				mode=2;//pvehard();
				restart();//modemenu.setVisible(false);
			}
		});
		
        setLayout(new GridLayout(x, y));
    	for(int i = 0; i < x; i++)   //斗牛士入场
        {
            for(int j = 0; j < y; j++) 
            {
                buts[i][j] = new JButton();
                //if(count % 2 == 0) {
                    buts[i][j].setBackground(c0); 
                /*} else {
                    buts[i][j].setBackground(Color.LIGHT_GRAY);  
                }*/
                buts[i][j].addMouseListener(new MouseAdapter()
                {
                    public void mousePressed(MouseEvent e){
                    	m=0; n=0;
                		for (int i = 0; i < x; i++) {
                            for (int j = 0; j < y; j++) {
                                if (buts[i][j] == e.getSource()) {
                                	m=i; n=j;
                                	System.out.println(m+","+n); 
                                	if(mode==0) pvp();
                                	else {pve(); if(!win && first) pve();}
                                }
                            }
                		}
                    }                          //已废弃功能
                    /*public void mouseEntered(MouseEvent e){  
                    	//System.out.println("mouseEntered---鼠标移入按钮区域"); 
                    	for (int i = 0; i < x; i++) {
                            for (int j = 0; j < y; j++) {
                                if (buts[i][j] == e.getSource()) {
                                	swing=buts[i][j].getBackground();
                                	m=i; n=j;
                                }
                            }
                    	}
                    	buts[m][n].setBackground(new Color(255,250,205));
                    }
                    public void mouseExited(MouseEvent e){  
                    	//System.out.println("mouseExited---鼠标移出按钮区域"); 
                    	buts[m][n].setBackground(swing);
                    }*/
                    
                    /*public void mouseClicked(MouseEvent e)  //测试用 作弊   o.O  可能造成卡顿!!
                    {
                        m=0; n=0;
                    	for (int i = 0; i < x; i++) {
                            for (int j = 0; j < y; j++) {
                               if (buts[i][j] == e.getSource()) {
                                    //buts[i][j].setBackground(Color.YELLOW);
                                    m=i; n=j;
                                    if(mode==0) pvp();
                                    else if(mode==1) pveeasy();
                                    else pvehard();
                                }
                            }
                    	}
                        if(e.getButton()==e.BUTTON1)  
                        {//System.out.println("mousePressed---鼠标按下事件");
                        	for (m=0;m+1<x && buts[m+1][n].getBackground()==(c0);m++); //棋子下落
                        	if(buts[m][n].getBackground()==c0 ) 
                            	buts[m][n].setBackground(buttonc);
                        	judger();
                        }  
                        if(e.getButton()==e.BUTTON3)  
                        {  
                        	buts[m][n].setBackground(Color.RED);
                        }  
                        if(e.getButton()==e.BUTTON2)  
                        {  
                        	buts[m][n].setBackground(c0); 
                        }
                    }*/
                });
                add(buts[i][j]);
            }
        }
   	}
	protected void pve() {
		// TODO Auto-generated method stub
		int num=0;
		for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (buts[i][j].getBackground()==(c0)) {
                	num++;
                }
            }
		}
    	if(first) {
    		if(num%2==0) buttonc=playerc1 ;
    		else {
    			buttonc=playerc2 ;
    			if(mode==1) stuip_bot();
    			else smart_bot();
    		}
    	}
    	else {
    		if(num%2==0) {
    			buttonc=playerc2 ;
    			if(mode==1) stuip_bot();
    			else smart_bot();
    		}
    		else {
    			buttonc=playerc1 ;
    		}
    	}
    	//try {Thread.sleep(200);} 
    	//catch (InterruptedException e) {e.printStackTrace();}
		setcolor();
    	judger();
	}
    protected void smart_bot() {
		// TODO Auto-generated method stub
    	int[][] scoremap = botScore();
    	int maxScore = 0;
    	for (int row = 0; row < x; row++)  
            for (int col = 0; col < y; col++)  
            {  
                // 前提是这个坐标是空的  
                if (buts[row][col].getBackground() == c0)  
                {  
                    if (scoremap[row][col] >= maxScore)          // 找最大的数和坐标  
                    {  
                        m=row;n=col;
                        maxScore = scoremap[row][col];  
                    }  
                    //else if (scoremap[row][col] == maxScore)      
                    //	m=row;n=col;
                }  
            }  
      
        // 随机落子，如果有多个点的话 
	}
    protected int[][] botScore() {
		// TODO Auto-generated method stub
    	int xx=x, yy = y;
    	int personNum = 0; // 玩家连成子的个数  
        int botNum = 0; // AI连成子的个数  
        int emptyNum = 0; // 各方向空白位的个数 
        int[][] scoremap = new int[xx][yy];
        for (int i=0;i<xx;i++) for (int j=0;j<yy;j++) scoremap[i][j]=0;
        for (int row = 0; row < xx; row++)  
            for (int col = 0; col < yy; col++)  
            {  
                // 空白点就算
            	if (piecedown) for (row=0;row+1<x && buts[row+1][col].getBackground()==(c0);row++);
                if (row >= 0 && col >= 0 && buts[row][col].getBackground() == c0)  
                {  
                    // 遍历周围八个方向  
                    for (int y = -1; y <= 1; y++)  
                        for (int x = -1; x <= 1; x++)  
                        {  
                            // 重置  
                            personNum = 0;  
                            botNum = 0;  
                            emptyNum = 0;  
      
                            // 原坐标不算  
                            if (!(y == 0 && x == 0))  
                            {  
                                // 每个方向延伸4个子  
      
                                // 防守评分（正反两个方向）  
                                for (int i = 1; i <= 4; i++)  
                                {  
                                    if (row + i * y >= 0 && row + i * y < xx &&  
                                        col + i * x >= 0 && col + i * x < yy &&  
                                        buts[row + i * y][col + i * x].getBackground() == playerc1 ) // 玩家的子  
                                    {  
                                        personNum++;  
                                    }  
                                    else if (row + i * y >= 0 && row + i * y < xx &&  
                                             col + i * x >= 0 && col + i * x < yy &&  
                                             buts[row + i * y][col + i * x].getBackground() == c0) // 空白位  
                                    {  
                                        emptyNum++;  
                                        break;  
                                    }  
                                    else            // 出边界  
                                        break;  
                                }  
      
                                for (int i = 1; i <= 4; i++)  
                                {  
                                    if (row - i * y >= 0 && row - i * y < xx &&  
                                        col - i * x >= 0 && col - i * x < yy &&  
                                        buts[row - i * y][col - i * x].getBackground() == playerc1) // 玩家的子  
                                    {  
                                        personNum++;  
                                    }  
                                    else if (row - i * y > 0 && row - i * y < xx &&  
                                             col - i * x > 0 && col - i * x < yy &&  
                                             buts[row - i * y][col - i * x].getBackground() == c0) // 空白位  
                                    {  
                                        emptyNum++;  
                                        break;  
                                    }  
                                    else            // 出边界  
                                        break;  
                                }  
      
                                if (personNum == 1)                       
                                	scoremap[row][col] += 10;  
                                else if (personNum == 2)                  
                                {  
                                    if (emptyNum == 1)  
                                    	scoremap[row][col] += 300;  
                                    else if (emptyNum >= 2)  
                                    	scoremap[row][col] += 400;  
                                }  
                                else if (personNum == 3)                 
                                { 
                                    if (emptyNum == 1)  
                                    	scoremap[row][col] += 1050;  
                                    else if (emptyNum >= 2)  
                                    	scoremap[row][col] += 1100;  
                                }  
                                else if (personNum == 4)                 
                                { 
                                    if (emptyNum == 1)  
                                    	scoremap[row][col] += 1150;  
                                    else if (emptyNum >= 2)  
                                    	scoremap[row][col] += 1200;  
                                }
                                
                                // 进行一次清空  
                                emptyNum = 0;  
      
                                // 进攻评分  
                                for (int i = 1; i <= 4; i++)  
                                {  
                                    if (row + i * y >= 0 && row + i * y < xx &&  
                                        col + i * x >= 0 && col + i * x < yy &&  
                                        buts[row + i * y][col + i * x].getBackground() == playerc2) // AI的子  
                                    {  
                                        botNum++;  
                                    }  
                                    else if (row + i * y >= 0 && row + i * y < xx &&  
                                             col + i * x >= 0 && col + i * x < yy &&  
                                             buts[row +i * y][col + i * x].getBackground() == c0) // 空白位  
                                    {  
                                        emptyNum++;  
                                        break;  
                                    }  
                                    else            // 出边界  
                                        break;  
                                }  
      
                                for (int i = 1; i <= 4; i++)  
                                {  
                                    if (row - i * y >= 0 && row - i * y < xx &&  
                                        col - i * x >= 0 && col - i * x < yy &&  
                                        buts[row - i * y][col - i * x].getBackground() == playerc2) // AI的子  
                                    {  
                                        botNum++;  
                                    }  
                                    else if (row - i * y >= 0 && row - i * y < xx &&  
                                             col - i * x >= 0 && col - i * x < yy &&  
                                             buts[row - i * y][col - i * x].getBackground() == c0) // 空白位  
                                    {  
                                        emptyNum++;  
                                        break;  
                                    }  
                                    else            // 出边界  
                                        break;  
                                }  
      
                                if (botNum == 0)                       
                                	scoremap[row][col] += 5;  
                                else if (botNum == 1)                  
                                	scoremap[row][col] += 10;  
                                else if (botNum == 2)  
                                {  
                                    if (emptyNum == 1)                 
                                    	scoremap[row][col] += 250;  
                                    else if (emptyNum >= 2)  
                                    	scoremap[row][col] += 500;   
                                }  
                                else if (botNum == 3)  
                                {  
                                    if (emptyNum == 1)                 
                                    	scoremap[row][col] += 1000;  
                                    else if (emptyNum >= 2)  
                                    	scoremap[row][col] += 1050;  
                                }
                                else if (botNum == 4)  
                                {  
                                    if (emptyNum == 1)                 
                                    	scoremap[row][col] += 1100;  
                                    else if (emptyNum >= 2)  
                                    	scoremap[row][col] += 1150;  
                                }
                            	scoremap[row][col] += (int)(System.currentTimeMillis()%5);
      
                            }  
                        }  
                }
                if (piecedown) if (col==yy-1) return scoremap;
            }  
        return scoremap;
	}
    protected void stuip_bot() {
		// TODO Auto-generated method stub
		m=(int)(System.currentTimeMillis()%x);
		n=(int)(System.currentTimeMillis()%y);
	}
	protected void pvp() {
		// TODO Auto-generated method stub
		for (int i = 0, num=0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (buts[i][j].getBackground()==(c0)) {
                	num++;
                	if(num%2==0) buttonc=playerc1 ;
                	else buttonc=playerc2 ;
                }
            }
		}
		setcolor();
    	judger();
	}
    protected void setcolor() {
		// TODO Auto-generated method stub
    	piecedown(); //棋子下落
    	if(buts[m][n].getBackground()==c0 ) {
        	buts[m][n].setBackground(buttonc);
        	/*
        	if(buttonc==playerc1) {  //设置图片
        		Image temp = playerIMG1.getImage().getScaledInstance(buts[m][n].getWidth(),  //图片缩放
        				buts[m][n].getHeight(), playerIMG1.getImage().SCALE_DEFAULT);//SCALE_SMOOTH 更平滑,但是运行不流畅
        		playerIMG1 = new ImageIcon(temp);
        		buts[m][n].setIcon(playerIMG1);
        	}
        	else {
        		Image temp = playerIMG2.getImage().getScaledInstance(buts[m][n].getWidth(),  
        				buts[m][n].getHeight(), playerIMG2.getImage().SCALE_DEFAULT);
        		playerIMG2 = new ImageIcon(temp);
        		buts[m][n].setIcon(playerIMG2);
        	}*/
    	}
    }
	/*protected void first_mark() {
		// TODO Auto-generated method stub
		int feedback=JOptionPane.showConfirmDialog(null,"先手?","",JOptionPane.YES_NO_OPTION);
		if(feedback==JOptionPane.YES_OPTION) first=true;
		else first=false;
	}
	protected void piecedown_mark() {
		// TODO Auto-generated method stub
		int feedback=JOptionPane.showConfirmDialog(null,"重力打开?","",JOptionPane.YES_NO_OPTION);
		if(feedback==JOptionPane.YES_OPTION) piecedown=true;
		else piecedown=false;
	}*/
	protected void piecedown() {
		// TODO Auto-generated method stub
		if(piecedown)
			for (m=0;m+1<x && buts[m+1][n].getBackground()==(c0);m++);
	}
	protected void sitting() {  
		// TODO Auto-generated method stub
		JTextField chang,kuan,lian,changin,kuanin,lianin,waring;
		JButton ok = new JButton("确定");
		//JButton fir = new JButton();
		JCheckBox fir = new JCheckBox("先手", first);
		JCheckBox pdown = new JCheckBox("重力打开", piecedown);
		JFrame sit = new JFrame("设置 ");
		
		sit.setResizable(false);  //禁止窗口放缩,拉伸
		/*
		if (first) fir.setText("先手: ✔");
		else fir.setText("先手: ✘");
		if (piecedown) pdown.setText("重力模式: ✔");
		else pdown.setText("重力模式: ✘");
		*/
		Container contentPane = sit.getContentPane();
		contentPane.setLayout(new BorderLayout());
		JPanel jp = new JPanel();
		
		ok.setBounds(400,200,50,20);
		fir.setBounds(300,100,50,20);
		pdown.setBounds(300,100,50,20);
		chang = new JTextField(" 棋盘长度 -> ",8);
		kuan = new JTextField(" 棋盘宽度 -> ",8);
		lian = new JTextField(" 获胜所需连续棋子数->  ",13);
		changin = new JTextField("7",5);
		kuanin = new JTextField("6",5);
		lianin = new JTextField("4",5);
		waring= new JTextField(" 请输入数字,错误输入无法创建新游戏  ", 30);
		chang.setFont(new Font("谐体",Font.BOLD|Font.ITALIC,13));
		kuan.setFont(new Font("谐体",Font.BOLD|Font.ITALIC,13));
		lian.setFont(new Font("谐体",Font.BOLD|Font.ITALIC,13));
		waring.setFont(new Font("谐体",Font.BOLD|Font.ITALIC,13));
		waring.setHorizontalAlignment(JTextField.CENTER);
		jp.add(chang);
		jp.add(changin);
		jp.add(kuan );
		jp.add(kuanin );
		jp.add(lian);
		jp.add(lianin);
		jp.add(waring);
		jp.add(fir);
		jp.add(pdown);
		jp.add(ok);
		contentPane.add(jp);
		
		sit.setSize(400,200);
		sit.setLocation(400, 200);
		sit.setVisible(true);
		
		fir.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                first=fir.isSelected();
            }
        });
		pdown.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                piecedown=pdown.isSelected();
            }
        });
		ok.addMouseListener(new MouseAdapter() {
			   public void mousePressed(MouseEvent e) {
				   y=Integer.parseInt(changin.getText());
				   x=Integer.parseInt(kuanin.getText());
				   point=Integer.parseInt(lianin.getText());
				   dispose();//setVisible(false);
				   sit.dispose();
				   power();
			   }
		});
	}
	protected int judger() {//Ride of the Valkyries 
		// TODO Auto-generated method stub
		win=false;
		int checknum=0, o=m, p=n, count ; //已检查个数  ,   检查坐标
		Color checkcolor = buts[m][n].getBackground();
		for(checknum=0;p-1>=0 && checknum<=point && buts[o][p-1].getBackground()==checkcolor ;checknum++,p--);//←水平检测<
		//System.out.println(o+" "+p);
		for(checknum=0;p<y && checknum<=point && buts[o][p].getBackground()==checkcolor;checknum++, p++);
		if(checknum>=point) {
			JOptionPane.showMessageDialog(null, whowin(buts[m][n].getBackground())+
					" win!!", "Click OK to continue", JOptionPane.OK_OPTION, new ImageIcon("image/win.png"));
			restart();
			win=true;
			return 0;
		}
		o=m; p=n; //复位
		for(checknum=0;o-1>=0 && checknum<=point && buts[o-1][p].getBackground()==checkcolor ;checknum++,o--);//↑竖直检测^
		//System.out.println(o+" "+p);
		for(checknum=0;o<x && checknum<=point && buts[o][p].getBackground()==checkcolor;checknum++, o++);
		if(checknum>=point) {
			JOptionPane.showMessageDialog(null, whowin(buts[m][n].getBackground())+
					" win!!", "Click OK to continue", JOptionPane.OK_OPTION, new ImageIcon("image/win.png"));
			restart();
			win=true;
			return 0;
		}
		o=m; p=n; //复位																		↖左倾检测◤
		for(checknum=0;o-1>=0 && p-1>=0 && checknum<=point && buts[o-1][p-1].getBackground()==checkcolor ;checknum++,o--,p--);
		for(checknum=0;o<x && p<y && checknum<=point && buts[o][p].getBackground()==checkcolor;checknum++, o++, p++);
		//System.out.println(o+" "+p);
		if(checknum>=point) {
			JOptionPane.showMessageDialog(null, whowin(buts[m][n].getBackground())+
					" win!!", "Click OK to continue", JOptionPane.OK_OPTION, new ImageIcon("image/win.png"));
			restart();
			win=true;
			return 0;
		}
		o=m; p=n; //复位																		↙右倾检测◣
		for(checknum=0;o+1<x && p-1>=0 && checknum<=point && buts[o+1][p-1].getBackground()==checkcolor ;checknum++,o++,p--);
		//System.out.println(o+" "+p);
		for(checknum=0;o>=0 && p<y && checknum<=point && buts[o][p].getBackground()==checkcolor;checknum++, o--, p++);
		if(checknum>=point) {
			JOptionPane.showMessageDialog(null, whowin(buts[m][n].getBackground())+
					" win!!", "Click OK to continue", JOptionPane.OK_OPTION, new ImageIcon("image/win.png"));
			restart();
			win=true;
			return 0;
		}
		for (o = 0,count=0; o < x; o++) {
            for (p = 0; p < y; p++) {
            	if(buts[o][p].getBackground()==c0) count++;
            }
		}
        if(count==0) JOptionPane.showMessageDialog(null," 平局  ", "Click OK to continue", 
        		JOptionPane.OK_OPTION, new ImageIcon("image/win.png"));
		count++;
		return 1;
	}
	protected String whowin(Color c1) {
		if (buts[m][n].getBackground()==playerc1) return player1;
		else return player2;
	}
	protected void restart() {
		for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
            	buts[i][j].setBackground(c0);
        		//buts[i][j].setIcon(null);
            }
		}
		win=false;
	} 
	public static void power() {
		// TODO Auto-generated method stub
		JFrame c4Board = new board();
		c4Board.setTitle("Connect 4");
		c4Board.setLocation(300, 200);
		c4Board.setSize(65*y, 65*x);  //窗口大小
		c4Board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c4Board.setVisible(true);
		//System.out.println(board.class.getResource("image/group.PNG"));
	}
	protected void info() {
		// TODO Auto-generated method stub
		JFrame TO = new JFrame("Product By:");
	 	JPanel panel = new JPanel();
	 	TO.getContentPane().add(panel, BorderLayout.CENTER);
	 	JLabel Label = new JLabel("");
	 	Label.setBackground(Color.CYAN);
	 	panel.add(Label);
	 	ImageIcon groupinfo = new ImageIcon(board.class.getResource("image/group.PNG"));
	 	Label.setIcon(groupinfo);
	 	TO.pack();
	 	TO.setLocation(400, 200);
	 	TO.setSize(650, 170);
	 	TO.setVisible(true);
	}
}