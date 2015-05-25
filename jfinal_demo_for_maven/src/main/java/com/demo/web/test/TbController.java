package com.demo.web.test;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.peon.jfinal.core.XssInterceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.test.api.TbService;
import com.demo.test.api.Tb_;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.JfinalxController;
import com.jfinal.plugin.activerecord.Page;

/**
 * TbController
 * 所有 sql 与业务逻辑写在Service 中，尽量少写在 Controller 中
 */
@Controller
@Scope("prototype")//切记这里的多例标记 非常重要 除非共享controller 否则都建议使用单例  
public class TbController extends JfinalxController {
	
	@Reference(version="1.0.0")
	private TbService tbService;
	
	
	/**
	 * 首页列表
	 */
	public void index() {
		
		Page<Map<String, Object>> tb_page = tbService.findPage(getParaToInt(0,1), fatchPs(), getMap(Tb_.class)," id desc ");
		
		setAttr("tb_page", tb_page);
		render("list_tb.ftl");
	}
	
	
	/**
	 * 添加页面
	 */
	public void add(){
		render("add_tb.ftl");
	}

	
	/**
	 * 修改页面
	 */
	public void edit(){
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			Map<String, Object> tb = tbService.findById(id);
			setAttr("tb", tb);
		}
		render("edit_tb.ftl");
	}
	
	
	/**
	 * 保存动作
	 */
	@Before(XssInterceptor.class)
	public void save() {
		Map<String, Object> m = getMap("tb.",Tb_.class,new String[]{"-tb.id"});
		System.out.println(m);
		Long id = tbService.save(m);
		if (id!=null&&id>0) {
			System.out.println("save ok");
		}
		redirect("/tb");
	}
	
	
	/**
	 * 更新动作
	 */
	@Before(XssInterceptor.class)
	public void update() {
		Map<String, Object> m = getMap("tb.",Tb_.class);
		System.out.println(m);
		boolean rs = tbService.update(m);
		System.out.println("update :"+rs);
		redirect("/tb");
	}
	
	
	/**
	 * 删除动作
	 */
	public void delete() {
		Long id = getParaToLong(0,-1L);
		if (id>0) {
			boolean rs = tbService.deleteById(id);
			System.out.println("删除结果："+rs);
		}
		redirect("/tb");
	}
	
	
	/**
	 * ajax删除动作
	 */
	@ActionKey("/tb/a/del")
	public void del() {
		Long id = getParaToLong(0,-1L);
		setAttr("code", "-1");
		if (id>0) {
			boolean rs = tbService.deleteById(id);
			System.out.println("删除结果："+rs);
			setAttr("code", "1");
		}
		setAttr("msg", "删除成功!");
		
		renderJson();
	}
	
	
	/**
	 * ajax列表页面
	 */
	public void ajax_tb_list(){
		render("ajax_tb_list.ftl");
	}
	
	
	/**
	 * ajax列表 
	 */
	public void ajax_tb_list_page(){
		
		Page<Map<String, Object>> tb_page = tbService.findPage(fatchPn(), fatchPs(), getMap(Tb_.class)," id desc ");
		
		setAttr("tb_page", tb_page);
		render("ajax_tb_list_page.ftl");
	}
	
	
}


