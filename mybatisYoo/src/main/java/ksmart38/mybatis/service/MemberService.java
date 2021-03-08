package ksmart38.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart38.mybatis.dao.MemberMapper;
import ksmart38.mybatis.domain.Member;

@Service
@Transactional
public class MemberService {
	
	private static final Logger log = LoggerFactory.getLogger(MemberService.class);

	/*//1. DI 필드 주입방식
	@Autowired
	private MemberMapper memberMapper;
	
	
	//2. DI SETTER 메소드 주입방식 
	private MemberMapper memberMapper2; //ex
	@Autowired
	public void setMemberMapper(MemberMapper memberMapper) {
		this.memberMapper2 = memberMapper;
	}
	*/
	//3. DI 생성자 메서드 주입방식  //권장방식
	private final MemberMapper memberMapper;
	//3-1. spring 4.3 이후부터 @Autowired 쓰지 않아도 주입이 가능하다. spring-web 5.3.3쓰는중
	@Autowired
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper=memberMapper;
	}

	@PostConstruct //객체가 생성이됐을때 이러한 메서드를 실행해주세요
	public void initialize() {
		log.error("MemberService :::::: {}","initialize()");
		System.out.println("====================================");
		System.out.println("MemberService bean 등록");
		System.out.println("====================================");
	}
	
	/**********************************************/
	

	public Map<String,Object> getLoginHistory(int currentPage) {
		//페이지에 보여줄 행의 갯수
		int rowPerPage = 5;
		// login table 행의 시작점
		int startNum= 0;
		//마지막 페이지
		int lastPage = 0;
		
		int startPageNum = 1;
		int endPageNum = 10;
		
		//페이지 알고리즘 (현재페이지-1)*페이지에 보여줄 행의 갯수 LIMIT ?,5
		startNum = (currentPage-1)*rowPerPage;
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("startNum", startNum);
		paramMap.put("rowPerPage", rowPerPage);
		
		 List<Map<String,Object>> loginHistory = memberMapper.getLoginHistory(paramMap);
		 
		 // 로그인이력 행의 갯수 
		 double LoginHistoryCount = memberMapper.getLoginHistoryCount();
		 
		 //마지막 페이지 소숫점반올림
		 lastPage=(int) Math.ceil(LoginHistoryCount/rowPerPage);
				 
		 if(currentPage >6) {
				startPageNum = currentPage -5;
				endPageNum = currentPage +4;
				
				if(endPageNum >=lastPage) {
					startPageNum = lastPage -9;
					endPageNum = lastPage;
				}
		 }
		 
		 Map<String,Object> resultMap = new HashMap<String,Object>();
		 resultMap.put("loginHistory", loginHistory);
		 resultMap.put("lastPage", lastPage);
		 resultMap.put("startPageNum", startPageNum);
		 resultMap.put("endPageNum", endPageNum);
		 
		return resultMap;
	}
	
	
	//회원정보검색조회
	/*
	 * public List<Member> getMemberList(String sk, String sv){ List<Member>
	 * memberList = memberMapper.getMemberList(); return memberList; }
	 */
	
	//회원정보삭제
	public String removeMember(String memberId, String memberPw) {
		String result = "회원삭제 실패";
		
		Member member = memberMapper.getMemberInfoById(memberId);
		
		if(memberPw.equals(member.getMemberPw())) {
			if(member.getMemberLevel() == 2) {
				memberMapper.removeOrderBySellerId(memberId);
				memberMapper.removeGoods(memberId);
			}
			
			if(member.getMemberLevel() == 3) {
				memberMapper.removeOrder(memberId);
			}
			
			memberMapper.removeLogin(memberId);
			memberMapper.removeMember(memberId);
			
			result = "회원삭제완료";
		}
		
		
		return result;
	}
	
	//회원정보수정
	public int modifyMember(Member member) {
		return memberMapper.modifyMember(member);
	}
	
	//회원정보조회
	public Member getMemberInfoById(String memberId) {
		Member member = memberMapper.getMemberInfoById(memberId);
		int memberLevel = 0;
		
		if(member != null && member.getMemberLevel() != 0) {
			memberLevel += member.getMemberLevel();
			
			switch (memberLevel) {
			case 1:
				member.setMemberLevelName("관리자");
				break;
			case 2:
				member.setMemberLevelName("판매자");
				break;
			case 3:
				member.setMemberLevelName("구매자");
				break;
			case 4:
				member.setMemberLevelName("회원");
				break;
			default:
				member.setMemberLevelName("비회원");
				break;
			}
		}
		
		return member;
	}
	
	//회원등록
	public int addMember(Member member) {
		int result = memberMapper.addMember(member);
		return result;
	}
	
	//회원등급이름 컬럼삽입 전체목록조회
	public List<Member> getMemberList(Map<String, Object> paramMap){
		String searchKey = (String) paramMap.get("searchKey");
		/* return memberMapper.getMemberList(); */
		//memberList 안에 객체 회원레벨 1:관리자, 2:판매자, 3:구매자 etc
		//memberLevelName 			: 관리자 or 판매자 or 구매자 etc
		
		  if(searchKey != null) {
			  if("1".equals(searchKey)) searchKey = "m_id";
			  if("2".equals(searchKey)) searchKey = "m_level";
			  if("3".equals(searchKey)) searchKey = "m_name";
			  if("4".equals(searchKey)) searchKey = "m_addr";
			  if("5".equals(searchKey)) searchKey = "m_reg_date";
			  if("6".equals(searchKey)) searchKey = "m_email";
			  paramMap.put("searchKey", searchKey);
		  }
		List<Member> memberList = memberMapper.getMemberList(paramMap);

		  int memberListSize = memberList.size();
		  for(int i =0;i<memberListSize; i++) {
			if(memberList.get(i).getMemberLevel() == 1) {
				memberList.get(i).setMemberLevelName("관리자");
			}else if (memberList.get(i).getMemberLevel() == 2 ) {
				memberList.get(i).setMemberLevelName("판매자");
			}else if(memberList.get(i).getMemberLevel() == 3) {
				memberList.get(i).setMemberLevelName("구매자");
			}
		  }
		  
		System.out.println(memberList+"memberList");
		return memberList; 
	}

	
	
}
