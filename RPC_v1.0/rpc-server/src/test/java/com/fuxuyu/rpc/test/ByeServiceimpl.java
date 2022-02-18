package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.ByeService;
import com.fuxuyu.rpc.entity.annotation.Service;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/18 13:45
 */
@Service
public class ByeServiceimpl implements ByeService {
    @Override
    public String bye(String name) {
        return "bye," + name;
    }
}
