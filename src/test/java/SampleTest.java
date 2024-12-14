import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.goodtown.MainApplication;
import com.goodtown.mapper.UserMapper;
import com.goodtown.pojo.User;
import com.goodtown.utils.MD5Util;
import com.goodtown.service.UserService;

import java.util.List;

@SpringBootTest(classes = MainApplication.class ) //springboot下测试环境注解
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        User user = new User();
        user.setBpwd("131112");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        //更新password
        int count=userMapper.update(user,updateWrapper.eq("username","jrcd"));
        if(count>0){
            System.out.println("更新成功");
        }else{
            userMapper.insert(user);
        }      
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper.like("username", "cd"));
        userList.forEach(System.out::println);
        System.out.println(MD5Util.encrypt("admin"));
    }

    @Test
    public void testRegist() {
        User user = new User();
        user.setBname("jrcd");
        user.setBpwd("RandomPwd123");
        user.setUname("RandomUser");
        user.setCtype("IDCard");
        user.setIdno("123456789012345678");
        user.setPhoneno("13800138000");
        user.setDescription("随机生成的用户");
        user.setUserlvl("Normal");
        userService.regist(user);
        System.out.println("注册成功");
    }
}
