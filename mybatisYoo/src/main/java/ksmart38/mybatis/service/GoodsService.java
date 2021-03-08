package ksmart38.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksmart38.mybatis.dao.GoodsMapper;
import ksmart38.mybatis.dao.MemberMapper;
import ksmart38.mybatis.domain.Goods;
import ksmart38.mybatis.domain.Member;

@Service
public class GoodsService {

	//3. DI 생성자 메서드 주입방식  //권장방식
	private final GoodsMapper goodsMapper;
	//3-1. spring 4.3 이후부터 @Autowired 쓰지 않아도 주입이 가능하다. spring-web 5.3.3쓰는중
	public GoodsService(GoodsMapper goodsMapper) {
		this.goodsMapper=goodsMapper;
	}
	
	
	/**********************************************/
	//상품등록
	public int addGoods(Goods goods) {
		return goodsMapper.addGoods(goods);
	}
	//상품조회
	public List<Goods> getGoodsList(String searchKey, String searchValue){
		if(searchKey != null) {
			if("goodsName".equals(searchKey)){
				searchKey = "g.g_name";
			}else if("memberId".equals(searchKey)) {
				searchKey = "m.m_id";
			}else if("memberName".equals(searchKey)) {
				searchKey = "m.m_name";
			}else {
				searchKey = "m.m_email";				
			}
		}
		
		return goodsMapper.getGoodsList(searchKey, searchValue);
	}
}
