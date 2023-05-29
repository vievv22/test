package com.ezen;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.dto.SampleDTO;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {
	
	//=/sample/ex1
	@GetMapping("/ex1")
	public void ex1() {
		log.info("ex1.......");
	}
	
	@GetMapping({"/ex2", "/exlink"})
	public void exModel(Model model) {
		//20의 데이터를 리스트로 만들어서 보내려고 함.
		List<SampleDTO> list = new ArrayList<>();
		
		for(int i=1; i<=20; i++) {
			SampleDTO dto = SampleDTO.builder()
					.sno((long)i)
					.first("first..." + i)
					.last("Last..." + i)
					.regTime(LocalDateTime.now())
					.build();
		
		list.add(dto);
		}
		model.addAttribute("list", list);
	}
	
	//
	@GetMapping({"/exInline"})
		public String exInline(RedirectAttributes redirectAttributes) {
			SampleDTO dto = SampleDTO.builder()
					.sno(100L)
					.first("First...100")
					.last("Last...100")
					.regTime(LocalDateTime.now())
					.build();
				
			//전달하고 삭제됨
			redirectAttributes.addFlashAttribute("dto", dto);
			redirectAttributes.addFlashAttribute("result", "success");
			
			return "redirect:/sample/ex3";
	}
	
	@GetMapping("/ex3")
	public void ex3() {
		System.out.println("ex3()...");
	}

	@GetMapping({"/exLayout1", "/exLayout2", "/exTemplate", "/exSidebar"})
	public void exLatout1() {
		System.out.println("exLayout...");
	}
	

}










