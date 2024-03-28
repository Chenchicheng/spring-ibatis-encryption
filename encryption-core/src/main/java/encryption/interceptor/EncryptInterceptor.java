package encryption.interceptor;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.List;


/**
 * @author chenchicheng
 * @date 2022/1/14
 */
@Component
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class)})
public class EncryptInterceptor extends MybatisEncryption implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(EncryptInterceptor.class);

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
        parameterField.setAccessible(true);
        Object parameterObject = parameterField.get(parameterHandler);
        if (parameterObject == null) {
            return invocation.proceed();
        }
        if (parameterObject instanceof MapperMethod.ParamMap) {
            // see org.apache.ibatis.reflection.ParamNameResolver.class
            MapperMethod.ParamMap map = (MapperMethod.ParamMap) parameterObject;
            if (!map.isEmpty() && map.containsKey("collection")) {
                Object list = map.get("collection");
                if (list == null) {
                    return invocation.proceed();
                }
                List<Object> objectList = (List<Object>) list;
                return encryptBatch(invocation, objectList);
            }
        }
        return encryptSingle(invocation, parameterObject);
    }

    private Object encryptBatch(Invocation invocation, List<Object> list) throws Throwable {
        Object object = list.stream().findFirst().orElse(null);
        if (!sensitive(object)) {
            return invocation.proceed();
        }
        long start = System.currentTimeMillis();
        logger.debug("Encrypt Batch start...");
        encryptList(list);
        Object proceed = invocation.proceed();
        decryptList(list);
        long end = System.currentTimeMillis();
        logger.debug("Encrypt Batch end..., spendTime:{}", end - start);
        return proceed;
    }

    private Object encryptSingle(Invocation invocation, Object parameterObject) throws Throwable {
        if (!sensitive(parameterObject)) {
            return invocation.proceed();
        }
        long start = System.currentTimeMillis();
        logger.debug("Encrypt Single start...");
        encrypt(parameterObject);
        Object proceed = invocation.proceed();
        decrypt(parameterObject);
        long end = System.currentTimeMillis();
        logger.debug("Encrypt Single end..., spendTime:{}", end - start);
        return proceed;
    }





}
