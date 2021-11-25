import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Board_GUI {
		final int N = 9;
		final int n = (int)Math.sqrt(N); 
		JFrame frame;
		JPanel bigPanel;
		JPanel[][] smallPanel;
		Border border;
		final JTextField arr[][] = new JTextField[N][N];
		JPanel buttonPanel;
		final JButton solveSudoku = new JButton("Solve Sudoku");
		final JButton clear = new JButton("Clear");
		final JButton nextSolution = new JButton("Next Solution");
		SolveSudoku ob;
		Board_GUI(){
			frame = new JFrame("Sudoku Solver");
			bigPanel = new JPanel();
			smallPanel = new JPanel[n][n];
			bigPanel.setLayout(new GridLayout(n,n));
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					smallPanel[i][j] = new JPanel();
					smallPanel[i][j].setLayout(new GridLayout(n,n));
					border = BorderFactory.createLineBorder(Color.black);
					smallPanel[i][j].setBorder(border);
					bigPanel.add(smallPanel[i][j]);
				}
			}
			for(int i=0;i<N;i++){
				for(int j=0;j<N;j++){
					arr[i][j] = new JTextField();
					arr[i][j].setFont(new Font("Serif",Font.CENTER_BASELINE,30));
					arr[i][j].setHorizontalAlignment(JTextField.CENTER);
					int r = i/n;
					int c = j/n;
					smallPanel[r][c].add(arr[i][j]);
				}
			}
			buttonPanel = new JPanel();
			solveSudoku.setFont(new Font("Serif",Font.CENTER_BASELINE,20));
			clear.setFont(new Font("Serif",Font.CENTER_BASELINE,20));
			nextSolution.setFont(new Font("Serif",Font.CENTER_BASELINE,20));
			
			ActionListener a1 = new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					boolean err = false;
					boolean err1 = false;
					int[][] board = new int[N][N];
					for(int i=0;i<N;i++){
						for(int j=0;j<N;j++){
							if(arr[i][j].getText().equals(""))
								board[i][j] = 0;
							else{
								try{
									board[i][j] = Integer.parseInt(arr[i][j].getText());
									if(!(board[i][j]>=1 && board[i][j]<=N))
										err = true;
								}
								catch(NumberFormatException e){
									err = true; break;
								}
							}
						}
						if(err == true)
							break;
					}
					if(err == false){
						ob = new SolveSudoku(N,board);
						if(!ob.check())
							err1 = true;
						else{
							if(!ob.solveSudoku()){
								JFrame noSolution = new JFrame();
								JLabel label = new JLabel("No Solution");
								label.setFont(new Font("Serif,",Font.BOLD,30));
								noSolution.add(label);
								noSolution.setSize(200,200);
								noSolution.setVisible(true);
								noSolution.setResizable(false);
								noSolution.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							}
							else{
								for(int i=0;i<N;i++){
									for(int j=0;j<N;j++){
										if(ob.oldtext[i][j]==true)
											arr[i][j].setForeground(Color.black);
										else
											arr[i][j].setForeground(Color.red);
										arr[i][j].setText(ob.board[i][j]+"");
									
									}
									
								}
							}
						}
						
					}
					if(err || err1){
						JFrame error = new JFrame("Error");
						JLabel label = new JLabel("Invalid Input");
						label.setFont(new Font("Serif,",Font.BOLD,30));
						error.add(label);
						error.setSize(200,200);
						error.setVisible(true);
						error.setResizable(false);
						error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					}
					
					nextSolution.setEnabled(true);
				}
			};
			solveSudoku.addActionListener(a1);
			
			
			ActionListener a2 = new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					for(int i=0;i<N;i++){
						for(int j=0;j<N;j++){
							arr[i][j].setForeground(Color.black);
							arr[i][j].setText("");
						}
					}
					nextSolution.setEnabled(false);
				}
			};
			clear.addActionListener(a2);
			
			
			ActionListener a3 = new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					boolean err = false;
					boolean err1 = false;
					boolean err2 = false;
					int[][] board = new int[N][N];
					boolean chkFull = true;
					for(int i=0;i<N;i++){
						for(int j=0;j<N;j++){
							if(arr[i][j].getText().equals("")){
								chkFull = false;
								break;
							}
							else{
								try{
									board[i][j] = Integer.parseInt(arr[i][j].getText());
									if(!(board[i][j]>=1 && board[i][j]<=N))
										err = true;
								}
								catch(NumberFormatException e){
									err = true; break;
								}
							}
						}
						if(err == true)
							break;
					}
					if(!err){
						ob = new SolveSudoku(N,board);
						if(ob.check()){
							if(chkFull){
								boolean pretext[][] = new boolean[N][N];
								for(int i=0;i<N;i++){
									for(int j=0;j<N;j++){
										if(arr[i][j].getForeground() == Color.red)
											pretext[i][j]=false;
										else
											pretext[i][j]=true;
									
									}
								}
							
								ob.solveSudoku2(pretext);
								for(int i=0;i<N;i++){
									for(int j=0;j<N;j++)
										arr[i][j].setText(ob.board[i][j]+"");
								}
							}
							else
								err2 = true;
						}
						else
							err1=true;
					}
					if(err || err1 || err2){
						JFrame error = new JFrame("Error");
						JLabel label = new JLabel("Invalid Input");
						label.setFont(new Font("Serif,",Font.BOLD,30));
						error.add(label);
						error.setSize(200,200);
						error.setVisible(true);
						error.setResizable(false);
						error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					}
					
				}
			};
			nextSolution.addActionListener(a3);
			
			buttonPanel.add(solveSudoku);
			buttonPanel.add(clear);
			buttonPanel.add(nextSolution);
			nextSolution.setEnabled(false);
			buttonPanel.setLayout(new GridLayout(2,2));
			frame.getContentPane().setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weighty = 2;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.BOTH;
		    frame.add(bigPanel,gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weighty = 0.1;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.BOTH;
			frame.add(buttonPanel,gbc);
			frame.setSize(700,775);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
}
