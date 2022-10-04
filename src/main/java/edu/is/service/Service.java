package edu.is.service;

import edu.is.entity.Requirements;
import edu.is.entity.User;
import edu.is.mapper.TestMapper;
import edu.is.util.ExcelUtil;
import edu.is.util.LkUtil;
import edu.is.util.Mapper;
import edu.is.util.SqlFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Service {

    //校验用户信息
    public boolean findUser(String username, String password) {
        System.out.println("debug");
        if (username != null && password != null) {
            try (SqlSession session = SqlFactoryUtil.openSqlSession(true)) {
                TestMapper mapper = session.getMapper(TestMapper.class);
                if (mapper.selectUser(new User()
                        .setUsername(username)
                        .setPassword(password)
                ) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    //
    public boolean pingDanTest(InputStream inputStream) throws Exception {

        LinkedList<double[]> sheet = ExcelUtil.readExcel(inputStream);
        LinkedList<Requirements> requirements = Mapper.changeDoubleArrToRequirements(sheet);

        Collections.sort(requirements);
        List<double[]> result ;
        result = LkUtil.pingDan(requirements);
        ExcelUtil.writeExcel("output.xlsx", result,requirements);
        return true;
    }

}
