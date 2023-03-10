package kr.co.kmarket2.controller;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmarket2.service.ProductService;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("product/list")
	public String list(String cate1, String cate2, String sort, Model model, @RequestParam(value="pg", defaultValue = "1") String pg) {
		// 페이징 처리
		int total = service.selectCountTotal(cate1, cate2);
		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage);
		int lastPageNum = service.getLastPageNum(total);
		int startPageNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		model.addAttribute("groups", groups);
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("lastPageNum", lastPageNum);
	    model.addAttribute("startPageNum", startPageNum);
		
		// 상품 정보 불러오기
		List<ProductVO> products = service.selectProducts(cate1, cate2, sort, start);
		// 할인된 가격 적용
		for(ProductVO product: products) {
			product.setDisPrice((int) Math.ceil(product.getPrice() * (1 - product.getDiscount() * 0.01)));
		}
		
		// 할인된 가격으로 sort(가격높은순, 가격낮은순)
		if(sort.equals(String.valueOf(2)))
			Collections.sort(products, (p1, p2) -> p1.getDisPrice() - p2.getDisPrice());
		else if(sort.equals(String.valueOf(3)))
			Collections.sort(products, (p1, p2) -> p2.getDisPrice() - p1.getDisPrice());
		
		model.addAttribute("products", products);
		model.addAttribute("cate1", cate1);
		model.addAttribute("cate2", cate2);
		model.addAttribute("sort", sort);
		
		// cate1, cate2 값에 따라 카테고리 및 하위 카테고리 이름 불러오기
		// 파라미터로 받아온 cate1, cate2값(실질적으로 int)을 이용해 DB에서 카테고리 및 하위카테고리 이름을 가져오기
		List<Cate2VO> cates = service.selectCates(cate1, cate2);
		model.addAttribute("cates", cates.get(0));
		
		return "product/list";
	}

	@GetMapping("product/cart")
	public String cart() {
		return "product/cart";
	}
	
	@GetMapping("product/view")
	public String view(String prodNo, String cate1, String cate2, Model model) {
		// cate1, cate2 값에 따라 카테고리 및 하위 카테고리 이름 불러오기(list() 참조)
		List<Cate2VO> cates = service.selectCates(cate1, cate2);
		model.addAttribute("cates", cates.get(0));
		
		// 파라미터로 받아온 prodNo값을 이용해 상품정보 DB에서 불러오기
		ProductVO product = service.selectProduct(prodNo);
		model.addAttribute("product", product);
		// 할인된 가격 적용
		product.setDisPrice((int) Math.ceil(product.getPrice() * (1 - product.getDiscount() * 0.01)));
		
		// 배송 예정 날짜 구하기
		String[] estimatedDeliveryInfo = service.getEstimatedDeliveryDate();
		model.addAttribute("estimatedDeliveryInfo", estimatedDeliveryInfo);
		
		return "product/view";
	}
	
	@GetMapping("product/order")
	public String order() {
		return "product/order";
	}
	
	@GetMapping("product/complete")
	public String complete() {
		return "product/complete";
	}
}
