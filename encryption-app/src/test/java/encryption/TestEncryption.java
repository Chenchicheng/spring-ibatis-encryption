package encryption;

import cn.hutool.core.lang.Assert;
import encryption.mapper.UserMapper;
import encryption.po.User;
import encryption.po.UserExample;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenchicheng
 * @date 2024/3/28
 */
@SpringBootTest(classes = EncryptApplication.class)
@MapperScan({"encryption.mapper"})
public class TestEncryption {

    @Resource
    UserMapper userMapper;

    @Test
    public void testAdd() {
        User user = new User();
        user.setAddress("testAddress");
        user.setPhone("15145121321");
        user.setName("kkkk");
        int id = userMapper.insert(user);

        User user1 = userMapper.selectByPrimaryKey((long) id);
        Assert.isTrue(user1.getName().equals(user.getName()));
        Assert.isTrue(user1.getPhone().equals(user.getPhone()));
        Assert.isTrue(user1.getAddress().equals(user.getAddress()));
    }


    @Test
    public void testAddBatch() {
        User user1 = new User();
        user1.setAddress("testAddress");
        user1.setPhone("15145121321");
        user1.setName("kkkk");

        User user2 = new User();
        user2.setAddress("testAddress2");
        user2.setPhone("15145121322");
        user2.setName("kkkk2");

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        userMapper.insertBatch(list);

        List<User> list1 = userMapper.selectByExample(new UserExample());
        Assert.isTrue(list1.stream().anyMatch(s -> s.getName().equals("kkkk2")));
    }

}
