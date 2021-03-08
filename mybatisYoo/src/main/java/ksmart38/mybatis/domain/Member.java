package ksmart38.mybatis.domain;

public class Member {
	private String memberId;		//회원아이디
	private String memberPw;		//회원비밀번호
	private String memberName;		//회원이름
	private int    memberLevel;		//회원등급
	private String memberLevelName;	//회원등급
	private String memberAddr;		//회원주소
	private String memberEmail;		//회원이메일
	private String memberRegDate;	//회원등록날짜
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}
	
	public String getMemberLevelName() {
		return memberLevelName;
	}
	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}
	public String getMemberAddr() {
		return memberAddr;
	}
	public void setMemberAddr(String memberAddr) {
		this.memberAddr = memberAddr;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberRegDate() {
		return memberRegDate;
	}
	public void setMemberRegDate(String memberRegDate) {
		this.memberRegDate = memberRegDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Member [memberId=");
		builder.append(memberId);
		builder.append(", memberPw=");
		builder.append(memberPw);
		builder.append(", memberName=");
		builder.append(memberName);
		builder.append(", memberLevel=");
		builder.append(memberLevel);
		builder.append(", memberLevelName=");
		builder.append(memberLevelName);
		builder.append(", memberAddr=");
		builder.append(memberAddr);
		builder.append(", memberEmail=");
		builder.append(memberEmail);
		builder.append(", memberRegDate=");
		builder.append(memberRegDate);
		builder.append("]");
		return builder.toString();
	}
	
}
