package com.lifang.agentsm.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lifang.agentsm.utils.ArrayUtils;
import com.lifang.agentsm.utils.ReflectUtil;

import lombok.Data;


@Data
public class Pagination extends HashMap<String, Object>{
    private String pageNoAlias="pageNo";
    
    private String pageSizeAlias="pageSize";
    
    private String sortField;
    
    private String sortFieldAlias="sortField";
    
    /**
     * 可排序字段的集合，key为前端传的排序字段名称，value为对应sql语句中排序的字段名称
     */
    private Map<String,String> sortFieldMap = new HashMap<>();
    
    private String sortOrder="ASC";
    
    private String sortOrderAlias="sortOrder";
    
    /**
     * 当前页码
     */
    private Integer pageNo = 1;
    
    /**
     * 一页显示多少条数据
     */
    private Integer pageSize = 20;
    
    /**
     * 总条数
     */
    private Integer total;
    
    /**
     * 总页数
     */
    private Integer totalPage;
    
    
    
    
    public void setTotal(Integer total){
        this.total = total;
        this.setTotalPage(total%pageSize==0?total/pageSize:total/pageSize+1);
    }
    
    public Pagination(){}
    
    /**
     * @param parseObject 需要转换成pagination的参数的对象
     * Creates a new instance of Pagination.
     * @param parseObject
     */
    public Pagination(Object parseObject){
        this.parse(parseObject);
    }
    
    /**
     * 
     * Creates a new instance of Pagination.
     * @param pageSizeAlias pageSize的别名，用来针对不同前端框架，传值得属性名不同而设计
     * @param pageNoAlias pageNo的别名，用来针对不同前端框架，传值得属性名不同而设计
     */
    public Pagination(String pageSizeAlias,String pageNoAlias){
        this.pageSizeAlias = pageSizeAlias;
        this.pageNoAlias = pageNoAlias;
    }
    
    /**
     * 
     * Creates a new instance of Pagination.
     * @param parseObject 需要转换成pagination的参数的对象
     * @param pageSizeAlias pageSize的别名，用来针对不同前端框架，传值得属性名不同而设计
     * @param pageNoAlias pageNo的别名，用来针对不同前端框架，传值得属性名不同而设计
     */
    public Pagination(Object parseObject,String pageSizeAlias,String pageNoAlias){
        this.pageSizeAlias = pageSizeAlias;
        this.pageNoAlias = pageNoAlias;
        this.parse(parseObject);
    }
    
    public Pagination(String pageSizeAlias,String pageNoAlias,String sortFieldAlias,String sortOrderAlias){
        this.pageSizeAlias = pageSizeAlias;
        this.pageNoAlias = pageNoAlias;
        this.sortFieldAlias = sortFieldAlias;
        this.sortOrderAlias = sortOrderAlias;
    }
    
    public Pagination(Object parseObject,String pageSizeAlias,String pageNoAlias,String sortFieldAlias,String sortOrderAlias){
        this.pageSizeAlias = pageSizeAlias;
        this.pageNoAlias = pageNoAlias;
        this.sortFieldAlias = sortFieldAlias;
        this.sortOrderAlias = sortOrderAlias;
        this.parse(parseObject);
    }
    
    @SuppressWarnings("rawtypes")
    public Pagination parse(Object object){
        if(object instanceof Map){
            Map map = (Map) object;
            Set set = map.entrySet();
            Iterator iterator = set.iterator(); 
            while(iterator.hasNext()){
                Map.Entry entry = (Entry) iterator.next();
                this.put(entry.getKey().toString(),entry.getValue());
            }
        }else if(object instanceof CharSequence ||object instanceof Number || object instanceof Boolean || object instanceof Character || object instanceof Collection){
            throw new RuntimeException(object.getClass().getSimpleName()+"类型无法转换");
        }else{
            Class<?> clazz = object.getClass();
            List<Field> fields = ReflectUtil.getDeclaredFields(clazz);
            if(ArrayUtils.hasValue(fields)){
                for(Field field:fields){
                    String fieldName = field.getName();
                    String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                    try {
                        Method getter = clazz.getMethod(methodName);
                        Object object2 = getter.invoke(object);
                        this.put(fieldName,object2);
                        //}
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    }
    
    /**
     * 获取真正的排序字段
     * 功能描述:TODO(描述这个方法的作用)
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2015年2月5日      新建
     * </pre>
     *
     * @return
     */
    public String getRealSortField(){
        if(this.sortFieldMap.containsKey(this.sortField)){
            return this.sortFieldMap.get(this.sortField);
        }
        return null;
    }
    
    public Pagination put(String key,Object value){
        
        if(key.equals(this.getPageSizeAlias())){
            if(value!=null){
                this.pageSize = Integer.parseInt(value.toString());
                super.put("pageSize",value);
                return this;
            }
        }else if(key.equals(this.getPageNoAlias())){
            if(value!=null){
                this.pageNo = Integer.parseInt(value.toString());
                super.put("pageNo", value);
                return this;
            }
        }else if(key.equals(this.getSortOrderAlias())){
            if(value!=null){
                this.sortOrder = value.toString();
                super.put("sortOrder", value);
                return this;
            }
        }else if(key.equals(this.getSortFieldAlias())){
            if(value != null){
                this.sortField = value.toString();
                super.put("sortOrder", getRealSortField());
                return this;
            }
        }
        super.put(key, value);
        return this;
    }
    
    
    public void setSortField(String sortField){
        this.sortField = sortField;
        super.put("sortField", sortField);
    }
    
    public void setSortOrder(String sortOrder){
        this.sortOrder = sortOrder;
        super.put("sortOrder",sortOrder);
    }
    
    public void setPageNo(Integer pageNo){
        this.pageNo = pageNo;
        super.put("pageNo", pageNo);
    }
    
    public void setPageSize(Integer pageSize){
        this.pageSize = pageSize;
        super.put("pageSize",pageSize);
    }
    
    public Pagination putSortField(String frontField,String dbField){
        this.sortFieldMap.put(frontField, dbField);
        return this;
    }
    
}
