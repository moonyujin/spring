package com.study.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.study.spring.entity.Board;
import com.study.spring.service.BoardService;


@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/board/write") //localhost:8080/board/write
	public String boardWriteForm() {

		return "boardwrite";
	}

	@PostMapping("/board/writepro")
	public String boardWritePro(Board board, Model model) {

		boardService.write(board);

		model.addAttribute("message", "글 작성이 완료되었습니다.");

		model.addAttribute("searchUrl", "/board/list");

		return "message";
	}



	@GetMapping("/board/list")
	public String boardList(Model model,
							@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
							String searchKeyword,
							String searchType) {

		System.out.println("searchType 확인 ; " + searchType);

		Page<Board> list = null;

		if(searchKeyword == null) {
			list = boardService.boardList(pageable);
		}else {
			list = boardService.boardSearchList(searchKeyword, pageable);
		}








		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage -4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());

		model.addAttribute("list", list);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);


		return "boardlist";
	}

	@GetMapping("/board/getpage") // localhost:8080/board/view?id=1
	public String boardGetPage(Model model, Integer id) {

		model.addAttribute("board", boardService.boardGetPage(id));

		return "boardgetpage";
	}

	@GetMapping("/board/delete")
	public String boardDelete(Integer id) {

		boardService.boardDelete(id);

		return "redirect:/board/list";
	}

	@GetMapping("/board/modify/{id}")
	public String boardModify(@PathVariable("id") Integer id,
							  Model model) {

		model.addAttribute("board", boardService.boardGetPage(id));

		return "boardmodify";
	}

	@PostMapping("/board/update/{id}")
	public String boardUpdate(@PathVariable("id") Integer id, Board board) {

		Board boardTemp = boardService.boardGetPage(id);
		boardTemp.setTitle(board.getTitle());
		boardTemp.setContent(board.getContent());

		boardService.write(boardTemp);

		return "redirect:/board/list";
	}
}
