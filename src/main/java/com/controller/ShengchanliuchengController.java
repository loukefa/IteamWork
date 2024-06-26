package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ShengchanliuchengEntity;
import com.entity.view.ShengchanliuchengView;
import com.service.ShengchanliuchengService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 生产流程
 * 后端接口
 */
@RestController
@RequestMapping("/shengchanliucheng")
public class ShengchanliuchengController {
    @Autowired
    private ShengchanliuchengService shengchanliuchengService;


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShengchanliuchengEntity shengchanliucheng,
		HttpServletRequest request){
        EntityWrapper<ShengchanliuchengEntity> ew = new EntityWrapper<ShengchanliuchengEntity>();
		PageUtils page = shengchanliuchengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shengchanliucheng), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShengchanliuchengEntity shengchanliucheng, 
		HttpServletRequest request){
        EntityWrapper<ShengchanliuchengEntity> ew = new EntityWrapper<ShengchanliuchengEntity>();
		PageUtils page = shengchanliuchengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shengchanliucheng), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShengchanliuchengEntity shengchanliucheng){
       	EntityWrapper<ShengchanliuchengEntity> ew = new EntityWrapper<ShengchanliuchengEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shengchanliucheng, "shengchanliucheng")); 
        return R.ok().put("data", shengchanliuchengService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShengchanliuchengEntity shengchanliucheng){
        EntityWrapper< ShengchanliuchengEntity> ew = new EntityWrapper< ShengchanliuchengEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shengchanliucheng, "shengchanliucheng")); 
		ShengchanliuchengView shengchanliuchengView =  shengchanliuchengService.selectView(ew);
		return R.ok("查询生产流程成功").put("data", shengchanliuchengView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShengchanliuchengEntity shengchanliucheng = shengchanliuchengService.selectById(id);
        return R.ok().put("data", shengchanliucheng);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShengchanliuchengEntity shengchanliucheng = shengchanliuchengService.selectById(id);
        return R.ok().put("data", shengchanliucheng);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShengchanliuchengEntity shengchanliucheng, HttpServletRequest request){
    	shengchanliucheng.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shengchanliucheng);
        shengchanliuchengService.insert(shengchanliucheng);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShengchanliuchengEntity shengchanliucheng, HttpServletRequest request){
    	shengchanliucheng.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shengchanliucheng);
        shengchanliuchengService.insert(shengchanliucheng);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShengchanliuchengEntity shengchanliucheng, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shengchanliucheng);
        shengchanliuchengService.updateById(shengchanliucheng);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shengchanliuchengService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<ShengchanliuchengEntity> wrapper = new EntityWrapper<ShengchanliuchengEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = shengchanliuchengService.selectCount(wrapper);
		return R.ok().put("count", count);
	}

    /**
     * （按值统计）
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}")
    public R value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        EntityWrapper<ShengchanliuchengEntity> ew = new EntityWrapper<ShengchanliuchengEntity>();
        List<Map<String, Object>> result = shengchanliuchengService.selectValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * （按值统计）时间统计类型
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
    public R valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        params.put("timeStatType", timeStatType);
        EntityWrapper<ShengchanliuchengEntity> ew = new EntityWrapper<ShengchanliuchengEntity>();
        List<Map<String, Object>> result = shengchanliuchengService.selectTimeStatValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * 分组统计
     */
    @RequestMapping("/group/{columnName}")
    public R group(@PathVariable("columnName") String columnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("column", columnName);
        EntityWrapper<ShengchanliuchengEntity> ew = new EntityWrapper<ShengchanliuchengEntity>();
        List<Map<String, Object>> result = shengchanliuchengService.selectGroup(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

}
