package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.Cate1VO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;

@Mapper
@Repository
public interface AdminDAO {
	
	public List<Cate1VO> selectCate1 ();
	public List<Cate2VO> selectCate2 (int cate1);
	public int insertProduct (ProductVO vo);
}
