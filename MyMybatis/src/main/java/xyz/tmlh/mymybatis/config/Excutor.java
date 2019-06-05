package xyz.tmlh.mymybatis.config;

/**
 * 
 * <p>
 * Executor是一个执行器，负责SQL语句的生成和查询
 * </p>
 *
 * @author TianXin
 * @since 2019年6月5日下午5:41:14
 */
public interface Excutor {

    public <T> T query(String statement, Object parameter);

}