package com.study.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.study.spring.entity.Board;
import com.study.spring.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	// 글 작성 처리
	public void write(Board board) {

		boardRepository.save(board);

	}


	// 게시글 리스트 처리
	public Page<Board> boardList(Pageable pageable) {

		return boardRepository.findAll(pageable);
	}

	public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {

	return boardRepository.findByTitleContaining(searchKeyword, pageable);
//		return boardRepository.findById(Integer.valueOf(searchKeyword), pageable);
	}

	/*
	public Page<Board> boardSearchId(String searchType, Integer id) {
		return boardRepository.findByIdContaining(searchType, id);
	}
	*/

	// 특정 게시글 불러오기
	public Board boardGetPage(Integer id) {

		return boardRepository.findById(id).get();
	}

	// 특정 게시글 삭제
	public void boardDelete(Integer id) {

		boardRepository.deleteById(id);
	}


}