package jp.co.internousecsite.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import jp.co.internousecsite.model.domain.MstUser;
import jp.co.internousecsite.model.form.LoginForm;

@Mapper
public interface MstUserMapper {
	
	@Select(value="SELECT * FROM mst_user WHERE user_name = #{userName} and password = #{password}")
	MstUser findByUserNameAndPassword(LoginForm form);
	
	@Select(value="SELECT count(id) FROM mst_user WHERE user_name =#{userName}")
	int findCountByUserName(String userName);
	

	

}
