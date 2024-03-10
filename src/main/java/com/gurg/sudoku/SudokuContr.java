package com.gurg.sudoku;

import org.springframework.web.bind.annotation.*;

@RestController
public class SudokuContr {

	@CrossOrigin(origins ="https://sudokudonequick.netlify.app")
	@PostMapping ("/solveSudoku")
	public int[][] solveSudoku(@RequestBody int[][] inputGrid) {
		return Sudoku.solveSudoku(inputGrid);
	}
}