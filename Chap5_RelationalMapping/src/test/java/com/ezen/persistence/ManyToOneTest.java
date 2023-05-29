package com.ezen.persistence;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.domain.Board;
import com.ezen.domain.Member;

@SpringBootTest
public class ManyToOneTest {

	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Test
	@Disabled
	public void testManyToOneInsert() {
		Member member1 = Member.builder()
				.id("member1")
				.password("1111")
				.name("홍길동")
				.role("User")
				.build();
		
		memberRepo.save(member1);
		
		Member member2 = Member.builder()
				.id("member2")
				.password("2222")
				.name("이순신")
				.role("Admin")
				.build();
		
		memberRepo.save(member2);
		
		// 게시글 데이터 저장
		for(int i=1; i<=3; i++) {
			Board board = Board.builder()
					.member(member1)
					.title("홍길동이 작성한 게시글" + i)
					.content("홍길동 게시글 내용" + i)
					.createDate(new Date())
					.cnt(0)
					.build();
			
			boardRepo.save(board);
		}
		
		for(int i=1; i<=3; i++) {
			Board board = Board.builder()
					.member(member2)
					.title("이순신이 작성한 게시글" + i)
					.content("이순신 게시글 내용" + i)
					.createDate(new Date())
					.cnt(0)
					.build();
			
			boardRepo.save(board);
		}
	}
	
	//조회 테스트(다대일 단방향 테스트)
	@Test
	@Disabled
	public void testManyToOneSelect() {
		Board board = boardRepo.findById(5L).get();
		
		System.out.println(">>> 게시글 정보");
		System.out.println(board);
		
		System.out.println("작성자: " + board.getMember().getName());
	}
	
	//조회 테스트(다대일 양방향 테스트)
	@Transactional//스프링 프레임워크
	@Test
	@Disabled
	public void testTwoWaySelect() {
		Member result = memberRepo.findById("member1").get();
		
		List<Board> list = result.getBoardList();
		
		System.out.println(">>> 회원명: " + result);
		
		//회원이 작성한 글
		for(Board board : list) {
			System.out.print(board.getTitle() + " : ");
			System.out.println(board.getContent());
			
		}
		
	}
	
	//영속성 전이 인서트 테스트 (빌더 삭제)
	@Test
	public void testManyToOneInsertCascade() {
		Member member1 = new Member();
		
		member1.setId("member3");
		member1.setPassword("111");
		member1.setName("유관순");
		member1.setRole("User");
		
		Member member2 = new Member();
		member2.setId("member4");
		member2.setPassword("2222");
		member2.setName("장보고");
		member2.setRole("Admin");
		
		// 게시글 데이터 저장
		for(int i=1; i<=3; i++) {
			Board board = new Board();
			board.setMember(member1);
			board.setTitle("유관순이 등록한 게시글 " + i);
			board.setContent("유관순이 등록한 게시글 내용 " + i);
			board.setCreateDate(new Date());
			board.setCnt(0);
		}
		
		memberRepo.save(member1);
		
		for(int i=1; i<=3; i++) {
			Board board = new Board();
			board.setMember(member2);
			board.setTitle("장보고가 등록한 게시글 " + i);
			board.setContent("장보고가 등록한 게시글 내용 " + i);
			board.setCreateDate(new Date());
			board.setCnt(0);
		}
		
		memberRepo.save(member2);
	}
	
	@Test
	public void testCascadeDelete() {
		memberRepo.deleteById("member2");
	}
}











