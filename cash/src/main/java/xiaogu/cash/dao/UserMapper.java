package xiaogu.cash.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xiaogu.cash.model.User;

@Repository
public interface UserMapper {
	int addNewUser(User user);
	
	int updateUser(User user);
	
	int checkPhoneExistOrNot(String phone);
	
	User selectUserById(Integer id);
	
	User selectUserByIdForUpdate(Integer id);
	
	User selectUserByPhone(String phone);
	/**
     * 通过微信openid选择用户
     * @param wechat 用户的openId
     * @return User
     */
	User selectUserByWechat(String wechat);
	
	User selectUserByInviteCode(String inviteCode);
	
	List<Map<String,Object>> selectUserForParams(Map<String,Object> params);
	
	int selectUserForParamsCount(Map<String,Object> params);
	/**
     * 查看用户的openId在user_main表中的记录数
     * @param wechat 用户的openId
     * @return int
     */
	int countOpenId(String wechat);
}
