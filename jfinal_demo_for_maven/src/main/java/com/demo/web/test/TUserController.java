package com.demo.web.test;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.peon.jfinal.core.XssInterceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.test.api.TUserService;
import com.demo.test.api.TUser_;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.activerecord.Page;

/**
 * TUserController
 * 所有 sql 与业务逻辑写在Service 中，尽量少写在 Controller 中
 */
@Controller
@Scope("prototype")//切记这里的多例标记 非常重要 除非共享controller 否则都建议使用单例  
public class TUserController extends JfinalxController {
	
	@Reference(version="1.0.0")
	private TUserService t_userService;
	
	
	/**
	 * 首页列表
	 */
	public void index() {
		
		Page<Map<String, Object>> t_user_page = t_userService.findPage(getParaToInt(0,1), fatchPs(), getMap(TUser_.class)," id desc ");
		
		setAttr("t_user_page", t_user_page);
		render("list_t_user.ftl");
	}
	
	
	/**
	 * 添加页面
	 */
	public void add(){
		render("add_t_user.ftl");
	}

	
	/**
	 * 修改页面
	 */
	public void edit(){
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			Map<String, Object> t_user = t_userService.findById(id);
			setAttr("t_user", t_user);
		}
		render("edit_t_user.ftl");
	}
	
	
	/**
	 * 保存动作
	 */
	@Before(XssInterceptor.class)
	public void save() {
		Map<String, Object> m = getMap("t_user.",TUser_.class,new String[]{"-t_user.id"});
		System.out.println(m);
		Long id = t_userService.save(m);
		if (id!=null&&id>0) {
			System.out.println("save ok");
		}
		redirect("/t_user");
	}
	
	
	/**
	 * 更新动作
	 */
	@Before(XssInterceptor.class)
	public void update() {
		Map<String, Object> m = getMap("t_user.",TUser_.class);
		System.out.println(m);
		boolean rs = t_userService.update(m);
		System.out.println("update :"+rs);
		redirect("/t_user");
	}
	
	
	/**
	 * 删除动作
	 */
	public void delete() {
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			boolean rs = t_userService.deleteById(id);
			System.out.println("删除结果："+rs);
		}
		redirect("/t_user");
	}
	
	
	/**
	 * ajax删除动作
	 */
	@ActionKey("/t_user/a/del")
	public void del() {
		Long id = getParaToLong(0,-1L);
		setAttr("code", "-1");
		if (id>0) {
			boolean rs = t_userService.deleteById(id);
			System.out.println("删除结果："+rs);
			setAttr("code", "1");
		}
		setAttr("msg", "删除成功!");
		
		renderJson();
	}
	
	
	/**
	 * ajax列表页面
	 */
	public void ajax_t_user_list(){
		render("ajax_t_user_list.ftl");
	}
	
	
	/**
	 * ajax列表 
	 */
	public void ajax_t_user_list_page(){
		
		Page<Map<String, Object>> t_user_page = t_userService.findPage(fatchPn(), fatchPs(), getMap(TUser_.class)," id desc ");
		
		setAttr("t_user_page", t_user_page);
		render("ajax_t_user_list_page.ftl");
	}
	
	
}


