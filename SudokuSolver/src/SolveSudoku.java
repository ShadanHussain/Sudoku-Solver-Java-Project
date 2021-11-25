class SolveSudoku
{
	private int N;
	private int n;
	int[][] board;
	boolean[][] oldtext;
	
	
	SolveSudoku(int N, int[][] board){
		this.N=N;
		n = (int)Math.sqrt(N);
		this.board = new int[N][N];
		for(int i =0;i<N;i++){
			for(int j=0;j<N;j++)
				this.board[i][j] = board[i][j];
		}
		oldtext = new boolean[N][N];
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(board[i][j]==0)
					oldtext[i][j]=false;
				else
					oldtext[i][j]=true;
			}
		}
	}
	
	
    public boolean isSafe(int row, int col,int num,boolean chk)
    {
       for (int d = 0; d < N; d++)
        {
    	   	if(chk==true && col==d)
    	   		continue;
            if (board[row][d] == num) 
                return false;
        }
        for (int r = 0; r < N; r++)
        {
            if(chk==true && row==r)
    	   		continue;
            if (board[r][col] == num)
            	return false;
            
        }
        int boxRowStart = row - row % n;
        int boxColStart = col - col % n;
 
        for (int r = boxRowStart; r < boxRowStart+n; r++)
        {
            for (int d = boxColStart;d < boxColStart + n; d++)
            {
            	if(chk==true && row==r && col==d)
        	   		continue;
                if (board[r][d] == num)
                	return false;
                
            }
        }
        return true;
    }
    
    public boolean check(){
    	for(int i=0;i<N;i++){
    		for(int j=0;j<N;j++){
    			if(board[i][j]!=0 && !isSafe(i,j,board[i][j],true))
    				return false;
    		}
    	}
    	return true; 
    }
    
    
    public void solveSudoku2(boolean[][] pretext){
    	
    	for(int i=N-1;i>=0;i--){
    		for(int j=N-1;j>=0;j--){
    			if(pretext[i][j])
    				continue;
    			int n = board[i][j];
    			for(int num=n+1;num<=N;num++){
    				if(isSafe(i,j,num,false)){
    					board[i][j] = num;
    					if(solveSudoku())
    						return;
    				}
    			}
    			board[i][j]=0;
    		}
    	}
    	solveSudoku();
    }
    public boolean solveSudoku()
    {
        int row = -1;
        int col = -1;
        boolean isFull = true;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (board[i][j] == 0)
                {
                    row = i;
                    col = j;
                    isFull = false;
                    break;
                }
            }
            if (!isFull) {
                break;
            }
        }
 
        if (isFull)
        	return true;
        
 
        
        for (int num = 1; num <= N; num++)
        {
            if (isSafe(row, col, num, false))
            {
                board[row][col] = num;
                if (solveSudoku())
                	return true;
                else
                	board[row][col] = 0;
                
            }
        }
        return false;
    }
 
   
    
}