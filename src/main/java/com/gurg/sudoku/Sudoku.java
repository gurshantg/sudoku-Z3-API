package com.gurg.sudoku;
import com.microsoft.z3.*;
public class Sudoku {
    //Cleaner implementation for writing output
    private static int[][] cleanWriting(Model model,IntExpr[][] vals){
        int[][] ans = new int[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                //If no ConstInterp exists, write value from input
                if(model.getConstInterp(vals[i][j])==null){
                    ans[i][j]=Integer.parseInt(vals[i][j]+"");
                }
                else{
                    ans[i][j]=Integer.parseInt(model.getConstInterp(vals[i][j])+"");
                }
            }
        }
        return ans;
    }
    public static int[][] solveSudoku(int[][] board) {

        Context ctx = new Context();
        Solver solver = ctx.mkSolver();

        //Loads values into IntExpr array
        IntExpr[][] d = new IntExpr[9][9];

        //If 0, assign d_i,j
        //Restrict digit [1,9]
        //Else just add value from input
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                int val=board[i][j];
                if(val==0){
                    d[i][j]=ctx.mkIntConst("d_" + i + "_" + j);
                    BoolExpr dom = ctx.mkAnd(ctx.mkLe(ctx.mkInt(1), d[i][j]), ctx.mkLe(d[i][j], ctx.mkInt(9)));
                    solver.add(dom);
                }
                else{
                    d[i][j]=ctx.mkInt(val);
                }
            }
        }
        //Ensure each row is distinct from one another
        for (int i=0;i<9;i++){
            BoolExpr dist = ctx.mkDistinct(d[i]);
            solver.add(dist);
        }
        //Create an array for each column
        //and ensures each is distinct from one another
        for (int i=0;i<9;i++){
            IntExpr[] cols = new IntExpr[9];
            for(int row = 0; row < 9; row++) {
                cols[row] = d[row][i];
            }
            BoolExpr dist = ctx.mkDistinct(cols);
            solver.add(dist);
        }
            //Create an array for each box 
            //and ensures each is distinct from one another
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                IntExpr[] box = new IntExpr[9];
                for (int m = 0; m < 3; ++m) {
                    for (int n = 0; n < 3; ++n) {
                        box[(3*m)+n]=d[(3*i)+m][(3*j)+n];
                    }
                }
                BoolExpr dist = ctx.mkDistinct(box);
                solver.add(dist);
            }
        }

        Status status = solver.check();
        //If not satisfiable, write "No solution" to output
        if(status==Status.UNSATISFIABLE){
            return null;
        }
        else{
            Model model = solver.getModel();
            int [][] ans=cleanWriting(model,d);
            ctx.close();
            return ans;
        }
    }
}