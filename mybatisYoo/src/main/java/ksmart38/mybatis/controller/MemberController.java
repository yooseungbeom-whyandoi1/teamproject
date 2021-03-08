package ksmart38.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ksmart38.mybatis.domain.Member;
import ksmart38.mybatis.service.MemberService;

@Controller
public class MemberController {
	
	//1. 필드주입방식
	@Autowired
	private MemberService memberService;
	
	@PostConstruct //객체가 생성이됐을때 이러한 메서드를 실행해주세요
	public void initialize() {
		System.out.println("====================================");
		System.out.println("MemberController bean 등록");
		System.out.println("====================================");
	}
	/****************************************/
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(value ="memberId", required = false)String memberId
						,@RequestParam(value ="memberPW", required = false)String memberPw
						,HttpSession session){
		if(memberId !=null && !"".equals(memberId) &&
			memberPw !=null && !"".equals(memberPw)){
			Member member = memberService.getMemberInfoById(memberId);
			if(member != null && memberPw.equals(member.getMemberPw())) {
				//아이디
				session.setAttribute("SID", member.getMemberId());
				//권한
				session.setAttribute("SLEVEL", member.getMemberLevelName());
				//이름
				session.setAttribute("SNAME", member.getMemberName());
				
				return "redirect:/";
			}
		}
		return "login/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
	

	
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam(value="memberId", required = false) String memberId
							  ,@RequestParam(value="memberPw", required = false) String memberPw
							  ,RedirectAttributes redirectAttr) {
		
		String result = memberService.removeMember(memberId, memberPw);
		
		System.out.println(result);
		
		if("회원삭제 실패".equals(result)) {
			redirectAttr.addAttribute("memberId", memberId);
			redirectAttr.addAttribute("result", result);
			return "redirect:/removeMember";
		}
		return "redirect:/memberList";
	}
	
	@GetMapping("/removeMember")
	public String removeMember( Model model
							   ,@RequestParam(value="memberId", required = false) String memberId
							   ,@RequestParam(value="result", required = false) String result) {
		model.addAttribute("result", result);
		model.addAttribute("memberId", memberId);
		
		return "member/removeMember";
	}
	
	
	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		
		System.out.println("==============================");
		System.out.println("커맨드객체를 통해 화면에서 입력받은 값->" + member);
		System.out.println("==============================");
		
		memberService.modifyMember(member);
		
		return "redirect:/memberList";
	}
	@GetMapping("/modifyMember")
	public String getmodifyMember(Model model,@RequestParam(name = "memberId", required = false) String memberId) {
		System.out.println("====================================");
		System.out.println("@GetMapping modifyMember "+ memberId);
		System.out.println("====================================");
		
		Member member = memberService.getMemberInfoById(memberId);
		model.addAttribute("member", member);
		return "member/modifyMember";		
	}
	
	
	@RequestMapping(value = "/ajax/idCheck", method=RequestMethod.POST)
	public @ResponseBody boolean idCheck(@RequestParam(value = "memberId", required = false)String memberId) {
		boolean checkResult = false;
		if(memberId != null && !"".equals(memberId)) {
			Member member = memberService.getMemberInfoById(memberId);
			if(member != null) {
				checkResult = true;
			}
		}
		return checkResult;
	}
	
	@PostMapping("/addMember")
	public String addMember(Member member) {
		System.out.println("====================================");
		System.out.println("@PostMapping addMember "+ member);
		System.out.println("====================================");
		
		if(member != null && !"".equals(member.getMemberId())){ //등록에는 아이디 null처리 꼭해야함
			//회원등록
			memberService.addMember(member);
		}
		return "redirect:/memberList"; 
	}
	@RequestMapping(value = "/addMember" , method = RequestMethod.GET)
	public String addMember() {
		return "member/addMember";
	}
	
	
	@GetMapping("/memberList")
	public String getMemberList(Model model
								,@RequestParam(name = "searchKey", required = false) String searchKey
								,@RequestParam(name = "searchValue", required = false) String searchValue) {
		System.out.println(searchKey);
		System.out.println(searchValue);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("searchKey", searchKey);
		paramMap.put("searchValue", searchValue);
		
		List<Member> memberList = memberService.getMemberList(paramMap);
		System.out.println("==============memberList============");
		System.out.println("@GetMapping memberList"+memberList);
		System.out.println("====================================");
		model.addAttribute("memberList", memberList);
		
		return "member/memberList";		
	}
	
	
}
