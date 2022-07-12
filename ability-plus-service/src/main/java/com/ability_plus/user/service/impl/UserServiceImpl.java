package com.ability_plus.user.service.impl;

import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.VO.ProjectDetailInfoVO;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.PO.UserProfileEditPO;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.UserPOJO;
import com.ability_plus.user.entity.VO.UserLoginVO;
import com.ability_plus.user.entity.VO.UserProfileEditVO;
import com.ability_plus.user.entity.VO.UserProfileVO;
import com.ability_plus.user.mapper.UserMapper;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.JwtUtil;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Text;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    static final long ONE_HOUR = 60*60*1000;

    @Override
    public Integer register(String fullName, String email, String password, String extraData, Boolean isCompany) throws Exception {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", email);
        List<User> users = this.list(wrapper);
        if (users.size()>=1){
            logger.warn("account: "+email+" exist");
            throw new CheckException("user exist");
        }

        User user = new User();
        user.setAccount(email);
        //TODO MD5加密
        user.setPassword(password);
        user.setFullName(fullName);
        user.setIsCompany(isCompany);
        user.setExtraData(extraData);

        this.save(user);
        return user.getId();
    }

    @Override
    public UserLoginVO login(String email, String password) throws Exception {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", email);
        wrapper.eq("password", password);
        List<User> users = this.list(wrapper);

        if (users.size() < 1) {
            logger.warn("account: " + email + " not found");
            throw new CheckException("user not found");
        }
        User user = users.get(0);
        HashMap<String, String> map = new HashMap<>();
        map.put("id",user.getId().toString());
        map.put("account",user.getAccount());
        map.put("isCompany",user.getIsCompany().toString());
        String token = JwtUtil.getToken(map);
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUserName(user.getFullName());
        userLoginVO.setAccessToken(token);
        userLoginVO.setIsCompany(user.getIsCompany());
        return userLoginVO;
    }

    @Override
    public UserProfileVO getProfileInfo(Integer id) {
        UserProfileVO userProfileVO = new UserProfileVO();
        User user = this.getById(id);
        CheckUtils.assertNotNull(user,"user not exists");
        BeanUtils.copyProperties(user,userProfileVO);

        return userProfileVO;
    }

    @Override
    public UserProfileEditVO getProfileEditInfo(Integer id) {
        UserProfileEditVO userProfileEditVO = new UserProfileEditVO();
        User user = this.getById(id);
        CheckUtils.assertNotNull(user,"user not exists");
        BeanUtils.copyProperties(user,userProfileEditVO);

        return userProfileEditVO;
    }

    @Override
    public void editProfile(UserProfileEditPO po){
        Integer userID=po.getUserId();
        String userName=po.getUserName();
        String extraData=po.getExtraData().toString();
        String password=po.getPassword();
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",userID);
        User user=new User();
        user.setExtraData(extraData);
        user.setPassword(password);
        user.setFullName(userName);
        this.update(user,updateWrapper);
    }

    @Override
    public void deleteAccount(Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        this.remove(queryWrapper);
    }
}
