package com.summit.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目名称：watf   
 * 类名称：CommonUtil   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:47:24    
 * @version
 */
public class CommonUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);
	private static Properties p;
	public static String getIdsFromJson(String json) {
		if (json == null || json.trim().length() == 0) {
			return "";
		}
		JSONArray jsonArr = JSONArray.fromObject(json);
		String ids = "";
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jobj = jsonArr.getJSONObject(i);
			ids += "'" + jobj.getString("id") + "'";
			if (i < jsonArr.size() - 1) {
				ids += ",";
			}
		}

		return ids;
	}
	
	
	
	/**
	 * 获得需要删除的数据-
	 * @param String UI传递的id列表  json格式
	 * @param Class	需要操作的实体Bean的Class
	 * 
	 * @return	List  生成的对象列表
	 * 
	 * */
	public static List<?> getBeanListFromJsonIds(String json, Class<?> clz) {
		
		if (json == null || json.trim().length() == 0) {
			return null;
		}
		JSONArray jsonArr = JSONArray.fromObject(json);
		List<?> list = (List<?>) JSONArray.toCollection(jsonArr,clz);

		return list;
	}
	
	/**
	 * 拷贝实体bean的属性到其他对象，同时，对日期进行了格式化
	 * 日期格式   yyyy-MM-dd HH:mm:ss:SS
	 * 
	 * @param Object source 源对象
	 * @param Object target	拷贝的目标对象
	 * 
	 * 
	 * */
	public static void copyBeanPropertiesToVo(Object source, Object target) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Method[] method = source.getClass().getDeclaredMethods();

			for (int i = 0; i < method.length; i++) {
				String methodName = method[i].getName();
				if (!methodName.startsWith("get")) {
					continue;
				}
				String setMethod = "s" + methodName.substring(1);
				Method targetSetMethod = getMethodByName(target, setMethod);
				String fieldName = methodName.substring(4);
				fieldName = methodName.substring(3, 4).toLowerCase()
						+ fieldName;

				Object srcValue = method[i].invoke(source, null);
				
				if (srcValue != null && targetSetMethod != null) {
					if (srcValue instanceof java.lang.String) {
						targetSetMethod.invoke(target, srcValue);
					} else if (srcValue instanceof java.lang.Integer) {
						targetSetMethod.invoke(target, srcValue.toString());
					} else if (srcValue instanceof java.lang.Float) {
						targetSetMethod.invoke(target, srcValue.toString());
					} else if (srcValue instanceof java.lang.Double) {
						targetSetMethod.invoke(target, srcValue.toString());
					} else if (srcValue instanceof java.lang.Long) {
						targetSetMethod.invoke(target, srcValue.toString());
					} else if (srcValue instanceof java.util.Date) {
						targetSetMethod.invoke(target, f.format(srcValue));
						
					} else if (srcValue instanceof java.sql.Timestamp) {
						targetSetMethod.invoke(target, f1.format(srcValue));
						
					}else if (srcValue instanceof java.lang.Boolean) {
						
						targetSetMethod.invoke(target,
								((java.lang.Boolean) srcValue).booleanValue());
					} else {
						
						targetSetMethod.invoke(target, srcValue);
						
					}
				} 
				else {
					continue;
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
			// do nothing
		}

	}
	
	/*
	 * javabean自动赋值给另一个bean
	 */
	public static Object coverBean2Bean(Object from, Object to) {   
		       Class<? extends Object> fClass = from.getClass();   
		        Class<? extends Object> tClass = to.getClass();   
		        // 拿to所有属性（如果有继承，父类属性拿不到）   
		        Field[] cFields = tClass.getDeclaredFields();   
		        try {   
		            for (Field field : cFields) {   
		               String cKey = field.getName();   
		               // 确定第一个字母大写   
		               cKey = cKey.substring(0, 1).toUpperCase() + cKey.substring(1);   
	  
		               Method fMethod;   
		               Object fValue;   
		               try {   
		                    fMethod = fClass.getMethod("get" + cKey);// public方法   
		                    fValue = fMethod.invoke(from);// 取getfKey的值   
		               } catch (Exception e) {   
		                 // 如果from没有此属性的get方法，跳过   
		                 continue;   
		               }   
		  
		                try {   
	                    // 用fMethod.getReturnType()，而不用fValue.getClass()   
		                   // 为了保证get方法时，参数类型是基本类型而传入对象时会找不到方法   
		                    Method cMethod = tClass.getMethod("set" + cKey, fMethod.getReturnType());   
	                   cMethod.invoke(to, fValue);   
		                } catch (Exception e) {   
		                    // 如果to没有此属性set方法，跳过   
		                    continue;   
		                }   
		            }   
		        } catch (Exception e) {   
		            e.printStackTrace();   
		        }   
		  
		        return to;   
		    }   

	/**
	 * 获得对象的指定方法
	 * 
	 * @param Object source 源对象
	 * @param String methodName	方法名
	 * 
	 * @return Method 
	 * */
	public static Method getMethodByName(Object obj, String methodName) {
		Method[] targetMethod = obj.getClass().getDeclaredMethods();
		Method method = null;
		for (Method m : targetMethod) {
			if (m.getName().equalsIgnoreCase(methodName)) {
				method = m;
				break;
			}
		}

		/*if(log.isInfoEnabled())
		{
			if (!isFind) {
				log.info("不能找到指定的方法 " + methodName);
			}
		}*/
		return method;
	}

	
	 public static Properties getSystemConfig()
   {
		if(p == null)
		{
			p = new Properties();
			try {
				p.load(CommonUtil.class.getClassLoader().getResourceAsStream("rmsSystemConfig.properties"));
				if(log.isDebugEnabled()){
					log.debug("加载系统配置文件 rmsSystemConfig.properties 成功！");
				}

			} catch (Exception e) {
				log.error("加载 rmsSystemConfig.properties 失败！");
			}
		}
		

		return p;
	}
	
	 
	//正则验证
	public static boolean regexString(String regex ,String str){
		boolean flag = true;
		Pattern pat = Pattern.compile(regex.trim());   
		Matcher mat = pat.matcher(str.trim());
		flag = mat.find();
		log.info("正则验证:("+regex+")-("+str+")="+flag);
		return flag;
	}
	
	
	/**
     * 获得唯一Id(通用)
     * @return
     */
	public static String getKey(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
		return uuid;
	}
	
	public interface TreeNodeClass {
		/**
		 * 获取该节点ID
		 * 
		 * @return
		 */
		public String getNodeId();

		/**
		 * 获取该节点父ID
		 * 
		 * @return
		 */
		public String getNodePid();

		/**
		 * 获取该节点显示文字
		 * 
		 * @return
		 */
		public String getNodeText();

		/**
		 * 获取节点的附加属性
		 */
		public Object getNodeData();

		/**
		 * 获取节点是否有复选框
		 * 
		 * @return
		 */
		public Boolean getChecked();

		/**
		 * 是否是叶子节点，如果不做强制要求，返回null即可。主要用于父节点没有子节点，但是还是要保持父节点的状态。
		 * 
		 * @return
		 * @author zzy
		 * @time 2016-10-9 下午04:11:44
		 * 
		 */
		public Boolean getLeaf();
	}
	
	private static <T extends TreeNodeClass> TreeNode creatTreeNode(
			TreeNodeClass treeNodeClass, Iterable<T> data, String pId,
			boolean expanded) {
		TreeNode treeNode = new TreeNode();
		treeNode.setId(treeNodeClass.getNodeId());
		treeNode.setText(treeNodeClass.getNodeText());
		treeNode.setData(treeNodeClass.getNodeData());
		treeNode.setChecked(treeNodeClass.getChecked());
		treeNode.setExpanded(expanded);
		List<TreeNode> children = creatTreeNode(data, treeNode.getId(),expanded);
//		if (treeNodeClass.getLeaf() != null) {
//			treeNode.setLeaf(treeNodeClass.getLeaf());
//		} else {
		if (collectionNotNull(children)) {
			treeNode.setLeaf(false);
		}
//		}
		if (collectionNotNull(children)) {
			treeNode.setChildren(children);
		}
		return treeNode;
	}
	
	public static <T extends Collection<?>> boolean collectionNotNull(T c) {
		if (c != null && c.size() > 0) {
			return true;
		}
		return false;
	}
	
	public static <T extends TreeNodeClass> List<TreeNode> creatTreeNode(
			Iterable<T> data, String pId, boolean expanded) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		if (data == null) {
			return list;
		}
		if (pId == null) {
			for (TreeNodeClass treeNodeClass : data) {
				if (treeNodeClass.getNodePid() == null) {
					list.add(creatTreeNode(treeNodeClass, data, pId, expanded));
				}
			}

		} else {
			for (TreeNodeClass treeNodeClass : data) {
				if (pId.equals(treeNodeClass.getNodePid())) {
					list.add(creatTreeNode(treeNodeClass, data, pId, expanded));
				}
			}
		}
		return list;
	}
	
	/**  
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1  
	 * @param String s 需要得到长度的字符串  
	 * @return int 得到的字符串长度  
	 */   
	public static int length(String s) {  
		if (s == null)  
			return 0;  //return 0;
		char[] c = s.toCharArray();  
		int len = 0;  
		for (int i = 0; i < c.length; i++) {  
			len++;  
			//if((c[i]>=65&&i<=90)||(c[i]>=97&&c[i]<=122))  
			if(((c[i]>='a'&&c[i]<='z')||(c[i]>='A'&&c[i]<='Z'))){
			}else if(Character.isLetter(c[i])) {  //汉字字符用Character.isLetter()
				len++;  
			}  
		} 
		return len;
	}  
	
	/**
	 * 填充字符串实现字符串左对齐
	 * @param s
	 * @param len
	 * @return
	 */
	public static String padWhitespaceLeft(String s, int len) {
	    return String.format("%1$" + len + "s", s);
	}

	/**
	 * 填充字符串实现字符串右对齐
	 * @param s
	 * @param len
	 * @return
	 */
	public static String padWhitespaceRight(String s, int len) {
	    return String.format("%1$-" + len + "s", s);
	}
	/**
	 * 截取字符串月日
	 * @param s
	 * @param len
	 * @return
	 */
	public static String subDatestr(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			int d1 = Integer.parseInt(dateStr.substring(5, 7));
			int d2 = Integer.parseInt(dateStr.substring(8, 10));
			return d1+"月"+d2+"日";
			
		}
		else
		{
			return dateStr;
		}
	}
	/**
	 * 截取字符串的日
	 * @param s
	 * @param len
	 * @return
	 */
	public static String subDateStr1(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			int d2 = Integer.parseInt(dateStr.substring(8, 10));
			return d2+"";
		}
		return "";
	}
	
	public static String subDateStr2(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			int d1 = Integer.parseInt(dateStr.substring(5, 7));
			int d2 = Integer.parseInt(dateStr.substring(8, 10));
			return d1+"-"+d2;
			
		}
		else
		{
			return dateStr;
		}
	}

}
