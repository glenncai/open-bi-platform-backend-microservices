package com.glenncai.openbiplatform.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glenncai.openbiplatform.user.mapper.UserMapper;
import com.glenncai.openbiplatform.user.model.entity.User;
import com.glenncai.openbiplatform.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * User service implementation
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




