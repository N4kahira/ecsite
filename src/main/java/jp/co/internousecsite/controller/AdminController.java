package jp.co.internousecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internousecsite.model.domain.MstGoods;
import jp.co.internousecsite.model.domain.MstUser;
import jp.co.internousecsite.model.form.GoodsForm;
import jp.co.internousecsite.model.form.LoginForm;
import jp.co.internousecsite.model.mapper.MstGoodsMapper;
import jp.co.internousecsite.model.mapper.MstUserMapper;

@Controller
@RequestMapping("/ecsite/admin")
public class AdminController {

	
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private MstGoodsMapper goodsMapper;
	
	@RequestMapping("/")
	public String index() {
		return "admintop";
	}
	
	
	@PostMapping("/welcome")
	public String welcome(LoginForm form, Model model) {
		/*ブラウザで入力された項目が
		自動的にformクラスの各フィールドに格納される
		htmlタグのname属性と変数名一致が条件*/
		
		MstUser user = userMapper.findByUserNameAndPassword(form);
		//MstUserMapperのfind~メソッドを呼び出す
		//sql文が実行結果が返却されMstUser型の変数userに代入される
		
		if(user == null) {
			model.addAttribute("errMessage","ユーザー名またはパスワードが違います。");
			return "forward:/ecsite/admin/";
		}
		/*if文でユーザー検索の結果を判定
		 * nll(ヒットしない)場合、
		 *ブロック内の処理が実行され、
		 *forwardによりトップべーじに戻る*/
		
		if(user.getIsAdmin() == 0) {
			model.addAttribute("errMessage","管理者ではありません。");
			return "forward:/ecsite/admin/";
		}
		/*if文によってログインしたユーザーが管理者
		 *かどうかをisAdminで判定。
		 *管理者ではなかった場合、
		 *forwardによりトップページに戻る*/
		
		
		List<MstGoods> goods = goodsMapper.findAll();
		model.addAttribute("userName",user.getUserName());
		model.addAttribute("password",user.getPassword());
		model.addAttribute("goods",goods);
		/*MstGoodsMapperのfindAllメソッドによって
		 * 商品情報をすべて検索し、HTMLに渡す情報を
		 * modelに登録している*/
		
		return "welcome";
		//welcom.htmlに遷移させる。
	}
	
	
	@PostMapping("/goodsMst")
	public String goodsMst(LoginForm f,Model m) {
		m.addAttribute("userName",f.getUserName());
		m.addAttribute("password",f.getPassword());
		
		return "goodsmst";
	}
	
	
	@PostMapping("/addGoods")
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		m.addAttribute("userName",loginForm.getUserName());
		m.addAttribute("password",loginForm.getPassword());
		
		MstGoods goods = new MstGoods();
		goods.setGoodsName(goodsForm.getGoodsName());
		goods.setPrice(goodsForm.getPrice());
		
		goodsMapper.insert(goods);
		
		return "forward:/ecsite/admin/welcome";
	}
	
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	public String deleteApi(@RequestBody GoodsForm f, Model m) {
		try {
			goodsMapper.deleteById(f.getId());
		} catch (IllegalArgumentException e) {
			return "-1"; //例外がキャッチされた場合、処理に失敗した印として”－1”を返却
		}
			return "1"; //例外が起きずにここまで到達できれば、処理が成功した印として”1”を返却
			
	}
	
}
