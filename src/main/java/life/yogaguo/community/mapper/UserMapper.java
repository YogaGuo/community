package life.yogaguo.community.mapper;

import life.yogaguo.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public  interface UserMapper {
    /**
     *
     * @param user
     */
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) " +
            "value(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void saveUser(User user);

    /**
     *
     * @param token
     * @return
     */
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}
