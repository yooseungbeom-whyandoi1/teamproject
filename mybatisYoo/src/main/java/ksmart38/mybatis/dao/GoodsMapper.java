package ksmart38.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart38.mybatis.domain.Goods;
import ksmart38.mybatis.domain.Member;
@Mapper
public interface GoodsMapper {
	//상품조회
	public List<Goods> getGoodsList(String searchKey, String searchValue);
	
	//상품등록
	public int addGoods(Goods goods);
}
