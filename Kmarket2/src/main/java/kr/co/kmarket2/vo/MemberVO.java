package kr.co.kmarket2.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberVO {
	private String uid;
	private String pass;
	private String pass1;
	private String pass2;
	private String name;
	private int gender;
	private String hp;
	private String email;
	private int type;
	private String point;
	private int level;
	private String zip;
	private String addr1;
	private String addr2;
	private String company;
	private String ceo;
	private String comRegNum;
	private String bizRegNum;
	private String tel;
	private String manager;
	private String managerHp;
	private String fax;	
	private String regip;
	private String wdate;
	private String rdate;
	private int locationTerms;
	
	
	
}
