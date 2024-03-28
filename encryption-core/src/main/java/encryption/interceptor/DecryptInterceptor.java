package encryption.interceptor;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * @author chenchicheng
 * @date 2022/1/14
 */
@Component
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class DecryptInterceptor extends MybatisEncryption implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(EncryptInterceptor.class);


    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        if (resultObject instanceof List) {
            List<Object> resultList = (List<Object>) resultObject;
            decryptBatch(resultList);
        } else {
            decryptSingle(resultObject);
        }
        return resultObject;
    }

    private void decryptBatch(List<Object> records) {
        long start = System.currentTimeMillis();
        logger.debug("Decrypt Batch start...");
        if (records == null || records.isEmpty()) {
            return;
        }
        if (!sensitive(records.get(0))) {
            return;
        }
        super.decryptList(records);
        long end = System.currentTimeMillis();
        logger.debug("Decrypt Batch end..., spendTime:{}", end - start);
    }

    private void decryptSingle(Object result) {
        long start = System.currentTimeMillis();
        logger.debug("Decrypt Single start...");
        if (!sensitive(result)) {
            return;
        }
        super.decrypt(result);
        long end = System.currentTimeMillis();
        logger.debug("Decrypt Single end..., spendTime:{}", end - start);
    }
}
