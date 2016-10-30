package xiaogu.cash.model;

import java.util.Date;

public class User {
	private Integer id;
	private String wechat;
	private String wechatNickname;
	private String qq;
	private String phone;
	private String password;
	private Date registerTime;
	private Long registerIp;
	private Short status;
	private Short level;
	private Integer invitor;
	private String invitorCode;
	private Short source;
	
	public String getWechatNickname() {
		return wechatNickname;
	}
	public void setWechatNickname(String wechatNickname) {
		this.wechatNickname = wechatNickname;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public Long getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(Long registerIp) {
		this.registerIp = registerIp;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Short getLevel() {
		return level;
	}
	public void setLevel(Short level) {
		this.level = level;
	}
	public Integer getInvitor() {
		return invitor;
	}
	public void setInvitor(Integer invitor) {
		this.invitor = invitor;
	}
	public String getInvitorCode() {
		return invitorCode;
	}
	public void setInvitorCode(String invitorCode) {
		this.invitorCode = invitorCode;
	}
	public Short getSource() {
		return source;
	}
	public void setSource(Short source) {
		this.source = source;
	}
}
