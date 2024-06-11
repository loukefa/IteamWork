package com.entity.view;

import com.baomidou.mybatisplus.annotations.TableName;
import com.entity.ShengchanliuchengEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;


/**
 * 生产流程
 * 后端返回视图实体辅助类 （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("shengchanliucheng")
public class ShengchanliuchengView extends ShengchanliuchengEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ShengchanliuchengView(){
	}
 
 	public ShengchanliuchengView(ShengchanliuchengEntity shengchanliuchengEntity){
 	try {
			BeanUtils.copyProperties(this, shengchanliuchengEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
