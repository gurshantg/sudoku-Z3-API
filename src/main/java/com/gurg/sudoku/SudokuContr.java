package com.gurg.sudoku;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuContr {

	@CrossOrigin(origins ="http://localhost:3000")
	@PostMapping ("/solveSudoku")
	public int[][] solveSudoku(@RequestBody int[][] inputGrid) {
		return Sudoku.solveSudoku(inputGrid);
	}
}